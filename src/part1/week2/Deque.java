package part1.week2;


import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by ashwinch on 10/6/15.
 *
 * Implement using LinkedList Model.
 *
 * Corner cases. Throw a java.lang.NullPointerException if the client attempts to add a null item;
 * throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque;
 * throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
 * throw a java.util.NoSuchElementException if the client calls the next() method in the iterator
 * and there are no more items to return.
 *
 * Performance requirements.   Your deque implementation must support each deque operation in constant worst-case time.
 *
 */


public class Deque<Item> implements  Iterable<Item> {

    private Node first, last;
    private int size = 0;

    private class Node {
        private Item item;
        private Node next, previous;
    }

    public  Deque() {
        first = null;
        last = null;
    }

    public  boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item item) {

        if (item == null) throw new NullPointerException();

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.previous = null;

        if (oldFirst == null) {
            first.next = null;
            last = first;
        } else {
            first.next = oldFirst;
            oldFirst.previous = first;
        }

        size++;
    }

    public void addLast(Item item) {

        if (item == null) throw new NullPointerException();

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;

        if (oldLast == null) {
            last.previous = null;
            first = last;
        } else {
            last.previous = oldLast;
            oldLast.next = last;
        }

        size++;
    }

    public Item removeFirst() {

        if (isEmpty()) throw new NoSuchElementException();

        Item item = first.item;
        first.item = null;

        if (first.next != null) {
            first = first.next;
            first.previous = null;
        }
         else {
            first.next = null;
            first = null;
            last = null;
        }

        size--;
        return item;
    }

    public  Item removeLast() {

        if (isEmpty()) throw new NoSuchElementException();

        Item item = last.item;
        last.item = null;

        if (last.previous != null) {
            last = last.previous;
            last.next = null;
        }
        else {
            last.next = null;
            first = null;
            last = null;
        }

        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements  Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
         throw new UnsupportedOperationException();
        }

        public Item next() {
            if (hasNext()) {
                Item item = current.item;
                current = current.next;
                return item;
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    public static void main(String[] args) {

        Deque<String> testDequeu = new Deque<>();

        System.out.println(testDequeu.isEmpty());
        System.out.println(testDequeu.size());

        testDequeu.addFirst("a");
        testDequeu.addFirst("b");
        testDequeu.addLast("c");
        testDequeu.addFirst("d");
        // d b a c
        System.out.println(testDequeu.removeLast());
        System.out.println(testDequeu.removeFirst());

        for (String s : testDequeu)
            System.out.println(s);

    }
}
