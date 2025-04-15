package utils;

import interfaces.Entry;

public class MapEntry<K extends Comparable<K>, V> implements Entry<K, V> {
    private K k; // key
    private V v; // value

    public MapEntry(K key, V value) {
        k = key;
        v = value;
    }




    // public methods of the Entry interface
    public K getKey() {
        return k;
    }

    // utilities not exposed as part of the Entry interface
    protected void setKey(K key) {
        k = key;
    }

    public V getValue() {
        return v;
    }

    public V setValue(V value) {
        V old = v;
        v = value;
        return old;
    }

    /**
     * Returns string representation (for debugging only)
     */
    public String toString() {
        //return "<" + k + ", " + v + ">";
        return "" + k;
    }

    @Override
    public int compareTo(Entry<K, V> o) {
        return this.getKey().compareTo(o.getKey());
    }
} // ----------- end of nested MapEntry class -----------