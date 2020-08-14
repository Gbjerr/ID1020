/************************
 * Assignment 5 in course ID1020 for lab 4 at KTH
 *
 * Program creates an directed graph where the graph uses adjacency-lists for implementation. Furthermore the program uses
 * depth first search for transversing paths and determining if a path exists from vertex X to vertex Y, if one exists.

	 Example run:
	 java -cp algs4.jar: L4Assignment5 MA PA < states.txt
	 There is indeed a path to from MA to PA
 ***********************/
import edu.princeton.cs.algs4.*;
public class L4Assignment5 {

	// declaration of american states mapped to array
	public static final String[] americanStates = new String[] {"AK", "AL", "AR", "AS", "AZ","CA", "CO", "CT", "DC", "DE", "FL", "GA","GU","HI",
          	"IA", "ID","IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS",
          	"MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "PR",
          	"RI", "SC", "SD", "TN", "TX", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY"};


	public static void main(String[] args) {

			// X point to initial start vertex and Y to end
			String X = args[0];
	    String Y = args[1];

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

					graph.addEdge(spot1, spot2);
			}

      DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(graph, selectState(X));

      if(dfs.hasPathTo(selectState(Y))) {
      		StdOut.println("There is indeed a path to from " + X + " to " + Y);
      }
			else StdOut.println("no path");

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
