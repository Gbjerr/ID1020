/**
 *
 * Assignment 4 for extra lab
 *
 * Following program calculates the shortest path from a node X to a node Y in a edge weighted digraph.
 * The user can by entering the second and third argument, decide the start node and the end node for the path.
 * The fourth argument represents a node which is mandatory to be included on the path from X to Y. The
 * shortest path is then calculated with the help of Dijkstras algrithm.
 *
 *
 */

import java.util.NoSuchElementException;
import java.lang.Integer;
import edu.princeton.cs.algs4.*;


public class L4Assignment4 {

    public static void main (String[] args) {
        // four arguments for the graph, node start point to end point, as well as
        // the mandatory node.
        In in = new In(args[0]);
        int startPoint = Integer.parseInt(args[1]);
        int endPoint = Integer.parseInt(args[2]);
        int mandatoryPoint = Integer.parseInt(args[3]);

        // creates an edge weighted greph based on first argument then proceeds to calculate
        // the shortest path from start point to all other nodes, and the shortest path from
        // the mandatory node to all other nodes.
        EdgeWeightedGraph graph = new EdgeWeightedGraph(in);
        DijkstraUndirectedSP startNode = new DijkstraUndirectedSP(graph, startPoint);
        DijkstraUndirectedSP mandatoryNode = new DijkstraUndirectedSP(graph, mandatoryPoint);

        // if a shortest path exists, we print that path out, and if not, simply
        // print out there is no such path.
        if(startNode.hasPathTo(mandatoryPoint) && mandatoryNode.hasPathTo(endPoint)) {

            System.out.println("There is a shortest path from node " + startPoint + " to node "
                    + endPoint + " including mandatory node " + mandatoryPoint + "\n");

            double path = startNode.distTo[mandatoryPoint] + mandatoryNode.distTo[endPoint];

            System.out.println("The shortest path is: " + path);

        }
        else {
            System.out.println("There is no shortest path from node " + startPoint + " to node "
                    + endPoint + " that includes the mandatory node " + mandatoryPoint + "\n");
        }

    }

    public static class EdgeWeightedGraph {
        private static final String NEWLINE = System.getProperty("line.separator");

        private final int V;
        private int E;
        private Bag<Edge>[] adj;

        /**
         * Initializes an empty edge-weighted graph with {@code V} vertices and 0 edges.
         *
         * @param  V the number of vertices
         * @throws IllegalArgumentException if {@code V < 0}
         */
        public EdgeWeightedGraph(int V) {
            if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
            this.V = V;
            this.E = 0;
            adj = (Bag<Edge>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<Edge>();
            }
        }


