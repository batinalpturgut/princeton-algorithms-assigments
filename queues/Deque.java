/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }


    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }


    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;

        if (oldFirst == null) {
            last = first;
        }
        else {
            oldFirst.previous = first;
        }
        size++;
    }

    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldLast = last;
        last = new Node();
        last.item = item;

        if (oldLast == null) {
            first = last;
        }
        else {
            oldLast.next = last;
            last.previous = oldLast;
        }

        size++;
    }

    public Item removeFirst() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.item;
        first = first.next;

        if (first == null) {
            last = null;
        }
        else {
            first.previous = null;
        }
        size--;
        return item;
    }

    public Item removeLast() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = last.item;
        last = last.previous;

        if (last == null) {
            first = null;
        }
        else {
            last.next = null;
        }
        size--;
        return item;
    }


    public Iterator<Item> iterator() {
        return new ListIterator1();
    }

    private class ListIterator1 implements Iterator<Item> {
        private Node currentNode = first;


        public boolean hasNext() {
            return currentNode != null;
        }

        public Item next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = currentNode.item;
            currentNode = currentNode.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deq = new Deque<>();
        deq.addFirst(1);
        deq.addLast(2);
        deq.addLast(3);
        deq.addLast(4);


        StdOut.println(deq.removeLast());
        StdOut.println(deq.removeFirst());
        StdOut.println(deq.size());

        deq.addLast(4);
        StdOut.println(deq.size());

        for (Integer temp : deq) {
            System.out.println(temp);
        }

        Iterator<Integer> iterator = deq.iterator();

        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }

        StdOut.println(deq.isEmpty());

        StdOut.println(deq.removeLast());
        StdOut.println(deq.removeLast());
        StdOut.println(deq.removeLast());

        StdOut.println(deq.size());
    }
}
