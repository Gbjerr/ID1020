/************************
 * Assignment 1 in course ID1020 for lab 4 at KTH
 *
 * Program creates an undirected graph, which uses adjacency-lists for implementation.
 * Furthermore the graph uses Depth First Search for finding and traversing paths
 * from X to Y if there is a path.

 	Example run:
	java -cp algs4.jar: L4Assignment1 IA ID < states.txt
	IA -> WI -> MN -> SD -> WY -> NV -> UT -> ID
	
 ***********************/
import edu.princeton.cs.algs4.*;

public class L4Assignment1 {

	// declaration of american states mapped to array
	public static final String[] americanStates = new String[] {"AK", "AL", "AR", "AS", "AZ","CA", "CO", "CT", "DC", "DE", "FL", "GA","GU","HI",
          	"IA", "ID","IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS",
          	"MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "PR",
          	"RI", "SC", "SD", "TN", "TX", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY"};

	public static void main(String[] args) {

		// X point to start and Y to end of searched path
        String X = args[0];
        String Y = args[1];

        Graph graph = new Graph(americanStates.length);

        // reads from StdIn, maps these to americanStates creates edges based on
				// input states and adds these to graph.
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

						graph.addEdge(spot1, spot2);
				}

        // create instance of depth first path search and print path if there is one.
        DepthFirstPaths dfs = new DepthFirstPaths(graph, selectState(X));
        StdOut.println(X + " -> " + dfs.pathTo(selectState(Y)));

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


	// graph implementation using adjacency lists for vertex connecting a vertex.
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

        // return vertices
        public int V() {
            return V;
        }

        // return amt of edges
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

    // implementation of depth first seach for finding all paths in the graph
    public static class DepthFirstPaths {
        private boolean[] marked;
        private int[] edgeTo;
        private final int s;

        public DepthFirstPaths(Graph G, int s) {
            // create array with same size as amount of vertices
        	marked = new boolean[G.V()];
            edgeTo = new int[G.V()];
            this.s = s;
            dfs(G, s);
        }

      // when all vertices are marked as true, then all vertices all visited
        private void dfs(Graph G, int v) {
            marked[v] = true;
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

        // returns path in form of a string
        public String pathTo(int v) {
        	if (!hasPathTo(v))
                return null;

        	StringBuilder sb = new StringBuilder();
        	int i = 0;

        	for (int x = v; x != s; x = edgeTo[x]) {
                i++;
            }

        	String [] collection = new String[i];
        	i = 0;
            for (int x = v; x != s; x = edgeTo[x]) {
                collection[i] = americanStates[x];
                i++;
            }

            for(i = collection.length - 1; i >= 0; i--) {
            	if(i == 0) sb.append(collection[i]);
            	else sb.append(collection[i] + " -> ");
            }
            return sb.toString();
        }
    }

}
