/**
 * Assignment 7 in Lab 2
 *
 * Purpose of progream: To implement a linked list where each node in the list
 * holds an int value, and after each insertion, the list should be in ascending
 * order.
 *
 * Execution of program:
 *
 * *******Assignment7*******
 * Inserting elements elements in following order 2, 7, 5, 3, 6, 0, 11
 *
 * Now these values will be inserted in order:
 * [2]
 * [2], [7]
 * [2], [5], [7]
 * [2], [3], [5], [7]
 * [2], [3], [5], [6], [7]
 * [0], [2], [3], [5], [6], [7]
 * [0], [2], [3], [5], [6], [7], [11]
 */
public class L2Assignment7 {

    // Node class which stores an int and has a next and prev reference
    static class Node {
        Node next;
        Node prev;
        int data;

        // Constructor
        public Node() {
            next = null;
            prev = null;
            data = 0;
        }

        /**
         * Constructor with input as the internal int stored and references
         * to next and previous node.
         * @param val
         * @param nextNode
         * @param prevNode
         */
        public Node(int val, Node nextNode, Node prevNode) {
            data = val;
            next = nextNode;
            prev = prevNode;
        }

        /**
         * Constructor with input as the internal int value stored.
         * @param val
         */
        public Node(int val) {
            next = null;
            prev = null;
            data = val;
        }

        // Set method for referencing next node
        public void setNext(Node nextNode) {
            next = nextNode;
        }

        // Set method for referencing previous node
        public void setPrev(Node prevNode) {
            prev = prevNode;
        }

        // Set method for the internal int value
        public void setData(int val) {
            data = val;
        }
    }

    // Class for the doubly linked FIFO queue which has references to head, tail
    // and for the number of elements in the list
    static class DoubleLinkedList {
        Node head;
        Node tail;
        int size;

        public DoubleLinkedList() {
            head = null;
            tail = null;
            size = 0;
        }

        /**
         * Method which finds and places a input node in its right position so
         * that the list is in ascending order.
         * @param node - node to be inserted
         */
        public void enqueueInOrder(Node node) {

            if(isEmpty()) {
                enqueue(node);
                return;
            }

            Node iterator = head;
            int i = 0;
            while(iterator != null) {

                if(iterator.data >= node.data) {
                    break;
                }

                iterator = iterator.next;
                i++;
            }

            if(i == 0) {
              enqueue(node);
            }
            else if(i == size) {
              enqueueLast(node);
            }
            else {
              Node temp = iterator.prev;
              node.prev = temp;
              node.next = iterator;
              temp.next = node;
              iterator.prev = node;
              size++;
            }

        }

        // Function for adding a node to the front of the queue
        public void enqueue(Node input) {
            input.next = head;
            if(!isEmpty()) head.prev = input;
            head = input;
            if(tail == null) tail = input;
            size++;

        }

        public void enqueueLast(Node input) {
            Node oldTail = tail;
            input.prev = oldTail;
            if(tail != null) oldTail.next = input;
            tail = input;
            size++;
        }

        // Function for removing a node at the tail of the queue
        public void dequeue() {
          if(isEmpty()) System.exit(0);
          Node oldTail = tail;
          tail = oldTail.prev;
          tail.next = null;
          size--;
        }

        public boolean isEmpty() {
          return head == null;
        }

        public String printList() {
            StringBuilder sb = new StringBuilder();
            Node node = head;

            int i = 0;
            while(node != null) {
                if(node.next == null){
                    sb.append("[" + node.data + "]");
                }
                else {
                    sb.append("[" + node.data + "], ");
                }
                node = node.next;
                i++;
            }
            return sb.toString();
        }



    }


    public static void main(String[] args) {
        DoubleLinkedList list = new DoubleLinkedList();

        System.out.println("*******Assignment7*******\n"
                + "Inserting elements elements in following order 2, 7, 5, 6, 1, 0\n" +
                "\nNow these values will be inserted in order: ");

        list.enqueueInOrder(new Node(2));
        System.out.println(list.printList());
        list.enqueueInOrder(new Node(7));
        System.out.println(list.printList());
        list.enqueueInOrder(new Node(5));
        System.out.println(list.printList());

        list.enqueueInOrder(new Node(3));
        System.out.println(list.printList());
        list.enqueueInOrder(new Node(6));
        System.out.println(list.printList());
        list.enqueueInOrder(new Node(0));
        System.out.println(list.printList());
        list.enqueueInOrder(new Node(11));
        System.out.println(list.printList());

    }
}
