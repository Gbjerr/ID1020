/**
 * Assignment 2 in course ID1020 for lab 4 at KTH
 * <p>
 * Program creates an undirected graph, which uses adjacency-lists for implementation.
 * Furthermore the program uses Breadth first search for finding and traversing paths
 * from X to Y if there is such a path.
 */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.Scanner;
import java.util.Stack;

public class L4Assignment2 {

    // array containing all american states as abbreviations
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
            System.out.println("1: Find a path from node X to Y with BFS");
            System.out.println("2: exit");

            switch (stdIn.nextInt()) {
                // by entering first case, user may choose to get path from X to Y
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
                    BreadthFirstPaths bfs = new BreadthFirstPaths(graph, start);
                    if (!bfs.hasPathTo(destination)) {
                        System.out.printf("There exists no path from %s to %s\n", X, Y);
                        break;
                    }

                    System.out.println(X + bfs.pathTo(destination));
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

    // implementation of breadth first search for finding all paths in the graph
    public static class BreadthFirstPaths {
        private boolean[] marked;
        private int[] edgeTo;
        private final int s;

        // constructor, where s is the start vertex point
        public BreadthFirstPaths(Graph G, int s) {

            marked = new boolean[G.V()];
            edgeTo = new int[G.V()];
            this.s = s;
            bfs(G, s);
        }

        // method creates the path system from one node to all other nodes
        private void bfs(Graph G, int s) {

            // queue is created to know which upcoming vertices to check
            Queue<Integer> queue = new Queue<Integer>();
            marked[s] = true;
            queue.enqueue(s);

            // while queue is not out of vertices, keep going
            while (!queue.isEmpty()) {

                // dequeue while processing its neighbors
                int v = queue.dequeue();

                // check if connected vertices are visited or not, and if not visited
                // mark as visited and add from w to v
                for (int w : G.adj(v)) {

                    if (!marked[w]) {
                        edgeTo[w] = v;
                        marked[w] = true;
                        queue.enqueue(w);
                    }
                }
            }
        }

        // checks if there is a path to given vertex
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


    // graph implementation using adjacency lists for vertex connecting a vertex.
    public static class Graph {

        // vertices
        private final int V;
        // edges
        private int E;
        // array of adjacency lists
        private Bag<Integer>[] adj;

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

}
