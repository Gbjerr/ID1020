import edu.princeton.cs.algs4.Queue;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Assignment 2 in Lab 3
 *
 * Purpose of program: To compare an ordered array symbol table with a binary search tree
 * by reading words from a text file and to benchmark the speed of put() and get() function.
 *
 */

public class L3Assignment2 {

    private static BinaryST<String, Integer> st;
    private static BST<String, Integer> bst;

    private static int wordsReadBST = 0, wordsReadST = 0;

    public static void main(String[] args) {

        System.out.println("********Assignment 2********");

        File file = new File("C:\\Users\\gurra\\Documents\\ID1020Labs\\searchLabb\\tale.txt");
        Scanner scan = new Scanner(System.in);

        // let user define how many words to be read in
        System.out.println("Enter number of words to be read: ");
        int quantity = scan.nextInt();
        if(quantity < 0) {
            System.out.println("ERROR: Number of words should at least be 0.");
            System.exit(0);
        }

        // let user define the minimum length of the words to read
        System.out.println("\n\nEnter the minimum length of words to be read: ");
        int minLen = scan.nextInt();
        if(minLen < 0) {
            System.out.println("ERROR: Minimum length should at least be 0.");
            System.exit(0);
        }

        long start, end;
        double time1 = 0, time2 = 0, time3 = 0, time4 = 0;


        // put words into ordered symbol table and benchmark
        start = System.nanoTime();
        try {
            for(int i = 0; i < 10; i++) {
                countSTFrequencies(file, minLen, quantity);
            }
        }
        catch (IOException ex) {
            System.out.println("ERROR: \n" + ex.getMessage());
            System.exit(0);
        }
        end = System.nanoTime();
        time1 += (((double) (end - start)) * (Math.pow(10, -9)));
        time1 /= 10;


        // put words into binary search tree and benchmark
        start = System.nanoTime();
        try {
            for(int i = 0; i < 10; i++){
                countBSTFrequencies(file, minLen, quantity);
            }
        }
        catch (IOException ex) {
            System.out.println("ERROR: \n" + ex.getMessage());
            System.exit(0);
        }
        end = System.nanoTime();
        time2 += (((double) (end - start)) * (Math.pow(10, -9)));
        time2 /= 10;


        String max1 = "", max2 = "";
        // benchmark the get function of ordered symbol table
        start = System.nanoTime();
        for(int i = 0; i < 10; i++){
            max1 = getMaxValST();
        }
        end = System.nanoTime();
        time3 = (((double) (end - start)) * (Math.pow(10, -9)));
        time3 /= 10;

        // benchmark the get function of binary search tree
        start = System.nanoTime();
        for(int i = 0; i < 10; i++){
            max2 = getMaxValBST();
        }
        end = System.nanoTime();
        time4 = (((double) (end - start)) * (Math.pow(10, -9)));
        time4 /= 10;

        assert(max1.equals(max2));

        System.out.println("\nResults: ");
        System.out.printf("Time for Ordered Symbol table to read %d words took %f seconds\n", wordsReadST, time1);
        System.out.printf("Time for Binary Search Tree to read %d words took %f seconds\n", wordsReadBST, time2);
        System.out.printf("Time for Ordered Symbol table to retrieve the word with greatest frequency was %f seconds\n", time3);
        System.out.printf("Time for Binary Search tree to retrieve the word with greatest frequency was %f seconds\n", time4);


    }

    // returns the string with the greatest frequency in a binary search symbol table
    public static String getMaxValST() {
        String max = "";
        st.put(max, 0);

        for (String word : st.keys()) {
            if (st.get(word) > st.get(max))
                max = word;
        }

        return max;
    }

    // returns the string with the greatest frequency in a binary search tree
    public static String getMaxValBST() {
        String max = "";
        bst.put(max, 0);

        for (String word : bst.keys()) {
            if (bst.get(word) > bst.get(max))
                max = word;
        }

        return max;
    }

    /**
     * method reads words from text file and puts these into a ordered symbol table
     *
     * @param text - the text file that we read from
     * @param minLen - the minimum length of words to read
     * @param quantity - the max amount of total words to be read
     * @throws IOException - is thrown in case of reading error
     */
    public static void countSTFrequencies(File text, int minLen, int quantity) throws IOException {
        st = new BinaryST<String, Integer>();
        Scanner scan = new Scanner(text);
        int distinct = 0, words = 0;

        while (scan.hasNext() && quantity > words) {
            String key = scan.next().toLowerCase().replaceAll("[^a-z]", "");

            if (key.length() < minLen) continue;

            words++;
            if (st.contains(key)) {
                st.put(key, st.get(key) + 1);
            }
            else {
                st.put(key, 1);
                distinct++;
            }
        }

        wordsReadST = words;

    }

    /**
     * method reads words from text file and puts these into a binary search tree
     *
     * @param text - the text file that we read from
     * @param minLen - the minimum length of words to read
     * @param quantity - the max amount of total words to be read
     * @throws IOException - is thrown in case of reading error
     */
    public static void countBSTFrequencies(File text, int minLen, int quantity) throws IOException {
        bst = new BST<String, Integer>();
        Scanner scan = new Scanner(text);
        int distinct = 0, words = 0;

        while (scan.hasNext() && quantity > words) {
            String key = scan.next().toLowerCase().replaceAll("[^a-z]", "");

            if (key.length() < minLen) continue;

            words++;
            if (bst.contains(key)) {
                bst.put(key, st.get(key) + 1);
            }
            else {
                bst.put(key, 1);
                distinct++;
            }
        }

        wordsReadBST = words;
    }

