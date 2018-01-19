package space.smarquardt.sortalgorithm.implementations.impl.bintree;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import space.smarquardt.sortalgorithm.implementations.impl.DoubleComparator;

/**
 * Knotenpunkt
 *
 * @author Sven Marquardt
 *
 */
public class Node {
	/**
	 * Value das gehalten wird
	 */
	private final double value;
	/**
	 * Zeiger auf den nächsten linken knoten. Liefert {@link Optional#empty()} wenn
	 * es keinen gibt
	 */
	private Optional<Node> left;
	/**
	 * Rechter Knoten
	 */
	private Optional<Node> right;

	private final DoubleComparator comparator;
	/**
	 * Höhe dieses Knotens
	 */
	private final int height;

	private int balance;
	/**
	 * Vater dieses Knotens
	 */
	private Optional<Node> parent;

	/**
	 * @param value
	 * @param next
	 */
	public Node(final double value, final DoubleComparator comparator, final int height, final Node parent) {
		super();
		this.value = value;
		this.left = Optional.empty();
		this.right = Optional.empty();
		this.comparator = comparator;
		this.height = height;
		this.parent = Optional.ofNullable(parent);
	}

	public void addLeft(final double value) {
		this.left = Optional.of(new Node(value, this.comparator, 0, this));
	}

	public void addRight(final double value) {
		this.right = Optional.of(new Node(value, this.comparator, 0, this));
	}

	/**
	 * @return the left
	 */
	public Optional<Node> getLeft() {
		return this.left;
	}

	/**
	 * @return the right
	 */
	public Optional<Node> getRight() {
		return this.right;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return this.value;
	}

	/**
	 * Füge einen knoten hinzu. Ermittel ob der neue wert größer oder kleiner ist
	 * und fügt diesen entsprechend hinzu
	 *
	 * @param value
	 *            Wert der hinzugefügt werden soll
	 */
	public void addValue(final double value) {
		if (this.comparator.isLessThan(value, this.value)) {
			if (this.left.isPresent()) {
				this.left.get().addValue(value);
			} else {
				this.addLeft(value);
			}
		} else {
			if (this.right.isPresent()) {
				this.right.get().addValue(value);
			} else {
				this.addRight(value);
			}
		}
		this.reBalance();
	}

	private void reBalance() {
		this.left.ifPresent(nL -> nL.reBalance());
		this.right.ifPresent(nR -> nR.reBalance());
		final int balance = this.getBalance();
		if (balance < -1) {
			// Linklastig
			this.rightRoation();
		} else if (balance > 1) {
			// Rechtslastig
			this.leftRoation();
		}
	}

	private void leftRoation() {
		if (!this.parent.isPresent()) {
			return;
		}
		final Node leftNode = this.left.orElse(null);
		final Node parentNode = this.parent.orElse(null);
		parentNode.right = Optional.ofNullable(leftNode);
		leftNode.parent = Optional.ofNullable(parentNode);
		this.left = Optional.ofNullable(parentNode);
		parentNode.parent = Optional.ofNullable(this);

	}

	private void rightRoation() {
		if (!this.parent.isPresent()) {
			return;
		}
		final Node rightNode = this.right.orElse(null);
		final Node parentNode = this.parent.orElse(null);
		parentNode.left = Optional.ofNullable(rightNode);
		rightNode.parent = Optional.ofNullable(parentNode);
		this.right = Optional.ofNullable(parentNode);
		this.parent = Optional.ofNullable(parentNode);

	}

	public Set<Double> getValuesInsertionOrder() {
		final Set<Double> values = new LinkedHashSet<>();
		this.left.ifPresent(node -> values.addAll(node.getValuesInsertionOrder()));
		values.add(this.value);
		this.right.ifPresent(node -> values.addAll(node.getValuesInsertionOrder()));
		return values;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(this.value);
		builder.append(" left: ");
		if (this.left.isPresent()) {
			builder.append(this.left.get().toString());
		}
		builder.append(" right: ");
		if (this.right.isPresent()) {
			builder.append(this.right.get().toString());
		}
		builder.append(" \n");
		return builder.toString();
	}

	private int getHeight() {
		return Math.max(this.getLeftHeight(), this.getRightHeight());
	}

	private int getBalance() {
		return this.getLeftHeight() - this.getRightHeight();
	}

	private int getLeftHeight() {
		int leftHeight = 0;
		if (this.left.isPresent()) {
			leftHeight++;
			leftHeight += this.left.get().getLeftHeight();
		}
		return leftHeight;
	}

	private int getRightHeight() {
		int leftHeight = 0;
		if (this.right.isPresent()) {
			leftHeight++;
			leftHeight += this.right.get().getLeftHeight();
		}
		return leftHeight;
	}
}
