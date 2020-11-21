/**
 * Assignment 3 for lab 1
 *
 * Purpose of program: To implement and test a FIFO queue based on a generic iterable circular
 * doubly linked list. The program enqueues elements at the end of the list and
 * dequeues elements at the front.
 */

import java.util.EmptyStackException;
import java.util.Iterator;

public class L1Assignment3 {

    public static void main(String[] args) {

        DoublyLinkedCircularList<Integer> queue = new DoublyLinkedCircularList<Integer>();

        System.out.println("********Assignment 3********");

        // testing enqueue function
        System.out.println("\nTest: enqueueing following nodes containing values given in the order: ");
        System.out.println("5  23  9  41  0  7");

        queue.enqueue(5);
        queue.printList();
        queue.enqueue(23);
        queue.printList();
        queue.enqueue(9);
        queue.printList();
        queue.enqueue(41);
        queue.printList();
        queue.enqueue(0);
        queue.printList();
        queue.enqueue(7);
        queue.printList();


        String expectedRes = "[5], [23], [9], [41], [0], [7]";
        System.out.printf("\n\nExpected string representation of queue: %s\n", expectedRes);

        System.out.println("Comparing expected string with string representation...");
        if(queue.toString().equals(expectedRes)) {
            System.out.println("\nTrue! The string result is equal to the expected result");
            System.out.printf("%s --is equal to-- %s", expectedRes, queue.toString());
        }
        else {
            System.out.println("\nFalse! The string result is not equal to the expected result");
            System.out.printf("%s --is not equal to-- %s", expectedRes, queue.toString());
        }

        // testing dequeue function
        System.out.println("\n\nTest: dequeueing two nodes from current circular list: ");

        queue.dequeue();
        queue.printList();
        queue.dequeue();
        queue.printList();

        expectedRes = "[9], [41], [0], [7]";
        System.out.printf("\n\nExpected string representation of queue: %s\n", expectedRes);

        System.out.println("Comparing expected string with string representation...");
        if(queue.toString().equals(expectedRes)) {
            System.out.println("\nTrue! The string result is equal to the expected result");
            System.out.printf("%s --is equal to-- %s", expectedRes, queue.toString());
        }
        else {
            System.out.println("\nFalse! The string result is not equal to the expected result");
            System.out.printf("%s --is not equal to-- %s", expectedRes, queue.toString());
        }

        // testing the iterator functionality of the queue
        System.out.println("\n\nTest: creating Iterator object of queue and iterating through it: ");
        Iterator it = queue.iterator();

        while (it.hasNext()) {
            System.out.printf("[%s], ", it.next());
        }

        // testing dequeue function on a list that is empty
        System.out.println("\n\nTest: dequeing one element from an empty circular list: ");

        // create empty list
        queue = new DoublyLinkedCircularList<>();
        try {
            queue.dequeue();
        }
        catch (EmptyStackException ex) {
            System.out.println("You tried to dequeue an element off an empty list.");
        }


    }

    // class representing each node in the list
    private static class Node<Item> {
        private Item data;                  //data stored in node
        private Node next;            //node positioned in front of current node
        private Node prev;                //node positioned previous to current node

        // constructor for a node in the list, taking data as argument and asserts to Node.
        // Makes sure there is no node attached in front of after it.
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

    //class representing the double linked sentinal list
    private static class DoublyLinkedCircularList<Item> implements Iterable<Item> {
        private Node sentinel; // sentinel node, basically acts as a dummy node
        private int size;

        //contructor for the double linked sentinal list, instantiates
        //a list which is Empty
        public DoublyLinkedCircularList() {
            sentinel = new Node(null);
            sentinel.next = sentinel;
            sentinel.prev = sentinel;

            size = 0;
        }

        // method returns boolean if current list is empty or not
        public boolean isEmpty() {
            return sentinel.prev == sentinel;
        }

        // method printing representation of current list
        public void printList() {
            Node current = sentinel.next;

            System.out.println();
            while (current != sentinel) {
                if (current.next == sentinel)
                    System.out.printf("[" + current.toString() + "]");
                else
                    System.out.printf("[" + current.toString() + "], ");
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

        // method which removes the first node  in the list and returns it
        public Node dequeue() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }

            Node currentHead = sentinel.next;
            Node oldHead = sentinel.next;

            currentHead.prev.next = currentHead.next;
            currentHead.next.prev = currentHead.prev;

            size--;
            return oldHead;
        }

        // method which adds a node to the back of the list
        public void enqueue(Item item) {
            Node temp = sentinel.prev;
            Node newTail = new Node(item);


            newTail.next = temp.next;
            newTail.prev = temp;
            temp.next.prev = newTail;
            temp.next = newTail;

            size++;
        }


        @Override
        public Iterator<Item> iterator() {
            return new DLLCircularIterator();
        }

        private class DLLCircularIterator implements Iterator<Item> {
            private boolean isLooped = false;
            private Node current = sentinel.next;

            // method checks if there is a next element
            public boolean hasNext() {
                return current != sentinel && !isLooped;
            }

            // iterates one position to next element
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
