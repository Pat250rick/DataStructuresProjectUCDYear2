package tree;

import interfaces.Entry;
import interfaces.Position;

import java.io.IOException;
import java.util.Comparator;


/**
 * An implementation of a sorted map using an AVL tree.
 */

public class AVLTreeMap<K extends Comparable<K>, V> extends TreeMap<K, V> {

	/** Constructs an empty map using the natural ordering of keys. */
	public AVLTreeMap() {
		super();
	}

	/**
	 * Constructs an empty map using the given comparator to order keys.
	 * 
	 * @param comp comparator defining the order of keys in the map
	 */
	public AVLTreeMap(Comparator<K> comp) {
		super(comp);
	}

	public static void main(String [] args) throws IOException {
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
		Integer[] arr = new Integer[]{35, 26, 15};//, 24, 33, 4, 12, 1, 23, 21, 2, 5};

		for (Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		map.remove(26);
		//System.out.println(map.toBinaryTreeString());
	}

	/** Returns the height of the given tree position. */
	protected int height(Position<Entry<K, V>> p) {
		return tree.getAux(p);
	}

	/**
	 * Recomputes the height of the given position based on its children's heights.
	 */
	protected void recomputeHeight(Position<Entry<K, V>> p) {
		Position<Entry<K, V>> left = tree.left(p);
		Position<Entry<K, V>> right = tree.right(p);
		// If a child is external (null element), its height is considered -1.
		int leftHeight = (left.getElement() == null ? -1 : height(left));
		int rightHeight = (right.getElement() == null ? -1 : height(right));
		int newHeight = 1 + Math.max(leftHeight, rightHeight);
		tree.setAux(p, newHeight);
	}

	/** Returns whether a position has balance factor between -1 and 1 inclusive. */
	protected boolean isBalanced(Position<Entry<K, V>> p) {
		Position<Entry<K, V>> left = tree.left(p);
		Position<Entry<K, V>> right = tree.right(p);
		int leftHeight = (left.getElement() == null ? -1 : height(left));
		int rightHeight = (right.getElement() == null ? -1 : height(right));
		return Math.abs(leftHeight - rightHeight) <= 1;
	}

	/** Returns a child of p with height no smaller than that of the other child. */
	protected Position<Entry<K, V>> tallerChild(Position<Entry<K, V>> p) {
		Position<Entry<K, V>> left = tree.left(p);
		Position<Entry<K, V>> right = tree.right(p);
		int leftHeight = (left.getElement() == null ? -1 : height(left));
		int rightHeight = (right.getElement() == null ? -1 : height(right));
		if (leftHeight >= rightHeight)
			return left;
		else
			return right;
	}

	/**
	 * Utility used to rebalance after an insert or removal operation. This
	 * traverses the path upward from p, performing a trinode restructuring when
	 * imbalance is found, continuing until balance is restored.
	 */
	protected void rebalance(Position<Entry<K, V>> p) throws IOException {
		while (p != null) {
			// Recompute the height at p.
			recomputeHeight(p);
			// If p is unbalanced, perform a restructure around its taller grandchild.
			if (!isBalanced(p)) {
				// Determine which child is taller.
				Position<Entry<K, V>> child = tallerChild(p);
				// And then which grandchild of p is taller (based on child).
				Position<Entry<K, V>> grandchild = tallerChild(child);
				// Perform restructure: trinode restructuring will rebalance the subtree.
				p = restructure(grandchild);
				// After restructuring, update the heights of the children of the new subtree root.
				recomputeHeight(tree.left(p));
				recomputeHeight(tree.right(p));
				recomputeHeight(p);
			}
			// Move upward to the parent.
			p = tree.parent(p);
		}
	}

	/** Overrides the TreeMap rebalancing hook that is called after an insertion. */
	@Override
	protected void rebalanceInsert(Position<Entry<K, V>> p) throws IOException {
		rebalance(p);
	}

	/** Overrides the TreeMap rebalancing hook that is called after a deletion. */
	@Override
	protected void rebalanceDelete(Position<Entry<K, V>> p) throws IOException {
		rebalance(p);
	}

}
