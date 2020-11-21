/**
 * Assignment 7 for lab 1
 *
 * Purpose of program: To implement and test a filter which reads characters from stdin and detects
 * whether the types of brackets: {},[],() are balanced. This is done by reading characters, detecting open
 * brackets, pushing them onto a stack. When a closed bracket is read the algorithm pops off element off
 * stack and checks if it's a open bracket of same sort.

 Compile and run tests by following:

     java -cp algs4.jar: L1Assignment7 < L1A7test1.txt
     ********Assignment 7********

     Brackets are balanced.

     java -cp algs4.jar: L1Assignment7 < L1A7test2.txt
     ********Assignment 7********

     Brackets are NOT balanced.

     java -cp algs4.jar: L1Assignment7 < L1A7test3.txt
     ********Assignment 7********

     You tried to pop an element off an empty stack.
     Brackets are NOT balanced.

 */

import edu.princeton.cs.algs4.StdIn;

import java.util.EmptyStackException;
import java.util.Iterator;

public class L1Assignment7 {
    public static void main(String[] args) {

        System.out.println("********Assignment 7********\n");

        LinkedStack<Character> list = new LinkedStack<Character>();

        char c;
        boolean isBalanced = true;
        // reads characters from stdIn
        while(StdIn.hasNextChar()) {

            c = StdIn.readChar();

            // push to stack if character is a open bracket
            if(isOpenBracket(c)) {
                list.push(new Node(c));
            }
            // pop element off stack if character is a closed bracket
            else if(isClosedBracket(c)) {

                // place holder for character
                Character ch = 'a';
                try {
                    ch = (Character) list.pop().item;
                }
                catch(EmptyStackException ex) {
                    System.out.println("You tried to pop an element off an empty stack.");
                }

                // if popped element is not an open bracket of same type as the closed one, break out from loop
                if(!isMatchBracket(ch, c)) {

                    isBalanced = false;
                    break;
                }
            }
        }

        if(isBalanced) {
            System.out.println("Brackets are balanced.");
        }
        else {
            System.out.println("Brackets are NOT balanced.");
        }

    }

    // method checks if open bracket, has a closed bracket counter part
    public static boolean isMatchBracket(char c, char c1) {
        switch (c) {
            case '(':
                return c1 == ')';
            case '[':
                return c1 == ']';
            case '{':
                return c1 == '}';
            default:
                return false;
        }
    }

    // method checks if a character is a open bracket
    public static boolean isOpenBracket(char input) {
        return input == '(' || input == '[' || input == '{';
    }

    // method checks if a character is a closed bracket
    public static boolean isClosedBracket(char input) {
        return input == ')' || input == ']' || input == '}';
    }

    // implementation of a single linked list.
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

        // places input element at the front of list
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

        // removes and returns the element at the front of list
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

            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            // returns current item and iterates one position
            @Override
            public Item next() {
                if(current == null) return null;
                Item val = (Item) current.item;
                current = current.next;
                return val;
            }

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

    }


}
