package tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TreePerformanceComparison {
    public static void main(String[] args) throws IOException {
//        int [] sizes = new int[]{100};
        int [] sizes = new int[]{10000,50000,100000,500000,1000000};

        ArrayList<Integer> randomIntList = randomList(1000000);
        ArrayList<Integer> sortedIntList = sortedList(1000000);

        Treap<Integer> treap = new Treap<>();
        AVLTreeMap<Integer,Integer> AVLTreeMap = new AVLTreeMap<>();
        TreeMap<Integer,Integer> treeMap = new TreeMap<>();

        for(int i : sizes){
            double randomTreapTime = benchmark(()->{for(int ix = 0; ix < i; ix++){try {treap.put(randomIntList.get(ix));} catch (IOException Ignored) {}}},5)/(double)(1e9);
            double sortedTreapTime = benchmark(()->{for(int ix = 0; ix < i; ix++){try {treap.put(sortedIntList.get(ix));} catch (IOException Ignored) {}}},5)/(double)(1e9);

            double randomAVLTime = benchmark(()->{for(int ix = 0; ix < i; ix++){try {AVLTreeMap.put(randomIntList.get(ix),randomIntList.get(ix));} catch (IOException Ignored) {}}},5)/(double)(1e9);
            double sortedAVLTime = benchmark(()->{for(int ix = 0; ix < i; ix++){try {AVLTreeMap.put(sortedIntList.get(ix),randomIntList.get(ix));} catch (IOException Ignored) {}}},5)/(double)(1e9);

            double randomTreeMapTime = benchmark(()->{for(int ix = 0; ix < i; ix++){try {treeMap.put(randomIntList.get(ix),randomIntList.get(ix));} catch (IOException Ignored) {}}},5)/(double)(1e9);
            double sortedTreeMapTime = benchmark(()->{for(int ix = 0; ix < i; ix++){try {treeMap.put(sortedIntList.get(ix),randomIntList.get(ix));} catch (IOException Ignored) {}}},5)/(double)(1e9);

            System.out.println(randomTreapTime + ", " + sortedTreapTime + ", " +  randomAVLTime + ", " +  sortedAVLTime + ", " +  randomTreeMapTime + ", " +  sortedTreeMapTime );
        }
    }

    private static long benchmark(Runnable task){

        return benchmark(task,1);

    }
    private static long benchmark(Runnable task, int freq){
        long start = System.nanoTime();
        for(int i = 0; i < freq; i++){
            task.run();
        }
        return (System.nanoTime() - start)/freq;
    }

    private static ArrayList<Integer> randomList(int count){
        Random rand = new Random();
        ArrayList<Integer> l = new ArrayList<>();

        for(int i = 0; i < count; i++){
            l.add(rand.nextInt());
        }

        return l;
    }
    private static ArrayList<Integer> randomList(int count, int lower, int higher){
        Random rand = new Random();
        ArrayList<Integer> l = new ArrayList<>();

        for(int i = 0; i < count; i++){
            l.add(rand.nextInt(lower,higher));
        }

        return l;
    }

    private static ArrayList<Integer> sortedList(int count){
        ArrayList<Integer> l = randomList(count);
        l.sort(Integer::compare);
        return l;
    }
    private static ArrayList<Integer> sortedList(int count, int lower, int higher){
        ArrayList<Integer> l = randomList(count, lower, higher);
        l.sort(Integer::compare);
        return l;
    }
}
