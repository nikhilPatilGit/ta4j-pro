package org.ta4j.core.indicators;

import org.junit.Test;
import org.ta4j.core.*;
import org.ta4j.core.mocks.MockBar;
import org.ta4j.core.num.Num;

import java.time.ZonedDateTime;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class ATRTrailingIndicatorTest extends AbstractIndicatorTest<BarSeries, Num> {
    private ExternalIndicatorTest xls;

    public ATRTrailingIndicatorTest(Function<Number, Num> numFunction) {
        super((data, params) -> new ATRTrailingIndicator(data, (int) params[0], (double) params[1]),
                numFunction);
        xls = new XLSIndicatorTest(this.getClass(), "ATR.xls", 5, numFunction);
    }

    @Test
    public void testGetValue() throws Exception {
        BarSeries series = new BaseBarSeriesBuilder().withNumTypeOf(numFunction).build();
        series.addBar(new MockBar(ZonedDateTime.now().minusSeconds(5), 0, 12, 15, 8, 0, 0, 0, numFunction));
        series.addBar(new MockBar(ZonedDateTime.now().minusSeconds(4), 0, 8, 11, 6, 0, 0, 0, numFunction));
        series.addBar(new MockBar(ZonedDateTime.now().minusSeconds(3), 0, 15, 17, 14, 0, 0, 0, numFunction));
        series.addBar(new MockBar(ZonedDateTime.now().minusSeconds(2), 0, 15, 17, 14, 0, 0, 0, numFunction));
        series.addBar(new MockBar(ZonedDateTime.now().minusSeconds(1), 0, 0, 0, 2, 0, 0, 0, numFunction));
        Indicator<Num> indicator = getIndicator(series, 3, 0.5);
        assertEquals(12.018518518518519d, indicator.getValue(3).doubleValue(), TestUtils.GENERAL_OFFSET);
    }
}