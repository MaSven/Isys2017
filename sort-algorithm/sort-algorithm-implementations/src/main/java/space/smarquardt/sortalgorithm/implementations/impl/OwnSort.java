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

	public OwnSort(final int cylces, final double[] data) {
		super(cylces, data);
		this.tree = new RedBlackTree(this);
		this.valuesToSort = new ArrayDeque<>();
	}

	@Override
	public double[] getResult() {
		final double[] sorted = this.tree.getSorted();
		final Double[] notSorted = this.valuesToSort.toArray(new Double[this.valuesToSort.size()]);
		double[] both = new double[0];
		both = ArrayUtils.insert(0, both, sorted);
		both = ArrayUtils.insert(sorted.length, both, ArrayUtils.toPrimitive(notSorted));
		return both;
	}

	@Override
	public String nameOfAlgorithm() {
		return "OwnSort";
	}

	@Override
	public void sort() {
		this.valuesToSort.addAll(Arrays.stream(this.data).boxed().collect(Collectors.toList()));
		while (!this.valuesToSort.isEmpty()) {
			final double d = this.valuesToSort.remove();
			this.tree.insert(d);
		}
		System.out.println("sort finished");
	}

}
