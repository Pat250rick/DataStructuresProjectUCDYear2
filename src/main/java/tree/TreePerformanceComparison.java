package tree;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import interfaces.Entry;

public class TreePerformanceComparison {

    public static void main(String[] args) throws IOException {
        // Define the sizes to test with
        int[] sizes = new int[]{100, 1000, 5000, 10000};

        // Generate the test data
        ArrayList<Integer> randomIntList = randomList(10000);
        ArrayList<Integer> sortedIntList = sortedList(10000);
        ArrayList<Integer> reverseSortedIntList = reverseSortedList(10000);
        ArrayList<Integer> partiallySortedIntList = partiallySortedList(10000);

        // Initialize the different tree structures
        Treap<Integer> treap = new Treap<>();
        AVLTreeMap<Integer, Integer> AVLTreeMap = new AVLTreeMap<>();
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        // Create CSV writer
        FileWriter csvWriter = new FileWriter("tree_performance_comparison.csv");
        // Write header row for the CSV
        csvWriter.append("Operation,DataStructure,Size,Time(ns)\n");

        // Measure performance for each size
        for (int size : sizes) {
            // Benchmark Insertions (Random & Sorted Data)
            System.out.println("Size: " + size);

            // Insert random data
            benchmarkInsertion(treap, AVLTreeMap, treeMap, randomIntList, size, true, csvWriter);
            benchmarkInsertion(treap, AVLTreeMap, treeMap, sortedIntList, size, false, csvWriter);

            // Search tests (Successful and Unsuccessful)
            benchmarkSearch(treap, AVLTreeMap, treeMap, randomIntList, size, csvWriter);
            benchmarkSearch(treap, AVLTreeMap, treeMap, sortedIntList, size, csvWriter);

            // Deletion tests
            benchmarkDeletion(treap, AVLTreeMap, treeMap, randomIntList, size, csvWriter);
            benchmarkDeletion(treap, AVLTreeMap, treeMap, sortedIntList, size, csvWriter);

            // In-order Traversal tests
            benchmarkInOrderTraversal(treap, AVLTreeMap, treeMap, randomIntList, size, csvWriter);
            benchmarkInOrderTraversal(treap, AVLTreeMap, treeMap, sortedIntList, size, csvWriter);
        }

        // Close the CSV writer
        csvWriter.flush();
        csvWriter.close();
    }

