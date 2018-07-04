import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arrayOfItems;
    private int size = 0;


    public RandomizedQueue() {
        arrayOfItems = (Item[]) new Object[size + 1];
    }

    public boolean isEmpty() {
        return size <= 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (size >= 1 && arrayOfItems[size - 1] != null) {
            resizeArray();
        }
        arrayOfItems[size] = item;
        size++;
    }


    private void resizeArray() {
        Item[] newArray = (Item[]) new Object[size * 2];
        for (int i = 0; i != size; i++) {
            newArray[i] = arrayOfItems[i];
        }
        arrayOfItems = newArray;
    }

    public Item dequeue() {
        if (!isEmpty()) {
            if (size <= (arrayOfItems.length / 2) && size > 2) {
                shrinkArray();
            }
            int indexToRemove = StdRandom.uniform(size);
            Item itemToReturn = arrayOfItems[indexToRemove];
            if (size - 1 == indexToRemove) {
                arrayOfItems[indexToRemove] = null;
            } else {
                arrayOfItems[indexToRemove] = arrayOfItems[size - 1];
                arrayOfItems[size - 1] = null;
            }
            size--;
            return itemToReturn;
        } else {
            throw new java.util.NoSuchElementException();
        }
    }


    private void shrinkArray() {
        Item[] newArray = (Item[]) new Object[size];
        int n = 0;
        for (int i = 0; i != arrayOfItems.length; i++) {
            if (arrayOfItems[i] != null) {
                newArray[n] = arrayOfItems[i];
                n++;
            }
        }
        arrayOfItems = newArray;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return arrayOfItems[StdRandom.uniform(size)];
    }

    public Iterator<Item> iterator() {
        return new RandIterator<>();
    }

    /* UnusedPrivateMethod */
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        rq.enqueue("A");
        rq.enqueue("N");
        rq.enqueue("D");
        rq.enqueue("C");
        rq.printIter();
    }

    private void printIter(){
        Iterator iterator = this.iterator();
        while(iterator.hasNext()){
           System.out.println(iterator.next());
        }
    }

    private class RandIterator<Item> implements Iterator<Item> {

        private int getElements = 0;
        private final int[] indexes;

        public RandIterator() {
            indexes = new int[size];
            for (int i = 0; i != indexes.length; i++) {
                indexes[i] = i;
            }
            StdRandom.shuffle(indexes);
        }

        @Override
        public boolean hasNext() {
            return getElements < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return (Item) arrayOfItems[indexes[getElements++]];
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

}