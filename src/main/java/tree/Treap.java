package tree;

import interfaces.Position;
import interfaces.Entry;
import org.junit.jupiter.api.Test;
import tree.BalanceableBinaryTree.*;
import utils.MapEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Treap<K extends Comparable<K>> extends TreeMap<K, Integer> {
    Random rand;

    public Treap() {
        super();
        rand = new Random();
    }

    public Treap(Comparator<K> comp) {
        super(comp);
        rand = new Random();
    }

    public Treap(int a) {
        rand = new Random(a);
    }

    @Override
    public Integer put(K key) throws IllegalArgumentException, IOException {
        return super.put(key, rand.nextInt());
    }

    @Override
    protected void rebalanceInsert(Position<Entry<K, Integer>> p) throws IOException {
        BalanceableBinaryTree.BSTNode<Entry<K, Integer>> node = (BSTNode<Entry<K, Integer>>) p;

        while (node.getParent() != null && node.getElement().getValue() > ((BSTNode<Entry<K, Integer>>) node.getParent()).getElement().getValue()) {
            rotate(p);
        }
    }

    public Iterable<Position<Entry<K, Integer>>> treapSort(ArrayList<K> arr) throws IllegalArgumentException, IOException {
        Treap<K> map = new Treap<>();

        for (K k : arr) {
            map.put(k);
        }

        // Create a list to hold the sorted elements (in-order traversal)
        List<Position<Entry<K, Integer>>> sortedPositions = new ArrayList<>();
        for (Position<Entry<K, Integer>> pos : map.tree.inorder()) {
            sortedPositions.add(pos);
        }

        // Return the sorted positions
        return sortedPositions;
    }
}
