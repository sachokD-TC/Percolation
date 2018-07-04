import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node firstItem;
    private Node lastItem;
    private int size = 0;

    private class Node {
        Node prev = null;
        Item item = null;
        Node next = null;

        public Node() {
        }
    }

    public Deque() {
        firstItem = null;
        lastItem = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        checkArgument(item);
        Node newFirst = new Node();
        newFirst.item = item;
        if (firstItem == null) {
            newFirst.next = null;
        } else {
            newFirst.next = firstItem;
            firstItem.prev = newFirst;
        }
        newFirst.prev = null;
        firstItem = newFirst;
        firstItem.item = item;
        size++;
        if (lastItem == null) {
            lastItem = firstItem;
        }
    }

    public void addLast(Item item) {
        checkArgument(item);
        Node newNode = new Node();
        newNode.item = item;
        if (lastItem == null) {
            newNode.prev = null;
        } else {
            newNode.prev = lastItem;
            lastItem.next = newNode;
        }
        newNode.next = null;
        lastItem = newNode;
        lastItem.item = item;
        size++;
        if (firstItem == null) {
            firstItem = lastItem;
        }
    }

    public Item removeFirst() {
        chekIsEmpty();
        Item item = firstItem.item;
        if (size == 1) {
            firstItem = null;
            lastItem = null;
        } else {
            firstItem = firstItem.next;
            firstItem.prev = null;
        }
        size--;
        return item;
    }     // remove and return the item from the front

    public Item removeLast() {
        chekIsEmpty();
        final Item item;
        if(firstItem!=null && lastItem != null && firstItem.equals(lastItem)){
            item = firstItem.item;
            firstItem = null;
            lastItem = null;
        } else {
            if (lastItem != null) {
                item = lastItem.item;
                if (lastItem.prev != null) {
                    lastItem = lastItem.prev;
                }
                lastItem.next = null;
            } else {
                item = firstItem.item;
                firstItem = null;
            }
        }
        size--;
        return item;
    }    // remove and return the item from the end

    public Iterator<Item> iterator() {
        return new DequeIterator<Item>();
    }    // return an iterator over items in order from front to end

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(0);
        System.out.println("removed = " + deque.removeLast());
        deque.addFirst(2);
        System.out.println("removed = " + deque.removeLast());
        System.out.println("removed = " + deque.isEmpty());
    }


    private void printIter(Deque deque) {
        Iterator<Integer> iterator = deque.iterator();
        while (iterator.hasNext()) {
            System.out.println("element = " + iterator.next());
        }
    }


    private void checkArgument(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    private void chekIsEmpty() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private Node current;

        public DequeIterator() {
            if (firstItem == null) {
                current = lastItem;
            } else {
                current = firstItem;
            }
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if(!hasNext()) throw new java.util.NoSuchElementException();
            Node retItem = current;
            current = current.next;
            return (Item) retItem.item;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

    }

}

