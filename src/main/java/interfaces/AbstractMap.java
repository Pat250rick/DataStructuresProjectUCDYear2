package interfaces;

import java.util.Iterator;

/**
 * An abstract base class to ease the implementation of the Map interface.
 *
 * The base class provides three means of support: 1) It provides an isEmpty
 * implementation based upon the abstract size() method. 2) It defines a
 * protected MapEntry class as a concrete implementation of the entry interface
 * 3) It provides implemenations of the keySet and values methods, based upon
 * use of a presumed implementation of the entrySet method.
 *
 */
public abstract class AbstractMap<K, V> implements Map<K, V> {

	/**
	 * Tests whether the map is empty.
	 * 
	 * @return true if the map is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}


	// Provides support for keySet() and values() methods, based upon
	// the entrySet() method that must be provided by subclasses

	// ---------------- nested KeyIterator class ----------------
	private class KeyIterator implements Iterator<K> {
		private final Iterator<Entry<K, V>> entries = entrySet().iterator(); // reuse entrySet

		public boolean hasNext() {
			return entries.hasNext();
		}

		public K next() {
			return entries.next().getKey();
		} // return key!

		public void remove() {
			throw new UnsupportedOperationException("remove not supported");
		}
	} // ----------- end of nested KeyIterator class -----------

	// ---------------- nested KeyIterable class ----------------
	private class KeyIterable implements Iterable<K> {
		public Iterator<K> iterator() {
			return new KeyIterator();
		}
	} // ----------- end of nested KeyIterable class -----------

	/**
	 * Returns an iterable collection of the keys contained in the map.
	 *
	 * @return iterable collection of the map's keys
	 */
	@Override
	public Iterable<K> keySet() {
		return new KeyIterable();
	}

	// ---------------- nested ValueIterator class ----------------
	private class ValueIterator implements Iterator<V> {
		private final Iterator<Entry<K, V>> entries = entrySet().iterator(); // reuse entrySet

		public boolean hasNext() {
			return entries.hasNext();
		}

		public V next() {
			return entries.next().getValue();
		} // return value!

		public void remove() {
			throw new UnsupportedOperationException("remove not supported");
		}
	} // ----------- end of nested ValueIterator class -----------

	// ---------------- nested ValueIterable class ----------------
	private class ValueIterable implements Iterable<V> {
		public Iterator<V> iterator() {
			return new ValueIterator();
		}
	} // ----------- end of nested ValueIterable class -----------

	/**
	 * Returns an iterable collection of the values contained in the map. Note that
	 * the same value will be given multiple times in the result if it is associated
	 * with multiple keys.
	 *
	 * @return iterable collection of the map's values
	 */
	@Override
	public Iterable<V> values() {
		return new ValueIterable();
	}
}
