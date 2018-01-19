package space.smarquardt.sortalgorithm.implementations.impl;

import java.util.Set;
import java.util.stream.Stream;

import space.smarquardt.sortalgorithm.implementations.impl.bintree.BinTree;

public class OwnSort extends AbstractSort {

	private final BinTree tree;

	private double notActuallyData;

	public OwnSort(final int cylces, final double[] data) {
		super(cylces, data);
		this.tree = new BinTree(this);
	}

	@Override
	public double[] getResult() {
		final Set<Double> values = this.tree.getValuesSoerted();
		for (final double d : this.data) {
			values.add(d);
		}
		values.remove(this.notActuallyData);
		final Double[] array = values.toArray(new Double[values.size()]);
		return Stream.of(array).mapToDouble(Double::doubleValue).toArray();
	}

	@Override
	public String nameOfAlgorithm() {
		return "OwnSort";
	}

	@Override
	public void sort() {
		final double firstNode = 100;
		this.notActuallyData = firstNode;
		this.tree.addValue(this.notActuallyData);
		for (final double d : this.data) {
			this.tree.addValue(d);
		}
	}

}
