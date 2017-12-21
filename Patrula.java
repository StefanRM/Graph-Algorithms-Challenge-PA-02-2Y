import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class is used to solve the second problem: "Patrula".
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class Patrula {
	// graph used to represent the cities and the streets between them.
	public static Graph graph;

	public static void main(String[] args) throws IOException {
		// reading information from file
		MyScanner br = new MyScanner("patrula.in");
		int N = br.nextInt(), M = br.nextInt(), i, from, to;
		graph = new Graph(N); // initialize graph

		// reading the edges, but we have to keep in mind that the nodes'
		// indexes in our graph start from 0 and in file they start from 1
		for (i = 0; i < M; i++) {
			from = br.nextInt() - 1;
			to = br.nextInt() - 1;
			graph.addEdge(from, to);
			graph.addEdge(to, from);
		}

		br.close();
		// end of reading information

		PrintWriter writer = new PrintWriter("patrula.out"); // output file
		// number of minimum paths from (N - 1) to 0 that passes through each
		// node in graph (enters the node)
		long[] paths_in = new long[N];
		// number of minimum paths from 0 to (N - 1) that passes through each
		// node in graph (enters the node)
		long[] paths_out = new long[N];
		// write in output file the number of minimum paths from (N - 1) to 0
		// paths_in is also updated
		writer.println(bfs(N - 1, 0, paths_in));
		long maxNrEdges = -1; // maximum number of enlightened edges

		// same number of paths as the one written in the file, but now
		// paths_out differs from paths_in because of the direction of paths
		// (now it is from 0 to (N - 1))
		final long nrPaths = bfs(0, N - 1, paths_out);
		// number of edges to be lightened for each node in graph
		long[] edgesThroughNode = new long[N];

		// nodes 0 and (N - 1) are terminal, so the number of edges is just the
		// numbers of paths_in and paths_out multiplied
		edgesThroughNode[0] = paths_in[0] * paths_out[0];
		edgesThroughNode[N - 1] = paths_in[N - 1] * paths_out[N - 1];
		// for the rest of nodes, each path gives two edges to enlighten
		for (i = 1; i < N - 1; i++) {
			edgesThroughNode[i] = 2 * paths_in[i] * paths_out[i];
		}

		for (i = 0; i < N; i++) { // obtain the maximum number of edges
			if (maxNrEdges < edgesThroughNode[i]) {
				maxNrEdges = edgesThroughNode[i];
			}
		}

		// write the mean in the file
		writer.println(String.format("%.3f",
				((float) maxNrEdges) / ((float) nrPaths)));
		writer.close(); // end of writing to file
	}

	/**
	 * This function does a BFS to our graph and computes number of minimum
	 * paths for each node from the start to stop direction. It keeps track of
	 * discovering states of the nodes (0 - White, Undiscovered; 1 - Grey,
	 * Discovering; 2 - Black, Discovered).
	 * 
	 * @param start
	 *            Start node
	 * @param stop
	 *            Stop/Destination node
	 * @param paths
	 *            Number of minimum paths for each node from the start to stop
	 *            direction
	 * @return Number of minimum paths to stop node, if we started with the
	 *         start node.
	 */
	public static long bfs(int start, int stop, long[] paths) {
		int nrNodes = graph.getTotalNumOfNodes(); // number of nodes
		// keep track of discovering states of the nodes
		int[] colour = new int[nrNodes];
		// distance from the start node to each node
		int[] dist = new int[nrNodes];
		int i, u; // i - for loop, u - node
		// parent of each node in the discovering process
		int[] parent = new int[nrNodes];

		// initializing all arrays
		for (i = 0; i < nrNodes; i++) {
			parent[i] = -1;
			colour[i] = 0;
			dist[i] = Integer.MAX_VALUE;
			paths[i] = 0;
		}

		// start node case
		paths[start] = 1;
		colour[start] = 1;
		dist[start] = 0;
		// queue for doing bfs()
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(start);
		while (!q.isEmpty()) {
			u = q.poll();
			if (u == stop) { // destination has been reached
				break;
			}
			for (Integer v : graph.getNeighboursOf(u)) {
				if (colour[v] == 0) { // undiscovered node
					dist[v] = dist[u] + 1; // update distance
					parent[v] = u; // update parent
					// the child will have the same number of minimum paths as
					// its parent
					paths[v] = paths[u];
					colour[v] = 1; // mark that it entered the discovering state
					q.add(v);
				} else {
					// discovered or in the process of discovering

					// if a shorter path was found (a parent closer to the
					// start node was found)
					if ((v != start) && (dist[parent[v]] > dist[u])) {
						parent[v] = u; // update parent
						paths[v] = paths[u]; // update number of minimum paths
					}

					// if another shortest path was found (the parent level is
					// the same)
					if ((v != start) && (dist[parent[v]] == dist[u])) {
						paths[v] += paths[u]; // add the new paths
					}
				}
			}

			colour[u] = 2; // mark as discovered
		}

		return paths[stop];
	}
}
