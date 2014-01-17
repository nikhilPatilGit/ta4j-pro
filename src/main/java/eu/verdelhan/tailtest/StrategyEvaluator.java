package eu.verdelhan.tailtest;

import eu.verdelhan.tailtest.analysis.evaluator.Decision;

/**
 * A strategy evaluator.
 */
public interface StrategyEvaluator {

	/**
	 * Evaluate a set of strategies.
	 * (Apply all <code>strategies</code> in <code>series</code>)
	 * @param slicePosition the slice position on which evaluate all the strategies
	 * @return the best <code>Decision</code> according to a criterion
	 */
	Decision evaluate(int slicePosition);
}
