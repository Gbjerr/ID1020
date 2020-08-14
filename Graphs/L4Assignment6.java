/************************
 * Assignment 6 in course ID1020 for lab 4 at KTH
 *
 * Program creates an directed graph where the graph uses adjacency-lists for implementation.
 * Furthermore the program detects if there is any directed cycles exists in graph.

	 Example run:
 	 java -cp algs4.jar: L4Assignment6 < states.txt
	 There is indeed a cycle in the graph
 ***********************/
import edu.princeton.cs.algs4.*;
public class L4Assignment6 {


	// declaration of american states mapped to array
	public static final String[] americanStates = new String[] {"AK", "AL", "AR", "AS", "AZ","CA", "CO", "CT", "DC", "DE", "FL", "GA","GU","HI",
          	"IA", "ID","IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS",
          	"MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "PR",
          	"RI", "SC", "SD", "TN", "TX", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY"};



	public static void main(String[] args) {
			Digraph graph = new Digraph(americanStates.length);

			// reads from StdIn, maps these to americanStates creates edges based on input
			// and adds these to directional graph.
			while (!StdIn.isEmpty()) {
					int spot1 = 0;
					int spot2 = 0;

					String state1 = StdIn.readString().toUpperCase();
					String state2 = StdIn.readString().toUpperCase();

					// iterating until proper element is found
					for(int i = 0; i < americanStates.length; i++) {
							if(state1.equals(americanStates[spot1])) {
									break;
							}
							else spot1++;
					}

					// iterating until proper element is found
					for(int j = 0; j < americanStates.length; j++) {
							if(state2.equals(americanStates[spot2])) {
									break;
							}
							else spot2++;
					}

					// adds edge to graph
					graph.addEdge(spot1, spot2);
			}

	    DirectedCycle dc = new DirectedCycle(graph);

	    if(dc.hasCycle()) {
					StdOut.println("There is indeed a cycle in the graph");
	    }
			else StdOut.println("no cycles found");
	}


	// implementation for finding cycles in a directional graph
	public static class DirectedCycle {
	    private boolean[] marked;        // marked[v] = has vertex v been marked?
	    private int[] edgeTo;            // edgeTo[v] = previous vertex on path to v
	    private boolean[] onStack;       // onStack[v] = is vertex on the stack?
	    private Stack<Integer> cycle;    // directed cycle (or null if no such cycle)

	    /**
	     * Determines whether the digraph has a directed cycle and, if so,
	     * finds such a cycle.
	     */
	    public DirectedCycle(Digraph G) {
	        marked  = new boolean[G.V()];
	        onStack = new boolean[G.V()];
	        edgeTo  = new int[G.V()];
	        for (int v = 0; v < G.V(); v++)
	            if (!marked[v] && cycle == null) dfs(G, v);
	    }

	    // check that algorithm computes either the topological order or finds a directed cycle
	    private void dfs(Digraph G, int v) {
	        onStack[v] = true;
	        marked[v] = true;
	        StdOut.println(americanStates[v]);
	        for (int w : G.adj(v)) {

	            // short circuit if directed cycle found
	            if (cycle != null) return;

	            // found new vertex, so recur
	            else if (!marked[w]) {
	                edgeTo[w] = v;
	                dfs(G, w);
	            }

	            // trace back directed cycle
	            else if (onStack[w]) {
	                cycle = new Stack<Integer>();
	                for (int x = v; x != w; x = edgeTo[x]) {
	                    cycle.push(x);
	                }
	                cycle.push(w);
	                cycle.push(v);
	                assert check();
	            }
	        }
	        onStack[v] = false;
	    }

	    /**
	     * Does the digraph have a directed cycle?
	     * @return {@code true} if the digraph has a directed cycle, {@code false} otherwise
	     */
	    public boolean hasCycle() {
	        return cycle != null;
	    }

	    /**
	     * Returns a directed cycle if the digraph has a directed cycle, and {@code null} otherwise.
	     * @return a directed cycle (as an iterable) if the digraph has a directed cycle,
	     *    and {@code null} otherwise
	     */
	    public Iterable<Integer> cycle() {
	        return cycle;
	    }


	    // certify that digraph has a directed cycle if it reports one
	    private boolean check() {

	        if (hasCycle()) {
	            // verify cycle
	            int first = -1, last = -1;
	            for (int v : cycle()) {
	                if (first == -1) first = v;
	                last = v;
	            }
	            if (first != last) {
	                System.err.printf("cycle begins with %d and ends with %d\n", first, last);
	                return false;
	            }
	        }


	        return true;
	    }
	}


    public static class DepthFirstDirectedPaths {
        private boolean[] marked;  //marked[v] = true iff v is reachable from s
        private final int s;       //source vertex

        /**
         * Computes a directed path from s to every other vertex in digraph G
         */
        public DepthFirstDirectedPaths(Digraph G, int s) {
            marked = new boolean[G.V()];
            this.s = s;
            dfs(G, s);
        }

        /**
         * Computes depth-first search and marks all reachable vertices w
         * with true.
         */
        private void dfs(Digraph G, int v) {
            marked[v] = true;        //mark vertex v as visited
            for (int w : G.adj(v)) { //go through all vertex in the list
                if (!marked[w]) {    //if unmarked
                    dfs(G, w);       //recrusively visit all unmakred vertices adjecent to v
                }
            }
        }

        /**
         * Is there a directed path from the source vertex s to vertex v
         */
        public boolean hasPathTo(int v) {
            return marked[v]; //if marked[v] is true then there is a path from s to v
        }
    }


  //implementation of a directional graph
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

        public Digraph(In in) {
            this(in.readInt());
            int E = in.readInt();
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                addEdge(v, w);
            }
        }

        public int V() {
            return V;
        }

        public int E() {
            return E;
        }

        public void addEdge(int v, int w) {
            adj[v].add(w);
            E++;
        }

        public Iterable<Integer> adj(int v) {
            return adj[v];
        }
    }

}
