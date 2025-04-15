
package tree;

import interfaces.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinkedBinaryTreeTest {

    @Test
    void testSize() {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<Integer>();
        Position<Integer> root = bt.addRoot(1);
        assertEquals(1, bt.size());

        Position<Integer> l = bt.addLeft(root, 2);

        bt.remove(bt.root());
        assertEquals(1, bt.size());
    }

    @Test
    void testAddRoot() {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<Integer>();

        Integer c = Integer.parseInt("0");
        bt.addRoot(c);
        assertEquals(c, bt.root().getElement(), "root not added correctly");
    }

    @Test
    void testAddLeft() {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<Integer>();

        Integer c = Integer.parseInt("0");
        bt.addRoot(c);
        bt.addLeft(bt.root(), 1);
        assertEquals(1, bt.left(bt.root()).getElement());
    }

    @Test
    void testAddRight() {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<Integer>();

        Integer c = Integer.parseInt("0");
        bt.addRoot(c);
        bt.addRight(bt.root(), 1);
        assertEquals(1, bt.right(bt.root()).getElement());
    }

    @Test
    void testRemove() {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<Integer>();

        Integer c = Integer.parseInt("0");
        bt.addRoot(c);
        bt.addRight(bt.root(), 1);
        Integer old = bt.remove(bt.right(bt.root()));
        assertEquals(old, 1);
        assertEquals(1, bt.size());
    }

    @Test
    void testToString() {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<Integer>();

        Integer [] arr = new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12};
        bt.createLevelOrder(arr);
        assertEquals("[8, 4, 9, 2, 10, 5, 11, 1, 12, 6, 3, 7]", bt.toString());
    }

    @Test
    void testCreateLevelOrder() {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<Integer>();

        Integer [] arr = new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12};
        bt.createLevelOrder(arr);
        System.out.println(bt.toString());
        assertEquals("[8, 4, 9, 2, 10, 5, 11, 1, 12, 6, 3, 7]", bt.toString());
    }

    @Test
    void testStructure() {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<Integer>();

        Integer [] arr = new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12};
        bt.createLevelOrder(arr);

        System.out.println(bt.toBinaryTreeString());
        for(Position<Integer> p : bt.positions()) {
            LinkedBinaryTree.Node<Integer> p_node = bt.validate(p);
            // check the child/parent pointer is correct in both directions
//			System.out.println("p: " + p_node.getElement() + ", "
//					+ (p_node.getLeft() != null) + ", "
//					+ (p_node.getLeft() != null && p_node.getLeft().getParent() == p_node)
//			);
            if(p_node.getLeft() != null )
                assertTrue(p_node.getLeft().getParent() == p_node);

            if(p_node.getRight() != null)
                assertTrue(p_node.getRight().getParent() == p_node);
        }
    }

    @Test
    void testStructure2() {
        int n = 500;
        LinkedBinaryTree<Integer> bt = LinkedBinaryTree.makeRandom(n);

        System.out.println(bt.toBinaryTreeString());

        for(Position<Integer> p : bt.positions()) {
            LinkedBinaryTree.Node<Integer> p_node = bt.validate(p);
            // check the child/parent pointer is correct in both directions
//			System.out.println("p: " + p_node.getElement() + ", "
//					+ (p_node.getLeft() != null) + ", "
//					+ (p_node.getLeft() != null && p_node.getLeft().getParent() == p_node)
            //);
            if(p_node.getLeft() != null )
                assertTrue(p_node.getLeft().getParent() == p_node);

            if(p_node.getRight() != null)
                assertTrue(p_node.getRight().getParent() == p_node);
        }
    }

    @Test
    void testInorder() {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<Integer>();

        Integer [] arr = new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12};
        bt.createLevelOrder(arr);
        //System.out.println(bt.toString());
        assertEquals("[8, 4, 9, 2, 10, 5, 11, 1, 12, 6, 3, 7]", bt.inorder().toString());
    }

    @Test
    void testDepth() {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<Integer>();

        Integer [] arr = new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12};
        bt.createLevelOrder(arr);

        assertEquals(0, bt.depth(bt.root()));
    }

    @Test
    void testHeight() {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<Integer>();

        Integer [] arr = new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12};
        bt.createLevelOrder(arr);

        assertEquals(3, bt.height(bt.root()));
    }

}
