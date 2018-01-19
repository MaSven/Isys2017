package space.smarquardt.sortalgorithm.implementations.impl.bintree;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import space.smarquardt.sortalgorithm.implementations.impl.DoubleComparator;

public class BinTree {
	/**
	 * Root knoten
	 */
	private Optional<Node> root;

	DoubleComparator comparator;

	public BinTree(final DoubleComparator comparator) {
		this.comparator = comparator;
		this.root = Optional.empty();
	}

	/**
	 * Fügt einen wert dem Baum hinzu
	 *
	 * @param value
	 */
	public void addValue(final double value) {
		if (this.root.isPresent()) {
			this.root.get().addValue(value);
		} else {
			this.root = Optional.of(new Node(value, this.comparator, 0, null));
		}
	}

	public Set<Double> getValuesSoerted() {
		final Set<Double> values = new LinkedHashSet<>();
		this.root.ifPresent(node -> values.addAll(node.getValuesInsertionOrder()));
		return values;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (this.root.isPresent()) {
			return this.root.get().toString();
		}
		return "";
	}

}
