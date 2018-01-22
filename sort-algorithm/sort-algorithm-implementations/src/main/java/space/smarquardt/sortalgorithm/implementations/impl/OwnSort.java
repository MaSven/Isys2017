package space.smarquardt.sortalgorithm.implementations.impl;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import space.smarquardt.sortalgorithm.implementations.impl.bintree.RedBlackTree;
import space.smarquardt.sortalgorithm.implementations.impl.bintree.Tree;

public class OwnSort extends AbstractSort {

	private final Tree tree;

	private final Queue<Double> valuesToSort;

	private Double valueNotAdded;

	private boolean cycleExceededValueAdd = true;

	private double[] sortedValuesForShortCycle;

	public OwnSort(final int cylces, final double[] data) {
		super(cylces, data);
		this.tree = new RedBlackTree(this);
		this.valuesToSort = new ArrayDeque<>();
	}

	@Override
	public double[] getResult() {
		if (this.sortedValuesForShortCycle != null) {
			return this.sortedValuesForShortCycle.clone();
		}
		final double[] sorted = this.tree.getSorted();
		final Double[] notSorted = this.valuesToSort.toArray(new Double[this.valuesToSort.size()]);
		double[] both = new double[0];
		both = ArrayUtils.insert(0, both, sorted);

		if (this.isCycleExceeded() && this.cycleExceededValueAdd) {
			both = ArrayUtils.insert(sorted.length, both, this.valueNotAdded);

			both = ArrayUtils.insert(sorted.length + 1, both, ArrayUtils.toPrimitive(notSorted));

			this.cycleExceededValueAdd = false;
		} else {
			both = ArrayUtils.insert(sorted.length, both, ArrayUtils.toPrimitive(notSorted));
		}

		return both;
	}

	@Override
	public String nameOfAlgorithm() {
		return "OwnSort";
	}

	@Override
	public void sort() {
		this.valuesToSort.addAll(Arrays.stream(this.data).boxed().collect(Collectors.toList()));
		if (((this.data.length <= 100))) {
			this.sortedValuesForShortCycle = this.data.clone();
			this.shortSort();
			return;
		}
		while (!this.valuesToSort.isEmpty()) {
			final double d = this.valuesToSort.remove();
			this.valueNotAdded = d;
			this.tree.insert(d);
		}

	}

	private void shortSort() {
		final int l = this.sortedValuesForShortCycle.length - 1;
		final int gaps[] = { 1, 2, 7, 15, 31, 63 };
		for (final int gap : gaps) {
			for (int i = gap; i < l; i++) {
				int j;
				final double temp = this.sortedValuesForShortCycle[i];
				for (j = i; (j >= gap)
						&& (this.isGreaterThan.test(this.sortedValuesForShortCycle[j - gap], temp)); j -= gap) {
					this.sortedValuesForShortCycle[j] = this.sortedValuesForShortCycle[j - gap];
				}
				this.sortedValuesForShortCycle[j] = temp;
			}
		}
	}

}
