package space.smarquardt.sortalgorithm.implementations.impl.bintree;

import java.util.Objects;
import java.util.Optional;

/**
 * Knotenpunkt
 *
 * @author Sven Marquardt
 *
 */
public class Node {
	/**
	 * Wert den dieser Knoten beinhaltet
	 */
	private double value;
	/**
	 * Vaterknoten dieses Knotens
	 */
	private Optional<Node> parent = Optional.empty();
	/**
	 * Linker kind knoten
	 */
	private Optional<Node> left = Optional.empty();
	/**
	 * Rechter kindknoten
	 */
	private Optional<Node> right = Optional.empty();
	/**
	 * Ist dieser Knoten ein schwarzer Knoten
	 */
	private Color color;

	enum Color {
		RED, BLACK
	}

	/**
	 * Ist dieser Knoten ein Nilknoten
	 */
	private boolean isNil;

	/**
	 * Nicht initialisieren
	 */
	private Node() {

	}

	static Node createRootNode(final double value) {
		final Node rootNode = new Node();
		rootNode.value = value;
		rootNode.color = Color.BLACK;
		rootNode.parent = Optional.empty();
		rootNode.left = Optional.empty();
		rootNode.right = Optional.empty();
		rootNode.isNil = false;
		return rootNode;
	}

	static Node createNilNode(final Node parentNode) {
		final Node nilNode = new Node();
		nilNode.value = 0;
		nilNode.color = Color.BLACK;
		nilNode.isNil = true;
		nilNode.parent = Optional.ofNullable(parentNode);
		nilNode.setLeft(null);
		nilNode.setRight(null);
		return nilNode;
	}

	/**
	 * Erhalte einen neuen Knoten entscheident der Übergebenene.</br>
	 * Der neue Knoten erhält den alten Knoten als Vater. Der Knoten wird außerdem
	 * dem Vaterknoten links oder rechts angehängt je nach dem ob <b>left</b> true
	 * oder false übergeben wurde. Der neue Knoten ist rot.
	 *
	 * @param parentNode
	 *            Vaterknoten des neuen Knotens
	 * @param value
	 *            Wert der in dem neuen Knoten gespeichert werden soll
	 * @param addLeft
	 *            Soll der neue Knoten dem Vaterknoten rechts oder links angeordnet
	 *            werden
	 * @return
	 */
	static Node getNewNodeDependentOfColour(final Node parentNode, final double value, final boolean addLeft) {
		if (Objects.isNull(parentNode)) {
			throw new IllegalArgumentException("Parent node darf nicht null sein");
		}
		final Node n = new Node();
		n.value = value;
		n.color = Color.RED;
		n.parent = Optional.of(parentNode);
		n.isNil = false;
		n.left = Optional.empty();
		n.right = Optional.empty();
		if (addLeft) {
			parentNode.setLeft(n);
		} else {
			parentNode.setRight(n);
		}
		return n;
	}

	/**
	 * @return the parent
	 */
	public Optional<Node> getParent() {
		return this.parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	protected void setParent(final Optional<Node> parent) {
		if (parent == null) {
			this.parent = Optional.empty();
		} else {
			this.parent = parent;
		}
	}

	/**
	 * @return the left
	 */
	public Optional<Node> getLeft() {
		return this.left;
	}

	/**
	 * @param left
	 *            the left to set
	 */
	protected void setLeft(final Node left) {
		this.left = Optional.ofNullable(left);
	}

	/**
	 * @return the right
	 */
	public Optional<Node> getRight() {
		return this.right;
	}

	/**
	 * @param right
	 *            the right to set
	 */
	protected void setRight(final Node right) {
		this.right = Optional.ofNullable(right);
	}

	/**
	 * @return the black
	 */
	public boolean isBlack() {
		return this.color == Color.BLACK;
	}

	/**
	 * @param black
	 *            the black to set
	 */
	protected void setBlack(final boolean black) {
		this.color = (black ? Color.BLACK : Color.RED);
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return this.value;
	}

	/**
	 * Erhalte den Großvater dieses Knotens
	 *
	 * @return {@link Optional} von Knoten. Wenn es keinen Großvater gibt ist es
	 *         empty
	 */
	protected Optional<Node> getGrandParent() {
		if (this.parent.isPresent()) {
			if (this.parent.get().getParent().isPresent()) {
				return this.parent.get().getParent();
			}
		}
		return Optional.empty();
	}

	/**
	 * Erhalte den Onkel dieses Knotens
	 *
	 * @return
	 */
	protected Optional<Node> getUncle() {
		final Node localParent;
		final Node grandParent;
		if (this.parent.isPresent()) {
			if (this.getGrandParent().isPresent()) {
				localParent = this.parent.get();
				grandParent = this.getGrandParent().get();
				if (localParent == grandParent.getLeft().orElse(null)) {
					return grandParent.getRight();
				} else {
					return grandParent.getLeft();
				}
			}
		}
		return Optional.empty();
	}

	public static boolean nodeIsNil(final Optional<Node> node) {
		if (node.isPresent()) {
			return Node.nodeIsNil(node.get());
		}
		return true;
	}

	public static boolean nodeIsNil(final Node node) {
		return node.isNil ? true : false;
	}

	public static boolean nodeNotNil(final Node node) {
		return node.isNil ? false : true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.color == null) ? 0 : this.color.hashCode());
		result = (prime * result) + (this.isNil ? 1231 : 1237);
		result = (prime * result) + ((this.left == null) ? 0 : this.left.hashCode());
		result = (prime * result) + ((this.parent == null) ? 0 : this.parent.hashCode());
		result = (prime * result) + ((this.right == null) ? 0 : this.right.hashCode());
		long temp;
		temp = Double.doubleToLongBits(this.value);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Node)) {
			return false;
		}
		final Node other = (Node) obj;
		if (this.color != other.color) {
			return false;
		}
		if (this.isNil != other.isNil) {
			return false;
		}
		if (this.left == null) {
			if (other.left != null) {
				return false;
			}
		} else if (!this.left.equals(other.left)) {
			return false;
		}
		if (this.parent == null) {
			if (other.parent != null) {
				return false;
			}
		} else if (!this.parent.equals(other.parent)) {
			return false;
		}
		if (this.right == null) {
			if (other.right != null) {
				return false;
			}
		} else if (!this.right.equals(other.right)) {
			return false;
		}
		if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Node [value=");
		builder.append(this.value);
		builder.append(", ");
		if (this.parent != null) {
			this.parent.ifPresent(p -> builder.append(", parent=[ value=").append(p.getValue()).append(", color=")
					.append(p.color).append("]"));
		}
		if (this.color != null) {
			builder.append("color=");
			builder.append(this.color);
			builder.append(", ");
		}
		builder.append(", isNil=");
		builder.append(this.isNil);
		this.left.ifPresent(l -> builder.append(", left=").append(l.getValue()));
		this.right.ifPresent(r -> builder.append(", right=").append(r.getValue()));
		builder.append("]");
		return builder.toString();
	}

}
