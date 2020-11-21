/**
 * Assignment 4 for lab 1
 *
 * Purpose of program: Implements a generic iterable circular linked list. The list contains
 * methods to add nodes to both front and back, likewise, delete an element from front and back.
 */

import java.util.EmptyStackException;
import java.util.Iterator;

public class L1Assignment4 {

    public static void main(String[] args) {
        DoublyLinkedCircularList<Integer> list = new DoublyLinkedCircularList<Integer>();

        System.out.println("********Assignment 4********");

        // testing add-to-front function
        System.out.println("\nTest: adding nodes to front of list, containing values given in the order: ");
        System.out.println("7  11  20");

        list.addFirst(7);
        list.printList();
        list.addFirst(11);
        list.printList();
        list.addFirst(20);
        list.printList();


        String expectedRes = "[20], [11], [7]";
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

        // testing add-to-back function
        System.out.println("\nTest: adding nodes to back of list, containing values given in the order: ");
        System.out.println("100 4");

        list.addLast(100);
        list.printList();
        list.addLast(4);
        list.printList();

        expectedRes = "[20], [11], [7], [100], [4]";
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

        // testing remove-from-back and remove-from-front functions
        System.out.println("\nTest: removing one element from back and one element from front of list in given order");

        list.removeLast();
        list.printList();
        list.removeFirst();
        list.printList();

        expectedRes = "[11], [7], [100]";
        System.out.printf("\n\nExpected string representation of queue: %s\n", expectedRes);

        System.out.println("Comparing expected string with string representation...");
        if(list.toString().equals(expectedRes)) {
            System.out.println("\nTrue! The string result is equal to the expected result");
            System.out.printf("%s --is equal to-- %s", expectedRes, list.toString());
        }
        else {
            System.out.println("\nFalse! The string result is not equal to the expected result");
            System.out.printf("%s --is not equal to-- %s", expectedRes, list.toString());
        }

        // testing the iterator functionality of the list
        System.out.println("\n\nTest: creating Iterator object of list and iterating through it: ");
        Iterator it = list.iterator();

        while (it.hasNext()) {
            System.out.printf("[%s], ", it.next());
        }

        // testing remove-from-back function from an empty list
        System.out.println("\n\nTest: removing one element from the back on an empty list:");

        list = new DoublyLinkedCircularList<>();

        try {
            list.removeLast();
        }
        catch (EmptyStackException ex) {
            System.out.println("You tried to dequeue an element off an empty list.");
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
    private static class DoublyLinkedCircularList<Item> implements Iterable<Item>{
        private Node sentinel; // sentinel node, basically acts as a dummy node
        private int size;

        //contructor list, instantiates a list which is Empty
        public DoublyLinkedCircularList() {
            sentinel = new Node(null);
            sentinel.next = sentinel;
            sentinel.prev = sentinel;

            size = 0;
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

        // method returns boolean if current list is empty or not
        public boolean isEmpty() {
            return sentinel.prev == sentinel;
        }

        // method which removes the first node  in the list and returns it
        public Node removeFirst() {
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

        // method which removes the last node in the list and returns it
        public Node removeLast() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }

            Node currentTail = sentinel.prev;
            Node oldTail = sentinel.prev;

            currentTail.prev.next = currentTail.next;
            currentTail.next.prev = currentTail.prev;

            size--;
            return oldTail;
        }

        // method which adds a node at the front of the list
        public void addFirst(Item item) {
            Node temp = sentinel;
            Node newHead = new Node(item);

            newHead.next = temp.next;
            newHead.prev = temp;
            temp.next.prev = newHead;
            temp.next = newHead;

            size++;
        }

        // method which adds a node to the back of the list
        public void addLast(Item item) {
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

            // checks if there's an element next in line
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
