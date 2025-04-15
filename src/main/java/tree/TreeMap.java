package tree;

import utils.MapEntry;

import interfaces.Entry;
import interfaces.Position;
import tree.BalanceableBinaryTree.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * An implementation of a sorted map using a binary search tree.
 */

public class TreeMap<K extends Comparable<K>, V> extends AbstractSortedMap<K, V> {

	public BalanceableBinaryTree<K, V> tree = new BalanceableBinaryTree<>();

	/** Constructs an empty map using the natural ordering of keys. */
	public TreeMap() {
		super(); // the AbstractSortedMap constructor
		tree.addRoot(null); // create a sentinel leaf as root
	}

	/**
	 * Constructs an empty map using the given comparator to order keys.
	 *
	 * @param comp comparator defining the order of keys in the map
	 */
	public TreeMap(Comparator<K> comp) {
		super(comp); // the AbstractSortedMap constructor
		tree.addRoot(null); // create a sentinel leaf as root
	}

	/**
	 * Returns the number of entries in the map.
	 *
	 * @return number of entries in the map
	 */
	@Override
	public int size() {
		return (tree.size() - 1) / 2; // only internal nodes have entries
	}

	protected Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) throws IOException {
		return tree.restructure(x);
	}

	/**
	 * Rebalances the tree after an insertion of specified position. This version of
	 * the method does not do anything, but it can be overridden by subclasses.
	 *
	 * @param p the position which was recently inserted
	 */
	protected void rebalanceInsert(Position<Entry<K, V>> p) throws IOException {
		// LEAVE EMPTY
	}

	/**
	 * Rebalances the tree after a child of specified position has been removed.
	 * This version of the method does not do anything, but it can be overridden by
	 * subclasses.
	 *
	 * @param p the position of the sibling of the removed leaf
	 */
	protected void rebalanceDelete(Position<Entry<K, V>> p) throws IOException {
		// LEAVE EMPTY
	}

	/**
	 * Rebalances the tree after an access of specified position. This version of
	 * the method does not do anything, but it can be overridden by a subclasses.
	 *
	 * @param p the Position which was recently accessed (possibly a leaf)
	 */
	protected void rebalanceAccess(Position<Entry<K, V>> p) throws IOException {
		// LEAVE EMPTY
	}

	/** Utility used when inserting a new entry at a leaf of the tree */
	private void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry) {
		// p must be external (i.e. p.getElement() is null)
		if (p.getElement() != null)
			throw new IllegalArgumentException("Position is not external");
		// Overwrite the external node with the new entry (making it internal)
		tree.set(p, entry);
		// Create two new external leaves as children
		tree.addLeft(p, null);
		tree.addRight(p, null);
		// Rebalance if necessary (can call rebalanceInsert, even though default is empty)
		try {
			rebalanceInsert(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the position in p's subtree having the given key (or else the
	 * terminal leaf).
	 *
	 * @param key a target key
	 * @param p   a position of the tree serving as root of a subtree
	 * @return Position holding key, or last node reached during search
	 */
	private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> p, K key) {
		if (p.getElement() == null) { // external node
			return p;
		}
		int comp = compare(key, p.getElement().getKey());
		if (comp == 0) {
			return p; // found exact match
		} else if (comp < 0) {
			return treeSearch(tree.left(p), key);
		} else {
			return treeSearch(tree.right(p), key);
		}
	}

	/**
	 * Returns position with the minimal key in the subtree rooted at Position p.
	 *
	 * @param p a Position of the tree serving as root of a subtree
	 * @return Position with minimal key in subtree
	 */
	protected Position<Entry<K, V>> treeMin(Position<Entry<K, V>> p) {
		Position<Entry<K, V>> curr = p;
		while (tree.left(curr).getElement() != null)
			curr = tree.left(curr);
		return curr;
	}

	/**
	 * Returns the position with the maximum key in the subtree rooted at p.
	 *
	 * @param p a Position of the tree serving as root of a subtree
	 * @return Position with maximum key in subtree
	 */
	protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> p) {
		Position<Entry<K, V>> curr = p;
		while (tree.right(curr).getElement() != null)
			curr = tree.right(curr);
		return curr;
	}

	/**
	 * Returns the value associated with the specified key, or null if no such entry
	 * exists.
	 *
	 * @param key the key whose associated value is to be returned
	 * @return the associated value, or null if no such entry exists
	 */
	@Override
	public V get(K key) throws IllegalArgumentException, IOException {
		Position<Entry<K, V>> p = treeSearch(tree.root(), key);
		if (p.getElement() != null) // internal node with a valid entry
			return p.getElement().getValue();
		return null;
	}

	/**
	 * Associates the given value with the given key. If an entry with the key was
	 * already in the map, this replaced the previous value with the new one and
	 * returns the old value. Otherwise, a new entry is added and null is returned.
	 *
	 * @param key   key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 * @return the previous value associated with the key (or null, if no such
	 *         entry)
	 */
	@Override
	public V put(K key, V value) throws IllegalArgumentException, IOException {
		Entry<K, V> newEntry = new MapEntry<>(key, value);
		Position<Entry<K, V>> p = treeSearch(tree.root(), key);

		if (p.getElement() == null) {
			// p is an external node: expand it with the new entry.
			expandExternal(p, newEntry);
			return null;
		} else {
			// p is internal; update the entry.
			V old = p.getElement().getValue();
			tree.set(p, newEntry);
			rebalanceAccess(p);
			return old;
		}
	}

	/**
	 * Removes the entry with the specified key, if present, and returns its
	 * associated value. Otherwise does nothing and returns null.
	 *
	 * @param key the key whose entry is to be removed from the map
	 * @return the previous value associated with the removed key, or null if no
	 *         such entry exists
	 */
	@Override
	public V remove(K key) throws IllegalArgumentException, IOException {
		Position<Entry<K, V>> p = treeSearch(tree.root(), key);
		if (p.getElement() == null)
			return null;  // key not found
		V old = p.getElement().getValue();
		// If both children are internal, find successor to replace p's entry.
		if (tree.left(p).getElement() != null && tree.right(p).getElement() != null) {
			Position<Entry<K, V>> r = treeMin(tree.right(p));
			// Copy successor’s entry into p.
			tree.set(p, r.getElement());
			p = r; // Now remove the successor.
		}
		// Now p has at most one internal child.
		Position<Entry<K, V>> leaf;
		if (tree.left(p).getElement() == null)
			leaf = tree.left(p);
		else
			leaf = tree.right(p);
		// Let sibling be the other child.
		Position<Entry<K, V>> sibling;
		if (leaf == tree.left(p))
			sibling = tree.right(p);
		else
			sibling = tree.left(p);
		// Remove p by replacing it with its sibling.
		Position<Entry<K, V>> parent = tree.parent(p);
		if (parent == null) {
			// p is the root; make sibling the new root.
			tree.setRoot(sibling);
		} else {
			if (p == tree.left(parent))
				tree.relink((LinkedBinaryTree.Node<Entry<K, V>>)tree.parent(p), (LinkedBinaryTree.Node<Entry<K, V>>)sibling, true);
			else
				tree.relink((LinkedBinaryTree.Node<Entry<K, V>>)tree.parent(p), (LinkedBinaryTree.Node<Entry<K, V>>)sibling, false);
		}
		// p becomes external
		tree.set(p, null);
		// Optionally, rebalance after deletion.
		rebalanceDelete(parent);
		tree.size--;
		return old;
	}

	// additional behaviors of the SortedMap interface

	/**
	 * Returns the entry having the least key (or null if map is empty).
	 *
	 * @return entry with least key (or null if map is empty)
	 */
	@Override
	public Entry<K, V> firstEntry() {
		Position<Entry<K, V>> p = tree.root();
		if (p.getElement() == null)
			return null;
		return treeMin(p).getElement();
	}

	/**
	 * Returns the entry having the greatest key (or null if map is empty).
	 *
	 * @return entry with greatest key (or null if map is empty)
	 */
	@Override
	public Entry<K, V> lastEntry() {
		Position<Entry<K, V>> p = tree.root();
		if (p.getElement() == null)
			return null;
		return treeMax(p).getElement();
	}

	/**
	 * Returns the entry with least key greater than or equal to given key (or null
	 * if no such key exists).
	 *
	 * @return entry with least key greater than or equal to given (or null if no
	 *         such entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
		Position<Entry<K, V>> p = treeSearch(tree.root(), key);
		if (p.getElement() != null) {
			// Exact match found.
			return p.getElement();
		} else {
			// p is external. Walk up the tree until coming from left.
			while (p != tree.root() && p == tree.right(tree.parent(p))) {
				p = tree.parent(p);
			}
			p = tree.parent(p);
			return (p == null || p.getElement() == null) ? null : p.getElement();
		}
	}

	/**
	 * Returns the entry with greatest key less than or equal to given key (or null
	 * if no such key exists).
	 *
	 * @return entry with greatest key less than or equal to given (or null if no
	 *         such entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
		Position<Entry<K, V>> p = treeSearch(tree.root(), key);
		if (p.getElement() != null) {
			// Exact match.
			return p.getElement();
		} else {
			// p is external. Walk up until coming from right.
			while (p != tree.root() && p == tree.left(tree.parent(p))) {
				p = tree.parent(p);
			}
			p = tree.parent(p);
			return (p == null || p.getElement() == null) ? null : p.getElement();
		}
	}

	/**
	 * Returns the entry with greatest key strictly less than given key (or null if
	 * no such key exists).
	 *
	 * @return entry with greatest key strictly less than given (or null if no such
	 *         entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
		Position<Entry<K, V>> p = treeSearch(tree.root(), key);
		if (p.getElement() == null) {  // did not find exact match
			// Use floorEntry logic.
			while (p != tree.root() && p == tree.left(tree.parent(p))) {
				p = tree.parent(p);
			}
			p = tree.parent(p);
			return (p == null || p.getElement() == null) ? null : p.getElement();
		} else {
			// p is internal and holds key, so return its predecessor.
			if (tree.left(p).getElement() != null) {
				return treeMax(tree.left(p)).getElement();
			} else {
				// Go up until coming from right.
				while (p != tree.root() && p == tree.left(tree.parent(p))) {
					p = tree.parent(p);
				}
				p = tree.parent(p);
				return (p == null || p.getElement() == null) ? null : p.getElement();
			}
		}
	}

	/**
	 * Returns the entry with least key strictly greater than given key (or null if
	 * no such key exists).
	 *
	 * @return entry with least key strictly greater than given (or null if no such
	 *         entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
		Position<Entry<K, V>> p = treeSearch(tree.root(), key);
		if (p.getElement() == null) {
			// p is external; use ceiling logic.
			while (p != tree.root() && p == tree.right(tree.parent(p))) {
				p = tree.parent(p);
			}
			p = tree.parent(p);
			return (p == null || p.getElement() == null) ? null : p.getElement();
		} else {
			// p holds key; successor is the minimum in p's right subtree.
			if (tree.right(p).getElement() != null)
				return treeMin(tree.right(p)).getElement();
			else {
				// Walk up until coming from left.
				while (p != tree.root() && p == tree.right(tree.parent(p))) {
					p = tree.parent(p);
				}
				p = tree.parent(p);
				return (p == null || p.getElement() == null) ? null : p.getElement();
			}
		}
	}

	// Support for iteration

	/**
	 * Returns an iterable collection of all key-value entries of the map.
	 *
	 * @return iterable collection of the map's entries
	 */
	@Override
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K, V>> buffer = new ArrayList<>();
		// We perform an inorder traversal of the tree.
		inorderTraversal(tree.root(), buffer);
		return buffer;
	}

	private void inorderTraversal(Position<Entry<K, V>> p, ArrayList<Entry<K, V>> buffer) {
		if (p == null) return;
		if (p.getElement() == null)
			return; // external node; do not add
		inorderTraversal(tree.left(p), buffer);
		buffer.add(p.getElement());
		inorderTraversal(tree.right(p), buffer);
	}


	@Override
	public double loadFactor() {
		return 0;
	}

	@Override
	public int numCollisions() {
		return 0;
	}


	public String toString() {
		return tree.toString();
	}

	/**
	 * Returns an iterable containing all entries with keys in the range from
	 * <code>fromKey</code> inclusive to <code>toKey</code> exclusive.
	 *
	 * @return iterable with keys in desired range
	 * @throws IllegalArgumentException if <code>fromKey</code> or
	 *                                  <code>toKey</code> is not compatible with
	 *                                  the map
	 */
	@Override
	public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
		ArrayList<Entry<K, V>> buffer = new ArrayList<>();
		subMapRecurse(fromKey, toKey, tree.root(), buffer);
		return buffer;
	}

	// utility to fill subMap buffer recursively (while maintaining order)
	private void subMapRecurse(K fromKey, K toKey, Position<Entry<K, V>> p, ArrayList<Entry<K, V>> buffer) {
		if (p == null || p.getElement() == null)  // external node
			return;
		K key = p.getElement().getKey();
		if (compare(key, fromKey) >= 0)
			subMapRecurse(fromKey, toKey, tree.left(p), buffer);
		if (compare(key, fromKey) >= 0 && compare(key, toKey) < 0)
			buffer.add(p.getElement());
		if (compare(key, toKey) < 0)
			subMapRecurse(fromKey, toKey, tree.right(p), buffer);
	}
	
	protected void rotate(Position<Entry<K, V>> p) {
		tree.rotate(p);
	}



	public String toBinaryTreeString() {
		BinaryTreePrinter< Entry<K, V> > btp = new BinaryTreePrinter<>(this.tree);
		return btp.print();
	}

	public static void main(String[] args) throws IOException {
	}


}
