/************************
 * Assignment 6 in course ID1020 for lab 3 at KTH
 *
 * Program reads words from StdIn and places words into a Linked list from java's own library.
 * User can enter a word, by entering word as argument and then program will search up at what position
 * these words are.
 	 Example run:
	 java -cp algs4.jar: L3Assignment6 strange < tale.txt
	 675

	 3041

	 3424
 ***********************/
import java.util.Scanner;
import java.util.LinkedList;
import edu.princeton.cs.algs4.*;

public class L3Assignment6 {

	public static void main(String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		String target = args[0];


		while(!StdIn.isEmpty()) {
			list.add(StdIn.readString().toLowerCase());
		}


		// reference i keeps track of position of word when iterating through list of words.
		int i = 0;
		for(String iterator : list) {

			if(iterator.equals(target)) {
				StdOut.println(i + "\n");
			}
			i++;
		}

	}
}
