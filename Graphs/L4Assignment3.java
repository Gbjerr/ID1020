/************************
 * Assignment 3 in course ID1020 for lab 4 at KTH
 *
 * Program creates an undirected weight edged graph where edges has special weight applied. The grapth uses adjacency-lists for implementation.
 * Furthermore the program uses Djikstras algorithm for traversing shortest the path from vertex X to vertex Y if there is one.

	 Example run:
	 java -cp algs4.jar: L4Assignment3 MA PA < states.txt
	 Distance of shortest path from MA to PA is 144.0
 ***********************/
import edu.princeton.cs.algs4.*;
import java.util.Random;
import java.lang.Math.*;

public class L4Assignment3 {

	// declaration of american states mapped to array
	public static final String[] americanStates = new String[] { "AK", "AL", "AR", "AS", "AZ", "CA", "CO", "CT", "DC",
			"DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO",
			"MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "PR", "RI", "SC", "SD",
			"TN", "TX", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY" };

	public static void main(String[] args) {
		// X point to initial start vertex and Y to end
		String X = args[0];
		String Y = args[1];
		double weight = 0;


		EdgeWeightedGraph graph = new EdgeWeightedGraph(americanStates.length);

		// reads from StdIn, maps these to americanStates creates edges based on input
		// states and adds these to graph.
		while (!StdIn.isEmpty()) {
			int spot1 = 0;
			int spot2 = 0;

			String state1 = StdIn.readString().toUpperCase();
			String state2 = StdIn.readString().toUpperCase();

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

			weight++;
			graph.addEdge(new Edge(spot1, spot2, weight));
		}

		DijkstraUndirectedSP dijk = new DijkstraUndirectedSP(graph, selectState(X));

		StdOut.println("Distance of shortest path from " + X + " to " + Y + " is " + dijk.distTo(selectState(Y)));

	}

	// method mapping name of a state to return a mapped value
	public static int selectState(String s) {

			int i = 0;
			while(i < americanStates.length) {
					if(s.equals(americanStates[i])) {
							break;
					}
					i++;
			}
			return i;
	}

	// implementation of dijstras algorithm for finding the shortest path
	public static class DijkstraUndirectedSP {
		private double[] distTo;
		private Edge[] edgeTo;
		private IndexMinPQ<Double> pq;

		/**
		 * Computes a shortest-paths tree from the source vertex to every
		 * other vertex in the edge-weighted graph
		 */
		public DijkstraUndirectedSP(EdgeWeightedGraph G, int s) {
			for (Edge e : G.edges()) {
				if (e.weight() < 0)
					throw new IllegalArgumentException("edge " + e + " has negative weight");
			}

			distTo = new double[G.V()];
			edgeTo = new Edge[G.V()];

			validateVertex(s);

			for (int v = 0; v < G.V(); v++)
				distTo[v] = Double.POSITIVE_INFINITY;
			distTo[s] = 0.0;

			// relax vertices in order of distance from s
			pq = new IndexMinPQ<Double>(G.V());
			pq.insert(s, distTo[s]);
			while (!pq.isEmpty()) {
				int v = pq.delMin();
				for (Edge e : G.adj(v))
					relax(e, v);
			}

			// check optimality conditions
			assert check(G, s);
		}

		// relax edge e and update pq if changed
		private void relax(Edge e, int v) {
			int w = e.other(v);
			if (distTo[w] > distTo[v] + e.weight()) {
				distTo[w] = distTo[v] + e.weight();
				edgeTo[w] = e;
				if (pq.contains(w))
					pq.decreaseKey(w, distTo[w]);
				else
					pq.insert(w, distTo[w]);
			}
		}

		/**
		 * Returns the length of a shortest path between the source vertex {@code s} and
		 * vertex.
		 */
		public double distTo(int v) {
			validateVertex(v);
			return distTo[v];
		}

		/**
		 * Returns true if there is a path between the source vertex {@code s} and
		 * vertex
		 */
		public boolean hasPathTo(int v) {
			validateVertex(v);
			return distTo[v] < Double.POSITIVE_INFINITY;
		}

		/**
		 * Returns a shortest path between the source vertex {@code s} and vertex
		 */
		public Iterable<Edge> pathTo(int v) {
			validateVertex(v);
			if (!hasPathTo(v))
				return null;
			Stack<Edge> path = new Stack<Edge>();
			int x = v;
			for (Edge e = edgeTo[v]; e != null; e = edgeTo[x]) {
				path.push(e);
				x = e.other(x);
			}
			return path;
		}


		private boolean check(EdgeWeightedGraph G, int s) {

			// check that edge weights are nonnegative
			for (Edge e : G.edges()) {
				if (e.weight() < 0) {
					System.err.println("negative edge weight detected");
					return false;
				}
			}

			// check that distTo[v] and edgeTo[v] are consistent
			if (distTo[s] != 0.0 || edgeTo[s] != null) {
				System.err.println("distTo[s] and edgeTo[s] inconsistent");
				return false;
			}
			for (int v = 0; v < G.V(); v++) {
				if (v == s)
					continue;
				if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
					System.err.println("distTo[] and edgeTo[] inconsistent");
					return false;
				}
			}

			// check that all edges e = v-w satisfy distTo[w] <= distTo[v] + e.weight()
			for (int v = 0; v < G.V(); v++) {
				for (Edge e : G.adj(v)) {
					int w = e.other(v);
					if (distTo[v] + e.weight() < distTo[w]) {
						System.err.println("edge " + e + " not relaxed");
						return false;
					}
				}
			}

			// check that all edges e = v-w on SPT satisfy distTo[w] == distTo[v] +
			// e.weight()
			for (int w = 0; w < G.V(); w++) {
				if (edgeTo[w] == null)
					continue;
				Edge e = edgeTo[w];
				if (w != e.either() && w != e.other(e.either()))
					return false;
				int v = e.other(w);
				if (distTo[v] + e.weight() != distTo[w]) {
					System.err.println("edge " + e + " on shortest path not tight");
					return false;
				}
			}
			return true;
		}

		// throw an IllegalArgumentException unless {@code 0 <= v < V}
		private void validateVertex(int v) {
			int V = distTo.length;
			if (v < 0 || v >= V)
				throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
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
		 * Initializes an edge between vertices {@code v} and {@code w} of the given
		 * {@code weight}
		 */
		public Edge(int v, int w, double weight) {
			if (v < 0)
				throw new IllegalArgumentException("vertex index must be a nonnegative integer");
			if (w < 0)
				throw new IllegalArgumentException("vertex index must be a nonnegative integer");
			if (Double.isNaN(weight))
				throw new IllegalArgumentException("Weight is NaN");
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
		 * @param vertex one endpoint of this edge
		 * @return the other endpoint of this edge
		 * @throws IllegalArgumentException if the vertex is not one of the endpoints of
		 *                                  this edge
		 */
		public int other(int vertex) {
			if (vertex == v)
				return w;
			else if (vertex == w)
				return v;
			else
				throw new IllegalArgumentException("Illegal endpoint");
		}

		/**
		 * Compares two edges by weight. Note that {@code compareTo()} is not consistent
		 * with {@code equals()}, which uses the reference equality implementation
		 * inherited from {@code Object}.
		 */
		@Override
		public int compareTo(Edge that) {
			return Double.compare(this.weight, that.weight);
		}

		/**
		 * Returns a string representation of this edge.

		 */
		public String toString() {
			return String.format("%d-%d %.5f", v, w, weight);
		}
	}

}
