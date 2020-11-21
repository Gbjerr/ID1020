/**
 * Assignment 5 for lab 1
 *
 * Purpose of program: Implements and tests a generalized queue built upon a doubly
 * linked circular list. Elements can be added to queue, where they end up at the first index.
 * One can remove elements at any given index, that exists.
 */

import java.util.EmptyStackException;
import java.util.Iterator;

public class L1Assignment5 {

    public static void main(String[] args) {
        GeneralizedDLCQueue<Integer> list = new GeneralizedDLCQueue<Integer>();

        System.out.println("********Assignment 5********");

        // testing the remove-k'th element function
        System.out.println("\nTest: adding nodes to front of list, containing values given in the order: ");
        System.out.println("------9  6  2  5  3  11  90  22");
        System.out.println("------and first deleting the 3th element and then the 4th of the list");


        list.addAtFront(9);
        list.printList();
        list.addAtFront(6);
        list.printList();
        list.addAtFront(2);
        list.printList();
        list.addAtFront(5);
        list.printList();
        list.addAtFront(3);
        list.printList();
        list.addAtFront(11);
        list.printList();
        list.addAtFront(90);
        list.printList();
        list.addAtFront(22);
        list.printList();

        list.removeAtPos(3);
        list.printList();
        list.removeAtPos(4);
        list.printList();

        String expectedRes = "[22], [90], [3], [2], [6], [9]";
        System.out.printf("\n\nExpected string representation of queue: %s\n", expectedRes);

        System.out.println("Comparing expected string with string representation...");
        if(list.toString().equals(expectedRes)) {
            System.out.println("\nTrue! The string result is equal to the expected result");
            System.out.printf("%s --is equal to-- %s\n", expectedRes, list.toString());
        }
        else {
            System.out.println("\nFalse! The string result is not equal to the expected result");
            System.out.printf("%s --is not equal to-- %s\n", expectedRes, list.toString());
        }

        // testing the remove-k'th element function with an out of bounds argument k
        System.out.println("\nTest: deleting the 10th element of current list");
        String msg = "";

        try {
            list.removeAtPos(10);
        }
        catch (IllegalArgumentException ex) {
            msg = ex.getMessage();
        }

        expectedRes = "Invalid argument <k>";
        System.out.printf("\nExpected exception message: %s\n", expectedRes);

        System.out.println("Comparing expected string with string representation...");
        if(msg.equals(expectedRes)) {
            System.out.println("\nTrue! The string result is equal to the expected result");
            System.out.printf("%s --is equal to-- %s\n", expectedRes, msg);
        }
        else {
            System.out.println("\nFalse! The string result is not equal to the expected result");
            System.out.printf("%s --is not equal to-- %s\n", expectedRes, msg);
        }

        // testing the iterator functionality of the list
        System.out.println("\n\nTest: creating Iterator object of list and iterating through it: ");
        Iterator it = list.iterator();

        while (it.hasNext()) {
            System.out.printf("[%s], ", it.next());
        }

    }

    // class representing each node in the list
    private static class Node<Item> {
        private Item data;                  //data stored in node
        private Node next;
        private Node prev;

        // constructor for a node in the list, taking data as argument and asserts to Node.
        public Node(Item item) {
            this.next = null;
            this.prev = null;
            data = item;
        }

        // method returning data as a String
        public String toString() {
            return data.toString();
        }
    }

    //class representing the double linked sentinel list
    private static class GeneralizedDLCQueue<Item> implements Iterable<Item>{
        private Node sentinel; // sentinel node, basically acts as a dummy node
        private int size;

        //contructor list, instantiates a list which is Empty
        public GeneralizedDLCQueue() {
            sentinel = new Node(null);
            sentinel.next = sentinel;
            sentinel.prev = sentinel;

            size = 0;
        }

        // method prints out representation of current list
        public void printList() {
            Node current = sentinel.next;

            System.out.println();
            while (current != sentinel) {
                if (current.next == sentinel)
                    System.out.printf("[%s]", current.toString());
                else
                    System.out.printf("[%s], ", current.toString());
                current = current.next;
            }

        }

        public String toString() {
            String res = "";

            Node current = sentinel.next;

            while (current != sentinel) {
                if (current.next == sentinel)
                    res += "[" + current.toString() + "]";
                else
                    res += "[" + current.toString() + "], ";

                current = current.next;
            }

            return res;
        }

        // method returns boolean if current list is empty or not
        public boolean isEmpty() {
            return sentinel.prev == sentinel;
        }

        // method which removes the k'th element in the list
        public void removeAtPos(int k) {
            if(k <= 0 || k > size) {
                throw new IllegalArgumentException("Invalid argument <k>");
            }
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            Node current = sentinel;
            // iterate until to the k'th element
            for(int i=0; i < k; i++) {
                current = current.next;
            }

            // arrange references so that the k'th element is excluded
            current.prev.next = current.next;
            current.next.prev = current.prev;

            size--;
        }

        // method which adds a node at the front of the list
        public void addAtFront(Item item) {
            Node temp = sentinel;
            Node newHead = new Node(item);

            newHead.next = temp.next;
            newHead.prev = temp;
            temp.next.prev = newHead;
            temp.next = newHead;

            size++;
        }

        @Override
        public Iterator<Item> iterator() {
            return new GeneralizedDLCIterator();
        }

        private class GeneralizedDLCIterator implements Iterator<Item> {
            private boolean isLooped = false;
            private Node current = sentinel.next;

            // checks if there's a next element
            public boolean hasNext() {
                return current != sentinel && !isLooped;
            }

            // iterates to next element
            public Item next() {
                if (current == sentinel) {
                    isLooped = true;
                    return null;
                }

                Item item = (Item) current.data;
                current = current.next;
                return item;
            }
        }

    }


}
