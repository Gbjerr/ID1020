/**
 * Assigment 3
 *
 * Purpose of program: To represent a generic iterable FIFO queue implemented as a
 * double linked list. The queue has a head and back and each node in the queue
 * has two references, previous and next which point to other nodes. Functions
  * enqueue and dequeue helps to add and delete nodes from the queue.
 */

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Consumer;

public class L1Assignment3 {

    public static void main(String[] args) {
        DoubleLinkedList<Integer> queue = new DoubleLinkedList();

        System.out.println("********Assignment 3********\n" +
            "Enter integers of your own choice \n");

        Scanner sc = new Scanner(System.in);

        while(sc.hasNextInt()) {
            queue.enqueue(sc.nextInt());
            System.out.println("printing content in queue: " + queue.toString() +
                    "\nTo continue, enter more integers. To interrupt, enter a char");

        }


        System.out.println("Lets dequeue one element!");
        queue.dequeue();
        System.out.println("This is now the list: ");
        System.out.println(queue.toString());

    }


    // Class for double linked list
    static class DoubleLinkedList<Item> implements Iterable<Item> {
        Node<Item> head;
        Node<Item> back;
        int size;

        // Constructor
        public DoubleLinkedList() {
            head = null;
            back = null;
            size = 0;
        }

        public void reset() {
          head = null;
          back = null;
          size = 0;
        }

        public boolean isEmpty() {
          return size == 0;
        }

        // Function for adding a node to the front of the queue
        public void enqueue(Item input) {
            Node newHead = new Node(input);
            newHead.next = head;
            if(!isEmpty()) head.prev = newHead;
            head = newHead;
            if(back == null) back = newHead;
            size++;

        }

        // Function for removing a node at the back of the queue
        public void dequeue() {
          if(isEmpty()) System.exit(0);
          Node oldBack = back;
          back = oldBack.prev;
          back.next = null;
          size--;
        }


        // Funtion toString creates a string with a string with all nodes.
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node iterator = head;

            for(int i = 0; i < size; i++) {
                if(i + 1 == size) {
                    sb.append("[" + iterator.data + "]");
                }
                else {
                    sb.append("[" + iterator.data + "], ");
                }
                iterator = iterator.next;
            }


            return sb.toString();
    }

        @Override
        public Iterator<Item> iterator() {
            return new QueueIterator<Item>(head, back);
        }
    }

    static class QueueIterator<Item> implements ListIterator<Item> {
        private Node<Item> current;
        private Node<Item> currentLast;

        public QueueIterator(Node<Item> first, Node<Item> last) {
            current = first;
            currentLast = last;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.data;
            current = current.next;
            return item;
        }

        public boolean hasPrevious() {
            return currentLast != null;
        }

        public Item previous() {
            Item item = currentLast.data;
            currentLast = currentLast.prev;
            return item;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void set(Item arg0) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void add(Item arg0) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    // Class for node
    static class Node<Item> {
        Node<Item> next;
        Node<Item> prev;
        Item data;

        // Constructor
        public Node(Item item) {
            data = item;
        }

        // Constructor for more arguments
        public Node(Item item, Node nextNode, Node prevNode) {
            next = nextNode;
            prev = prevNode;
            data = item;
        }

        //set method for next node
        public void setNext(Node<Item> nextNode) {
            next = nextNode;
        }

        //set method for previous node
        public void setPrev(Node<Item> prevNode) {
            prev = prevNode;
        }

        //get method for next node
        public Node getNext() {
            return next;
        }

        //method for getting previous node.
        public Node getPrev() {
            return prev;
        }

    }

}
