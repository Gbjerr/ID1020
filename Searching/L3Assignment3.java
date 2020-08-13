/************************
 * Assignment 3 for lab 3
 *
 * Program reads a limited number of words from StdIn and measures the frequency of these and
 * places them into an ST, where the user then can set two arguments "front" and "back".
 * Front is the word with N'th most frequencies and end is the word with N + x'th most frequencies.
 * The program then prints the words from N to (N + x) according to order of frequencies in the ST.

 	 Example run:
	 java -cp algs4.jar: L3Assignment3 0 100 1 10 < tale.txt
	 BST initialized!
	 1. the
	 2. and
	 3. of
	 4. a
	 5. to
	 6. in
	 7. it
	 8. with
	 9. i
	 10. you

 ***********************/

import edu.princeton.cs.algs4.*;
public class L3Assignment3 {

	// switch between the data structures
	private static BST<String, Integer> st = new BST<String, Integer>();
	//private BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();

	public static void main(String[] args) {

		// key-length cutoff and limit for amount of words to read in
		int minlen = Integer.parseInt(args[0]);
		int limit = Integer.parseInt(args[1]);
		int front = Integer.parseInt(args[2]) ;
		int back = Integer.parseInt(args[3]);

		int wordCount = 0;

		if(args.length < 4) {
			System.out.println("Four arguments should be entered");
			System.exit(0);
		}

		// using FreuencyCounter from the book, which builds st and counts frequencies
		while (!StdIn.isEmpty()) {

			String word = StdIn.readString().toLowerCase();

			if (word.length() < minlen) {
				continue; // Ignore short keys.
			}
			else if (st.contains(word)){
				st.put(word, st.get(word) + 1);
			}
			else if (!st.contains(word) && wordCount < limit) {
				st.put(word, 1);
				wordCount++;
			}
		}

		printQueriedWords(front, back);

	}

	private static void printQueriedWords(int front, int back) {

		// arguments front and back marks the collection of words to be printed in order according to
		// the frequency.
		String [] freqList = new String[back];

		String maxRef = "";
		st.put(maxRef, 0);

		// finding the word with most frequency
		for (String word : st.keys()) {
			if (st.get(word) > st.get(maxRef)) {
				maxRef = word;
			}
		}

		// placing the word with highest frequency at the front of array and resets maxRef
		freqList[0] = maxRef;
		maxRef = "";
		st.put(maxRef, 0);

		// iterates through the ST and finds word with highest frequency that has less frequency than
		// current element i in our array.
		for (int i = 1; i < back; i++) {

        for (String word : st.keys()) {
            if (st.get(word) > st.get(maxRef) && st.get(word) < st.get(freqList[i - 1]))
                maxRef = word;
            }

            freqList[i] = maxRef;
            maxRef = "";
        }

				// prints from front to back of the elements in our array.
        for (int i = front - 1; i < back; i++) {
            System.out.println((i + 1) + ". " + freqList[i]);
        }

	}

	// binary search symbol table
	private static class BinarySearchST<Key extends Comparable<Key>, Value> {
	    private static final int INIT_CAPACITY = 2;
	    private Key[] keys;
	    private Value[] vals;
	    private int n = 0;



	    // Creates an empty Symbol Table
	    public BinarySearchST() {
	    	this(INIT_CAPACITY);
	    	System.out.println("BinarySearchST initialized!");
	    }


	    // Creates an empty symbol table with the initial capacity.
	    public BinarySearchST(int capacity) {
	        keys = (Key[]) new Comparable[capacity];
	        vals = (Value[]) new Object[capacity];
	    }

	    // Method creating temporary arrays with a larger capacity and copies over elements from
	    private void resize(int capacity) {
	        Key[]   tempk = (Key[])   new Comparable[capacity];
	        Value[] tempv = (Value[]) new Object[capacity];
	        for (int i = 0; i < n; i++) {
	            tempk[i] = keys[i];
	            tempv[i] = vals[i];
	        }
	        vals = tempv;
	        keys = tempk;
	    }


	    // Returns the number of key-value pairs in this symbol table.
 		int size() {
	        return n;
	    }

 		// Checks if the amount of key-value pairs is zero
	    public boolean isEmpty() {
	        return size() == 0;
	    }


	    // Method returning true/false if a key-value pair exists
	    public boolean contains(Key key) {
	    	return get(key) != null;
	    }


	    // Method returning associated value of a specified key
	    public Value get(Key key) {
	        if (isEmpty()) return null;
	        int i = rank(key);
	        if (i < n && keys[i].compareTo(key) == 0) return vals[i];
	        return null;
	    }


	    // Returns the number of keys in this symbol table strictly less than key.
	    public int rank(Key key) {
	        if (key == null) throw new IllegalArgumentException("argument to rank() is null");

	        int lo = 0, hi = n-1;
	        while (lo <= hi) {
	            int mid = lo + (hi - lo) / 2;
	            int cmp = key.compareTo(keys[mid]);
	            if      (cmp < 0) hi = mid - 1;
	            else if (cmp > 0) lo = mid + 1;
	            else return mid;
	        }
	        return lo;
	    }



