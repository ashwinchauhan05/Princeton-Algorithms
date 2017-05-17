package part1.week2;

/**
 * Created by ashwinch on 26/03/17.
 *
 * Permutation client that takes command-line integer k; reads sequence of strings and print exactly k of them, uniformly
 * at random. Print each of the item at most once. 0<=k<=n
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }

        for (int i = 0; i <k; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }

    }
}
