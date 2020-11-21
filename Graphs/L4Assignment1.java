/**
 * Assignment 1 in course ID1020 for lab 4 at KTH
 *
 * Program creates an undirected graph, which uses adjacency-lists for implementation.
 * Furthermore the graph uses Depth First Search for finding and traversing paths
 * from X to Y if there is a path.
 */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

import java.util.Scanner;
import java.util.Stack;

public class L4Assignment1 {

    // declaration of american states mapped to array
    public static final String[] americanStates = new String[]
            {"AK", "AL", "AR", "AS", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "GU", "HI",
                    "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS",
                    "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "PR",
                    "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY"};

    public static void main(String[] args) {

        In in = new In(args[0]);
        Graph graph = new Graph(americanStates.length);

        // reads connected vertices from text file, and maps these accordingly to
        // array of american states to identify which numbers from the graph corresponds
        // to which state
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

            // iterating until proper element is found
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
            System.out.println("1: Find a path from node X to Y with DFS");
            System.out.println("2: exit");

            switch (stdIn.nextInt()) {
                // by entering first case, user may choose to get path from X to Y
                case 1:

                    System.out.println("\nEnter an American state as starting vertex (as abbreviation ex. Kentucky as KY):");
                    X = stdIn.next();
                    if (!isState(X)) {
                        System.out.printf("ERROR: entered string \"%s\" is not an american state\n", X);
                        System.exit(0);
                    }

                    System.out.println("\nEnter an American state as end vertex (as abbreviation ex. New York as NY):");
                    Y = stdIn.next();
                    if (!isState(Y)) {
                        System.out.printf("ERROR: entered string \"%s\" is not an american state\n", Y);
                        System.exit(0);
                    }

                    int start = selectState(X), destination = selectState(Y);
                    DepthFirstPaths dfs = new DepthFirstPaths(graph, start);
                    if (!dfs.hasPathTo(destination)) {
                        System.out.printf("There exists no path from %s to %s\n", X, Y);
                        break;
                    }
                    System.out.println(X + dfs.pathTo(destination));
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

    // method mapping a name of a state by returning an index where that state is located
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


    // graph implementation using adjacency lists for connecting vertices
    public static class Graph {
        private final int V;        //vertices
        private int E;              //edges
        private Bag<Integer>[] adj; //Lists that are adjacent

        public Graph(int V) {
            this.V = V;
            this.E = 0;
            adj = (Bag<Integer>[]) new Bag[V];
            //initialize all lists
            for (int v = 0; v < V; v++)
                adj[v] = new Bag<Integer>();
        }

        // return number of vertices
        public int V() {
            return V;
        }

        // return number of edges
        public int E() {
            return E;
        }

        // make connection between vertices in two directions
        public void addEdge(int v, int w) {
            adj[v].add(w);
            adj[w].add(v);
            E++;
        }

        public Iterable<Integer> adj(int v) {
            return adj[v];
        }
    }

    // implementation of depth first search for finding all paths in a graph
    public static class DepthFirstPaths {
        private boolean[] marked;
        private int[] edgeTo;
        private final int s;

        // constructor, where s is the start vertex point
        public DepthFirstPaths(Graph G, int s) {

            // create arrays with same size as number of vertices
            marked = new boolean[G.V()];
            edgeTo = new int[G.V()];
            this.s = s;
            dfs(G, s);
        }

        // method computes depth first search and marks all reachable vertices
        private void dfs(Graph G, int v) {

            marked[v] = true;
            // loop finds the first connected vertex to current vertex, makes a
            // connection and immediately calls recursively with that vertex
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    dfs(G, w);
                }
            }
        }


        // if there is a path, return true
        public boolean hasPathTo(int v) {
            return marked[v];
        }

        // method returns path from start vertice to the vertcie given as argument
        public String pathTo(int v) {
            if (!hasPathTo(v)) return null;

            Stack<Integer> temp = new Stack<Integer>();
            StringBuilder sb = new StringBuilder();
            for (int x = v; x != s; x = edgeTo[x]) {
                temp.push(x);
            }

            while(!temp.isEmpty()) {
                sb.append(" -> " + americanStates[temp.pop()]);
            }
            return sb.toString();
        }

    }
}
