package org.ta4j.core.indicators;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;

public class ATRTrailingIndicator extends AbstractIndicator<Num>{
    private final ClosePriceIndicator closePriceIndicator;
    private final ATRIndicator atrIndicator;
    Num previousStopLoss = numOf(0.0);
    private final int barCount;
    private final double multiplier;

    protected ATRTrailingIndicator(BarSeries series, int barCount, double multiplier) {
        super(series);
        this.barCount = barCount;
        this.multiplier = multiplier;
        atrIndicator = new ATRIndicator(series, barCount);
        closePriceIndicator = new ClosePriceIndicator(series);
    }

    @Override public Num getValue(int index) {
        if(index < getUnstableBars()){
            return numOf(0.0);
        }
        Num atrValue = atrIndicator.getValue(index);
        Num currentStopLoss = atrValue.multipliedBy(numOf(multiplier));
        Num currentClosePrice = closePriceIndicator.getValue(index);
        Num previousClosePrice = closePriceIndicator.getValue(index - 1);

        if(currentClosePrice.isGreaterThan(previousStopLoss) && previousClosePrice.isGreaterThan(previousStopLoss)) {
            previousStopLoss = previousStopLoss.max(currentClosePrice.minus(currentStopLoss));
        } else if (currentClosePrice.isLessThan(previousStopLoss) && previousClosePrice.isLessThan(previousStopLoss)) {
            previousStopLoss =  previousStopLoss.min(currentClosePrice.plus(currentStopLoss));
        } else if (currentClosePrice.isGreaterThan(previousStopLoss) ) {
            previousStopLoss = currentClosePrice.minus(currentStopLoss);
        } else {
            previousStopLoss = currentClosePrice.plus(currentStopLoss);
        }
        return previousStopLoss;
    }

    @Override public int getUnstableBars() {
        return getBarCount();
    }

    public int getBarCount() {
        return barCount;
    }
}
