package space.smarquardt.sortalgorithm.implementations.impl.bintree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;

import space.smarquardt.sortalgorithm.implementations.impl.DoubleComparator;

public class RedBlackTree implements Tree {
	/**
	 * Letzter hinzugefügter Knoten
	 */
	private Node lastAdded;
	/**
	 * Erster Knoten des Baumes
	 */
	private Optional<Node> root = Optional.empty();

	private final DoubleComparator comparator;

	/**
	 *
	 */
	public RedBlackTree(final DoubleComparator comparator) {
		super();
		this.comparator = comparator;
	}

	@Override
	public void insert(final double value) {
		if (!this.root.isPresent() || Node.nodeIsNil(this.root)) {
			this.root = Optional.of(Node.createRootNode(value));
			this.lastAdded = this.root.get();
		} else {
			Optional<Node> nOptional = this.root;
			while (nOptional.isPresent()) {
				final Node node = nOptional.get();
				if (this.comparator.isLessThan(value, node.getValue())) {
					if (node.getLeft().isPresent()) {
						nOptional = node.getLeft();
					} else {
						this.lastAdded = Node.getNewNodeDependentOfColour(node, value, true);
						break;
					}
				} else {
					if (node.getRight().isPresent()) {
						nOptional = node.getRight();
					} else {
						this.lastAdded = Node.getNewNodeDependentOfColour(node, value, false);
						break;
					}
				}
			}
		}
		if (this.lastAdded.getParent().isPresent()) {
			final Node localParent = this.lastAdded.getParent().get();
			if (!localParent.isBlack()) {
				this.redBlackInsert();
			}
		}

	}

	private void redBlackInsert() {
		Node problemNode = this.lastAdded;
		while (true) {
			if (problemNode == null) {
				break;
			}
			// Fall 1
			if (!problemNode.getParent().isPresent()) {
				this.root = Optional.of(problemNode);
				problemNode.setBlack(true);
				return;
			}
			// Fall 2
			if (problemNode.getParent().get().isBlack()) {
				return;
			}
			Optional<Node> grandParent = problemNode.getGrandParent();
			Optional<Node> uncle = problemNode.getUncle();
			Node parentOfProblem = problemNode.getParent().get();

			// Fall 3
			if (uncle.isPresent() && !uncle.get().isBlack()) {
				parentOfProblem.setBlack(true);
				uncle.get().setBlack(true);
				if (grandParent.isPresent()) {
					final Node gP = grandParent.get();
					gP.setBlack(false);
					problemNode = gP;
				}

				continue;
			}
			if ((problemNode == parentOfProblem.getRight().orElse(null)) && grandParent.isPresent()
					&& (parentOfProblem == grandParent.get().getLeft().orElse(null))) {
				this.leftRotate(parentOfProblem);
				problemNode = problemNode.getLeft().orElse(null);
				parentOfProblem = problemNode.getParent().orElse(null);
				grandParent = problemNode.getGrandParent();
				uncle = problemNode.getUncle();
			} else if ((problemNode == parentOfProblem.getLeft().orElse(null)) && grandParent.isPresent()
					&& (parentOfProblem == grandParent.get().getRight().orElse(null))) {
				this.rightRotate(parentOfProblem);
				problemNode = problemNode.getParent().orElse(null);
				parentOfProblem = problemNode.getParent().orElse(null);
				grandParent = problemNode.getGrandParent();
				uncle = problemNode.getUncle();
			}

			if ((parentOfProblem != null) && !parentOfProblem.isBlack() && uncle.isPresent() && uncle.get().isBlack()) {
				parentOfProblem.setBlack(true);
				grandParent.ifPresent(gP -> gP.setBlack(false));
				if ((problemNode == parentOfProblem.getLeft().orElse(null)) && grandParent.isPresent()
						&& (parentOfProblem == grandParent.get().getLeft().orElse(null))) {
					this.rightRotate(grandParent.get());
				} else if ((problemNode == parentOfProblem.getRight().orElse(null)) && grandParent.isPresent()
						&& (parentOfProblem == grandParent.get().getRight().orElse(null))) {
					this.leftRotate(grandParent.get());
				}
			}
			break;
		}
	}

	private void leftRotate(final Node n) {
		final Optional<Node> parentLocal = n.getParent();
		final Optional<Node> right = n.getRight();
		Optional<Node> mNode = Optional.empty();
		if (right.isPresent()) {
			mNode = right.get().getLeft();
			right.get().setLeft(n);
		}
		n.setParent(right);
		n.setRight(mNode.orElse(null));
		mNode.ifPresent(mN -> mN.setParent(Optional.of(n)));
		if (parentLocal.isPresent()) {
			if (n == parentLocal.get().getLeft().orElse(null)) {
				parentLocal.get().setLeft(right.orElse(null));
			} else if (n == parentLocal.get().getRight().orElse(null)) {
				parentLocal.get().setRight(right.orElse(null));
			} else {
				throw new RuntimeException("Knoten hat keinen Vater" + n.toString());
			}
			right.ifPresent(rN -> rN.setParent(parentLocal));
		} else {
			this.root = right;
			this.root.ifPresent(r -> r.setParent(null));
		}

	}

	private void rightRotate(final Node n) {

		final Optional<Node> parentLocal = n.getParent();
		final Optional<Node> left = n.getLeft();
		Optional<Node> mNode = Optional.empty();
		if (left.isPresent()) {
			mNode = left.get().getRight();
			left.get().setRight(n);
		}
		n.setParent(left);
		n.setLeft(mNode.orElse(null));
		mNode.ifPresent(mN -> mN.setParent(Optional.of(n)));
		if (parentLocal.isPresent()) {
			if (n == parentLocal.get().getLeft().orElse(null)) {
				parentLocal.get().setLeft(left.orElse(null));
			} else if (n == parentLocal.get().getRight().orElse(null)) {
				parentLocal.get().setRight(left.orElse(null));
			} else {
				throw new RuntimeException("Knoten hat keinen Vater" + n.toString());
			}
			left.ifPresent(rN -> rN.setParent(parentLocal));
		} else {
			this.root = left;
			this.root.ifPresent(r -> r.setParent(null));
		}
	}

	@Override
	public double[] getSorted() {
		if (!this.root.isPresent()) {
			return new double[0];
		}
		final List<Double> valuesSorted = new ArrayList<>();

		final Deque<Optional<Node>> nodesToTraverse = new ArrayDeque<>();
		Optional<Node> visitNode = this.root;
		while ((visitNode.isPresent()) || !nodesToTraverse.isEmpty()) {
			if (visitNode.isPresent()) {
				nodesToTraverse.push(visitNode);
				visitNode = visitNode.get().getLeft();
			} else {
				visitNode = nodesToTraverse.pop();
				visitNode.ifPresent(n -> valuesSorted.add(n.getValue()));
				visitNode = visitNode.get().getRight();
			}
		}
		return ArrayUtils.toPrimitive(valuesSorted.toArray(new Double[valuesSorted.size()]));
	}

	@Override
	public double[] getSortedWithout() {
		// TODO Auto-generated method stub
		return null;
	}

}
