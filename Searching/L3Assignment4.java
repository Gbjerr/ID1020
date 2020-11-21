/**
 * Assignment 4 in lab 3 at KTH
 *
 * Program reads from a given text and stores each word as key, and a list with its positions in the text as value. It's
 * using a binary search symbol table for storing the key-pairs, as well as retrieving them. This allows the user to
 * ask the question "on which positions in the text (i.e. the number of characters from the beginning) you find the word X"
 */
import edu.princeton.cs.algs4.Queue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class L3Assignment4 {

    private static BinaryST<String, ArrayList<Integer>> st;

    public static void main(String[] args) throws IOException {

        System.out.println("********Assignment 4********");

        st = new BinaryST<String, ArrayList<Integer>>();

        File file = new File("C:\\Users\\gurra\\Documents\\ID1020Labs\\searchLabb\\tale.txt");
        Scanner scan = new Scanner(file);

        System.out.println("Reading from text file....\n");
        int position = 0, minLen = 1;
        while (scan.hasNext()) {

            // remove all non-alphabetic characters from the read word
            String key = scan.next().toLowerCase().replaceAll("[^a-z]", "");

            if (key.length() < minLen) continue;

            // if word is not contained in our symbol table we initialize a list as value and add the position to list
            if (!st.contains(key)) {

                ArrayList<Integer> list = new ArrayList<Integer>();
                list.add(position);
                st.put(key, list);
            }
            else {

                ArrayList<Integer> list = st.get(key);
                list.add(position);
                st.put(key, list);
            }

            position++;
        }

        Scanner stdIn = new Scanner(System.in);

        boolean bool = true;
        while(bool) {

            System.out.println("\nEnter a number according to the menu:");
            System.out.println("1: Get positions of a word in the text");
            System.out.println("2: exit");

            switch(stdIn.nextInt()) {
                case 1:
                    System.out.println("Enter a word");
                    String word = stdIn.next();
                    printPositions(word);
                    break;
                case 2:
                    bool = false;
                    break;
                default :
                    System.out.println("Entered number was not valid!");
            }
        }


    }

    // method prints the positions of a given word in the text
    private static void printPositions(String word) {
        ArrayList<Integer> list = st.get(word);
        if(word == null) {
            System.out.println("You did not enter a word");
            return;
        }
        else if(!st.contains(word)) {
            System.out.println("The word you entered does not exist in the symbol table");
            return;
        }

        Iterator i = list.iterator();
        while(i.hasNext()) {
            System.out.printf("[%d], ", i.next());
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
