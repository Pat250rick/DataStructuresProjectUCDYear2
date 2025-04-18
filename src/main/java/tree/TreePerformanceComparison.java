package tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TreePerformanceComparison {
    public static void main(String[] args){
        Treap<Integer> treap = new Treap<>();

        ArrayList<Integer> randomList = randomList(1000000);
        ArrayList<Integer> sortedList = sortedList(1000000);

        System.out.println(benchmark(()->{try {treap.treapSort(randomList);} catch (IOException e) {throw new RuntimeException(e);}},5)/(double)100000);
        System.out.println(benchmark(()->{try {treap.treapSort(sortedList);} catch (IOException e) {throw new RuntimeException(e);}},5)/(double)100000);
    }

    private static long benchmark(Runnable task, int freq){
        long start = System.currentTimeMillis();
        for(int i = 0; i < freq; i++){
            task.run();
        }
        return (System.currentTimeMillis() - start)/freq;
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
