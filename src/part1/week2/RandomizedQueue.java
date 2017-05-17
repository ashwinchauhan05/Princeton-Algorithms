package part1.week2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by ashwinch on 10/6/15.
 *
 * Implement using the Arrays Model with re-sizing.
 *
 * Corner cases. The order of two or more iterators to the same randomized queue must be mutually independent;
 * each iterator must maintain its own random order.
 * Throw a java.lang.NullPointerException if the client attempts to add a null item;
 * throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;

 Performance requirements:
 Your randomized queue implementation must support each randomized queue operation (besides creating an iterator)
 in constant amortized time. That is, any sequence of M randomized queue operations (starting from an empty queue)
 should take at most cM steps in the worst case, for some constant c. A randomized queue containing N items must use at
 most 48N + 192 bytes of memory. Additionally, your iterator implementation must support operations next() and hasNext()
 in constant worst-case time; and construction in linear time; you may (and will need to) use a linear amount of extra
 memory per iterator.
 */


public class RandomizedQueue<Item> implements  Iterable<Item>  {

    private Item[] rQueue;
    private int size = 0;

    public RandomizedQueue() {
        rQueue = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (size == rQueue.length) resize(2 * rQueue.length);
        rQueue[size++] = item;
    }

    public Item dequeue() {

        if (isEmpty()) throw new NoSuchElementException();

        int randomIndex = StdRandom.uniform(size);
        Item item = rQueue[randomIndex];

        if (randomIndex != size-1)
            rQueue[randomIndex] = rQueue[size-1];

        rQueue[size-1] = null;
        size--;

        if (size > 0 && size == rQueue.length/4) resize(rQueue.length/2);

        return  item;
    }

    private void resize(int capacity) {
        Item[] newQueue =  (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            newQueue[i] = rQueue[i];
        rQueue = newQueue;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return  rQueue[StdRandom.uniform(size)];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>();
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {

        private boolean[] visitedItems = new boolean[size];
        private int visitedCount = 0;

        public RandomizedQueueIterator() {
            for (int i = 0; i < visitedItems.length; i++)
                visitedItems[i] = false;
        }

        public boolean hasNext() {
            return visitedCount < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            int randomIndex = StdRandom.uniform(size);
            if (!hasNext()) throw new NoSuchElementException();

            while (visitedItems[randomIndex])
                randomIndex = StdRandom.uniform(size);

            visitedItems[randomIndex] = true;
            visitedCount++;
            return (Item) rQueue[randomIndex];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        System.out.println(rq.isEmpty());
        System.out.println(rq.size());

        rq.enqueue("a");
        rq.enqueue("b");
        rq.enqueue("c");
        rq.enqueue("d");
        rq.enqueue("f");
        rq.enqueue("e");
        rq.enqueue("h");
        rq.enqueue("g");

        System.out.println("Sample : " + rq.sample());
        System.out.println("Sample : " + rq.sample());

        System.out.println("dequeue : " + rq.dequeue());
        System.out.println("dequeue : " + rq.dequeue());

        for (String s : rq) {
            System.out.println(s);
        }

    }
}
