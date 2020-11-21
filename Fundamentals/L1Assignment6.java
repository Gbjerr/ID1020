/**
 * Assignment 6 for lab 1
 *
 * Purpose of program: Implements and tests an ordered queue based on a circular doubly linked list.
 * Elements can be added to queue, where the queue always maintains an ascending order of the elements.
 */

import java.util.EmptyStackException;
import java.util.Iterator;
import java.lang.Integer;

public class L1Assignment6 {

    public static void main(String[] args) {
        OrderedDLCQueue<Integer> list = new OrderedDLCQueue<Integer>();

        System.out.println("********Assignment 6********");


        // testing the the ordered add-element function
        System.out.println("\nTest: adding nodes with ordered insert function, containing values given in the order: ");
        System.out.println("------34  9  12  43  0  19  50");


        list.addElement(34);
        list.printList();
        list.addElement(9);
        list.printList();
        list.addElement(12);
        list.printList();
        list.addElement(43);
        list.printList();
        list.addElement(0);
        list.printList();
        list.addElement(19);
        list.printList();
        list.addElement(50);
        list.printList();


        String expectedRes = "[0], [9], [12], [19], [34], [43], [50]";
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
    private static class OrderedDLCQueue<Item> implements Iterable<Item>{
        private Node sentinel; // sentinel node, basically acts as a dummy node
        private int size;

        //contructor list, instantiates a list which is Empty
        public OrderedDLCQueue() {
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

        public void addElement(Item item) {
            Node input = new Node(item);
            Integer i = (Integer) item;

            // empty list case
            if(isEmpty()) {
                addAtFront(item);
            }
            else {
                Node current = sentinel;

                // until the end of list is met, or when our input is less than the next element
                // we stop iterating
                while(current.next != sentinel &&
                        (i.compareTo((Integer) current.next.data)) > 0) {

                    current = current.next;
                }

                input.next = current.next;
                input.prev = current;
                current.next.prev = input;
                current.next = input;

                size++;
            }

        }

        @Override
        public Iterator<Item> iterator() {
            return new OrderedDLCIterator();
        }

        private class OrderedDLCIterator implements Iterator<Item> {
            private boolean isLooped = false;
            private Node current = sentinel.next;

            // checks if there's a next element in line
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
