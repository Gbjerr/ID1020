import edu.princeton.cs.algs4.SequentialSearchST;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Assignment 3 in Lab 3
 *
 * Purpose of program: To showcase how evenly the built-in hashcode() function is for strings. This is shown by creating
 * a large hash table that uses separate chaining for collision handling, where keys are strings (words) read from a text
 * file that is hashed to positions of the table.
 */
public class L3Assignment3 {

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("********Assignment 3********");
        SeparateChainingHashST<String, Integer> ht = new SeparateChainingHashST<String, Integer>(10000);

        File file = new File("C:\\Users\\gurra\\Documents\\ID1020Labs\\searchLabb\\tale.txt");


        Scanner textScanner = new Scanner(file);
        Scanner stdIn = new Scanner(System.in);

        System.out.println("Enter number of words to be read: ");
        int quantity = stdIn.nextInt();
        if (quantity < 0) {
            System.out.println("ERROR: Number of words should at least be 0.");
            System.exit(0);
        }

        int words = 0, distinct = 0, collisions = 0, minLen = 3;
        String key;

        while (textScanner.hasNext() && words < quantity) {
            // read next word from stdIn, make it lower case and then remove all characters in that word
            // that is non-alphabetic
            key = textScanner.next().toLowerCase().replaceAll("[^a-z]", "");

            if (key.length() < minLen)
                continue;


            if (!ht.contains(key)) {
                ht.put(key, 1);
                distinct++;
            }
            else {
                ht.put(key, ht.get(key) + 1);
            }

            words++;

        }

        int meanValCollisions = 0, maxValCollisions = 0, minValCollisions = Integer.MAX_VALUE;
        int current;
        // derive the maximum collisions in a cell, and the minimum
        for(int i = 0; i < ht.st.length; i++) {
            if(ht.st[i].isEmpty()) continue;
            current = ht.st[i].size();

            if(maxValCollisions < current) maxValCollisions = current;
            if(minValCollisions > current) minValCollisions = current;
            meanValCollisions += ht.st[i].size();
        }
        int nonEmptyCells = ht.getNonEmptyCells();
        meanValCollisions /= nonEmptyCells;

        System.out.println("Stats:");
        System.out.printf("Total number of words read from file: %d\n", words);
        System.out.printf("Total number of distinct words read from file: %d\n", distinct);
        System.out.printf("Minimum collisions in a cell is %d, Maximum is %d and the mean value for collisions in each cell is %d\n",
                minValCollisions, maxValCollisions, meanValCollisions);

    }

    // class representing a hash table which uses separate chaining to handle collisions
    public static class SeparateChainingHashST<Key, Value> {
        private static final int INIT_CAPACITY = 4;

        private int n;                                // number of key-value pairs
        private int m;                                // hash table size
        private SequentialSearchST<Key, Value>[] st;  // array of linked-list symbol tables


        /**
         * Creates empty symbol table with number of cells given as argument
         */
        public SeparateChainingHashST(int m) {
            this.m = m;
            st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[m];
            for (int i = 0; i < m; i++)
                st[i] = new SequentialSearchST<Key, Value>();
        }

        // resize the hash table to have the given number of chains,
        // rehashing all of the keys
        private void resize(int chains) {
            SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<Key, Value>(chains);
            for (int i = 0; i < m; i++) {
                for (Key key : st[i].keys()) {
                    temp.put(key, st[i].get(key));
                }
            }
            this.m = temp.m;
            this.n = temp.n;
            this.st = temp.st;
        }

        // hash value between 0 and m-1
        private int hash(Key key) {
            return (key.hashCode() & 0x7fffffff) % m;
        }

        private void printCells() {
            SequentialSearchST<Key, Value> current;
            for(int i = 0; i < m; i++) {

                current = st[i];
                if(current.isEmpty()) continue;
                System.out.printf("\nCell: %d contains collisions: ", i);
                for(Key key : current.keys()) {
                    System.out.printf("%s, ", key);
                }
            }
        }

        private int getNonEmptyCells() {
            int count = 0;
            for(int i = 0; i < m; i++) {

                if(st[i].isEmpty()) continue;
                count++;
            }

            return count;
        }

        /**
         * method returns number of key-pairs in hash table
        */
        public int size() {
            return n;
        }

        /**
         * method checks if hash table is empty
         */
        public boolean isEmpty() {
            return size() == 0;
        }

        /**
         * method looks if a key, given as argument, exists in current hash table
         */
        public boolean contains(Key key) {
            if (key == null) return false;
            return get(key) != null;
        }

        /**
         * Returns the value associated with the specified key in this symbol table.
         */
        public Value get(Key key) {
            if (key == null) throw new IllegalArgumentException("argument to get() is null");
            int i = hash(key);
            return st[i].get(key);
        }

        /**
         * Inserts the specified key-value pair into the symbol table, overwriting the old
         * value with the new value if the symbol table already contains the specified key.
         * Deletes the specified key (and its associated value) from this symbol table
         */
        public void put(Key key, Value val) {
            if (key == null) return;
            if (val == null) {
                delete(key);
                return;
            }

            // double table size if average length of list >= 10
            if (n >= 10 * m) resize(2 * m);

            int i = hash(key);
            if (!st[i].contains(key)) n++;
            st[i].put(key, val);
        }

        /**
         * Removes the specified key and its associated value from this symbol table
         * (if the key is in this symbol table).
         */
        public void delete(Key key) {
            if (key == null) throw new IllegalArgumentException("argument to delete() is null");

            int i = hash(key);
            if (st[i].contains(key)) n--;
            st[i].delete(key);

            // halve table size if average length of list <= 2
            if (m > INIT_CAPACITY && n <= 2 * m) resize(m / 2);
        }
    }
}
