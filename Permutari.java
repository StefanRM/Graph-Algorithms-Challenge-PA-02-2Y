import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to solve the first problem: "Permutari".
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class Permutari {
	public static Graph graph = new Graph(); // graph used for topological sort
	// map used to identify a char (obtained from words' checking) to an integer
	// (node in graph)
	public static Map<Character, Integer> map =
			new HashMap<Character, Integer>();
	// list to store the solution of topological sort
	public static List<Integer> solution = new ArrayList<Integer>();
	public static boolean cycle = false; // if the built graph has a cycle

	public static void main(String[] args) throws IOException {
		// reading information from file
		MyScanner br = new MyScanner("permutari.in");
		int nrWords = br.nextInt(); // number of words
		String[] words = new String[nrWords]; // words from file array

		for (int i = 0; i < nrWords; i++) {
			words[i] = br.nextLine();
		}

		br.close();
		// end of reading information

		PrintWriter writer = new PrintWriter("permutari.out"); // output file

		// min - minimum length of two consecutive strings;
		// nrNodes - current number of nodes in graph - 1, because we use it as
		// index for adding in graph;
		// from, to - left, right node in the edge;
		// i, j - used in for loops
		int min, nrNodes = -1, from, to, i, j;
		boolean added; // if an edge was added for the two consecutive strings
		for (i = 0; i < nrWords - 1; i++) {
			added = false;
			min = Math.min(words[i].length(), words[i + 1].length());

			for (j = 0; j < min; j++) { // iterate to the smallest size
				if (words[i].charAt(j) != words[i + 1].charAt(j)) {
					// adding an edge; obtaining the nodes of the edge

					if (map.containsKey(words[i].charAt(j))) {
						// character already in map, so there is a node in graph
						from = map.get(words[i].charAt(j));
					} else {
						nrNodes++; // update number of nodes
						from = nrNodes;
						// adding character to the map and the node to graph
						map.put(words[i].charAt(j), nrNodes);
						graph.adjList.add(nrNodes, new ArrayList<Integer>());
					}

					if (map.containsKey(words[i + 1].charAt(j))) {
						// character already in map, so there is a node in graph
						to = map.get(words[i + 1].charAt(j));
					} else {
						nrNodes++; // update number of nodes
						to = nrNodes;
						// adding character to the map and the node to graph
						map.put(words[i + 1].charAt(j), nrNodes);
						graph.adjList.add(nrNodes, new ArrayList<Integer>());
					}

					graph.addEdge(from, to);
					added = true;
					break;
				}
			}

			// order not right in the file and no relevant information
			if (words[i].length() > words[i + 1].length() && !added) {
				writer.print("Imposibil");
				cycle = true; // mark it as it was a cycle (impossible case)
				break;
			}
		}

		if (!cycle) { // if the input was alright
			// do topological sort to obtain the order
			topologicalSort();

			if (!cycle) { // there was not any cycle
				// write the first part of permutation
				for (Integer integer : solution) {
					writer.print(getKeyMap(map, integer));
				}

				// add the rest of the alphabet
				for (char k = 'a'; k <= 'z'; k++) {
					if (!map.containsKey(k)) {
						writer.print(k);
					}
				}
			} else { // impossible case
				writer.print("Imposibil");
			}
		}

		writer.close(); // end of writing to file
	}

	/**
	 * This function does a topological sort for the built graph. It uses DFS
	 * algorithm.
	 */
	public static void topologicalSort() {
		int nrNodes = graph.getNrNodes(); // number of nodes in graph
		// keep track of discovering states of the nodes
		int[] coloured = new int[nrNodes];

		for (int node = 0; node < nrNodes; node++) {
			if (cycle) { // in case of cycle
				return;
			}

			if (coloured[node] == 0) { // an undiscovered node
				dfs(coloured, node);
			}
		}
	}

	/**
	 * This function does DFS starting with a given node. It keeps track of
	 * discovering states of the nodes (0 - White, Undiscovered; 1 - Grey,
	 * Discovering; 2 - Black, Discovered).
	 * 
	 * @param coloured
	 *            Array to keep track of discovering states of the nodes.
	 * @param node
	 *            Node to continue DFS.
	 */
	public static void dfs(int[] coloured, int node) {
		if (coloured[node] == 1) { // we went to a discovering node
			cycle = true; // found a cycle
			return;
		}

		if (coloured[node] == 0) { // an undiscovered node
			coloured[node] = 1; // mark as discovering
			for (int neighNode : graph.getNeighboursOf(node)) {
				dfs(coloured, neighNode); // go to neighbours
			}
			coloured[node] = 2; // mark as discovered
			if (cycle) { // in case of cycle
				return;
			}
			solution.add(0, node); // add to solution
		}
	}

	/**
	 * This function searches for a key in a given map by comparing a given
	 * value.
	 * 
	 * @param map
	 *            Map to search.
	 * @param value
	 *            Given value to find the corresponding key.
	 * @return The first key that has the given value, otherwise null.
	 */
	public static Character getKeyMap(Map<Character, Integer> map,
			Integer value) {
		for (Character key : map.keySet()) {
			if (map.get(key) == value) {
				return key;
			}
		}

		return null;
	}
}