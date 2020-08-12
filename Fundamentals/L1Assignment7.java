/**
 * Assignment 7
 *
 * Purpose of program: To implement a filter which reads from stdin detects
 * whether the types of parantheses {},[],() are balanced.
 */

import java.io.IOException;
import java.util.Scanner;


public class L1Assignment7 {
    private static StringBuilder collection = new StringBuilder();
    private static int bracketLeft, bracketRight;
    private static int curlyLeft, curlyRight;
    private static int paranLeft, paranRight;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);


        System.out.println("********Assignment 7********\n" +
            "Write a line and finish with ENTER!\n");

        String s = filter(sc.nextLine());

        checkBalanced(s);
    }

    // Filter which appends a character when a type {},[],() is found.
    public static String filter(String input) {

        String s = "";

        for(int i = 0; i < input.length(); i++) {

            char ch = input.charAt(i);
            if(ch == '(' || ch == ')') {
                s += String.valueOf(ch);
            }
            else if(ch == '{' || ch == '}') {
                s += String.valueOf(ch);
            }
            else if(ch == '[' || ch == ']') {
                s += String.valueOf(ch);
            }
        }

        return s;
    }

    // Functionality which counts encounters of {},[],() in a string and prints
    // out whether those are balanced or not.
    public static void checkBalanced(String input) {

        for(int i = 0; i < input.length(); i++) {
          switch(input.charAt(i)) {
            case '(' :
                paranLeft++;
                break;

            case ')' :
                paranRight++;
                break;

            case '[' :
                bracketLeft++;
                break;

            case ']' :
                bracketRight++;
                break;

            case '{' :
                curlyLeft++;
                break;

            case '}' :
                curlyRight++;
                break;
          }
        }


        if(paranRight == 0 && paranLeft == 0) {
            System.out.println("Parantheses cannot be found");

        }
        else if(paranRight == paranLeft) {
            System.out.println("paranthesises are balanced");
        }

        if(bracketRight == 0 && bracketLeft == 0) {
            System.out.println("brackets cannot be found");
        }
        else if(bracketRight == bracketLeft) {
            System.out.println("bracket are balanced");
        }

        if(curlyRight == 0 && curlyLeft == 0) {
            System.out.println("Curly brackets cannot be found");
        }
        else if(curlyRight == curlyLeft) {
            System.out.println("curly brackets are balanced");
        }
    }

}
