// --== CS400 File Header Information ==--
// Name: Ryan Kassem
// Email: rmkassem@wisc.edu
// Group and Team: AN Blue
// Group TA: Gary Dahl
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.util.ArrayList;

public interface AlgorithmExecutionInterfaceAE<NodeType, EdgeType extends Number> {
    // AE class definition:
    // public class AlgorithmExecutionAE<NodeType, EdgeType extends Number> extends DijkstraGraphAE<NodeType, EdgeType>
    // implements AlgorithmExecutionInterfaceAE<NodeType, EdgeType>

    // Computes the Minimum Spanning Tree (MST) of the graph using Kruskal's or Prim's algorithm.
    public BaseGraph<NodeType, EdgeType> minimumSpanningTree();

    // Helper methods for minimumSpanningTree():
    // private Node findRepresentative(Node node, HashMap<Node, Node> representatives)
    // private void union(Node node1, Node node2, HashMap<Node, Node> representatives)

    // Generates a string representation of the Minimum Spanning Tree (MST) of the graph.
    public String printMST();
    // Determines the buildings which could be visited given a cost cap
    public ArrayList<NodeType> findUnderCost(NodeType start, int costCap);
}

