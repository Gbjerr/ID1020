/**
 *
 * Assignment 3 for extra lab
 *
 * Program reads a text file as argument and counts the frequency of each word by
 * storing words, together with their frequency into a linear probing hash table.
 * Then by retrieving the arrays from the hash table and sorting these according
 * to freqeuncy, User can thereafter ask: "what is the k'th most frequent word?"
 * and "what are the k+n'th most frequent words?"
 */

import edu.princeton.cs.algs4.*;
import java.util.*;


public class LEAssignment3Ver2 {
	private static String [] words;
	private static int [] freq;

	public static void main(String[] args) {

		LinearProbingHashST ht = new LinearProbingHashST();

		In in = new In(args[0]);
		int limit = Integer.parseInt(args[1]);
		int minlen = 0; // key-length cutoff
		int uniqueWordCount = 0;
		int totWordCount = 0;


		// using FreuencyCounter from the book to read from text file and store to
		// hash table
		while (!in.isEmpty()) { // Build symbol table and count frequencies.

				if(totWordCount > limit) {
						break;
				}
				String word = in.readString().toLowerCase();

				if (word.length() < minlen) {
						continue; // Ignore short keys.
				}
				else if (ht.contains(word)){
						ht.put(word, ht.get(word) + 1);
						totWordCount++;

				}
				else if (!ht.contains(word)) {
						ht.put(word, 1);
						uniqueWordCount++;
						totWordCount++;
				}
		}
		System.out.println("Total of " + uniqueWordCount + " uniue words was read from file");
		System.out.println("Total of " + totWordCount + " words was read from file \n");


		// retrieving keys and values from hash table
		words = ht.keys;
		freq = ht.vals;
		removeNulls();

		System.out.println("Size of array to be sorted: " + freq.length);

		// starting sort of keys and values and measuring time to do so
		long start = System.nanoTime();
		iterativeQuickSort(freq, 0, freq.length);
		long end = System.nanoTime();
		double time1 = (((double) (end - start)) * (Math.pow(10, -9)));
		System.out.println("took " + time1 + " seconds to sort array of " + ht.size() + " frequencies");

		// using scanner class for user to showcase the n'th most common word
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter k'th most frequent element of your own choice...");
		int k = sc.nextInt();
		String s = "";
		System.out.println("k'th most frequent element is: " + words[words.length - k]);

		// making it possible for user to continously show case the n'th to n+x'th most common words
		while(!s.equals("-1")) {
				System.out.println("Enter k'th to n'th most frequent elements of your own choice...");
				int n = sc.nextInt();
				k = sc.nextInt();
				printRankedWords(k, n);

				System.out.println("Press -1 to interrupt");
				s = sc.next();
		}


	}

	// method prints the k to n'th most freqeunt words from the text
	public static void printRankedWords(int k, int n) {
		for(int i = n; i < k + 1; i++) {
			System.out.println(i + ": " + words[words.length - i] + " - has "
					+ freq[freq.length - i] + " occurrences");
		}
	}

	public static void printstuff() {

	}

	// method removes nulls from the array of strings and array using arraycopy()
	// function, threreby reducing the size of the arrays and reducing the chance of
	// getting worst time complexity for quickSort
	public static void removeNulls() {

		int targetIndex = 0;
		for(int sourceIndex = 0;  sourceIndex < freq.length;  sourceIndex++) {
		    if(freq[sourceIndex] != 0 )	{
		        freq[targetIndex] = freq[sourceIndex];
						words[targetIndex] = words[sourceIndex];
						targetIndex++;
				}
		}
		int[] newArray = new int[targetIndex];
		String[] temp = new String[targetIndex];
		System.arraycopy( freq, 0, newArray, 0, targetIndex );
		System.arraycopy( words, 0, temp, 0, targetIndex );
		freq = newArray;
		words = temp;
	}


	// iterative quickSort method using LIFO stack data structure
	public static void iterativeQuickSort(int[] arr, int start, int end) {
		Stack stack = new Stack();
		stack.push(0);
		stack.push(end);

		while(!stack.isEmpty()) {
			end = stack.pop();
			start = stack.pop();
			if(end - start < 2){
				continue;
			}
			else if(end - start < 36) {
			insertionSort(arr, start, end);
				continue;
			}


				int p = start + (end - start)/2;
				p = partition(arr, p, start, end);

				stack.push(p + 1);
				stack.push(end);

				stack.push(start);
				stack.push(p - 1);

		}
	}

	private static int partition(int[] input, int position, int start, int end) {
		int l = start;
		int h = end - 2;
		int piv = input[position];
		swap(input, position, end - 1);

		while (l < h) {
			if (input[l] < piv) {
				l++;
			}
			else if (input[h] >= piv) {
				h--;
			} else {
				swap(input, l, h);
			}
		}
		int idx = h;
		if (input[h] < piv) idx++;
		swap(input, end - 1, idx);
		return idx;
	}

