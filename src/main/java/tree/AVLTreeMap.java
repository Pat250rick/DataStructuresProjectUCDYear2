package tree;

import interfaces.Entry;
import interfaces.Position;

import java.io.IOException;
import java.util.Comparator;

public class AVLTreeMap<K extends Comparable<K>, V> extends TreeMap<K, V> {

	public AVLTreeMap() {
		super();
	}

	public AVLTreeMap(Comparator<K> comp) {
		super(comp);
	}

	public static void main(String[] args) throws IOException {
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
		Integer[] arr = new Integer[]{35, 26, 15}; // Adjusted input array size for testing

		for (Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		map.remove(26);
		//System.out.println(map.toBinaryTreeString());
	}

	protected int height(Position<Entry<K, V>> p) {
		return tree.getAux(p);
	}

	protected void recomputeHeight(Position<Entry<K, V>> p) {
		Position<Entry<K, V>> left = tree.left(p);
		Position<Entry<K, V>> right = tree.right(p);
		int leftHeight = (left.getElement() == null ? -1 : height(left));
		int rightHeight = (right.getElement() == null ? -1 : height(right));
		int newHeight = 1 + Math.max(leftHeight, rightHeight);
		tree.setAux(p, newHeight);
	}

	protected boolean isBalanced(Position<Entry<K, V>> p) {
		Position<Entry<K, V>> left = tree.left(p);
		Position<Entry<K, V>> right = tree.right(p);
		int leftHeight = (left.getElement() == null ? -1 : height(left));
		int rightHeight = (right.getElement() == null ? -1 : height(right));
		return Math.abs(leftHeight - rightHeight) <= 1;
	}

	protected Position<Entry<K, V>> tallerChild(Position<Entry<K, V>> p) {
		Position<Entry<K, V>> left = tree.left(p);
		Position<Entry<K, V>> right = tree.right(p);
		int leftHeight = (left.getElement() == null ? -1 : height(left));
		int rightHeight = (right.getElement() == null ? -1 : height(right));
		return leftHeight >= rightHeight ? left : right;
	}

	protected void rebalance(Position<Entry<K, V>> p) throws IOException {
		while (p != null) {
			recomputeHeight(p);
			if (!isBalanced(p)) {
				Position<Entry<K, V>> child = tallerChild(p);
				Position<Entry<K, V>> grandchild = tallerChild(child);
				p = restructure(grandchild);
				recomputeHeight(tree.left(p));
				recomputeHeight(tree.right(p));
				recomputeHeight(p);
			}
			p = tree.parent(p);
		}
	}

	@Override
	protected void rebalanceInsert(Position<Entry<K, V>> p) throws IOException {
		rebalance(p);
	}

	@Override
	protected void rebalanceDelete(Position<Entry<K, V>> p) throws IOException {
		rebalance(p);
	}
}
