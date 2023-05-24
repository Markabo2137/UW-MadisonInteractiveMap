// --== CS400 File Header Information ==--
// Name: Ryan Kassem
// Email: rmkassem@wisc.edu
// Group and Team: AN Blue
// Group TA: Gary Dahl
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * Provides implementations for various algorithms, including computing the
 * Minimum Spanning Tree of a graph using Kruskal's algorithm, finding nodes
 * under a certain path cost, and printing the Minimum Spanning Tree. This class
 * also includes helper methods for use in Kruskal's algorithm, such as
 * findRepresentative() and union().
 * 
 * @param <NodeType> the type of data stored in each node
 * @param <EdgeType> the type of data stored in each edge
 */
public class AlgorithmExecutionAE<NodeType, EdgeType extends Number> extends DijkstraGraphAE<NodeType, EdgeType>
        implements AlgorithmExecutionInterfaceAE<NodeType, EdgeType> {

    /**
     * Retrieves the representative node of a given node within a disjoint-set data
     * structure.
     * The representative node helps identify if nodes are part of the same set.
     *
     * @param node            Node to find the representative for
     * @param representatives HashMap storing node representatives
     * @return The representative node for the given node
     */
    private Node findRepresentative(Node node, HashMap<Node, Node> representatives) {
        // Find the representative of the given node in the given disjoint-set data
        // structure
        Node representative = node;
        while (representatives.get(representative) != representative) {
            representative = representatives.get(representative);
        }
        return representative;
    }

    /**
     * Unites two disjoint sets by updating the representative of one set to the
     * representative of the other set.
     * This method is used in the context of a disjoint-set data structure for
     * Kruskal's algorithm.
     *
     * @param node1           First node involved in the union operation
     * @param node2           Second node involved in the union operation
     * @param representatives HashMap storing node representatives
     */
    private void union(Node node1, Node node2, HashMap<Node, Node> representatives) {
        Node rep1 = findRepresentative(node1, representatives);
        Node rep2 = findRepresentative(node2, representatives);
        representatives.put(rep1, rep2);
    }

    /**
     * Computes the Minimum Spanning Tree (MST) of the graph using Kruskal's
     * algorithm.
     * The method returns a new BaseGraph instance containing the nodes and edges of
     * the MST.
     *
     * @return A new BaseGraph instance representing the Minimum Spanning Tree of
     *         the graph
     */
    @Override
    public BaseGraph<NodeType, EdgeType> minimumSpanningTree() {
        // Create a new graph to store the minimum spanning tree
        BaseGraph<NodeType, EdgeType> mst = new BaseGraph<>();

        for (NodeType nodeData : nodes.keySet()) {
            mst.insertNode(nodeData);
        }

        ArrayList<Edge> allEdges = new ArrayList<>();
        for (Node node : nodes.values()) {
            allEdges.addAll(node.edgesLeaving);
        }

        // BaseGraph class is provided and cannot be edited to include compareTo():
        // Lambda expression: // Sort all edges in non-descending order of their weights
        Collections.sort(allEdges, (edge1, edge2) -> {
            // Return -1 if condition is true, 1 otherwise
            return edge1.data.doubleValue() < edge2.data.doubleValue() ? -1 : 1;
        });
        // Keep track of which nodes are connected
        HashMap<Node, Node> representatives = new HashMap<>();
        for (Node node : nodes.values()) {
            representatives.put(node, node);
        }
        // Iterate through all edges in ascending order of their weights and add them to
        // the MST
        for (Edge edge : allEdges) {
            Node pred = edge.predecessor;
            Node succ = edge.successor;

            if (findRepresentative(pred, representatives) != findRepresentative(succ, representatives)) {
                mst.insertEdge(pred.data, succ.data, edge.data);
                union(pred, succ, representatives);
            }
        }
        return mst;
    }

    /**
     * Generates a string representation of the Minimum Spanning Tree (MST) of the
     * graph.
     * The method computes the MST using minimumSpanningTree() and formats the
     * output as a string.
     *
     * @return A string representation of the Minimum Spanning Tree of the graph
     */
    @Override
    public String printMST() {
        // Compute the minimum spanning tree of the graph
        BaseGraph<NodeType, EdgeType> mst = minimumSpanningTree();
        String stringMST = "";
        // Loop over all nodes in the minimum spanning tree
        for (Node node : mst.nodes.values()) {
            // Loop over all edges leaving from the current node
            for (Edge edge : node.edgesLeaving) {
                // Format the edge data and add it to the string representation of the MST
                stringMST = "\n" + edge.successor.data + " - " + node.data + " : " + edge.data.doubleValue() + stringMST;
            }
        }
        // Check if the MST is empty
        if (stringMST.isBlank() || stringMST.isEmpty()) {
            return "The minimum spanning tree is empty.\n";
        }
        // If not empty, return built string
        return stringMST + "\n";
    }

    /**
     * Returns an ArrayList of node data whose total path cost from a start node is
     * less than or equal to a given cost cap.
     * 
     * @param start   The starting node for the search
     * @param costCap The maximum cost limit for the path
     * @return An ArrayList of node data that satisfies the given criteria
     * @throws NoSuchElementException If the given start node is not in the graph
     */
    @Override
    public ArrayList<NodeType> findUnderCost(Object start, int costCap) {
        // Check if the given start node is in the graph
        if (!nodes.containsKey(start)) {
            throw new NoSuchElementException("Given start node is not in the graph");
        }

        // Create ArrayList to store the node data satifsying the provided costCap
        ArrayList<NodeType> toReturn = new ArrayList<NodeType>();

        // Keep track of visited nodes
        Hashtable<NodeType, SearchNode> visitedNodes = new Hashtable<NodeType, SearchNode>();
        Node startNode = nodes.get(start);
        SearchNode startSearchNode = new SearchNode(startNode, 0, null);

        // Create a priority queue to store search nodes and sort them by cost
        PriorityQueue<SearchNode> queue = new PriorityQueue<SearchNode>();

        // Add the start search node to the priority queue
        queue.add(startSearchNode);

        // Continue searching until the priority queue is empty
        while (!queue.isEmpty()) {
            // Get the search node with the lowest cost
            SearchNode currentSearchNode = queue.poll();

            // Check if the current node has been visited
            if (!visitedNodes.containsKey(currentSearchNode.node.data)) {
                // Add the current node and its corresponding search node to the visitedNodes
                // Hashtable
                visitedNodes.put(currentSearchNode.node.data, currentSearchNode);
                double cost = currentSearchNode.cost;

                // Check the cost of all edges leaving the current node
                for (Edge edge : currentSearchNode.node.edgesLeaving) {
                    double edgeCost = edge.data.doubleValue();

                    if (!visitedNodes.containsKey(edge.successor.data)) {
                        // Successor node has not been visited, add it to the queue with the appropriate
                        // cost
                        queue.add(new SearchNode(edge.successor, edgeCost + cost, currentSearchNode));
                    } else {
                        // If the successor node has been visited, update its cost if the new cost is
                        // lower
                        if (edgeCost + cost < visitedNodes.get(edge.successor.data).cost) {
                            queue.add(new SearchNode(edge.successor, edgeCost + cost, currentSearchNode));
                        }
                    }
                }
            }
        }
        // Create an ArrayList of all node data in the graph
        ArrayList<NodeType> keys = new ArrayList<NodeType>(nodes.keySet());

        // Check each visited node to see if it satisfies the criteria
        for (NodeType key : keys) {
            if (visitedNodes.containsKey(key) && visitedNodes.get(key).cost <= costCap) {
                // The node satisfies the cost criteria, add its data to the toReturn ArrayList
                toReturn.add(key);
            }
        }
        return toReturn;
    }
}