	    /**
	     * Method which adds a key-value pair into symbol table, and if corresponding key already exists,
	     * we overwriting the previous value with the new one.
	     * Deletes the specified key (and its associated value) from this symbol table
	     * if the specified value is {@code null}.
	     */
	    public void put(Key key, Value val)  {
	        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

	        if (val == null) {
	            return;
	        }

	        int i = rank(key);

	        // Input key is already existing in Symbol Table
	        if (i < n && keys[i].compareTo(key) == 0) {
	            vals[i] = val;
	            return;
	        }

	        // Inserting new key-value pair
	        if (n == keys.length) resize(2*keys.length);

	        for (int j = n; j > i; j--)  {
	            keys[j] = keys[j-1];
	            vals[j] = vals[j-1];
	        }
	        keys[i] = key;
	        vals[i] = val;
	        n++;
	    }


	    // Method returning the mininum key
	    public Key min() {
	        if (isEmpty()) System.out.println("Symbol table empty");
	        return keys[0];
	    }

	    // Method returning max key
	    public Key max() {
	        if (isEmpty()) System.out.println("Symbol table empty");
	        return keys[n-1];
	    }


	    // Returning keys of the Symbol table
        public Iterable<Key> keys() {
            return keys(min(), max()); //in the range of min and manx
        }

	    //Returns all keys in this symbol table in the given range
	    public Iterable<Key> keys(Key lo, Key hi) {
	        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
	        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

	        Queue<Key> queue = new Queue<Key>();
	        if (lo.compareTo(hi) > 0) return queue;
	        for (int i = rank(lo); i < rank(hi); i++)
	            queue.enqueue(keys[i]);
	        if (contains(hi)) queue.enqueue(keys[rank(hi)]);
	        return queue;
	    }



    }

		// binary symbol table
    private static class BST<Key extends Comparable<Key>, Value> {
        private Node root;

        public BST() {
        	System.out.println("BST initialized!");
        }

        // Class for node where each node has a left and right node and a value
        private class Node {
            private Key key;
            private Value val;
            private Node left, right;
            private int N;

            public Node(Key key, Value val, int N) {  //constructor
                this.key = key;
                this.val = val;
                this.N = N;
            }
        }

        // Returns amount of key-value pairs
        public int size() {
            return size(root);
        }


        // Returns number of key-value pairs in BST rooted at x
        private int size(Node x) {
            if (x == null)
                return 0;
            else
                return x.N;
        }

        // Methods returning value associated with a specified key, beginning at root
        public Value get(Key key) {
            return get(root, key);
        }

        private Value get(Node x, Key key) {

        	if (x == null)                    //base case if node is null
                return null;
            int cmp = key.compareTo(x.key);   //Comparing each key with current node
            if (cmp < 0)                      //if key is smaller than current node, we go down left sub-tree
                return get(x.left, key);
            else if (cmp > 0)                 //if key is larger than current node, we go down right sub-tree
                return get(x.right, key);
            else
                return x.val;                 //return the associated value of input key meaning, we found correct value
        }


        // Insertion of a new key-pair value
        public void put(Key key, Value val) {
            root = put(root, key, val);
        }

        private Node put(Node x, Key key, Value val) {
            if (x == null) return new Node(key, val, 1); //if it is the first node in the tree or found the right place for the new node
            int cmp = key.compareTo(x.key);              //otherwise compare the key and the key at current node
            if (cmp < 0)                                 //if key is smaller than the key at the current node go left (i.e lexicographically samller)
                x.left = put(x.left, key, val);          //recrusive call, to go down i the tree to find the right place
            else if (cmp > 0)                            //if key is bigger than the key at the root go right (i.e lexicographically greater)
                x.right = put(x.right, key, val);        //recrusive call, to go down i the tree to find the right place
            else
                x.val = val;                             //else if key is equal to the current node update the value

            x.N = size(x.left) + size(x.right) + 1;      //update the number of nodes in subtree
            return x;                                    //return root
        }

        // We check if current Symbol table contains specified key
        public boolean contains(Key key) {
            return get(key) != null;
        }

        // Returning the minimum value
        public Key min() {
            return min(root).key;
        }

        // Returns the minimum key by going down the left sub-tree
        private Node min(Node x) {
            if (x.left == null) return x;
            return min(x.left);
        }

        // Returns the max key of current tree
        public Key max() {
            return max(root).key;
        }

        // Returns the max key of current tree by going down the right sub-tree
        private Node max(Node x) {
            if (x.right == null) return x;
            else return max(x.right);
        }

        // Returns current keys
        public Iterable<Key> keys() {
            return keys(min(), max());
        }

        // Returns keys in a queue
        public Iterable<Key> keys(Key lo, Key hi) {
            Queue<Key> queue = new Queue<Key>();
            keys(root, queue, lo, hi);
            return queue;
        }

        private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
            if (x == null) return;                                    //base case if node is null
            int cmplo = lo.compareTo(x.key);                          //comparison of current key with minimum key
            int cmphi = hi.compareTo(x.key);                          //comparison of current key with maximum key
            if (cmplo < 0)                                            //if smaller go down left sub tree
                keys(x.left, queue, lo, hi);
            if (cmplo <= 0 && cmphi >= 0)                             //meaning key found and we add to queue
                queue.enqueue(x.key);
            if (cmphi > 0)                                            //if greater, then go down right subtree
                keys(x.right, queue, lo, hi);
        }
    }
}
