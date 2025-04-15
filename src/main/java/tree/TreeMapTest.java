package tree;

import interfaces.Entry;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TreeMapTest {

	@Test
	void testSize() throws IOException {
		TreeMap<Integer, String> map = new TreeMap<>();
		assertEquals(0, map.size());
		map.put(1, "one");
		map.put(2, "two");
		assertEquals(2, map.size());
	}

	@Test
	void testRoot() throws IOException {
		TreeMap<Integer, String> map = new TreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		
		assertEquals(35, map.tree.root().getElement().getKey());
	}

	@Test
	void testGet() throws IOException {
		TreeMap<Integer, String> map = new TreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		assertEquals("15", map.get(15));
		assertEquals("24", map.get(24));
		assertEquals(null, map.get(-1));

	}

	@Test
	void testPut() throws IOException {
		TreeMap<Integer, String> map = new TreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		
		assertEquals("[1, 2, 4, 5, 12, 15, 21, 23, 24, 26, 33, 35]", map.toString());
	}

	@Test
	void testRemoveK() throws IOException {
		TreeMap<Integer, String> map = new TreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}

		assertEquals(12, map.size());
		assertEquals("26", map.remove(26));
		assertEquals(11, map.size());

	}

	@Test
	void testFirstEntry() throws IOException {
		//TreeMap<Integer, String> map = new TreeMap<>();
		TreeMap<Integer, String> map = new TreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		
		assertEquals(1, map.firstEntry().getKey());
	}

	@Test
	void testLastEntry() throws IOException {
		TreeMap<Integer, String> map = new TreeMap<>();
		//java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		
		assertEquals(35, map.lastEntry().getKey());
	}

	@Test
	void testCeilingEntry() throws IOException {
		TreeMap<Integer, String> map = new TreeMap<>();
		//java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		
		assertEquals(12, map.ceilingEntry(11).getKey());
		
		assertEquals(2, map.ceilingEntry(2).getKey());

	}

	@Test
	void testFloorEntry() throws IOException {
		TreeMap<Integer, String> map = new TreeMap<>();
		//java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		
		assertEquals(5, map.floorEntry(11).getKey());
		assertEquals(5, map.floorEntry(5).getKey());

	}

	@Test
	void testLowerEntry() throws IOException {
		TreeMap<Integer, String> map = new TreeMap<>();
		//java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}

		//System.out.println(map.toBinaryTreeString());
		assertEquals(23, map.lowerEntry(24).getKey());
		assertEquals(26, map.lowerEntry(31).getKey());
	}

	@Test
	void testHigherEntry() throws IOException {
		TreeMap<Integer, String> map = new TreeMap<>();
		//java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		
		assertEquals(12, map.higherEntry(11).getKey());
	}

	@Test
	void testEntrySet() throws IOException {
		TreeMap<Integer, String> map = new TreeMap<>();
		//java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		java.util.List<Entry<Integer, String>> esStr = new java.util.ArrayList<>();
		for(var e : map.entrySet()) {
			esStr.add(e);
		}
		assertEquals("[1, 2, 4, 5, 12, 15, 21, 23, 24, 26, 33, 35]", esStr.toString());
	}

	@Test
	void testToString() throws IOException {
		TreeMap<Integer, String> map = new TreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		assertEquals("[1, 2, 4, 5, 12, 15, 21, 23, 24, 26, 33, 35]", map.toString());
	}

	@Test
	void testSubMap() throws IOException {
		TreeMap<Integer, String> map = new TreeMap<>();
		//java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
				
		assertEquals("[12, 15, 21, 23, 24, 26, 33]", map.subMap(12, 34).toString());
	}

}
