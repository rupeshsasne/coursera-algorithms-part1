import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int top = -1;

    public RandomizedQueue() {
        // noinspection unchecked
        array = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        if (top == array.length - 1)
            resize(2 * array.length);

        array[++top] = item;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();

        int randomIndex = getRandomIndex();

        Item valueToBeReturned = array[randomIndex];

        array[randomIndex] = array[top];

        array[top--] = null;

        if (top > -1 && top == (array.length - 1) / 4)
            resize(array.length / 2);

        return valueToBeReturned;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();

        return array[getRandomIndex()];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>(array, top);
    }

    private int getRandomIndex() {
        return StdRandom.uniform(0, top + 1);
    }

    private void resize(int capacity) {
        // noinspection unchecked
        Item[] tempArray = (Item[]) new Object[capacity];

        System.arraycopy(array, 0, tempArray, 0, Math.min(array.length, capacity));

        array = tempArray;
    }

    private static class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private final Item[] array;

        private int trav = 0;

        public RandomizedQueueIterator(Item[] array, int top) {
            // noinspection unchecked
            this.array = (Item[]) new Object[top + 1];

            System.arraycopy(array, 0, this.array, 0, top + 1);

            StdRandom.shuffle(this.array);
        }

        @Override
        public boolean hasNext() {
            return trav < array.length;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            return array[trav++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        // noinspection CheckStyle
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(2);
        rq.dequeue();

    }
}
