// --== CS400 File Header Information ==--
// Name: Ryan Kassem
// Email: rmkassem@wisc.edu
// Group and Team: AN Blue
// Group TA: Gary Dahl
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.util.PriorityQueue;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraphAE<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in it's node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in it's node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {

        if (start == null) {
            throw new NoSuchElementException("Given start node is null");
        }
        if (end == null) {
            throw new NoSuchElementException("Given end node is null");
        }
        if (!containsNode(start)) {
            throw new NoSuchElementException("Given start node does not correspond to the graph's start node");
        }
        if (!containsNode(end)) {
            throw new NoSuchElementException("Given end node does not correspond to the graph's end node");
        }

        // Priority queue which will hold the graph nodes
        PriorityQueue<SearchNode> queue = new PriorityQueue<>();

        // The starting node
        SearchNode startSearchNode = new SearchNode(nodes.get(start), 0, null);
        // Hastable which will hold all nodes that have been visisted and their
        // associated searchNodes
        Hashtable<NodeType, SearchNode> visitedNodes = new Hashtable<>();

        queue.add(startSearchNode);

        while (!queue.isEmpty()) {
            // Get first node to search from the priority queue
            SearchNode currentSearchNode = queue.poll();
            Node currentNode = currentSearchNode.node;

            // Start has reached the end
            if (currentNode.data.equals(end)) {
                return currentSearchNode;
            }

            // Determines all possible cost outcomes
            visitedNodes.put(currentNode.data, currentSearchNode);
            for (Edge edge : currentNode.edgesLeaving) {
                Node newNode = edge.successor; // Destination

                // Full cost if path is taken
                double totalCost = currentSearchNode.cost + edge.data.doubleValue();
                SearchNode newSearchNode = visitedNodes.get(newNode.data);

                // Determines if the new search node is null or costs more than full cost
                if (newSearchNode == null || (currentSearchNode.compareTo(newSearchNode) < 0)) {
                    newSearchNode = new SearchNode(newNode, totalCost, currentSearchNode);
                    queue.add(newSearchNode);
                }
            }
        }

        // If no path is found throw exception
        throw new NoSuchElementException("No path could be found from start to end");
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        List<NodeType> shortestPath = new LinkedList<>();
        SearchNode shortestPathSearchNode = computeShortestPath(start, end);

        // Goes through predecessor references from end to start node
        SearchNode currentSearchNode = shortestPathSearchNode;
        while (currentSearchNode != null) {
            shortestPath.add(0, currentSearchNode.node.data);
            currentSearchNode = currentSearchNode.predecessor;
        }
        return shortestPath;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        // Determines full cost from end search node
        SearchNode searchNode = computeShortestPath(start, end);
        return searchNode.cost;
    }
}