    // Benchmark the insertion time for single and batch insertion
    private static void benchmarkInsertion(Treap<Integer> treap, AVLTreeMap<Integer, Integer> AVLTreeMap,
                                           TreeMap<Integer, Integer> treeMap, ArrayList<Integer> data, int size, boolean isRandom,
                                           FileWriter csvWriter) throws IOException {
        System.out.println("Insertion for " + (isRandom ? "Random" : "Sorted") + " Data of size " + size);

        // Insert into Treap
        long treapTime = benchmark(() -> {
            for (int ix = 0; ix < size; ix++) {
                try {
                    treap.put(data.get(ix));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Insert into AVLTreeMap
        long AVLTreeMapTime = benchmark(() -> {
            for (int ix = 0; ix < size; ix++) {
                try {
                    AVLTreeMap.put(data.get(ix), data.get(ix));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Insert into TreeMap
        long treeMapTime = benchmark(() -> {
            for (int ix = 0; ix < size; ix++) {
                try {
                    treeMap.put(data.get(ix), data.get(ix));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Write to CSV
        csvWriter.append("Insertion," + "Treap," + size + "," + treapTime + "\n");
        csvWriter.append("Insertion," + "AVLTreeMap," + size + "," + AVLTreeMapTime + "\n");
        csvWriter.append("Insertion," + "TreeMap," + size + "," + treeMapTime + "\n");
    }

    // Benchmark the search time for both successful and unsuccessful searches
    private static void benchmarkSearch(Treap<Integer> treap, AVLTreeMap<Integer, Integer> AVLTreeMap,
                                        TreeMap<Integer, Integer> treeMap, ArrayList<Integer> data, int size, FileWriter csvWriter) throws IOException {
        System.out.println("Search for Data of size " + size);

        // Successful search (search for the middle element)
        long treapSuccessTime = benchmark(() -> {
            try {
                treap.get(data.get(size / 2));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        long AVLTreeMapSuccessTime = benchmark(() -> {
            try {
                AVLTreeMap.get(data.get(size / 2));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        long treeMapSuccessTime = benchmark(() -> {
            try {
                treeMap.get(data.get(size / 2));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Unsuccessful search (search for a random element not in the data)
        long treapFailTime = benchmark(() -> {
            try {
                treap.get(size + 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        long AVLTreeMapFailTime = benchmark(() -> {
            try {
                AVLTreeMap.get(size + 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        long treeMapFailTime = benchmark(() -> {
            try {
                treeMap.get(size + 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Write to CSV
        csvWriter.append("Search,Successful," + "Treap," + size + "," + treapSuccessTime + "\n");
        csvWriter.append("Search,Successful," + "AVLTreeMap," + size + "," + AVLTreeMapSuccessTime + "\n");
        csvWriter.append("Search,Successful," + "TreeMap," + size + "," + treeMapSuccessTime + "\n");
        csvWriter.append("Search,Unsuccessful," + "Treap," + size + "," + treapFailTime + "\n");
        csvWriter.append("Search,Unsuccessful," + "AVLTreeMap," + size + "," + AVLTreeMapFailTime + "\n");
        csvWriter.append("Search,Unsuccessful," + "TreeMap," + size + "," + treeMapFailTime + "\n");
    }

    // Benchmark the deletion time
    private static void benchmarkDeletion(Treap<Integer> treap, AVLTreeMap<Integer, Integer> AVLTreeMap,
                                          TreeMap<Integer, Integer> treeMap, ArrayList<Integer> data, int size, FileWriter csvWriter) throws IOException {
        System.out.println("Deletion for Data of size " + size);

        // Delete from Treap
        long treapDelTime = benchmark(() -> {
            for (int ix = 0; ix < size; ix++) {
                try {
                    treap.remove(data.get(ix));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Delete from AVLTreeMap
        long AVLTreeMapDelTime = benchmark(() -> {
            for (int ix = 0; ix < size; ix++) {
                try {
                    AVLTreeMap.remove(data.get(ix));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Delete from TreeMap
        long treeMapDelTime = benchmark(() -> {
            for (int ix = 0; ix < size; ix++) {
                try {
                    treeMap.remove(data.get(ix));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Write to CSV
        csvWriter.append("Deletion," + "Treap," + size + "," + treapDelTime + "\n");
        csvWriter.append("Deletion," + "AVLTreeMap," + size + "," + AVLTreeMapDelTime + "\n");
        csvWriter.append("Deletion," + "TreeMap," + size + "," + treeMapDelTime + "\n");
    }

    // Benchmark the in-order traversal time
    private static void benchmarkInOrderTraversal(Treap<Integer> treap, AVLTreeMap<Integer, Integer> AVLTreeMap,
                                                  TreeMap<Integer, Integer> treeMap, ArrayList<Integer> data, int size, FileWriter csvWriter) throws IOException {

        System.out.println("In-order Traversal for Data of size " + size);

        // In-order traversal of Treap
        // Assuming Treap extends TreeMap or has similar interface
        long treapTraversalTime = benchmark(() -> {
            try {
                // Using entrySet() which should perform an in-order traversal in TreeMap derivatives
                for (Entry<Integer, Integer> entry : treap.entrySet()) {
                    // Just access each element to ensure traversal
                    Integer key = entry.getKey();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // In-order traversal of AVLTreeMap
        long AVLTreeMapTraversalTime = benchmark(() -> {
            try {
                // Using entrySet() which performs an in-order traversal in TreeMap
                for (Entry<Integer, Integer> entry : AVLTreeMap.entrySet()) {
                    // Just access each element to ensure traversal
                    Integer key = entry.getKey();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // In-order traversal of TreeMap
        long treeMapTraversalTime = benchmark(() -> {
            try {
                for (Entry<Integer, Integer> entry : treeMap.entrySet()) {
                    // Just access each element to ensure traversal
                    Integer key = entry.getKey();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Write to CSV
        csvWriter.append("InOrderTraversal," + "Treap," + size + "," + treapTraversalTime + "\n");
        csvWriter.append("InOrderTraversal," + "AVLTreeMap," + size + "," + AVLTreeMapTraversalTime + "\n");
        csvWriter.append("InOrderTraversal," + "TreeMap," + size + "," + treeMapTraversalTime + "\n");

        System.out.println("Treap In-order Traversal Time: " + treapTraversalTime + " ns");
        System.out.println("AVLTreeMap In-order Traversal Time: " + AVLTreeMapTraversalTime + " ns");
        System.out.println("TreeMap In-order Traversal Time: " + treeMapTraversalTime + " ns");
    }

    // Benchmark helper function to measure execution time
    private static long benchmark(Runnable task) {
        long start = System.nanoTime();
        task.run();
        return System.nanoTime() - start;
    }

    // Helper functions to generate test data
    private static ArrayList<Integer> randomList(int count) {
        Random rand = new Random();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(rand.nextInt());
        }
        return list;
    }

    private static ArrayList<Integer> sortedList(int count) {
        ArrayList<Integer> list = randomList(count);
        Collections.sort(list);
        return list;
    }

    private static ArrayList<Integer> reverseSortedList(int count) {
        ArrayList<Integer> list = sortedList(count);
        Collections.reverse(list);
        return list;
    }

    private static ArrayList<Integer> partiallySortedList(int count) {
        ArrayList<Integer> list = randomList(count);
        int half = count / 2;
        Collections.sort(list.subList(0, half));
        return list;
    }
}