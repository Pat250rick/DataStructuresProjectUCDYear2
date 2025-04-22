package benchmark;

import tree.Treap;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;
import java.time.Instant;
import java.time.Duration;

public class Sorting {

    // Quick Sort implementation
    public static <K extends Comparable<K>> void quickSort(ArrayList<K> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);
            quickSort(list, low, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, high);
        }
    }

    private static <K extends Comparable<K>> int partition(ArrayList<K> list, int low, int high) {
        K pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j).compareTo(pivot) <= 0) {
                i++;
                K temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        K temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;
    }

    // Merge Sort implementation
    public static <K extends Comparable<K>> void mergeSort(ArrayList<K> list, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(list, low, mid);
            mergeSort(list, mid + 1, high);
            merge(list, low, mid, high);
        }
    }

    private static <K extends Comparable<K>> void merge(ArrayList<K> list, int low, int mid, int high) {
        int n1 = mid - low + 1;
        int n2 = high - mid;

        ArrayList<K> left = new ArrayList<>(n1);
        ArrayList<K> right = new ArrayList<>(n2);

        for (int i = 0; i < n1; i++) {
            left.add(list.get(low + i));
        }
        for (int j = 0; j < n2; j++) {
            right.add(list.get(mid + 1 + j));
        }

        int i = 0, j = 0, k = low;
        while (i < n1 && j < n2) {
            if (left.get(i).compareTo(right.get(j)) <= 0) {
                list.set(k, left.get(i));
                i++;
            } else {
                list.set(k, right.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            list.set(k, left.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            list.set(k, right.get(j));
            j++;
            k++;
        }
    }

    // PQSort implementation
    public static <K extends Comparable<K>> ArrayList<K> pqSort(ArrayList<K> list) {
        PriorityQueue<K> pq = new PriorityQueue<>();
        pq.addAll(list);

        ArrayList<K> result = new ArrayList<>(list.size());
        while (!pq.isEmpty()) {
            result.add(pq.poll());
        }

        return result;
    }

    // TreapSort implementation (calls Treap's treapSort method)
    public static <K extends Comparable<K>> ArrayList<K> treapSort(ArrayList<K> list) throws IllegalArgumentException, IOException {
        Treap<K> treap = new Treap<>();

        // Insert elements into Treap
        for (K item : list) {
            treap.put(item);
        }

        // Extract elements in order
        ArrayList<K> result = new ArrayList<>(list.size());
        for (var position : treap.treapSort(list)) {
            result.add(position.getElement().getKey());
        }

        return result;
    }

    // Java's built-in Collections.sort (TimSort)
    public static <K extends Comparable<K>> void javaSort(ArrayList<K> list) {
        Collections.sort(list);
    }

    // Method to generate different types of input
    public static ArrayList<Integer> generateInput(int size, String type) {
        ArrayList<Integer> input = new ArrayList<>(size);
        Random rand = new Random(42);  // Fixed seed for reproducibility

        switch (type) {
            case "random":
                for (int i = 0; i < size; i++) {
                    input.add(rand.nextInt(size * 10));
                }
                break;

            case "nearly_sorted":
                for (int i = 0; i < size; i++) {
                    input.add(i);
                }
                // Swap a few elements (5% of the total)
                for (int i = 0; i < size * 0.05; i++) {
                    int idx1 = rand.nextInt(size);
                    int idx2 = rand.nextInt(size);
                    Collections.swap(input, idx1, idx2);
                }
                break;

            case "reverse_sorted":
                for (int i = size - 1; i >= 0; i--) {
                    input.add(i);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown input type: " + type);
        }

        return input;
    }

    // Measure execution time for a sorting algorithm
    public static <K extends Comparable<K>> long measureSortTime(String algorithm, ArrayList<K> input) throws IOException {
        ArrayList<K> list = new ArrayList<>(input);  // Create a copy to avoid modifying original

        Instant start = Instant.now();

        switch (algorithm) {
            case "treap":
                treapSort(list);
                break;

            case "pq":
                pqSort(list);
                break;

            case "java":
                javaSort(list);
                break;

            case "quick":
                quickSort(list, 0, list.size() - 1);
                break;

            case "merge":
                mergeSort(list, 0, list.size() - 1);
                break;

            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }

        Instant end = Instant.now();
        return Duration.between(start, end).toMillis();
    }

    public static void main(String[] args) {
        try {
            // Define input sizes and types
            int[] sizes = {100, 500, 1000, 5000, 10000};
            String[] inputTypes = {"random", "nearly_sorted", "reverse_sorted"};
            String[] algorithms = {"treap", "pq", "java", "quick", "merge"};

            // Create CSV file for results
            FileWriter csvWriter = new FileWriter("sorting_benchmark_results.csv");

            // Write header
            csvWriter.append("Size,InputType,Algorithm,ExecutionTime(ms)\n");

            // Run benchmarks
            for (int size : sizes) {
                for (String inputType : inputTypes) {
                    System.out.println("Benchmarking input size: " + size + ", type: " + inputType);

                    // Generate input based on size and type
                    ArrayList<Integer> input = generateInput(size, inputType);

                    // Run each algorithm on this input
                    for (String algorithm : algorithms) {
                        long time = measureSortTime(algorithm, input);

                        // Write result to CSV
                        csvWriter.append(String.format("%d,%s,%s,%d\n", size, inputType, algorithm, time));

                        System.out.println("  " + algorithm + ": " + time + " ms");
                    }
                }
            }

            csvWriter.flush();
            csvWriter.close();
            System.out.println("Benchmark completed! Results written to sorting_benchmark_results.csv");

        } catch (IOException e) {
            System.err.println("Error during benchmark: " + e.getMessage());
            e.printStackTrace();
        }
    }
}