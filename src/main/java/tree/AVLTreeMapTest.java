package tree;

import interfaces.Entry;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AVLTreeMapTest {

	@Test
	void testGet() throws IOException {
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		assertEquals("15", map.get(15));
		assertEquals("24", map.get(24));
        assertNull(map.get(-1));
	}

	@Test
	void testPut() throws IOException {
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}

		Iterator<Integer> keys = map.keySet().iterator();
		List<Integer> list = new ArrayList<>();
		keys.forEachRemaining(list::add);

		assertEquals("[1, 2, 4, 5, 12, 15, 21, 23, 24, 26, 33, 35]", list.toString());
		assertEquals("[1, 2, 4, 5, 12, 15, 21, 23, 24, 26, 33, 35]", map.tree.inorder().toString());
		assertEquals("[15, 4, 1, 2, 12, 5, 26, 23, 21, 24, 35, 33]", map.tree.preorder().toString());


		map.put(3, "3");
		assertEquals("[1, 2, 3, 4, 5, 12, 15, 21, 23, 24, 26, 33, 35]", map.tree.inorder().toString());
		assertEquals("[15, 4, 2, 1, 3, 12, 5, 26, 23, 21, 24, 35, 33]", map.tree.preorder().toString());

	}

	@Test
	void testRemove2() throws IOException {
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
		//Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};
		Integer[] arr = new Integer[]{14, 11,17,7,12,53,4,8,13};

		for (Integer i : arr) {
			map.put(i, Integer.toString(i));
		}

		//System.out.println(map.tree.toBinaryTreeString());

		//assertEquals(12, map.size());
		//assertEquals("26", map.remove(26));
		//assertEquals(11, map.size());

		assertEquals("[4, 7, 8, 11, 12, 13, 14, 17, 53]", map.toString());

		map.remove(53);

		//System.out.println(map.tree.toBinaryTreeString());

		assertEquals("[4, 7, 8, 11, 12, 13, 14, 17]", map.toString());

	}

	@Test
	void testRemoveK() throws IOException {
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
		//Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};
		Integer[] arr = new Integer[]{14, 11,17,7,12,53,4,8,13};

		for (Integer i : arr) {
			map.put(i, Integer.toString(i));
		}

		//System.out.println(map.tree.toBinaryTreeString());

		//assertEquals(12, map.size());
		//assertEquals("26", map.remove(26));
		//assertEquals(11, map.size());

		assertEquals("[4, 7, 8, 11, 12, 13, 14, 17, 53]", map.toString());

		map.remove(53);

		//System.out.println(map.tree.toBinaryTreeString());

		assertEquals("[4, 7, 8, 11, 12, 13, 14, 17]", map.toString());

	}

	@Test
	void testRemoveKOld() throws IOException {
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
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
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		
		assertEquals(1, map.firstEntry().getKey());
	}

	@Test
	void testLastEntry() throws IOException {
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		
		assertEquals(35, map.lastEntry().getKey());
	}

	@Test
	void testCeilingEntry() throws IOException {
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		
		assertEquals(12, map.ceilingEntry(11).getKey());
		assertEquals(2, map.ceilingEntry(2).getKey());
	}

	@Test
	void testFloorEntry() throws IOException {
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		
		assertEquals(5, map.floorEntry(11).getKey());
		assertEquals(5, map.floorEntry(5).getKey());
	}

	@Test
	void testLowerEntry() throws IOException {
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		
		assertEquals(23, map.lowerEntry(24).getKey());
		assertEquals(26, map.lowerEntry(31).getKey());
	}

	@Test
	void testHigherEntry() throws IOException {
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		
		assertEquals(12, map.higherEntry(11).getKey());
	}

	@Test
	void testEntrySet() throws IOException {
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		List<Entry<Integer, String>> esStr = new ArrayList<>();
		for(var e : map.entrySet()) {
			esStr.add(e);
		}
		assertEquals("[1, 2, 4, 5, 12, 15, 21, 23, 24, 26, 33, 35]", esStr.toString());
	}

	@Test
	void testToString() throws IOException {
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		assertEquals("[1, 2, 4, 5, 12, 15, 21, 23, 24, 26, 33, 35]", map.toString());
	}

	@Test
	void testSubMap() throws IOException {
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

		for(Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
				
		assertEquals("[12, 15, 21, 23, 24, 26, 33]", map.subMap(12, 34).toString());
	}

}
