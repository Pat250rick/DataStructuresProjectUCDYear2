package tree;

import interfaces.Entry;
import interfaces.Position;

import java.io.IOException;

public class BalanceableBinaryTree<K, V> extends LinkedBinaryTree<Entry<K, V>> {

    // positional-based methods related to aux field
    public int getAux(Position<Entry<K, V>> p) {
        BSTNode<Entry<K, V>> node = (BSTNode<Entry<K, V>>)p;
        return node.getAux();
    }

    public void setAux(Position<Entry<K, V>> p, int value) {
        BSTNode<Entry<K, V>> node = (BSTNode<Entry<K, V>>)p;
        node.setAux(value);
    }

    public Position<Entry<K,V>> addRoot(Entry<K,V> e) throws IllegalStateException {
        if (root != null) {
            throw new IllegalStateException("Tree already has a root");
        }
        root = new BSTNode<>(e,null,null,null);
        size++;
        return root;
    }

    /**
     * Creates a new left child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the left of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a left child
     */
    public Position<Entry<K,V>> addLeft(Position<Entry<K,V>> p, Entry<K,V> e) throws IllegalArgumentException {
        if(left(p) != null){
            //throw new IllegalArgumentException("Position already has value.");
            ((BSTNode<Entry<K,V>>) p).setLeft(new BSTNode<Entry<K,V>>(e, (BSTNode<Entry<K,V>>) p,null,null));
            size++;
        }else{
            ((BSTNode<Entry<K,V>>) p).setLeft(new BSTNode<Entry<K,V>>(e, (BSTNode<Entry<K,V>>) p,null,null));
            size++;
        }
        return left(p);
    }

    /**
     * Creates a new right child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the right of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p already has a right child
     */
    public Position<Entry<K,V>> addRight(Position<Entry<K,V>> p, Entry<K,V> e) throws IllegalArgumentException {
        if(right(p) != null){
            //throw new IllegalArgumentException("Position already has value.");
            ((BSTNode<Entry<K,V>>) p).setRight(new BSTNode<Entry<K,V>>(e, (BSTNode<Entry<K,V>>) p,null,null));
            size++;
        }else{
            ((BSTNode<Entry<K,V>>) p).setRight(new BSTNode<Entry<K,V>>(e, (BSTNode<Entry<K,V>>) p,null,null));
            size++;
        }
        return right(p);
    }


    protected BSTNode<Entry<K, V>> createNode(Entry<K, V> e, BSTNode<Entry<K, V>> parent, BSTNode<Entry<K, V>> left, BSTNode<Entry<K, V>> right) {
        return new BSTNode<>(e, parent, left, right);
    }

    /**
     * Relinks a parent node with its oriented child node.
     */
    void relink(BSTNode<Entry<K, V>> parent, BSTNode<Entry<K, V>> child, boolean makeLeftChild) {
        if (makeLeftChild) {
            parent.setLeft(child);
        } else {
            parent.setRight(child);
        }
        if (child != null) {
            child.setParent(parent);
        }
    }

    /**
     * Rotates Position p above its parent. Switches between these configurations,
     * depending on whether p is a or p is b.
     *
     * <pre>
     *          b                  a
     *         / \                / \
     *        a  t2             t0   b
     *       / \                    / \
     *      t0  t1                 t1  t2
     * </pre>
     * <p>
     * Caller should ensure that p is not the root.
     */
    public void rotate(Position<Entry<K, V>> p) {
        // Validate p and retrieve its parent.
        BSTNode<Entry<K, V>> x = (BSTNode<Entry<K, V>>)validate(p);
        BSTNode<Entry<K, V>> y = (BSTNode<Entry<K, V>>)x.getParent();
        if (y == null)
            throw new IllegalArgumentException("Cannot rotate the root");

        Node<Entry<K, V>> z = y.getParent(); // may be null

        // Determine if x is a left or right child.
        if (x == y.getLeft()) {
            // Right rotation:
            relink(y, (BSTNode<Entry<K, V>>)x.getRight(), true); // t1 becomes y’s left subtree
            x.setRight(y);
        } else {
            // Left rotation:
            relink(y, (BSTNode<Entry<K, V>>)x.getLeft(), false); // t1 becomes y’s right subtree
            x.setLeft(y);
        }
        // x becomes child of z (which might be null, meaning x is new root)
        x.setParent(z);
        if (z == null) {
            root = x;  // x is now the root
        } else if (y == z.getLeft()) {
            z.setLeft(x);
        } else {
            z.setRight(x);
        }
        y.setParent(x);
    }

    /**
     * Returns the Position that becomes the root of the restructured subtree.
     * <p>
     * Assumes the nodes are in one of the following configurations:
     *
     * <pre>
     *     z=a                 z=c           z=a               z=c
     *    /  \                /  \          /  \              /  \
     *   t0  y=b             y=b  t3       t0   y=c          y=a  t3
     *      /  \            /  \               /  \         /  \
     *     t1  x=c         x=a  t2            x=b  t3      t0   x=b
     *        /  \        /  \               /  \              /  \
     *       t2  t3      t0  t1             t1  t2            t1  t2
     * </pre>
     * <p>
     * The subtree will be restructured so that the node with key b becomes its
     * root.
     *
     * <pre>
     *           b
     *         /   \
     *       a       c
     *      / \     / \
     *     t0  t1  t2  t3
     * </pre>
     * <p>
     * Caller should ensure that x has a grandparent.
     */
    public Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) throws IOException {
        // Let x be a child, y its parent, and z its grandparent.
        Node<Entry<K, V>> xNode = validate(x);
        Node<Entry<K, V>> y = xNode.getParent();
        Node<Entry<K, V>> z = y.getParent();
        if (z == null)
            throw new IllegalArgumentException("Cannot restructure without a grandparent");

        /*
         * If x and y are both on the same side of their parents (both left or both right),
         * then a single rotation at y brings y to the top. Otherwise, perform a double rotation.
         */
        if ((xNode == y.getRight() && y == z.getRight()) ||
                (xNode == y.getLeft() && y == z.getLeft())) {
            // Single rotation (rotate y)
            rotate(y);
            return y;
        } else {
            // Double rotation: rotate x, then rotate x again.
            rotate(x);  // first rotation makes x the child of z
            rotate(x);  // second rotation makes x the root of the subtree
            return x;
        }
    }

    protected static class BSTNode<E> extends Node<E> {
        int aux = 0;

        BSTNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
            super(e, parent, leftChild, rightChild);
        }

        public int getAux() {
            return aux;
        }

        public void setAux(int value) {
            aux = value;
        }

        public String toString() {
            String s = this.getElement() == null ? "" : this.getElement().toString();
            //return s + "/" + height(this);
            return s;
        }
    }
}