    /**
     * Generic binary search tree data structure
     * @param <Key> - keys in tree
     * @param <Value> - values in tree
     */
    public static class BST<Key extends Comparable, Value> {
        private Node root;

        // constructor
        public BST() {}

        public boolean contains(Key key) {
            return get(key) != null;
        }

        public Value get(Key key) {
            return get(root,key);
        }

        private Value get(Node node, Key key) {
            if (node == null) {
                return null;
            }

            int compare = key.compareTo((Key) node.key);
            // in case the searched key is smaller than key current node, search in the left sub tree,
            // or in case if it's greater, search in right sub tree, otherwise we have found it.
            if(compare < 0)
                return get(node.l,key);
            else if(compare > 0)
                return get(node.r, key);
            else
                return (Value) node.val;
        }

        public int size() {
            return size(root);
        }

        private int size(Node node) {
            if (node == null)
                return 0;
            else
                return node.size;
        }

        public boolean isEmpty() {
            return size() == 0;
        }

        public void put(Key key, Value val) {
            root = put(root, key, val);
        }

        private Node put(Node node, Key key, Value val) {

            // base case where the position to insert node is found
            if(node == null) return new Node(key, val, 1);
            int compare = key.compareTo(node.key);

            // in case the searched key is smaller than key current node, search for spot in the left sub tree,
            // or in case it's greater, search for spot in right sub tree. Otherwise, we have found a spot
            if(compare < 0)
                node.l  = put(node.l, key, val);
            else if (compare > 0)
                node.r = put(node.r, key, val);
            else
                node.val   = val;

            node.size = 1 + size(node.l) + size(node.r);
            return node;
        }

        public Key min() {
            return (Key) min(root).key;
        }

        private Node min(Node x) {
            if (x.l == null) return x;
            else return min(x.l);
        }


        public Key max() {
            return (Key) max(root).key;
        }
        private Node max(Node x) {
            if (x.r == null) return x;
            else return max(x.r);
        }


        public Iterable<Key> keys() {
            if (isEmpty()) return new Queue<Key>();
            return keys(min(), max());
        }

        // creates an iterable of our tree
        public Iterable<Key> keys(Key lo, Key hi) {

            Queue<Key> queue = new Queue<Key>();
            keys(root, queue, lo, hi);
            return queue;
        }

        private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
            if (x == null) return;
            int cmplo = lo.compareTo(x.key);
            int cmphi = hi.compareTo(x.key);
            if (cmplo < 0) keys(x.l, queue, lo, hi);
            if (cmplo <= 0 && cmphi >= 0) queue.enqueue((Key) x.key);
            if (cmphi > 0) keys(x.r, queue, lo, hi);
        }


    }

    /**
     * Generic node class
     * @param <Key> - key in node
     * @param <Value> - value in node
     */
    private static class Node<Key, Value> {
        private Key key;
        private Value val;
        private Node l, r;
        private int size;

        public Node(Key key, Value val, int size)
        {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    /**
     * Generic ordered array symbol table class
     * @param <Key>
     * @param <Value>
     */
    public static class BinaryST<Key extends Comparable<Key>, Value>
    {
        private static final int init_size = 2;
        private Key[] keys;
        private Value[] vals;
        private int n = 0;

        // constructor
        public BinaryST()
        {
            this(init_size);
        }

        // constructor
        public BinaryST(int size)
        {
            keys = (Key[]) new Comparable[size];
            vals = (Value[]) new Object[size];
        }

        public boolean isEmpty()
        {
            return n == 0;
        }

        // method inserts key value pair in ordered array
        public void put(Key key, Value value)
        {
            if(value == null)
            {
                delete(key);
                return;
            }

            int index = rank(key);
            if(index < n && keys[index].compareTo(key) == 0)
            {
                vals[index] = value;
                return;
            }
            if(n == keys.length)
                resize(2* keys.length);

            // shift key-pairs that is positioned at right to our index, one position to right
            for (int j = n; j > index; j--)
            {
                keys[j] = keys[j-1];
                vals[j] = vals[j-1];
            }

            keys[index] = key;
            vals[index] = value;
            n++;
        }

        // method returns value from symbol table based on input key
        public Value get(Key key)
        {
            if(isEmpty())
                return null;

            int index = rank(key);
            if(index < n && keys[index].compareTo(key) == 0)
                return vals[index];
            else
                return null;
        }

        public void delete(Key key)
        {
            put(key,null);
        }

        // method returns number of keys that is strictly less than input key
        public int rank(Key key)
        {
            int lo = 0;
            int hi = n - 1;
            while(lo <= hi)
            {
                int mid = lo + (hi - lo)/2;
                int compare = key.compareTo(keys[mid]);
                if(compare < 0)
                    hi = mid - 1;
                else if(compare > 0)
                    lo = mid + 1;
                else
                    return mid;
            }
            return lo;
        }

        // method resizes arrays containing the key-pair values
        private void resize(int capacity)
        {
            assert capacity >= n;
            Key[]   tempk = (Key[])   new Comparable[capacity];
            Value[] tempv = (Value[]) new Object[capacity];
            for (int i = 0; i < n; i++) {
                tempk[i] = keys[i];
                tempv[i] = vals[i];
            }
            vals = tempv;
            keys = tempk;
        }

        public boolean contains(Key key)
        {
            return get(key) != null;
        }

        public Key max()
        {
            return keys[n-1];
        }

        public Key min()
        {
            return keys[0];
        }

        public Iterable<Key> keys() {
            return keys(min(), max());
        }

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
}
