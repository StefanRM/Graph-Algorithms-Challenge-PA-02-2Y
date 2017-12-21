import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to store a graph using adjacency list for each node.
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class Graph {

	public List<List<Integer>> adjList; // adjacency list

	public Graph() {
		adjList = new ArrayList<List<Integer>>();
	}

	public Graph(int nrNodes) {
		adjList = new ArrayList<List<Integer>>();

		// initializing a list for all nodes
		for (int i = 0; i < nrNodes; i++) {
			adjList.add(i, new ArrayList<Integer>());
		}
	}

	/**
	 * This function adds an edge to the graph. An edge: (from, to).
	 * 
	 * @param from
	 *            Left node in the edge.
	 * @param to
	 *            Right node in the edge.
	 */
	void addEdge(int from, int to) {
		adjList.get(from).add(to);
	}

	/**
	 * This function returns the number of nodes in graph.
	 * 
	 * @return Number of nodes in graph.
	 */
	int getNrNodes() {
		return adjList.size();
	}

	/**
	 * This function returns a list of all neighbours of a given node.
	 * 
	 * @param node
	 *            Index of the node.
	 * @return Given node's list with its neighbours.
	 */
	List<Integer> getNeighboursOf(int node) {
		return adjList.get(node);
	}

	/**
	 * This function gets the total number of nodes in the graph.
	 * 
	 * @return Total number of nodes in graph.
	 */
	int getTotalNumOfNodes() {
		return adjList.size();
	}
}
