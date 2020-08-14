/************************
 * Assignment 2 in course ID1020 for lab 4 at KTH
 *
 * Program creates an undirected graph, which uses adjacency-lists for implementation.
 * Furthermore the graph uses Breadth path search for finding and traversing paths
 * from vertex X to vertex Y if there is one.

 	 Example run:
	 java -cp algs4.jar: L4Assignment2 IA ID < states.txt
	 IA -> SD -> WY -> ID

 ***********************/
import edu.princeton.cs.algs4.*;

public class L4Assignment2 {

	// declaration of american states mapped to array
	public static final String[] americanStates = new String[] {"AK", "AL", "AR", "AS", "AZ","CA", "CO", "CT", "DC", "DE", "FL", "GA","GU","HI",
          	"IA", "ID","IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS",
          	"MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "PR",
          	"RI", "SC", "SD", "TN", "TX", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY"};

	public static void main(String[] args) {

				// X point to initial start vertex and Y to end vertex
        String X = args[0];
        String Y = args[1];

        Graph graph = new Graph(americanStates.length);

        // reads from StdIn, maps these to americanStatesm creates edges based on input states and adds these to graph.
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
		    BreadthFirstPaths bfs = new BreadthFirstPaths(graph, selectState(X));
		    StdOut.println(X + " -> " + bfs.pathTo(selectState(Y)));

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

	// implementation of breadth first search for finding all paths in the graph
	public static class BreadthFirstPaths {
        private boolean[] marked;
        private int[] edgeTo;
        private final int s;

        // argument s is the source of the path were looking for
        public BreadthFirstPaths(Graph G, int s) {
        	// create array with same size as amount of vertices
        	marked = new boolean[G.V()];
            edgeTo = new int[G.V()];
            this.s = s;
            bfs(G, s);
        }

        private void bfs(Graph G, int s) {
            Queue<Integer> queue = new Queue<Integer>(); //create a queue to know which vertices to check, dequeue while checked
            marked[s] = true;               //mark the vertex s to true
            queue.enqueue(s);               //put it on the queue
            while (!queue.isEmpty()) {      //while queue is not empty
                int v = queue.dequeue();    //remove next vertex from the queue
                for (int w : G.adj(v))      //iterate through the adj[v]
                    if (!marked[w]) {       //for every unmarked adjacent vertex
                        edgeTo[w] = v;      //save last edge on a shortest path
                        marked[w] = true;   //mark it because path is known,
                        queue.enqueue(w);   //and add it to the queue
                    }
            }
        }

        public boolean hasPathTo(int v) {
            return marked[v];
        }

        // returns string representation of the path to chosen vertex
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
}
