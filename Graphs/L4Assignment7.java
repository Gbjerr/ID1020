
/************************
 * Assignment 7 in course ID1020 for lab 4 at KTH
 *
 * Program creates an directed graph where the graph uses adjacency-lists for implementation. Furthermore the program detects
 * if there's any directed cycles exists in graph, and finds thereby the topological order of the graph.

	 Example run:
	 java -cp algs4.jar: L4Assignment7 < states.txt
	 graph has no topological order
 ***********************/
import edu.princeton.cs.algs4.*;

public class L4Assignment7 {

	// declaration of american elements mapped to array
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

					String element1 = StdIn.readString().toUpperCase();
					String element2 = StdIn.readString().toUpperCase();

					// iterating until proper element is found
					for(int i = 0; i < americanStates.length; i++) {
							if(element1.equals(americanStates[spot1])) {
									break;
							}
							else spot1++;
					}

					// iterating until proper element is found
					for(int j = 0; j < americanStates.length; j++) {
							if(element2.equals(americanStates[spot2])) {
									break;
							}
							else spot2++;
					}

					graph.addEdge(spot1, spot2);
			}

			Topological topo = new Topological(graph);
			StringBuilder sb = new StringBuilder();

			// determines whether graph has an topologica order
			if(topo.hasOrder()) {
					for (int v : topo.order()) {
							sb.append(americanStates[v] + " ");
					}
					StdOut.println(sb.toString());
			}
			else StdOut.println("graph has no topological order");

	}


	/**
	 * Given a directional graph without cycles, finds the topological order of a graph.
	 */
	public static class Topological {
		private Iterable<Integer> order; // topological order
		private int[] rank; // rank[v] = rank of vertex v in order

		/**
		 * Determines whether the digraph has a topological order and, if so,
		 * finds such a topological order.
		 *
		 * @param G the digraph
		 */
		public Topological(Digraph G) {
			DirectedCycle finder = new DirectedCycle(G);
			if (!finder.hasCycle()) {
				DepthFirstOrder dfs = new DepthFirstOrder(G);
				order = dfs.reversePost();
				rank = new int[G.V()];
				int i = 0;
				for (int v : order)
					rank[v] = i++;
			}
		}

		/**
		 * Returns a topological order if the digraph has a topologial order, and
		 * {@code null} otherwise.
		 *
		 * @return a topological order of the vertices (as an interable) if the digraph
		 *         has a topological order (or equivalently, if the digraph is a DAG),
		 *         and {@code null} otherwise
		 */
		public Iterable<Integer> order() {
			return order;
		}

		/**
		 * returns true if the digraph has a topological order, and false otherwise
		 */
		public boolean hasOrder() {
			return order != null;
		}

		/**
		 * Does the digraph have a topological order?
		 *
		 * @return {@code true} if the digraph has a topological order (or equivalently,
		 *         if the digraph is a DAG), and {@code false} otherwise
		 * @deprecated Replaced by {@link #hasOrder()}.
		 */
		@Deprecated
		public boolean isDAG() {
			return hasOrder();
		}

		/**
		 * The the rank of vertex {@code v} in the topological order; -1 if the digraph
		 * is not a DAG
		 *
		 * @param v the vertex
		 * @return the position of vertex {@code v} in a topological order of the
		 *         digraph; -1 if the digraph is not a DAG
		 * @throws IllegalArgumentException unless {@code 0 <= v < V}
		 */
		public int rank(int v) {
			validateVertex(v);
			if (hasOrder())
				return rank[v];
			else
				return -1;
		}

		// throw an IllegalArgumentException unless {@code 0 <= v < V}
		private void validateVertex(int v) {
			int V = rank.length;
			if (v < 0 || v >= V)
				StdOut.println("vertex " + v + " is not between 0 and " + (V - 1));
		}
	}

	// implementation for finding cycles in the graph
	public static class DirectedCycle {
		private boolean[] marked; // marked[v] = has vertex v been marked?
		private int[] edgeTo; // edgeTo[v] = previous vertex on path to v
		private boolean[] onStack; // onStack[v] = is vertex on the stack?
		private Stack<Integer> cycle; // directed cycle (or null if no such cycle)

		/**
		 * Determines whether the digraph {@code G} has a directed cycle and, if so,
		 * finds such a cycle.
		 *
		 * @param G the digraph
		 */
		public DirectedCycle(Digraph G) {
			marked = new boolean[G.V()];
			onStack = new boolean[G.V()];
			edgeTo = new int[G.V()];
			for (int v = 0; v < G.V(); v++)
				if (!marked[v] && cycle == null)
					dfs(G, v);
		}

		// check that algorithm computes either the topological order or finds a
		// directed cycle
		private void dfs(Digraph G, int v) {
			onStack[v] = true;
			marked[v] = true;
			for (int w : G.adj(v)) {

				// short circuit if directed cycle found
				if (cycle != null)
					return;

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
		 *
		 * @return {@code true} if the digraph has a directed cycle, {@code false}
		 *         otherwise
		 */
		public boolean hasCycle() {
			return cycle != null;
		}

		/**
		 * Returns a directed cycle if the digraph has a directed cycle, and
		 * {@code null} otherwise.
		 *
		 * @return a directed cycle (as an iterable) if the digraph has a directed
		 *         cycle, and {@code null} otherwise
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
					if (first == -1)
						first = v;
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

	public static class DepthFirstOrder {
		private boolean[] marked; // marked[v] = has v been marked in dfs?
		private int[] pre; // pre[v] = preorder number of v
		private int[] post; // post[v] = postorder number of v
		private Queue<Integer> preorder; // vertices in preorder
		private Queue<Integer> postorder; // vertices in postorder
		private int preCounter; // counter or preorder numbering
		private int postCounter; // counter for postorder numbering

		/**
		 * Determines a depth-first order for the digraph {@code G}.
		 *
		 * @param G the digraph
		 */
		public DepthFirstOrder(Digraph G) {
			pre = new int[G.V()];
			post = new int[G.V()];
			postorder = new Queue<Integer>();
			preorder = new Queue<Integer>();
			marked = new boolean[G.V()];
			for (int v = 0; v < G.V(); v++) {
				if (!marked[v]) {
					dfs(G, v);
				}
			}
		}

		/**
		 * Returns the preorder number of vertex {@code v}.
		 *
		 * @param v the vertex
		 * @return the preorder number of vertex {@code v}
		 * @throws IllegalArgumentException unless {@code 0 <= v < V}
		 */
		public int pre(int v) {
			validateVertex(v);
			return pre[v];
		}

		// run DFS in digraph G from vertex v and compute preorder/postorder
		private void dfs(Digraph G, int v) {
			marked[v] = true;
			pre[v] = preCounter++;
			preorder.enqueue(v);
			for (int w : G.adj(v)) {
				if (!marked[w]) {
					dfs(G, w);
				}
			}
			postorder.enqueue(v);
			post[v] = postCounter++;
		}

		/**
		 * Returns the postorder number of vertex {@code v}.
		 *
		 * @param v the vertex
		 * @return the postorder number of vertex {@code v}
		 * @throws IllegalArgumentException unless {@code 0 <= v < V}
		 */
		public int post(int v) {
			validateVertex(v);
			return post[v];
		}

		/**
		 * Returns the vertices in postorder.
		 *
		 * @return the vertices in postorder, as an iterable of vertices
		 */
		public Iterable<Integer> post() {
			return postorder;
		}

		/**
		 * Returns the vertices in preorder.
		 *
		 * @return the vertices in preorder, as an iterable of vertices
		 */
		public Iterable<Integer> pre() {
			return preorder;
		}

		/**
		 * Returns the vertices in reverse postorder.
		 *
		 * @return the vertices in reverse postorder, as an iterable of vertices
		 */
		public Iterable<Integer> reversePost() {
			Stack<Integer> reverse = new Stack<Integer>();
			for (int v : postorder)
				reverse.push(v);
			return reverse;
		}

		// check that pre() and post() are consistent with pre(v) and post(v)
		private boolean check() {

			// check that post(v) is consistent with post()
			int r = 0;
			for (int v : post()) {
				if (post(v) != r) {
					StdOut.println("post(v) and post() inconsistent");
					return false;
				}
				r++;
			}

			// check that pre(v) is consistent with pre()
			r = 0;
			for (int v : pre()) {
				if (pre(v) != r) {
					StdOut.println("pre(v) and pre() inconsistent");
					return false;
				}
				r++;
			}

			return true;
		}

		// throw an IllegalArgumentException unless {@code 0 <= v < V}
		private void validateVertex(int v) {
			int V = marked.length;
			if (v < 0 || v >= V)
				StdOut.println("vertex " + v + " is not between 0 and " + (V - 1));
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
