/************************
 * Assignment 4 in course ID1020 for lab 4 at KTH
 *
 * Program creates an undirected weight edged graph where edges has special weight applied. The graph uses adjacency-lists for implementation.
 * Furthermore the program uses Kruskal's algorithm for finding the minimum spanning tree.
 ***********************/

import edu.princeton.cs.algs4.*;

public class L4Assignment4 {

	// declaration of american states mapped to array
	public static final String[] americanStates = new String[] {"AK", "AL", "AR", "AS", "AZ","CA", "CO", "CT", "DC", "DE", "FL", "GA","GU","HI",
          	"IA", "ID","IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS",
          	"MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "PR",
          	"RI", "SC", "SD", "TN", "TX", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY"};


	public static void main(String[] args) {

		EdgeWeightedGraph graph = new EdgeWeightedGraph(americanStates.length);
		double weight = 0;
		int i;

		// reads from StdIn, maps these to americanStates creates edges based on input
		// states with weight and adds these to graph.
		while (!StdIn.isEmpty()) {
				int spot1 = 0;
				int spot2 = 0;

				String state1 = StdIn.readString().toUpperCase();
				String state2 = StdIn.readString().toUpperCase();

				// iterating until proper element is found
				for(i = 0; i < americanStates.length; i++) {
					if(state1.equals(americanStates[spot1])) {
						break;
					}
					else spot1++;
				}

				// iterating until proper element is found
				for(i = 0; i < americanStates.length; i++) {
						if(state2.equals(americanStates[spot2])) {
								break;
						}
						else spot2++;
				}

				weight++;
				Edge edge = new Edge(spot1, spot2, weight);
				graph.addEdge(edge);
		}


		// Initializing the minimum spanning tree with the help of Kruskal's algorithm
		KruskalMST mst = new KruskalMST(graph);
	  for (Edge e : mst.edges()) {
	  		StdOut.println(americanStates[e.either()] + " -> " + americanStates[e.other(e.either())] + " " + e.weight());
	  }
	  StdOut.printf("%.5f\n", mst.weight());


	}

	// method mapping name of a state to return a mapped value in the array of
	// states
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

	// implementation of kruskals algorithm for finding the minimum spanning tree
	public static class KruskalMST {
	    private static final double FLOATING_POINT_EPSILON = 1E-12;

	    private double weight;                        // weight of MST
	    private Queue<Edge> mst = new Queue<Edge>();  // edges in MST

	    /**
	     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
	     */
	    public KruskalMST(EdgeWeightedGraph G) {
	        // more efficient to build heap by passing array of edges
	        MinPQ<Edge> pq = new MinPQ<Edge>();
	        for (Edge e : G.edges()) {
	            pq.insert(e);
	        }

	        // run greedy algorithm
	        UF uf = new UF(G.V());
	        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
	            Edge e = pq.delMin();
	            int v = e.either();
	            int w = e.other(v);
	            if (!uf.connected(v, w)) { // v-w does not create a cycle
	                uf.union(v, w);  // merge v and w components
	                mst.enqueue(e);  // add edge e to mst
	                weight += e.weight();
	            }
	        }

	        // check optimality conditions
	        assert check(G);
	    }

	    /**
	     * Returns the edges in a minimum spanning tree (or forest).
	     * @return the edges in a minimum spanning tree (or forest) as
	     *    an iterable of edges
	     */
	    public Iterable<Edge> edges() {
	        return mst;
	    }

	    /**
	     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
	     * @return the sum of the edge weights in a minimum spanning tree (or forest)
	     */
	    public double weight() {
	        return weight;
	    }

	    // check optimality conditions (takes time proportional to E V lg* V)
	    private boolean check(EdgeWeightedGraph G) {

	        // check total weight
	        double total = 0.0;
	        for (Edge e : edges()) {
	            total += e.weight();
	        }
	        if (Math.abs(total - weight()) > FLOATING_POINT_EPSILON) {
	            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", total, weight());
	            return false;
	        }

	        // check that it is acyclic
	        UF uf = new UF(G.V());
	        for (Edge e : edges()) {
	            int v = e.either(), w = e.other(v);
	            if (uf.connected(v, w)) {
	                System.err.println("Not a forest");
	                return false;
	            }
	            uf.union(v, w);
	        }

	        // check that it is a spanning forest
	        for (Edge e : G.edges()) {
	            int v = e.either(), w = e.other(v);
	            if (!uf.connected(v, w)) {
	                System.err.println("Not a spanning forest");
	                return false;
	            }
	        }

	        // check that it is a minimal spanning forest (cut optimality conditions)
	        for (Edge e : edges()) {

	            // all edges in MST except e
	            uf = new UF(G.V());
	            for (Edge f : mst) {
	                int x = f.either(), y = f.other(x);
	                if (f != e) uf.union(x, y);
	            }

	            // check that e is min weight edge in crossing cut
	            for (Edge f : G.edges()) {
	                int x = f.either(), y = f.other(x);
	                if (!uf.connected(x, y)) {
	                    if (f.weight() < e.weight()) {
	                        System.err.println("Edge " + f + " violates cut optimality conditions");
	                        return false;
	                    }
	                }
	            }

	        }

	        return true;
	    }
	}


