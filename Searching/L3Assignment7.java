/************************
 * Assignment 7 in course ID1020 for lab 3 at KTH
 *
 * Program reads words from StdIn and measures the time intervals for put function and get function from
 * hash tables whereas one uses linear probing and the other, separate chaining for handling collisions.

	 Example run
	 java -cp algs4.jar: L3Assignment7 0 100 < tale.txt
	 Time for put function was: 0.266
	 Time for get function was: 0.001

	 Number of total words read: 3907
	 Number of unique words read: 100
 ***********************/
import java.util.LinkedList;
import edu.princeton.cs.algs4.*;
//import java.util.NoSuchElementException;

public class L3Assignment7 {

	// switch between measuring separate chaining and linear probing
	private static SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
	//private static LinearProbingHashST<String, Integer> st = new LinearProbingHashST<String, Integer>();

	public static void main(String[] args) {

		// key-length cutoff and limit for amount of words to read in
		int minlen = Integer.parseInt(args[0]);
		int limit = Integer.parseInt(args[1]);
		int uniqueWordCount = 0;
		int totWordCount = 0;

		Stopwatch timer1 = new Stopwatch();

		while (!StdIn.isEmpty()) { // Build symbol table and count frequencies.

			String word = StdIn.readString().toLowerCase();

			if (word.length() < minlen) {
				continue; // Ignore short keys.
			}
			else if (st.contains(word)){
				st.put(word, st.get(word) + 1);
				totWordCount++;
			}
			else if (!st.contains(word) && uniqueWordCount < limit) {
				st.put(word, 1);
				uniqueWordCount++;
				totWordCount++;
			}
		}

		double time1 = timer1.elapsedTime();

		// Find a key with the highest frequency count.
		String max = "";
		st.put(max, 0);

		Stopwatch timer2 = new Stopwatch();

		for (String word : st.keys()) {
			if (st.get(word) > st.get(max)) {
				max = word;
			}
		}

		double time2 = timer2.elapsedTime();


		StdOut.println("Time for put function was: " + time1);
		StdOut.println("Time for get function was: " + time2);
		StdOut.println();
		StdOut.println("Number of total words read: " + totWordCount);
		StdOut.println("Number of unique words read: " + uniqueWordCount);

	}

	public static class SeparateChainingHashST<Key, Value> {
	    private static final int INIT_CAPACITY = 4;

	    private int n;                                // number of key-value pairs
	    private int m;                                // hash table size
	    private SequentialSearchST<Key, Value>[] st;  // array of linked-list symbol tables


	    public SeparateChainingHashST() {
	        this(INIT_CAPACITY);
	    }

	    public SeparateChainingHashST(int m) {
	        this.m = m;
	        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[m];
	        for (int i = 0; i < m; i++)
	            st[i] = new SequentialSearchST<Key, Value>();
	    }

	    private void resize(int chains) {
	        SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<Key, Value>(chains);
	        for (int i = 0; i < m; i++) {
	            for (Key key : st[i].keys()) {
	                temp.put(key, st[i].get(key));
	            }
	        }
	        this.m  = temp.m;
	        this.n  = temp.n;
	        this.st = temp.st;
	    }

	    private int hash(Key key) {
	        return (key.hashCode() & 0x7fffffff) % m;
	    }

	    public int size() {
	        return n;
	    }

	    public boolean isEmpty() {
	        return size() == 0;
	    }

	    public boolean contains(Key key) {
	        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
	        return get(key) != null;
	    }

	    public Value get(Key key) {
	        if (key == null) throw new IllegalArgumentException("argument to get() is null");
	        int i = hash(key);
	        return st[i].get(key);
	    }

	    public void put(Key key, Value val) {
	        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
	        if (val == null) {
	            return;
	        }

	        if (n >= 10*m) resize(2*m);

	        int i = hash(key);
	        if (!st[i].contains(key)) n++;
	        st[i].put(key, val);
	    }

	    public Iterable<Key> keys() {
	        Queue<Key> queue = new Queue<Key>();
	        for (int i = 0; i < m; i++) {
	            for (Key key : st[i].keys())
	                queue.enqueue(key);
	        }
	        return queue;
	    }
	}

	public static class LinearProbingHashST<Key, Value> {
	    private static final int INIT_CAPACITY = 4;

	    private int n;           // number of key-value pairs in the symbol table
	    private int m;           // size of linear probing table
	    private Key[] keys;      // the keys
	    private Value[] vals;    // the values

	    public LinearProbingHashST() {
	        this(INIT_CAPACITY);
	    }

	    public LinearProbingHashST(int capacity) {
	        m = capacity;
	        n = 0;
	        keys = (Key[])   new Object[m];
	        vals = (Value[]) new Object[m];
	    }

	    public int size() {
	        return n;
	    }

	    public boolean isEmpty() {
	        return size() == 0;
	    }

	    public boolean contains(Key key) {
	        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
	        return get(key) != null;
	    }

	    private int hash(Key key) {
	        return (key.hashCode() & 0x7fffffff) % m;
	    }

	    private void resize(int capacity) {
	        LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<Key, Value>(capacity);
	        for (int i = 0; i < m; i++) {
	            if (keys[i] != null) {
	                temp.put(keys[i], vals[i]);
	            }
	        }
	        keys = temp.keys;
	        vals = temp.vals;
	        m    = temp.m;
	    }

	    public void put(Key key, Value val) {
	        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

	        if (val == null) {
	            return;
	        }

	        if (n >= m/2) resize(2*m);

	        int i;
	        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
	            if (keys[i].equals(key)) {
	                vals[i] = val;
	                return;
	            }
	        }
	        keys[i] = key;
	        vals[i] = val;
	        n++;
	    }

	    public Value get(Key key) {
	        if (key == null) throw new IllegalArgumentException("argument to get() is null");
	        for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
	            if (keys[i].equals(key))
	                return vals[i];
	        return null;
	    }

	    public Iterable<Key> keys() {
	        Queue<Key> queue = new Queue<Key>();
	        for (int i = 0; i < m; i++)
	            if (keys[i] != null) queue.enqueue(keys[i]);
	        return queue;
	    }


	}
}
