/**
 * Assignment 2 for lab 1
 *
 * Purpose of program: To read characters from stdin and print them in reverse order.
 * The program allows two ways of doing this, by a recursive function and one iterative
 * version. The iterative method is using a stack.
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.EmptyStackException;
import java.util.Iterator;

public class L1Assignment2 {


    public static void main(String[] args) {

        System.out.println("********Assignment 2********\n");

        LinkedStack<Character> stack = new LinkedStack<Character>();

        // testing push function
        System.out.println("\nTest: pushing following nodes containing values given in the order: ");
        System.out.println("b  p  q  z  m  o");

        stack.push(new Node('b'));
        stack.printList();
        stack.push(new Node('p'));
        stack.printList();
        stack.push(new Node('q'));
        stack.printList();
        stack.push(new Node('z'));
        stack.printList();
        stack.push(new Node('m'));
        stack.printList();
        stack.push(new Node('o'));
        stack.printList();


        String expectedRes = "[o], [m], [z], [q], [p], [b]";
        System.out.printf("\n\nExpected string representation of queue: %s\n", expectedRes);

        System.out.println("Comparing expected string with string representation...");
        if(stack.toString().equals(expectedRes)) {
            System.out.println("\nTrue! The string result is equal to the expected result");
            System.out.printf("%s --is equal to-- %s", expectedRes, stack.toString());
        }
        else {
            System.out.println("\nFalse! The string result is not equal to the expected result");
            System.out.printf("%s --is not equal to-- %s", expectedRes, stack.toString());
        }

        // testing the iterator
        System.out.println("\n\nTest: creating Iterator object of stack and iterating through it ");
        Iterator it = stack.iterator();

        while (it.hasNext()) {
            System.out.printf("[%s], ", it.next());
        }

        // testing the pop function when the stack is empty
        System.out.println("\n\nTest: attempt to pop an element off an empty stack: ");

        stack = new LinkedStack<Character>();
        try {
            stack.pop();
        }
        catch (EmptyStackException ex) {
            System.out.println("You tried to pop an element off an empty stack.");
        }

        // user input
        StdOut.println("\n\nRecursive version");
        recursiveVersion();

        StdOut.println("\nIterative version");

        iterativeVersion();


    }


    // Method reading chars and pushing each character to stack and then
    // popping each element out of the stack and prints it, thus reversed
    public static void iterativeVersion() {
        LinkedStack<Character> stack = new LinkedStack<Character>();

        char chTemp;
        while((chTemp = StdIn.readChar()) != '\n') {
            Node temp = new Node(chTemp);
            stack.push(temp);
        }

        for (int i = 0; !stack.isEmpty(); i++) {
            StdOut.printf("[%c], ", stack.pop().item);
        }

    }

    // function reads from stdIn until newline character is reached, and prints letters reversed
    public static void recursiveVersion() {

        char chTemp;
        if((chTemp = StdIn.readChar()) != '\n') {

            recursiveVersion();
            StdOut.printf("[%c], ", chTemp);
        }
    }

    // Class for node
    private static class Node<Item> {
        private Item item;
        private Node next;

        // Counstructor for node
        public Node(Item input) {
            item = input;
            next = null;
        }

        // method returning data as a String
        public String toString() {
            return item.toString();
        }
    }

    // Class for the stack, implemented as a single linked list.
    private static class LinkedStack<Item> implements Iterable<Item>{
        private Node head;
        private int size;

        // Constructor for stack
        public LinkedStack() {
            head = null;
            size = 0;
        }

        public boolean isEmpty() {
            return head == null;
        }

        // adds element to the front of list
        public void push(Node input) {
            if (isEmpty()) {
                head = input;
                size++;
                return;
            }

            Node oldHead = head;
            input.next = oldHead;
            head = input;
            size++;
        }

        // removes the element in the front of list
        public Node pop() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }

            Node newHead = head.next;
            Node oldHead = head;
            head = null;
            head = newHead;
            size--;
            return oldHead;
        }

        // method printing representation of current list
        public void printList() {
            Node current = head;

            System.out.println();
            while (current != null) {
                if (current.next == null)
                    System.out.printf("[" + current.toString() + "]");
                else
                    System.out.printf("[" + current.toString() + "], ");
                current = current.next;
            }

        }

        // returns string representation of list
        public String toString() {
            String res = "";

            Node current = head;

            while (current != null) {
                if (current.next == null)
                    res += "[" + current.item.toString() + "]";
                else
                    res += "[" + current.item.toString() + "], ";

                current = current.next;
            }

            return res;
        }

        @Override
        public Iterator<Item> iterator() {
            return new StackIterator();
        }

        private class StackIterator implements Iterator<Item> {
            Node current = head;

            // checks if there's a next element in line
            @Override
            public boolean hasNext() {
                return current != null;
            }

            // iterates one position to next element
            @Override
            public Item next() {
                if(current == null) return null;
                Item val = (Item) current.item;
                current = current.next;
                return val;
            }

        }
    }


}