	public static void swap(int[] arr, int i, int j) {
		String tempString = words[i];
		words[i] = words[j];
		words[j] = tempString;
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static void insertionSort(int[] a, int start, int end) {
		int j;
    for (int p = start; p < end; p++) {
        int tmp = a[p];
				String tmpString = words[p];
        for(j = p; j > 0 && tmp < a[j - 1]; j--) {
            a[j] = a[j-1];
						words[j] = words[j-1];
        }
        a[j] = tmp;
				words[j] = tmpString;
    }
	}


	// linear probing hash table used to store words and frequency as keys and values
	public static class LinearProbingHashST {
		private static final int INIT_CAPACITY = 4;

		private int n;           // number of key-value pairs in the symbol table
		private int m;           // size of linear probing table
		private String[] keys;      // the keys
		private int[] vals;    // the values



		/**
		 * Initializes an empty symbol table.
		 */
		public LinearProbingHashST() {
				this(INIT_CAPACITY);
		}

		/**
		 * Initializes an empty symbol table with the specified initial capacity.
		 *
		 * @param capacity the initial capacity
		 */
		public LinearProbingHashST(int capacity) {
				m = capacity;
				n = 0;
				keys = new String[m];
				vals = new int[m];
		}

		/**
		 * Returns the number of key-value pairs in this symbol table.
		 *
		 * @return the number of key-value pairs in this symbol table
		 */
		public int size() {
				return n;
		}


		/**
		 * Returns true if this symbol table is empty.
		 *
		 * @return {@code true} if this symbol table is empty;
		 *         {@code false} otherwise
		 */
		public boolean isEmpty() {
				return size() == 0;
		}

		/**
		 * Returns true if this symbol table contains the specified key.
		 *
		 * @param  key the key
		 * @return {@code true} if this symbol table contains {@code key};
		 *         {@code false} otherwise
		 * @throws IllegalArgumentException if {@code key} is {@code null}
		 */
		public boolean contains(String key) {
				if (key == null) throw new IllegalArgumentException("argument to contains() is null");
				return get(key) != 0;
		}

		// hash function for keys - returns value between 0 and M-1
		private int hash(String key) {
				return (key.hashCode() & 0x7fffffff) % m;
		}

		// resizes the hash table to the given capacity by re-hashing all of the keys
		private void resize(int capacity) {
				LinearProbingHashST temp = new LinearProbingHashST(capacity);
				for (int i = 0; i < m; i++) {
						if (keys[i] != null) {
								temp.put(keys[i], vals[i]);
						}
				}
				keys = temp.keys;
				vals = temp.vals;
				m    = temp.m;
		}

		/**
		 * Inserts the specified key-value pair into the symbol table, overwriting the old
		 * value with the new value if the symbol table already contains the specified key.
		 * Deletes the specified key (and its associated value) from this symbol table
		 * if the specified value is {@code null}.
		 *
		 * @param  key the key
		 * @param  val the value
		 * @throws IllegalArgumentException if {@code key} is {@code null}
		 */
		public void put(String key, int val) {
				if (key == null) throw new IllegalArgumentException("first argument to put() is null");

				// double table size if 50% full
				if (n >= m/2) resize(2*m);

				int i;
				for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
						if (keys[i].equals(key)) {
								vals[i] = val;
								return;
						}
				}
				//System.out.println(key + " placed at space " + i + " in array of keys");
				keys[i] = key;
				vals[i] = val;
				n++;
		}

		/**
		 * Returns the value associated with the specified key.
		 * @param key the key
		 * @return the value associated with {@code key};
		 *         {@code null} if no such value
		 * @throws IllegalArgumentException if {@code key} is {@code null}
		 */
		public int get(String key) {
				if (key == null) throw new IllegalArgumentException("argument to get() is null");
				for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
						if (keys[i].equals(key))
								return vals[i];
				return 0;
		}

		// integrity check - don't check after each put() because
		// integrity not maintained during a delete()
		private boolean check() {

				// check that hash table is at most 50% full
				if (m < 2*n) {
						System.err.println("Hash table size m = " + m + "; array size n = " + n);
						return false;
				}

				// check that each key in table can be found by get()
				for (int i = 0; i < m; i++) {
						if (keys[i] == null) continue;
						else if (get(keys[i]) != vals[i]) {
								System.err.println("get[" + keys[i] + "] = " + get(keys[i]) + "; vals[i] = " + vals[i]);
								return false;
						}
				}
				return true;
		}
}



// LIFO stack data structure
public static class Stack {
    private Node first;     // top of stack
    private int n;          // size of the stack

    // helper linked list class
    private class Node {
        private int value;
        private Node next;
    }

    /**
     * Initializes an empty stack.
     */
    public Stack() {
        first = null;
        n = 0;
    }

    /**
     * Returns true if this stack is empty.
     *
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this stack.
     *
     * @return the number of items in this stack
     */
    public int size() {
        return n;
    }

    /**
     * Adds the item to this stack.
     *
     * @param  item the item to add
     */
    public void push(int val) {
        Node oldfirst = first;
        first = new Node();
        first.value = val;
        first.next = oldfirst;
        n++;
    }

    /**
     * Removes and returns the item most recently added to this stack.
     *
     * @return the item most recently added
     * @throws NoSuchElementException if this stack is empty
     */
    public int pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int val = first.value;        // save item to return
        first = first.next;            // delete first node
        n--;
        return val;                   // return the saved item
    }
	}

}
