// --== CS400 File Header Information ==--
// Name: Ryan Kassem
// Email: rmkassem@wisc.edu
// Group and Team: AN Blue
// Group TA: Gary Dahl
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlgorithmEngineerTests<NodeType, EdgeType extends Number> {

    @Test
    /**
     * Tests that a NoSuchElementException is thrown as expected seeing as no path
     * should be found given how this graph is built
     */
    public void testDijkstraNoPathFound() {
        DijkstraGraphAE<String, Integer> graph = new DijkstraGraphAE<String, Integer>();

        // Insert all nodes
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("L");
        graph.insertNode("M");

        // Create all edges and associated costs
        graph.insertEdge(graph.nodes.get("A").data, graph.nodes.get("B").data, 1);
        graph.insertEdge(graph.nodes.get("A").data, graph.nodes.get("H").data, 8);
        graph.insertEdge(graph.nodes.get("A").data, graph.nodes.get("M").data, 5);
        graph.insertEdge(graph.nodes.get("B").data, graph.nodes.get("M").data, 3);
        graph.insertEdge(graph.nodes.get("D").data, graph.nodes.get("A").data, 7);
        graph.insertEdge(graph.nodes.get("D").data, graph.nodes.get("G").data, 2);
        graph.insertEdge(graph.nodes.get("F").data, graph.nodes.get("G").data, 9);
        graph.insertEdge(graph.nodes.get("G").data, graph.nodes.get("L").data, 7);
        graph.insertEdge(graph.nodes.get("H").data, graph.nodes.get("B").data, 6);
        graph.insertEdge(graph.nodes.get("H").data, graph.nodes.get("I").data, 2);
        graph.insertEdge(graph.nodes.get("I").data, graph.nodes.get("H").data, 2);
        graph.insertEdge(graph.nodes.get("I").data, graph.nodes.get("L").data, 5);
        graph.insertEdge(graph.nodes.get("I").data, graph.nodes.get("D").data, 1);
        graph.insertEdge(graph.nodes.get("M").data, graph.nodes.get("F").data, 4);
        graph.insertEdge(graph.nodes.get("M").data, graph.nodes.get("E").data, 3);

        // Should throw NoSuchElementException as no path should be found from start to
        // end
        try {
            graph.computeShortestPath("M", "I");
        } catch (NoSuchElementException e) {
            assertEquals("No path could be found from start to end", e.getMessage());
        }
    }

    /**
     * Tests a graph with multiple edges between nodes.
     * Checks that Dijsktra's algorithm functions as expected as well as for
     * shortestPathData and shortedPathCost
     */
    @Test
    public void testDijkstraGraphWithMultipleEdges() {
        DijkstraGraphAE<String, Integer> graph = new DijkstraGraphAE<String, Integer>();

        // Insert all nodes
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("L");
        graph.insertNode("M");

        // Create all edges and associated costs
        graph.insertEdge(graph.nodes.get("A").data, graph.nodes.get("B").data, 1);
        graph.insertEdge(graph.nodes.get("A").data, graph.nodes.get("H").data, 8);
        graph.insertEdge(graph.nodes.get("A").data, graph.nodes.get("M").data, 5);
        graph.insertEdge(graph.nodes.get("B").data, graph.nodes.get("M").data, 3);
        graph.insertEdge(graph.nodes.get("D").data, graph.nodes.get("A").data, 7);
        graph.insertEdge(graph.nodes.get("D").data, graph.nodes.get("G").data, 2);
        graph.insertEdge(graph.nodes.get("F").data, graph.nodes.get("G").data, 9);
        graph.insertEdge(graph.nodes.get("G").data, graph.nodes.get("L").data, 7);
        graph.insertEdge(graph.nodes.get("H").data, graph.nodes.get("B").data, 6);
        graph.insertEdge(graph.nodes.get("H").data, graph.nodes.get("I").data, 2);
        graph.insertEdge(graph.nodes.get("I").data, graph.nodes.get("H").data, 2);
        graph.insertEdge(graph.nodes.get("I").data, graph.nodes.get("L").data, 5);
        graph.insertEdge(graph.nodes.get("I").data, graph.nodes.get("D").data, 1);
        graph.insertEdge(graph.nodes.get("M").data, graph.nodes.get("F").data, 4);
        graph.insertEdge(graph.nodes.get("M").data, graph.nodes.get("E").data, 3);

        // Check that the expected results are returned for cost and path sequence
        assertEquals("[H, I, D, G]",
                graph.shortestPathData(graph.nodes.get("H").data, graph.nodes.get("G").data).toString());
        assertEquals(5, graph.shortestPathCost(graph.nodes.get("H").data, graph.nodes.get("G").data));
    }

    /**
     * Tests a graph with multiple edges between nodes.
     * Checks that printMST() prints the correct minimum spanning tree for the
     * created graph
     */
    @Test
    public void testMSTGraphWithMultipleEdges() {
        AlgorithmExecutionAE<String, Integer> graph = new AlgorithmExecutionAE<>();

        // Insert all nodes
        graph.insertNode("0");
        graph.insertNode("1");
        graph.insertNode("2");
        graph.insertNode("3");
        graph.insertNode("4");

        // Create all edges and associated costs
        graph.insertEdge("0", "1", 2);
        graph.insertEdge("0", "3", 6);
        graph.insertEdge("1", "0", 2);
        graph.insertEdge("1", "2", 3);
        graph.insertEdge("1", "3", 8);
        graph.insertEdge("1", "4", 5);
        graph.insertEdge("2", "1", 3);
        graph.insertEdge("2", "4", 7);
        graph.insertEdge("3", "0", 6);
        graph.insertEdge("3", "1", 8);
        graph.insertEdge("3", "4", 9);
        graph.insertEdge("4", "1", 5);
        graph.insertEdge("4", "2", 7);
        graph.insertEdge("4", "3", 9);

        String listMST = graph.printMST();
        String expectedOutput = "\n0 - 1 : 2.0\n1 - 2 : 3.0\n0 - 3 : 6.0\n1 - 4 : 5.0\n";
        assertEquals(expectedOutput, listMST);
    }

    /**
     * Tests a graph with a single node.
     * Checks if printMST() prints out the correct message when the MST is empty
     */
    @Test
    public void testSingleGraphNode() {
        AlgorithmExecutionAE<String, Integer> graph = new AlgorithmExecutionAE<>();

        graph.insertNode("A");

        String listMST = graph.printMST();
        String expectedOutput = "The minimum spanning tree is empty.\n";
        assertEquals(expectedOutput, listMST);
    }

    /**
     * Tests a disconnected graph.
     * Checks that printMST() prints out the minimum spanning tree for each
     * connected component separately
     */
    @Test
    public void testDisconnectedGraph() {
        AlgorithmExecutionAE<String, Integer> graph = new AlgorithmExecutionAE<>();

        // Insert all nodes
        graph.insertNode("0");
        graph.insertNode("1");
        graph.insertNode("2");
        graph.insertNode("3");
        graph.insertNode("4");

        // Create all edges and associated costs
        graph.insertEdge("0", "1", 2);
        graph.insertEdge("1", "0", 2);
        graph.insertEdge("1", "2", 3);
        graph.insertEdge("2", "1", 3);
        graph.insertEdge("3", "4", 9);
        graph.insertEdge("4", "3", 9);

        String listMST = graph.printMST();
        String expectedOutput = "\n0 - 1 : 2.0\n1 - 2 : 3.0\n3 - 4 : 9.0\n";
        assertEquals(expectedOutput, listMST);
    }

    /**
     * Tests that findUnderCost() works as expected given different cost caps
     */
    @Test
    public void testFindUnderCost() {
        AlgorithmExecutionAE<String, Integer> graph = new AlgorithmExecutionAE<String, Integer>();

        // Insert all nodes
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        // Create all edges and associated costs
        graph.insertEdge("A", "B", 4);
        graph.insertEdge("A", "C", 3);
        graph.insertEdge("B", "C", 2);
        graph.insertEdge("B", "D", 5);
        graph.insertEdge("C", "D", 7);
        graph.insertEdge("C", "E", 3);
        graph.insertEdge("D", "E", 2);
        graph.insertEdge("D", "F", 6);
        graph.insertEdge("E", "F", 5);
        graph.insertEdge("E", "G", 5);
        graph.insertEdge("F", "G", 2);
        graph.insertEdge("F", "H", 4);
        graph.insertEdge("G", "H", 3);

        // Cost cap = 7 - Should include: A, B, C, E
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("A");
        expected.add("B");
        expected.add("C");
        expected.add("E");
        ArrayList<String> result = graph.findUnderCost("A", 7);
        Collections.sort(result);
        assertEquals(expected, result);

        // Cost cap = 10 - Should include: A, B, C, D, E
        ArrayList<String> expected1 = new ArrayList<String>();
        expected1.add("A");
        expected1.add("B");
        expected1.add("C");
        expected1.add("D");
        expected1.add("E");
        ArrayList<String> result1 = graph.findUnderCost("A", 10);
        Collections.sort(result1);
        assertEquals(expected1, result1);

        // Cost cap = 15 - Should include: A, B, C, D, E, F, G
        ArrayList<String> expected2 = new ArrayList<String>();
        expected2.add("A");
        expected2.add("B");
        expected2.add("C");
        expected2.add("D");
        expected2.add("E");
        expected2.add("F");
        expected2.add("G");
        expected2.add("H");
        ArrayList<String> result2 = graph.findUnderCost("A", 15);
        Collections.sort(result2);
        assertEquals(expected2, result2);
    }

    /**
     * This method tests that DW throws a File Not Found exception as expected when
     * given an invalid file
     */
    @Test
    public void CodeReviewOfDataWrangler1() {
        DotReaderDW dotReader = new DotReaderDW();

        // Load file which will cause File Not Found exception
        try {
            List<DotReaderDW.Node> nodesList = dotReader.readDotfile("throwsException.gv");
        } catch (Exception e) {
            assertEquals("File could not be found.", e.getMessage());
        }
    }

    /**
     * This method tests that DW works as expected (Correct functionality) when it
     * comes to reading DOT files as well tests for the getter methods getRoadCost
     * and getBuildingName
     */
    @Test
    public void CodeReviewOfDataWrangler2() {
        DotReaderDW dotReader = new DotReaderDW();
        List<DotReaderDW.Node> nodesList = null;
        try {
            // Load test graph created
            nodesList = dotReader.readDotfile("locations.gv");
            // Check that there are 11 buildings within the list as expected
            assertEquals(11, nodesList.size());
            // Initialize Node objs (Buildings)
            DotReaderDW.Node memorialUnion = null;
            DotReaderDW.Node bascomHall = null;
            DotReaderDW.Node unionSouth = null;
            DotReaderDW.Node memorialLibrary = null;
            for (int i = 0; i < nodesList.size(); ++i) {
                // Get specific DotReaderDW Node objects for testing
                if (nodesList.get(i).building.getBuildingName().equals("Memorial_Union")) {
                    memorialUnion = nodesList.get(i);
                }
                if (nodesList.get(i).building.getBuildingName().equals("Bascom_Hall")) {
                    bascomHall = nodesList.get(i);
                }
                if (nodesList.get(i).building.getBuildingName().equals("Union_South")) {
                    unionSouth = nodesList.get(i);
                }
                if (nodesList.get(i).building.getBuildingName().equals("Memorial_Library")) {
                    memorialLibrary = nodesList.get(i);
                }
            }
            // Check that the correct number of edges leaving and entering nodes in the
            // created graph are as expected
            assertEquals(5, memorialUnion.roadsLeaving.size());
            assertEquals(4, bascomHall.roadsLeaving.size());
            assertEquals(1, unionSouth.roadsLeaving.size());
            assertEquals(1, memorialLibrary.roadsLeaving.size());
            assertEquals(5, memorialUnion.roadsEntering.size());
            assertEquals(4, bascomHall.roadsEntering.size());
            assertEquals(1, unionSouth.roadsEntering.size());
            assertEquals(1, memorialLibrary.roadsEntering.size());

            // Check that the correct cost of roads in the created graph are as expected
            assertEquals(2, unionSouth.roadsEntering.get(0).road.getRoadCost());
            assertEquals(6, memorialLibrary.roadsEntering.get(0).road.getRoadCost());
        } catch (Exception e) {
            assertEquals(1, 2); // Always causes test to fail if any Exception is thrown
        }
    }

    /**
     * This method tests the integration of AE with BD
     * Tests that BD functions as expected, checks that MST weight is as expected.
     * Also tests the addBuilding and deleteBuilding methods of BD
     */
    @Test
    public void AEIntegrationTestWithBD() {
        // Setup AE and BD
        AlgorithmExecutionAE<DotReaderDW.Building, DotReaderDW.Road> graph = new AlgorithmExecutionAE<DotReaderDW.Building, DotReaderDW.Road>();
        DijkstraBackendBD backend = new DijkstraBackendBD(graph);
        Scanner scnr = null;

        // Load a graph from a file using BD code and test that it functions as
        // expected.
        try {
            // Setup graph
            backend.setGraphFromFile("locations.gv");
            // Make sure it has expected number of nodes
            assertEquals(11, graph.nodes.size());
            // Get the string representation of the MST
            String actualMST = backend.graph.printMST();
            // Use scanner to parse through the MST string
            scnr = new Scanner(actualMST);
            // totalWeight of the MST paths together used to check correctness of BD code
            Double totalWeight = 0.0;
            // Take care of existing whitespace
            String currentLine = backend.printMST().trim();
            // Array to store the weights that will be converted to double
            String[] parts = null;
            scnr.nextLine();
            while (scnr.hasNextLine()) {
                currentLine = scnr.nextLine();
                // If at the last line of the MST string break from loop since end is reached
                if (currentLine.isEmpty() || currentLine.isBlank()) {
                    break;
                }
                // Get the weight of each path which comes after the semi column on each string
                parts = currentLine.split(":");
                // Add the weight of the current path to the totalWeight
                totalWeight = totalWeight + Double.parseDouble(parts[1].trim());
            }
            // Total weight of the MST should be 47.0 (calculation done on paper)
            assertEquals(47.0, totalWeight);
        } catch (Exception e) {
            assertEquals(1, 2); // Always causes test to fail if any Exception is thrown
        } finally {
            // Close scanner at the end
            scnr.close();
        }
    }

    /**
     * This test methods tests the integration of AE, BD, and FD
     * Testing multiple methods from each of the three mentioned ROLES, makes sure
     * that methods function as expected given a pre-defined input using
     * TextUITester
     */
    @Test
    public void AEIntegrationTestWithFD() {
        TextUITester tester = new TextUITester(
                "1\nlocations.gv\n4\nMemorial_Union\nHelen_C_White_Hall\n10");
        try {
            // Setup AE, BD, FD
            AlgorithmExecutionAE<DotReaderDW.Building, DotReaderDW.Road> graph = new AlgorithmExecutionAE<DotReaderDW.Building, DotReaderDW.Road>();
            DijkstraBackendBD backend = new DijkstraBackendBD(graph);
            Scanner scnr = new Scanner(System.in);
            FrontendFD frontend = new FrontendFD(scnr, backend);
            // Get output given specific input provided in TextUITester obj tester
            frontend.runCommandLoop();
            String output = tester.checkOutput();
            System.out.println(output);
            scnr.close();
            // Make sure output contains expected strings
            assertEquals(true, output.contains("**** You Selected to Add the Data from a File ****"));
            assertEquals(true, output.contains("Enter the name of the file:"));
            assertEquals(true, output.contains("You successfully added a file!"));
            assertEquals(true, output.contains("**** You Selected to Find the Shortest Path ****"));
            assertEquals(true, output.contains("Enter the start location:"));
            assertEquals(true, output.contains("Enter the end location:"));
            assertEquals(true,
                    output.contains("The shortest path between Memorial_Union and Helen_C_White_Hall is 2.0 minutes!"));
            assertEquals(true, output.contains("**** Goodbye, enjoy your map! ****"));
        } catch (Exception e) {
            assertEquals(1, 2); // Always causes test to fail if any Exception is thrown
        }

    }

}

