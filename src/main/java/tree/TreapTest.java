package tree;

import interfaces.Entry;
import interfaces.Position;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreapTest {

    @Test
    public void testPut() throws IOException {
        Random rand = new Random(1);
        Treap<Integer> map = new Treap<>(1);

        Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

        for(Integer i : arr) {
            map.put(i);
        }

        System.out.println(map.tree.toBinaryTreeString());
        assertEquals("[1, 2, 4, 5, 12, 15, 21, 23, 24, 26, 33, 35]", map.tree.toString());
        assertEquals("[1, 2, 4, 5, 12, 15, 21, 23, 24, 26, 33, 35]", map.tree.inorder().toString());


        map.put(3);
        assertEquals("[1, 2, 3, 4, 5, 12, 15, 21, 23, 24, 26, 33, 35]", map.tree.inorder().toString());
    }

    @Test
    public void testRebalanceInsert() throws IOException {
        Random rand = new Random(1);
        Treap<Integer> map = new Treap<>(1);

        Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

        for(Integer i : arr) {
            map.put(i);
        }

        for(Position<Entry<Integer,Integer>> p : map.tree.preorder()){
            if(map.tree.left(p).getElement() != null){
                assertTrue(((BalanceableBinaryTree.BSTNode<Entry<Integer,Integer>>)p).getElement().getValue().compareTo(((BalanceableBinaryTree.BSTNode<Entry<Integer,Integer>>)(map.tree.left(p))).getElement().getValue() ) > 0);
            }
            if(map.tree.right(p).getElement() != null){
                assertTrue(((BalanceableBinaryTree.BSTNode<Entry<Integer,Integer>>)p).getElement().getValue().compareTo(((BalanceableBinaryTree.BSTNode<Entry<Integer,Integer>>)(map.tree.right(p))).getElement().getValue() ) > 0);
            }
        }
    }

    @Test
    public void testTreapSort() throws IOException {
        Random rand = new Random(1);
        Treap<Integer> map = new Treap<>(1);

        ArrayList<Integer> arr = new ArrayList<>();

        arr.addAll(Arrays.asList(35,26,15,24,33,4,12,1,23,21,2,5));
        Integer[] arr2 = new Integer[] {1,2,4,5,12,15,21,23,24,26,33,35};

        assertEquals(Arrays.toString(arr2), Arrays.toString(map.treapSort(arr).toArray()));
    }

}
