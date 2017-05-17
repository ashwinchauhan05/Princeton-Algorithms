package part1.week2;

import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

/**
 * Created by ashwinch on 10/10/15.
 */
public class Subset {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        while (!StdIn.isEmpty())
            rq.enqueue(StdIn.readString());

        Iterator<String> it = rq.iterator();

        for (int i = 0; i < k && it.hasNext(); i++) {
            System.out.println(it.next());
        }
    }
}