        /**
         * Initializes an edge-weighted graph from an input stream.
         * The format is the number of vertices <em>V</em>,
         * followed by the number of edges <em>E</em>,
         * followed by <em>E</em> pairs of vertices and edge weights,
         * with each entry separated by whitespace.
         *
         * @param  in the input stream
         * @throws IllegalArgumentException if {@code in} is {@code null}
         * @throws IllegalArgumentException if the endpoints of any edge are not in prescribed range
         * @throws IllegalArgumentException if the number of vertices or edges is negative
         */
        public EdgeWeightedGraph(In in) {
            if (in == null) throw new IllegalArgumentException("argument is null");

            try {
                V = in.readInt();
                adj = (Bag<Edge>[]) new Bag[V];
                for (int v = 0; v < V; v++) {
                    adj[v] = new Bag<Edge>();
                }

                int E = in.readInt();
                if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
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
            catch (NoSuchElementException e) {
                throw new IllegalArgumentException("invalid input format in EdgeWeightedGraph constructor", e);
            }

        }

        /**
         * Initializes a new edge-weighted graph that is a deep copy of {@code G}.
         *
         * @param  G the edge-weighted graph to copy
         */
        public EdgeWeightedGraph(EdgeWeightedGraph G) {
            this(G.V());
            this.E = G.E();
            for (int v = 0; v < G.V(); v++) {
                // reverse so that adjacency list is in same order as original
                Stack<Edge> reverse = new Stack<Edge>();
                for (Edge e : G.adj[v]) {
                    reverse.push(e);
                }
                for (Edge e : reverse) {
                    adj[v].add(e);
                }
            }
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

        // throw an IllegalArgumentException unless {@code 0 <= v < V}
        private void validateVertex(int v) {
            if (v < 0 || v >= V)
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
        }

        /**
         * Adds the undirected edge {@code e} to this edge-weighted graph.
         *
         * @param  e the edge
         * @throws IllegalArgumentException unless both endpoints are between {@code 0} and {@code V-1}
         */
        public void addEdge(Edge e) {
            int v = e.either();
            int w = e.other(v);
            validateVertex(v);
            validateVertex(w);
            adj[v].add(e);
            adj[w].add(e);
            E++;
        }

        /**
         * Returns the edges incident on vertex {@code v}.
         *
         * @param  v the vertex
         * @return the edges incident on vertex {@code v} as an Iterable
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public Iterable<Edge> adj(int v) {
            validateVertex(v);
            return adj[v];
        }

        /**
         * Returns the degree of vertex {@code v}.
         *
         * @param  v the vertex
         * @return the degree of vertex {@code v}
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public int degree(int v) {
            validateVertex(v);
            return adj[v].size();
        }

        /**
         * Returns all edges in this edge-weighted graph.
         * To iterate over the edges in this edge-weighted graph, use foreach notation:
         * {@code for (Edge e : G.edges())}.
         *
         * @return all edges in this edge-weighted graph, as an iterable
         */
        public Iterable<Edge> edges() {
            Bag<Edge> list = new Bag<Edge>();
            for (int v = 0; v < V; v++) {
                int selfLoops = 0;
                for (Edge e : adj(v)) {
                    if (e.other(v) > v) {
                        list.add(e);
                    }
                    // add only one copy of each self loop (self loops will be consecutive)
                    else if (e.other(v) == v) {
                        if (selfLoops % 2 == 0) list.add(e);
                        selfLoops++;
                    }
                }
            }
            return list;
        }

        /**
         * Returns a string representation of the edge-weighted graph.
         * This method takes time proportional to <em>E</em> + <em>V</em>.
         *
         * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
         *         followed by the <em>V</em> adjacency lists of edges
         */
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(V + " " + E + NEWLINE);
            for (int v = 0; v < V; v++) {
                s.append(v + ": ");
                for (Edge e : adj[v]) {
                    s.append(e + "  ");
                }
                s.append(NEWLINE);
            }
            return s.toString();
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

    public static class DijkstraUndirectedSP {
        private double[] distTo;          // distTo[v] = distance  of shortest s->v path
        private Edge[] edgeTo;            // edgeTo[v] = last edge on shortest s->v path
        private IndexMinPQ<Double> pq;    // priority queue of vertices

        /**
         * Computes a shortest-paths tree from the source vertex {@code s} to every
         * other vertex in the edge-weighted graph {@code G}.
         *
         * @param  G the edge-weighted digraph
         * @param  s the source vertex
         * @throws IllegalArgumentException if an edge weight is negative
         * @throws IllegalArgumentException unless {@code 0 <= s < V}
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
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }

        /**
         * Returns the length of a shortest path between the source vertex {@code s} and
         * vertex {@code v}.
         *
         * @param  v the destination vertex
         * @return the length of a shortest path between the source vertex {@code s} and
         *         the vertex {@code v}; {@code Double.POSITIVE_INFINITY} if no such path
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public double distTo(int v) {
            validateVertex(v);
            return distTo[v];
        }

        /**
         * Returns true if there is a path between the source vertex {@code s} and
         * vertex {@code v}.
         *
         * @param  v the destination vertex
         * @return {@code true} if there is a path between the source vertex
         *         {@code s} to vertex {@code v}; {@code false} otherwise
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public boolean hasPathTo(int v) {
            validateVertex(v);
            return distTo[v] < Double.POSITIVE_INFINITY;
        }

        /**
         * Returns a shortest path between the source vertex {@code s} and vertex {@code v}.
         *
         * @param  v the destination vertex
         * @return a shortest path between the source vertex {@code s} and vertex {@code v};
         *         {@code null} if no such path
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public Iterable<Edge> pathTo(int v) {
            validateVertex(v);
            if (!hasPathTo(v)) return null;
            Stack<Edge> path = new Stack<Edge>();
            int x = v;
            for (Edge e = edgeTo[v]; e != null; e = edgeTo[x]) {
                path.push(e);
                x = e.other(x);
            }
            return path;
        }


        // check optimality conditions:
        // (i) for all edges e = v-w:            distTo[w] <= distTo[v] + e.weight()
        // (ii) for all edge e = v-w on the SPT: distTo[w] == distTo[v] + e.weight()
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
                if (v == s) continue;
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

            // check that all edges e = v-w on SPT satisfy distTo[w] == distTo[v] + e.weight()
            for (int w = 0; w < G.V(); w++) {
                if (edgeTo[w] == null) continue;
                Edge e = edgeTo[w];
                if (w != e.either() && w != e.other(e.either())) return false;
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
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
        }
    }

}
