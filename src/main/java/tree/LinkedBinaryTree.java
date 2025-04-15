package tree;

import interfaces.BinaryTree;
import interfaces.Position;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static java.lang.Math.*;

/**
 * Concrete implementation of a binary tree using a node-based, linked
 * structure.
 */
public class LinkedBinaryTree<E extends Comparable<E>> implements BinaryTree<E> {


    protected Node<E> root = null; // root of the tree

    // LinkedBinaryTree instance variables
    protected int size = 0; // number of nodes in the tree

    public LinkedBinaryTree() {
    } // constructs an empty binary tree

    public static <E extends Comparable<E>> LinkedBinaryTree<E> makeRandom(int n, E[] arr) {
        // TODO
        return null;
    }

    public static LinkedBinaryTree<Integer> makeRandom(int n) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        Random rand = new Random();

        Integer [] arr = rand.ints(n).boxed().toArray(Integer[]::new);

        Arrays.sort(arr);
        int pivot = rand.nextInt(0,arr.length);

        bt.root = new Node<Integer>(arr[pivot],null,null,null);
        bt.root.setLeft(randomTree(bt.root,0,pivot,arr));
        bt.root.setRight(randomTree(bt.root,pivot+1,arr.length,arr));
        return bt;
    }

    public static <E> Node<E> randomTree(Node<E> parent, int first, int last, E[] arr) {
        if(first == last-1){
            return new Node<>(arr[first],parent,null,null);
        }else if(first == last){
            return null;
        }

        Random rand = new Random();
        int random = rand.nextInt(first,last);

        Node<E> node = new Node<>(arr[random],parent,null,null);
        node.setLeft(randomTree(node,first,random,arr));
        node.setRight(randomTree(node,random+1,last,arr));

        return node;
    }


    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

    }

    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the Position of p's sibling (or null if no sibling exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the sibling (or null if no sibling exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    public Position<E> sibling(Position<E> p) {
        // TODO
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    /**
     * Returns true if Position p has one or more children.
     *
     * @param p A valid Position within the tree
     * @return true if p has at least one child, false otherwise
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public boolean isInternal(Position<E> p) {
        return numChildren(p) > 0;
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given
     * snapshot using an inorder traversal
     *
     * @param p        Position serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    private void inorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        // TODO
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given
     * snapshot using a preorder traversal
     *
     * @param p        Position serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    private void preorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        // TODO
    }

    /**
     * Returns an iterable collection of positions of the tree, reported in preorder.
     *
     * @return iterable collection of the tree's positions in preorder
     */
    public Iterable<Position<E>> preorder() {
        // TODO
        return null;
    }

    /**
     * Returns an iterable collection of positions of the tree, reported in inorder.
     *
     * @return iterable collection of the tree's positions reported in inorder
     */
    public Iterable<Position<E>> inorder() {
        return positions();
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given
     * snapshot using a postorder traversal
     *
     * @param p        Position serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    private void postorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        // TODO
    }

    /**
     * Returns an iterable collection of positions of the tree, reported in postorder.
     *
     * @return iterable collection of the tree's positions in postorder
     */
    public Iterable<Position<E>> postorder() {
        // TODO
        return null;
    }

    public Iterable<Position<E>> positions() {
        ArrayList<Position<E>> iterable = new ArrayList<>();

        if(left(root) != null){
            iterable.addAll(positionsHelper(left(root)));
        }
        iterable.addLast(root);
        if(right(root) != null){
            iterable.addAll(positionsHelper(right(root)));
        }

        return iterable;
    }

    private ArrayList<Position<E>> positionsHelper(Position<E> node){
        ArrayList<Position<E>> iterable = new ArrayList<>();

        if(left(node) != null){
            iterable.addAll(positionsHelper(left(node)));
        }
        iterable.addLast(node);
        if(right(node) != null){
            iterable.addAll(positionsHelper(right(node)));
        }

        return iterable;
    }

    /**
     * Returns the number of levels separating Position p from the root.
     *
     * @param p A valid Position within the tree
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public int depth(Position<E> p) throws IllegalArgumentException {
        // TODO
        return 0;
    }

    /**
     * Returns the height of the tree.
     * <p>
     * Note: This implementation works, but runs in O(n^2) worst-case time.
     */
    private int heightBad() {
        // TODO
        return 0;
    }

    /**
     * Returns the height of the subtree rooted at Position p.
     *
     * @param p A valid Position within the tree
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public int height(Position<E> p) throws IllegalArgumentException {
        if(left(p) == null && right(p) == null){
            return 0;
        }else if(left(p) != null && right(p) != null){
            return max(height(left(p)),height(right(p))) + 1;
        }else if(left(p) != null){
            return height(left(p))+1;
        }else{
            return height(right(p))+1;
        }
    }

    /**
     * Returns true if Position p represents the root of the tree.
     *
     * @param p A valid Position within the tree
     * @return true if p is the root of the tree, false otherwise
     */
    public boolean isRoot(Position<E> p) {
        return p == root();
    }
    // nonpublic utility

    /**
     * Returns true if Position p does not have any children.
     *
     * @param p A valid Position within the tree
     * @return true if p has zero children, false otherwise
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public boolean isExternal(Position<E> p) {
        // TODO
        return false;
    }

    /**
     * Returns an iterable collection of the Positions representing p's children.
     *
     * @param p A valid Position within the tree
     * @return iterable collection of the Positions of p's children
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public Iterable<Position<E>> children(Position<E> p) {
        // TODO
        return null;
    }

    /**
     * Returns the number of children of Position p.
     *
     * @param p A valid Position within the tree
     * @return number of children of Position p
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public int numChildren(Position<E> p) {
        // TODO
        return 0;
    }

    // Function to find minimum value node in a given BST
    private Node<E> findMinimum(Node<E> n) {
        // TODO
        return null;
    }

    // Function to find minimum value node in a given BST
    private Node<E> findMaximum(Node<E> n) {
        // TODO
        return null;
    }

    // Recursive function to find an inorder successor
    private Node<E> inorderSuccessor(Node<E> node, Node<E> succ, E key) {
        // TODO
        return null;

    }

    private Node<E> inorderPredecessor(Node<E> node, Node<E> pred, E key) {
        // TODO
        return null;
    }

    public Position<E> inorderSuccessor(E key) {
        return inorderSuccessor(root, null, key);
    }

    public Position<E> inorderPredecessor(E key) {
        return inorderPredecessor(root, null, key);
    }


    /**
     * Returns an iterable collection of positions of the tree in breadth-first order.
     *
     * @return iterable collection of the tree's positions in breadth-first order
     */
    public Iterable<Position<E>> breadthfirst() {
        // TODO
        return null;
    }

    public void construct(E[] inorder, E[] preorder) {
        // TODO
    }

    private Node<E> construct_tree(E[] inorder, E[] preorder, int pStart, int pEnd, int iStart, int iEnd) {
        // TODO
        return null;
    }


    /**
     * Factory function to create a new node storing element e.
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    /**
     * Verifies that a Position belongs to the appropriate class, and is not one
     * that has been previously removed. Note that our current implementation does
     * not actually verify that the position belongs to this particular list
     * instance.
     *
     * @param p a Position (that should belong to this tree)
     * @return the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node<E> node)) throw new IllegalArgumentException("Not valid position type");
        // safe cast
        if (node.getParent() == node) // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     *
     * @return root Position of the tree (or null if tree is empty)
     */
    public Position<E> root() {
        return root;
    }

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getParent();
    }

    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getLeft();
    }

    // update methods supported by this class

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getRight();
    }

    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
         root = new Node<E>(e,null,null,null);
         size++;
        return root;
    }

    /*
     * Create a detached node!
     */
    public Position<E> add(E e, Position<E> parent, Position<E> left, Position<E> right) {
        Node<E> newNode = new Node<>(e,(Node<E>)parent,(Node<E>)left,(Node<E>)right);
        return newNode;
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
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        if(left(p) != null){
            throw new IllegalArgumentException("Position already has value.");
        }else{
            ((Node<E>) p).setLeft(new Node<E>(e, (Node<E>) p,null,null));
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
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        if(right(p) != null){
            throw new IllegalArgumentException("Position already has value.");
        }else{
            ((Node<E>) p).setRight(new Node<E>(e, (Node<E>) p,null,null));
            size++;
        }
        return right(p);
    }

    /**
     * Replaces the element at Position p with element e and returns the replaced
     * element.
     *
     * @param p the relevant Position
     * @param e the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        p.setElement(e);
        return null;
    }

    public void setRoot(Position<E> e) throws IllegalArgumentException {
        root = (Node<E>)e;
    }

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     *
     * @param p the relevant Position
     * @return element that was removed
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p has two children.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        E out;
        if(left(p) != null && right(p) == null){
            out = p.getElement();
            p = left(p);
            size--;
            return out;
        } else if (left(p) == null && right(p) != null) {
            out = p.getElement();
            p = right(p);
            size--;
            return out;
        } else if (left(p) == null && right(p) == null) {
            out = p.getElement();
            p = null ;
            size--;
            return out;
        }else{
            throw new IllegalArgumentException("Cant park that there mate");
        }
    }

    public String toString() {
        return positions().toString();
    }

    public void createLevelOrder(ArrayList<E> l) {
        root = createLevelOrderHelper(l, root, 0);
    }

    private Node<E> createLevelOrderHelper(java.util.ArrayList<E> l, Node<E> p, int i) {
        // TODO
        return null;
    }

    public void createLevelOrder(E[] arr) {
        root = createLevelOrderHelper(arr, root, 0);
    }

    private Node<E> createLevelOrderHelper(E[] arr, Node<E> parent, int i) {
        if(arr.length == 0 ){
            return null;
        }
        int depth = 1;
        int num = 1;
        ArrayList<Position<E>> nodes = new ArrayList<>();
        Node<E> header = new Node<>(arr[0],parent,null,null);
        //Node<E> nodePointer = header;
        nodes.addFirst(header);
        int l;
        while(nodes.size() < arr.length){
            for(int x = 0; x < pow(2,depth) && nodes.size() < arr.length; x++, num++){
                l = (x/2)+(int)pow(2,depth-1)-1;

                if(x % 2 == 0){
                    addLeft(nodes.get(l),arr[num]);
                    nodes.addLast(left(nodes.get(l)));
                }else{
                    addRight(nodes.get(l),arr[num]);
                    nodes.addLast(right(nodes.get(l)));
                }
            }
            depth++;
        }

        return header;
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<E> btp = new BinaryTreePrinter<>(this);
        return btp.print();
    }



    /*
     * Nested static class for a binary tree node.
     */
    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
            element = e;
            left = l;
            right = r;
            parent = p;
        }

        // accessor
        public E getElement() {
            return element;
        }

        // modifiers
        public void setElement(E e) {
            element = e;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> n) {
            left = n;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> n) {
            right = n;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> n) {
            parent = n;
        }

        public String toString() {
            // (e)
            StringBuilder sb = new StringBuilder();
            if (element == null) {
                sb.append("\u29B0");
            } else {
                sb.append(element);
            }
            // sb.append(" l:").append(left.element).append(" r:").append(right.element);
            // sb.append();
            return sb.toString();
        }
    }

    /* This class adapts the iteration produced by positions() to return elements. */
    private class ElementIterator implements Iterator<E> {
        Iterator<Position<E>> posIterator = positions().iterator();

        public boolean hasNext() {
            return posIterator.hasNext();
        }

        public E next() {
            return posIterator.next().getElement();
        }

        public void remove() {
            posIterator.remove();
        }
    }

}
