/**
 * Assignment 2
 *
 * Purpose of program: To read characters from stdin and print them in reverse order.
 * The program allows two ways of doing this, by a recursive function and one iterative
 * version. The recursive and iterative methods are using a stack as ADT.
 */
import java.util.Scanner;


public class L1Assignment2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LinkedStack stack1 = new LinkedStack();
        LinkedStack stack2 = new LinkedStack();

        System.out.println("Assignment 2\n");

        System.out.println("Enter characters of your own choice (recursive version)!\n");
        String input1 = sc.nextLine();
        System.out.println("reversing characters");
        recursiveVersion(input1, stack1);
        System.out.println("Here is your characters in reverse order:");

        stack1.printList();
        System.out.println("----------------------------------------------\n");


        System.out.println("Enter characters of your own choice (iterative version)!\n");
        String input2 = sc.nextLine();
        System.out.println("reversing characters\n");
        iterativeVersion(input2, stack2);
        System.out.println("Here is your characters in reverse order:");
        stack2.printList();

    }

    // Method reading string and pushing each character to stack and then
    // printing out the stack.
    public static void iterativeVersion(String str, LinkedStack list) {
        if(str.length() < 0) {
            return;
        }

        for(int i = 0; i < str.length(); i++) {
            list.push(new Node(str.charAt(i)));
        }

        Node iterator = list.head;
        for(int i = 0; i < str.length(); i++) {
            iterator = iterator.next;
        }
    }

    // Method reading string (previously read from stdin), then pushes the
    // first element to stack, and then calls itself with
    public static void recursiveVersion(String str, LinkedStack list) {
        // Base case
        if(str.length() < 1) {
            return;
        }

        list.push(new Node(str.charAt(0)));
        recursiveVersion(str.substring(1, str.length()), list);

    }

    // Class for node
    static class Node {
        char ch;
        Node next;

        // Counstructor for node
        public Node(char input) {
            ch = input;
            next = null;
        }

        public void setNext(Node input) {
            next = input;
        }
    }

    // Class for the stack, implemented as a single linked list.
    static class LinkedStack {
        Node head;
        int size;

        // Constructor for stack
        public LinkedStack() {
            head = null;
            size = 0;
        }

        public boolean ifEmpty() {
            return head == null;
        }

        public void printList() {
            Node node = head;
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < size; i++) {
                if(i+1 == size) {
                   sb.append("[" + node.ch + "]");
                }
                else {
                    sb.append("[" + node.ch + "], ");
                }
                node = node.next;
            }

            System.out.println(sb.toString());
        }

        public void push(Node input) {
            if(ifEmpty()) {
                head = input;
                size++;
                return;
            }

            Node oldHead = head;
            input.next = oldHead;
            head = input;
            size++;
        }
    }



}
