/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int DEFAULT_CAPACITY = 10;

    private Item[] arr;

    private int objectCount;

    public RandomizedQueue() {
        arr = (Item[]) new Object[DEFAULT_CAPACITY];
        objectCount = 0;
    }

    public boolean isEmpty() {
        return objectCount == 0;
    }

    public int size() {
        return objectCount;
    }

    public void enqueue(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (objectCount == arr.length) {
            resize(arr.length * 2);
        }
        arr[objectCount++] = item;
    }

    private void resize(int capacity) {
        Item[] copyArray = (Item[]) new Object[capacity];
        for (int i = 0; i < objectCount; i++) {
            copyArray[i] = arr[i];
        }
        arr = copyArray;
    }

    public Item dequeue() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (objectCount != 0 && objectCount == arr.length / 4) {
            resize(arr.length / 2);
        }

        int rand = takeRand();
        Item item = arr[rand];
        arr[rand] = arr[--objectCount];
        arr[objectCount] = null;
        return item;
    }

    public Item sample() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int rand = takeRand();

        return arr[rand];
    }

    private int takeRand() {
        return StdRandom.uniformInt(objectCount);
    }


    public Iterator<Item> iterator() {
        return new ArrIterator();
    }

    private class ArrIterator implements Iterator<Item> {
        private Item[] randArr;
        private int currentIndex;

        public ArrIterator() {
            randArr = (Item[]) new Object[objectCount];
            for (int i = 0; i < objectCount; i++) {
                randArr[i] = arr[i];
            }
            StdRandom.shuffle(randArr);
        }

        public boolean hasNext() {
            return currentIndex != objectCount;
        }

        public Item next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return randArr[currentIndex++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> randQueue = new RandomizedQueue<>();
        randQueue.enqueue("A");
        randQueue.enqueue("B");
        randQueue.enqueue("C");
        randQueue.enqueue("D");
        randQueue.enqueue("E");
        randQueue.dequeue();

        System.out.println(randQueue.size());
        System.out.println(randQueue.sample());
        System.out.println(randQueue.isEmpty());

        Iterator<String> iter = randQueue.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}
