// --== CS400 File Header Information ==--
// Name: Fatimah Mohammed
// Email: famohammed2@wisc.edu
// Group and Team: AN Blue
// Group TA: Gary Dahl
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.util.List;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataWranglerTests {
    /**
     * Makes sure that an exception is thrown when attempting to read a fake file.
     */
    @Test
    public void test1() {
        // initizilize needed variables
        DotReaderDW dotReader = new DotReaderDW();

        // try and load a fake file and check expected exception
        try {
            List<DotReaderDW.Node> nodes = dotReader.readDotfile("fake.gv");
            fail("Expected FileNotFoundException to be thrown");
        } catch (Exception e) {
            assertEquals("File could not be found.", e.getMessage());
        }
    }


    /**
     * makes sure that the graph contains the number
     * of nodes that is expected from the locations.gv file
     */
    @Test
    public void test2() {
        // initizilize needed variables
        DotReaderDW dotReader = new DotReaderDW();
        List<DotReaderDW.Node> nodes = null;

        // load the file
        try {
            nodes = dotReader.readDotfile("locations.gv");
        } catch (Exception e) {
            fail("Un-Expected FileNotFoundException thrown");
        }
        assertEquals(11, nodes.size());
    }

    /**
     * Check that Bascom_Hall, the node type with the 
     * highest number of nodes connected to it, has all expected edges and nodes
     */
    @Test
    public void test3() {
        // initizilize needed variables
        DotReaderDW dotReader = new DotReaderDW();
        List<DotReaderDW.Node> nodes = null;

        // load the file
        try {
            nodes = dotReader.readDotfile("locations.gv");
        } catch (Exception e) {
            fail("Un-Expected FileNotFoundException thrown");
        }
        
        // find bascom hall node
        DotReaderDW.Node bascomHallNode = null;
        for (DotReaderDW.Node node : nodes) {
            if (node.building.getBuildingName().equals("Bascom_Hall")) {
                bascomHallNode = node;
                break;
            }
        }

        // get all connected nodes for comparison
        List<DotReaderDW.Edge> leaving = bascomHallNode.roadsLeaving;
        List<DotReaderDW.Edge> entering = bascomHallNode.roadsEntering;
        
        // for each edge, make sure that the road that it contains has the expected cost
        for(DotReaderDW.Edge edge: leaving) {
            if(edge.successor.building.buildingName.equals("Union_South")) {
                assertEquals(2, edge.road.cost);
            }

            if(edge.successor.building.buildingName.equals("Memorial_Union")) {
                assertEquals(1, edge.road.cost);
            }

            if(edge.successor.building.buildingName.equals("Engineering_Hall")) {
                assertEquals(2, edge.road.cost);
            }

            if(edge.successor.building.buildingName.equals("Sterling_Hall")) {
                assertEquals(11, edge.road.cost);
            }
        }

        for(DotReaderDW.Edge edge: entering) {
            if(edge.successor.building.buildingName.equals("Union_South")) {
                assertEquals(2, edge.road.cost);
            }

            if(edge.successor.building.buildingName.equals("Memorial_Union")) {
                assertEquals(1, edge.road.cost);
            }

            if(edge.successor.building.buildingName.equals("Engineering_Hall")) {
                assertEquals(2, edge.road.cost);
            }
            
            if(edge.successor.building.buildingName.equals("Sterling_Hall")) {
                assertEquals(11, edge.road.cost);
            }
        }

        // should pass becasue graph has 6 edges connected to bascom hall node
        assertEquals(8, leaving.size() + entering.size());    
    }

    /**
     * 
     */
    @Test
    public void test4() {
        // initizilize needed variables
        DotReaderDW dotReader = new DotReaderDW();
        List<DotReaderDW.Node> nodes = null;

        // load the file
        try {
            nodes = dotReader.readDotfile("locations.gv");
        } catch (Exception e) {
            fail("Un-Expected FileNotFoundException thrown");
        }
        
        // find helen c white node
        DotReaderDW.Node helenNode = null;
        for (DotReaderDW.Node node : nodes) {
            if (node.building.getBuildingName().equals("Helen_C_White_Hall")) {
                helenNode = node;
                break;
            }
        }

        // get all connected nodes for comparison
        List<DotReaderDW.Edge> leaving = helenNode.roadsLeaving;
        List<DotReaderDW.Edge> entering = helenNode.roadsEntering;
        
        // for each edge, make sure that the road that it contains has the expected cost
        for(DotReaderDW.Edge edge: entering) {
            if(edge.successor.building.buildingName.equals("Grainger_Hall")) {
                assertEquals(7, edge.road.cost);
            }
            if(edge.successor.building.buildingName.equals("Memorial_Union")) {
                assertEquals(2, edge.road.cost);
            }
        }

        for(DotReaderDW.Edge edge: leaving) {
            if(edge.successor.building.buildingName.equals("Grainger_Hall")) {
                assertEquals(7, edge.road.cost);
            }
            if(edge.successor.building.buildingName.equals("Memorial_Union")) {
                assertEquals(2, edge.road.cost);
            }
        }

        // should pass becasue graph has 1 edges connected to helen c white node
        assertEquals(4, leaving.size() + entering.size()); 
    }

    /**
     * Check that the non-tests nodes have expected number 
     * if total connected nodes (leaving + entering)
     */
    @Test
    public void test5() {
        // initizilize needed variables
        DotReaderDW dotReader = new DotReaderDW();
        List<DotReaderDW.Node> nodes = null;

        // load the file
        try {
            nodes = dotReader.readDotfile("locations.gv");
        } catch (Exception e) {
            fail("Un-Expected FileNotFoundException thrown");
        }

        // check total connected nodes for all specific buildings
        for(DotReaderDW.Node node: nodes) {
            if(node.building.buildingName.equals("Memorial_Library")){
                assertEquals(2, node.roadsEntering.size() + node.roadsLeaving.size());
            }

            if(node.building.buildingName.equals("Engineering_Hall")){
                assertEquals(4, node.roadsEntering.size() + node.roadsLeaving.size());
            }

            if(node.building.buildingName.equals("Union_South")){
                assertEquals(2, node.roadsEntering.size() + node.roadsLeaving.size());
            }

            if(node.building.buildingName.equals("Sterling_Hall")){
                assertEquals(6, node.roadsEntering.size() + node.roadsLeaving.size());
            }

            if(node.building.buildingName.equals("Humanities_Building")){
                assertEquals(4, node.roadsEntering.size() + node.roadsLeaving.size());
            }

            if(node.building.buildingName.equals("Science_Hall")){
                assertEquals(4, node.roadsEntering.size() + node.roadsLeaving.size());
            }

            if(node.building.buildingName.equals("Grainger_Hall")){
                assertEquals(6, node.roadsEntering.size() + node.roadsLeaving.size());
            }

            if(node.building.buildingName.equals("Vilas_Hall")){
                assertEquals(2, node.roadsEntering.size() + node.roadsLeaving.size());
            }

            if(node.building.buildingName.equals("Memorial_Union")){
                assertEquals(10, node.roadsEntering.size() + node.roadsLeaving.size());
            }
        }
    }

    /**
     * Tests a that printMST() prints the expected minimum spanning tree for the
     * created graph
     */
    @Test
    public void codeReviewOfAlgorithmEngineer1() {
        // create the graph to add nodes and edges to
        AlgorithmExecutionAE<String, Integer> graph = new AlgorithmExecutionAE<>();

        // insert all nodes for the graph
        graph.insertNode("building1");
        graph.insertNode("building2");
        graph.insertNode("building3");
        graph.insertNode("building4");
        graph.insertNode("building5");

        // create edges for and their costs
        graph.insertEdge("building1", "building2", 2);
        graph.insertEdge("building1", "building4", 6);
        graph.insertEdge("building2", "building1", 2);
        graph.insertEdge("building2", "building3", 3);
        graph.insertEdge("building2", "building4", 8);
        graph.insertEdge("building2", "building5", 5);
        graph.insertEdge("building3", "building2", 3);
        graph.insertEdge("building3", "building5", 7);
        graph.insertEdge("building4", "building1", 6);
        graph.insertEdge("building4", "building2", 8);
        graph.insertEdge("building4", "building5", 9);
        graph.insertEdge("building5", "building2", 5);
        graph.insertEdge("building5", "building3", 7);
        graph.insertEdge("building5", "building4", 9);

        // junit should return true if the retuned string equals the expected result
        String result = graph.printMST();
        String expectedResult = "\nbuilding1 - building2 : 2.0\nbuilding2 - building3 : 3.0\nbuilding1 - building4 : 6.0\nbuilding2 - building5 : 5.0\n";
        assertEquals(expectedResult, result);
    }

    /**
     * Tests a graph with a one node only and checks if 
     * printMST() returns that graph is empty as expected
     */
    @Test
    public void codeReviewOfAlgorithmEngineer2() {
        // create the graph to add nodes and edges to
        AlgorithmExecutionAE<String, Integer> graph = new AlgorithmExecutionAE<>();
        
        // insert all nodes for the graph
        graph.insertNode("building");

        // junit should return true if the retuned string equals the expected result
        String result = graph.printMST();
        String expectedResult = "The minimum spanning tree is empty.\n";
        assertEquals(expectedResult, result);
    }

    /**
     * Tests the integration of Data wrangler and Backend Developer
     * by ensuring DW's readDotFile method works in BD's setGraphFromFile
     * method.
     */
    @Test
    public void integrationTestOfDWandBD() {
        // create graph where edges and buildings will be added
        AlgorithmExecutionAE<DotReaderDW.Building, DotReaderDW.Road> graph = new AlgorithmExecutionAE<>();
        DijkstraBackendBD bd = new DijkstraBackendBD(graph);

        try {
            // use of DW role code here within the setGraphFromFile method
            bd.setGraphFromFile("locations.gv");

            // check that numbers of buildings and their names is as expected
            assertEquals(11, bd.getBuildingCount());
            assertEquals(11, bd.getBuildingNames().size());

            // check that the names contain all expected names
            ArrayList<String> names = bd.getBuildingNames();
            assertEquals(true, names.contains("Bascom_Hall"));
            assertEquals(true, names.contains("Engineering_Hall"));
            assertEquals(true, names.contains("Grainger_Hall"));
            assertEquals(true, names.contains("Helen_C_White_Hall"));
            assertEquals(true, names.contains("Humanities_Building"));
            assertEquals(true, names.contains("Memorial_Library"));
            assertEquals(true, names.contains("Memorial_Union"));
            assertEquals(true, names.contains("Science_Hall"));
            assertEquals(true, names.contains("Sterling_Hall"));
            assertEquals(true, names.contains("Union_South"));
            assertEquals(true, names.contains("Vilas_Hall"));
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    /**
     * Tests the integration of Data wrangler and
     * Algorithm by checking that nodes created
     * by ensuring that DW's inner road class 
     * is able to integrate with AE's extended 
     * methods insert node edge as well AE's 
     * printMST() method
     */
    @Test
    public void integrationTest2OfDWandAE() {
        // create graph where edges and buildings will be added
        AlgorithmExecutionAE<DotReaderDW.Building, Integer> graph = new AlgorithmExecutionAE<>();
        DotReaderDW dw = new DotReaderDW();

        // create all buildings for the graph
        DotReaderDW.Building building1 = dw.new Building("building1");
        DotReaderDW.Building building2 = dw.new Building("building2");
        DotReaderDW.Building building3 = dw.new Building("building3");
        DotReaderDW.Building building4 = dw.new Building("building4");

        // insert the buildings into the graph
        graph.insertNode(building1);
        graph.insertNode(building2);
        graph.insertNode(building3);
        graph.insertNode(building4);

        // insert the edges for the graph
        graph.insertEdge(building1, building2, 2);
        graph.insertEdge(building2, building4, 3);
        graph.insertEdge(building1, building3, 1);
        graph.insertEdge(building3, building4, 3);

        // should pass if expected mst path is returned
        assertEquals("\nbuilding4 - building2 : 3.0\nbuilding2 - building1 : 2.0\nbuilding3 - building1 : 1.0\n", graph.printMST());
    }
}
