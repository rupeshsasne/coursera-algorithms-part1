import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first, last;

    private int size;

    public Deque() {
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return first == null && last == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        requiresNonNull(item);

        Node<Item> oldFirst = first;

        Node<Item> newNode = new Node<>(item);
        newNode.prev = null;
        newNode.next = oldFirst;

        if (oldFirst != null) {
            oldFirst.prev = newNode;
        }

        first = newNode;

        if (newNode.next == null) {
            last = first;
        }

        size++;
    }

    public void addLast(Item item) {
        requiresNonNull(item);

        if (first == null)
            addFirst(item);
        else {
            Node<Item> newNode = new Node<>(item);

            last.next = newNode;
            newNode.prev = last;
            last = newNode;

            size++;
        }
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        Node<Item> oldFirst = first;

        first = first.next;

        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }

        size--;

        oldFirst.prev = null;
        oldFirst.next = null;

        return oldFirst.data;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        Node<Item> oldLast = last;

        last = last.prev;

        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }

        size--;

        oldLast.next = null;
        oldLast.prev = null;

        return oldLast.data;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator<>(first);
    }

    private void requiresNonNull(Object obj) {
        if (obj == null)
            throw new IllegalArgumentException();
    }

    private static class Node<E> {
        private final E data;
        private Node<E> next;
        private Node<E> prev;

        public Node(E data) {
            this.data = data;
        }
    }

    private static class DequeIterator<T> implements Iterator<T> {

        private Node<T> trav;

        DequeIterator(Node<T> head) {
            trav = head;
        }

        @Override
        public boolean hasNext() {
            return trav != null;
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Node<T> node = trav;
            trav = trav.next;
            return node.data;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(6);
        deque.addFirst(7);
        deque.removeLast();
        System.out.println(deque.isEmpty());
    }
}
