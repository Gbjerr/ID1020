/**
 * Assignment 3 in course ID1020 for lab 4 at KTH
 *
 * Program creates a directed graph which uses uses adjacency-lists for implementation.
 * The program uses Depth first search to find out if there is a path from vertex
 * X to Y in the graph.
 */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

import java.util.Scanner;
import java.util.Stack;

public class L4Assignment3 {

    // declaration of american states mapped to array
    public static final String[] americanStates = new String[]{"AK", "AL", "AR", "AS", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "GU", "HI",
            "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS",
            "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "PR",
            "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY"};


    public static void main(String[] args) {


        In in = new In(args[0]);
        Digraph graph = new Digraph(americanStates.length);

        // reads connected vertices from text file, and maps these accordingly to
        // array of american states to identify which numbers from the graph corresponds
        // to what state
        System.out.println("Reading from text file and creating graph...");
        while (in.hasNextLine()) {

            int spot1 = 0;
            int spot2 = 0;

            String state1 = in.readString().trim().toUpperCase();
            String state2 = in.readString().trim().toUpperCase();

            // iterating until proper element is found
            for (int i = 0; i < americanStates.length; i++) {
                if (state1.equals(americanStates[spot1])) {
                    break;
                } else spot1++;
            }

            // iterates until proper element is found
            for (int j = 0; j < americanStates.length; j++) {
                if (state2.equals(americanStates[spot2])) {
                    break;
                } else spot2++;
            }

            graph.addEdge(spot1, spot2);
        }
        System.out.println("Done!");


        Scanner stdIn = new Scanner(System.in);
        String X = "", Y = "";

        // user input for deciding which vertices to find a path in between
        boolean bool = true;
        while (bool) {

            System.out.println("\nEnter a number according to the menu:");
            System.out.println("1: Find out if there is a path from vertex X to Y");
            System.out.println("2: exit");

            // by entering first case, user may choose to get path from X to Y
            switch (stdIn.nextInt()) {
                case 1:
                    System.out.println("\nEnter an American state as starting vertex (as abbreviation ex. Kentucky as KY):");
                    X = stdIn.next();

                    // confirm that entered start vertex is entered as an abbreviated american state
                    if (!isState(X)) {
                        System.out.printf("ERROR: entered string \"%s\" is not an american state\n", X);
                        System.exit(0);
                    }

                    System.out.println("\nEnter an American state as end vertex (as abbreviation ex. New York as NY):");
                    Y = stdIn.next();

                    // confirm that entered destination vertex is entered as an abbreviated american state
                    if (!isState(Y)) {
                        System.out.printf("ERROR: entered string \"%s\" is not an american state\n", Y);
                        System.exit(0);
                    }

                    int start = selectState(X), destination = selectState(Y);
                    DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(graph, start);

                    if (dfs.hasPathTo(destination)) {
                        System.out.printf("There is indeed a path to from %s to %s\n", X, Y);
                        System.out.println(X + dfs.pathTo(destination));
                    } else {
                        System.out.printf("There exists no path from %s to %s\n", X, Y);
                    }

                    break;
                // second case means we exit program
                case 2:
                    bool = false;
                    break;
                // default case means nothing happens
                default:
                    System.out.println("Entered number was not valid!");
            }
        }

    }

    // method mapping name of a state to return a mapped value
    public static int selectState(String s) {

        int i = 0;
        while (i < americanStates.length) {
            if (s.equals(americanStates[i])) {
                break;
            }
            i++;
        }
        return i;
    }

    // return true if string value is contained in array
    public static boolean isState(String s) {

        for (int i = 0; i < americanStates.length; i++) {

            if (s.equals(americanStates[i])) return true;
        }

        return false;
    }


    // implementation of depth first search for finding all paths in a digraph
    public static class DepthFirstDirectedPaths {
        private boolean[] marked;  //marked[v] = true iff v is reachable from s
        private int[] edgeTo;      // edgeTo[v] = last edge on path from s to v
        private final int s;       //source vertex

        // constructor, where s is the start vertex point
        public DepthFirstDirectedPaths(Digraph G, int s) {
            marked = new boolean[G.V()];
            edgeTo = new int[G.V()];
            this.s = s;
            dfs(G, s);
        }

        // method computes depth first search and marks all reachable vertices
        private void dfs(Digraph G, int v) {

            marked[v] = true;        //mark vertex v as visited
            // loop finds the first connected vertex to current vertex, makes a
            // connection and immediately calls recursively with that vertex
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    dfs(G, w);
                }
            }
        }

        // method returns path from start vertice to the vertcie given as argument
        public String pathTo(int v) {
            if (!hasPathTo(v)) return null;

            java.util.Stack<Integer> temp = new Stack<Integer>();
            StringBuilder sb = new StringBuilder();
            for (int x = v; x != s; x = edgeTo[x]) {
                temp.push(x);
            }

            while(!temp.isEmpty()) {
                sb.append(" -> " + americanStates[temp.pop()]);
            }
            return sb.toString();
        }

        // if there is a path, return true
        public boolean hasPathTo(int v) {
            return marked[v]; //if marked[v] is true then there is a path from s to v
        }
    }

    //implementation of a directional graph, which uses adjacency lists
    public static class Digraph {
        private final int V;
        private int E;
        private Bag<Integer>[] adj;

        public Digraph(int V) {
            this.V = V;
            this.E = 0;
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++)
                adj[v] = new Bag<Integer>();
        }

        // method returns number of vertices
        public int V() {
            return V;
        }

        // method returns number of edges
        public int E() {
            return E;
        }

        // adds edge in one direction from v to W
        public void addEdge(int v, int w) {
            adj[v].add(w);
            E++;
        }

        public Iterable<Integer> adj(int v) {
            return adj[v];
        }
    }
}