	// implementation of a grapth with weighted edges
		public static class EdgeWeightedGraph {
			private final int V;
			private int E;
			private Bag<Edge>[] adj;

			public EdgeWeightedGraph(int V) {
				this.V = V;
				this.E = 0;
				adj = (Bag<Edge>[]) new Bag[V];
				for (int v = 0; v < V; v++) {
					adj[v] = new Bag<Edge>();
				}
			}

			private void validateVertex(int v) {
				if (v < 0 || v >= V)
					StdOut.println("vertex " + v + " is not between 0 and " + (V - 1));
			}

			public EdgeWeightedGraph(In in) {
				this(in.readInt());
				int E = in.readInt();
				if (E < 0)
					throw new IllegalArgumentException("Number of edges must be nonnegative");
				for (int i = 0; i < E; i++) {
					int v = in.readInt();
					int w = in.readInt();
					validateVertex(v);
					validateVertex(w);
					double weight = in.readDouble();
					Edge e = new Edge(v, w, weight);
					addEdge(e);
				}
			}

			public void addEdge(Edge e) {
				int v = e.either();
				int w = e.other(v);
				adj[v].add(e);
				adj[w].add(e);
				E++;
			}

			public Iterable<Edge> adj(int v) {
				return adj[v];
			}

			public Iterable<Edge> edges() {
				Bag<Edge> bag = new Bag<Edge>();
				for (int v = 0; v < V; v++)
					for (Edge e : adj[v])
						if (e.other(v) > v)
							bag.add(e);
				return bag;
			}

			/**
			 * Returns the number of vertices in this edge-weighted graph.
			 *
			 * @return the number of vertices in this edge-weighted graph
			 */
			public int V() {
				return V;
			}

			/**
			 * Returns the number of edges in this edge-weighted graph.
			 *
			 * @return the number of edges in this edge-weighted graph
			 */
			public int E() {
				return E;
			}
		}

	public static class Edge implements Comparable<Edge> {

	    private final int v;
	    private final int w;
	    private final double weight;

	    /**
	     * Initializes an edge between vertices {@code v} and {@code w} of
	     * the given {@code weight}.
	     *
	     * @param  v one vertex
	     * @param  w the other vertex
	     * @param  weight the weight of this edge
	     * @throws IllegalArgumentException if either {@code v} or {@code w}
	     *         is a negative integer
	     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
	     */
	    public Edge(int v, int w, double weight) {
	        if (v < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
	        if (w < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
	        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
	        this.v = v;
	        this.w = w;
	        this.weight = weight;
	    }

	    /**
	     * Returns the weight of this edge.
	     *
	     * @return the weight of this edge
	     */
	    public double weight() {
	        return weight;
	    }

	    /**
	     * Returns either endpoint of this edge.
	     *
	     * @return either endpoint of this edge
	     */
	    public int either() {
	        return v;
	    }

	    /**
	     * Returns the endpoint of this edge that is different from the given vertex.
	     *
	     * @param  vertex one endpoint of this edge
	     * @return the other endpoint of this edge
	     * @throws IllegalArgumentException if the vertex is not one of the
	     *         endpoints of this edge
	     */
	    public int other(int vertex) {
	        if      (vertex == v) return w;
	        else if (vertex == w) return v;
	        else throw new IllegalArgumentException("Illegal endpoint");
	    }

	    /**
	     * Compares two edges by weight.
	     * Note that {@code compareTo()} is not consistent with {@code equals()},
	     * which uses the reference equality implementation inherited from {@code Object}.
	     *
	     * @param  that the other edge
	     * @return a negative integer, zero, or positive integer depending on whether
	     *         the weight of this is less than, equal to, or greater than the
	     *         argument edge
	     */
	    @Override
	    public int compareTo(Edge that) {
	        return Double.compare(this.weight, that.weight);
	    }

	    /**
	     * Returns a string representation of this edge.
	     *
	     * @return a string representation of this edge
	     */
	    public String toString() {
	        return String.format("%d-%d %.5f", v, w, weight);
	    }
	}

}
