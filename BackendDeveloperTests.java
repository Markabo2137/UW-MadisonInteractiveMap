// --== CS400 File Header Information ==--
// Name: Matthew Wang
// Email: mewang@wisc.edu
// Group and Team: AN blue
// Group TA: Florian Heimerl
// Lecturer: Gary Dahl
// Notes to Grader: haiiii :3

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class BackendDeveloperTests {

    /**
     * tester method for addBuilding() and insertEdge() of the BD class
     */
    @Test
    public void test1() {

        AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
        DijkstraBackendBD bd = new DijkstraBackendBD(graph);

        assertTrue(bd.addBuilding("first"));
        assertTrue(bd.addBuilding("second"));
        assertTrue(bd.addBuilding("third"));
        assertTrue(bd.addBuilding("matthew"));

        assertEquals(4, bd.getBuildingCount());

        // starts off with 0 edges
        assertEquals(0, bd.getEdgeCount());

        // adds an edge, so edge count should be updated
        assertTrue(bd.insertEdge("first", "second", 5));
        assertEquals(1, bd.getEdgeCount());

        // since these buildings don't exist, return false
        assertFalse(bd.insertEdge("fifth", "tenth", 5));
        assertEquals(1, bd.getEdgeCount());

    }

    /**
     * tester method for deleteBuilding() and removeEdge() of the BD class
     */
    @Test
    public void test2() {

        AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
        DijkstraBackendBD bd = new DijkstraBackendBD(graph);

        // adds the buildings together, we know this works from test1()
        bd.addBuilding("first");
        bd.addBuilding("second");
        bd.addBuilding("third");
        assertTrue(bd.addBuilding("matthew"));
        assertEquals(4, bd.getBuildingCount());

        // remove a building and ensure that the building count has decreased
        assertTrue(bd.deleteBuilding("matthew"));
        assertEquals(3, bd.getBuildingCount());

        // now we test remove on an edge
        bd.insertEdge("first", "second", 5);
        assertEquals(1, bd.getEdgeCount());

        assertTrue(bd.removeEdge("first", "second"));
        assertEquals(0, bd.getEdgeCount());
    }

    /**
     * tester method for the miscellaneous methods (getters/setters) in the BD class
     */
    @Test
    public void test3() {
        AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
        DijkstraBackendBD bd = new DijkstraBackendBD(graph);

        bd.addBuilding("first");
        bd.addBuilding("second");
        bd.addBuilding("third");

        // testing searchBuilding()
        assertTrue(bd.searchBuilding("second"));
        assertFalse(bd.searchBuilding("eighth"));

        // testing getEdgeCount() and searchEdge()
        assertEquals(0, bd.getEdgeCount());
        bd.insertEdge("first", "second", 5);
        assertEquals(1, bd.getEdgeCount());

        assertTrue(bd.searchEdge("first", "second"));
        assertFalse(bd.searchEdge("second", "third"));

        assertTrue(bd.removeEdge("first", "second"));
        assertEquals(0, bd.getEdgeCount());

    }

    @Test
    /**
     * tester method for the getBuildingNames() method of the BD class
     */
    public void test4() {

        AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
        DijkstraBackendBD bd = new DijkstraBackendBD(graph);

        bd.addBuilding("first");
        bd.addBuilding("second");
        bd.addBuilding("third");

        assertEquals("[first, second, third]", bd.getBuildingNames().toString());

        // tests the alphabetical ranking
        bd.addBuilding("hello");
        assertEquals("[first, hello, second, third]", bd.getBuildingNames().toString());

    }

    /**
     * tester method for the setGraphFromFile() method of the BD class
     */
    @Test
    public void test5() {

        AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
        DijkstraBackendBD bd = new DijkstraBackendBD(graph);

        // adding buildings and edges to find a path between them
        bd.addBuilding("A");
        bd.addBuilding("B");
        bd.addBuilding("H");
        bd.addBuilding("M");
        bd.addBuilding("E");
        bd.addBuilding("I");
        bd.addBuilding("D");
        bd.addBuilding("F");
        bd.addBuilding("L");
        bd.addBuilding("G");

        bd.insertEdge("A", "B", 1);
        bd.insertEdge("B", "M", 3);
        bd.insertEdge("A", "M", 5);
        bd.insertEdge("A", "H", 8);
        bd.insertEdge("H", "B", 6);
        bd.insertEdge("H", "I", 2);
        bd.insertEdge("I", "H", 2);
        bd.insertEdge("M", "E", 3);
        bd.insertEdge("M", "F", 4);
        bd.insertEdge("F", "G", 9);
        bd.insertEdge("D", "A", 7);
        bd.insertEdge("D", "G", 2);
        bd.insertEdge("G", "L", 7);
        bd.insertEdge("I", "L", 5);
        bd.insertEdge("I", "D", 1);

        // weird way of printing out the return statement of a List of BuildingsBD
        List<DotReaderDW.Building> path = bd.findShortestPath("D", "I");
        ArrayList<String> names = new ArrayList<>();
        ArrayList<DotReaderDW.Building> build = new ArrayList<>();
        build.addAll(path);
        for (int i = 0; i < build.size(); i++) {
            names.add(build.get(i).getBuildingName());
        }
        assertEquals("[D, A, H, I]", names.toString());
        assertEquals(17.0, bd.findShortestPathCost("D", "I"));
    }

    /**
     * tester method for the FrontendDeveloper class, checking to see if the command loop is correct
     */
    @Test
    public void CodeReviewOfFrontendDeveloper1() {
        TextUITester input = new TextUITester("2\nBascom Hall\n2\nVilas Hall\n6\n10");

        Scanner scanner = new Scanner(System.in);
        AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
        DijkstraBackendBD backend = new DijkstraBackendBD(graph);
        FrontendFD frontend = new FrontendFD(scanner, backend);
        frontend.runCommandLoop();
        String output = input.checkOutput();
        scanner.close();

        assertEquals("**** Welcome to Traffic Map! ****\n" +
                "Select an option by entering one of the following numbers:\n" +
                "    1. Load Map from File\n" +
                "    2. Add a Location\n" +
                "    3. Remove a Location\n" +
                "    4. Find Shortest Path\n" +
                "    5. Find All Paths Under Weight\n" +
                "    6. View List of Locations\n" +
                "    7. Add a Road\n" +
                "    8. Delete a Road\n" +
                "    9. Print MST\n" +
                "    10. Exit Application\n" +
                "Enter selection: \n" +
                "\n" +
                "**** You Selected to Add a Location ****\n" +
                "Enter the name of the building:\n" +
                "**** You Have Added Bascom Hall to the graph. ****\n" +
                "Select an option by entering one of the following numbers:\n" +
                "    1. Load Map from File\n" +
                "    2. Add a Location\n" +
                "    3. Remove a Location\n" +
                "    4. Find Shortest Path\n" +
                "    5. Find All Paths Under Weight\n" +
                "    6. View List of Locations\n" +
                "    7. Add a Road\n" +
                "    8. Delete a Road\n" +
                "    9. Print MST\n" +
                "    10. Exit Application\n" +
                "Enter selection: \n" +
                "\n" +
                "**** You Selected to Add a Location ****\n" +
                "Enter the name of the building:\n" +
                "**** You Have Added Vilas Hall to the graph. ****\n" +
                "Select an option by entering one of the following numbers:\n" +
                "    1. Load Map from File\n" +
                "    2. Add a Location\n" +
                "    3. Remove a Location\n" +
                "    4. Find Shortest Path\n" +
                "    5. Find All Paths Under Weight\n" +
                "    6. View List of Locations\n" +
                "    7. Add a Road\n" +
                "    8. Delete a Road\n" +
                "    9. Print MST\n" +
                "    10. Exit Application\n" +
                "Enter selection: \n" +
                "\n" +
                "**** You Selected to List the Locations in Map ****\n" +
                "In Your Map: \n" +
                "Bascom Hall\n" +
                "Vilas Hall\n" +
                "Select an option by entering one of the following numbers:\n" +
                "    1. Load Map from File\n" +
                "    2. Add a Location\n" +
                "    3. Remove a Location\n" +
                "    4. Find Shortest Path\n" +
                "    5. Find All Paths Under Weight\n" +
                "    6. View List of Locations\n" +
                "    7. Add a Road\n" +
                "    8. Delete a Road\n" +
                "    9. Print MST\n" +
                "    10. Exit Application\n" +
                "Enter selection: \n" +
                "**** Goodbye, enjoy your map! ****\n", output);
    }

    /**
     * tester method for the FrontendDeveloper class, checking to see if the command loop is correct when given an invalid command
     */
    @Test
    public void CodeReviewOfFrontendDeveloper2() {
        TextUITester input = new TextUITester("error\nerror\n6\n10");

        Scanner scanner = new Scanner(System.in);
        AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
        DijkstraBackendBD backend = new DijkstraBackendBD(graph);
        FrontendFD frontend = new FrontendFD(scanner, backend);
        frontend.runCommandLoop();
        String output = input.checkOutput();
        System.out.println(output);
        scanner.close();

        assertEquals("**** Welcome to Traffic Map! ****\n" +
                "Select an option by entering one of the following numbers:\n" +
                "    1. Load Map from File\n" +
                "    2. Add a Location\n" +
                "    3. Remove a Location\n" +
                "    4. Find Shortest Path\n" +
                "    5. Find All Paths Under Weight\n" +
                "    6. View List of Locations\n" +
                "    7. Add a Road\n" +
                "    8. Delete a Road\n" +
                "    9. Print MST\n" +
                "    10. Exit Application\n" +
                "Enter selection: \n" +
                "Didn't recognize that. Please Try Again.\n" +
                "Select an option by entering one of the following numbers:\n" +
                "    1. Load Map from File\n" +
                "    2. Add a Location\n" +
                "    3. Remove a Location\n" +
                "    4. Find Shortest Path\n" +
                "    5. Find All Paths Under Weight\n" +
                "    6. View List of Locations\n" +
                "    7. Add a Road\n" +
                "    8. Delete a Road\n" +
                "    9. Print MST\n" +
                "    10. Exit Application\n" +
                "Enter selection: \n" +
                "Didn't recognize that. Please Try Again.\n" +
                "Select an option by entering one of the following numbers:\n" +
                "    1. Load Map from File\n" +
                "    2. Add a Location\n" +
                "    3. Remove a Location\n" +
                "    4. Find Shortest Path\n" +
                "    5. Find All Paths Under Weight\n" +
                "    6. View List of Locations\n" +
                "    7. Add a Road\n" +
                "    8. Delete a Road\n" +
                "    9. Print MST\n" +
                "    10. Exit Application\n" +
                "Enter selection: \n" +
                "\n" +
                "**** You Selected to List the Locations in Map ****\n" +
                "Your Map is Empty!\n" +
                "Select an option by entering one of the following numbers:\n" +
                "    1. Load Map from File\n" +
                "    2. Add a Location\n" +
                "    3. Remove a Location\n" +
                "    4. Find Shortest Path\n" +
                "    5. Find All Paths Under Weight\n" +
                "    6. View List of Locations\n" +
                "    7. Add a Road\n" +
                "    8. Delete a Road\n" +
                "    9. Print MST\n" +
                "    10. Exit Application\n" +
                "Enter selection: \n" +
                "**** Goodbye, enjoy your map! ****\n", output);
    }

    /**
     * tester method for the DW building object and the AE generic graph
     */
    @Test
    public void IntegrationOfDWandAE() {
        AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
        DotReaderDW dw = new DotReaderDW();

        DotReaderDW.Building a = dw.new Building("A building name");
        DotReaderDW.Building b = dw.new Building("B building name");
        DotReaderDW.Building c = dw.new Building("C building name");
        DotReaderDW.Building d = dw.new Building("D building name");

        graph.insertNode(a);
        graph.insertNode(b);
        graph.insertNode(c);
        graph.insertNode(d);

        graph.insertEdge(a, b, 2);
        graph.insertEdge(b, d, 3);
        graph.insertEdge(a, c, 1);
        graph.insertEdge(c, d, 3);

        assertEquals("[A building name, C building name, D building name]", graph.shortestPathData(a, d).toString());
        assertEquals(4, graph.shortestPathCost(a, d));

    }

    /**
     * tester method for the BD and DW class, testing if DW's readDotFile() method works with BD's setGraphFromFile()
     */
    @Test
    public void IntegrationOfBDandDW() {
        AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
        DijkstraBackendBD bd = new DijkstraBackendBD(graph);

        try {
            bd.setGraphFromFile("src/locations.gv");

            assertEquals(11, bd.getBuildingCount());
            assertEquals(26, bd.getEdgeCount());

            ArrayList<String> b = bd.getBuildingNames();

            assertEquals("Bascom_Hall", b.get(0));
            assertEquals("Engineering_Hall", b.get(1));
            assertEquals("Grainger_Hall", b.get(2));
            assertEquals("Helen_C_White_Hall", b.get(3));
            assertEquals("Humanities_Building", b.get(4));
            assertEquals("Memorial_Library", b.get(5));
            assertEquals("Memorial_Union", b.get(6));
            assertEquals("Science_Hall", b.get(7));
            assertEquals("Sterling_Hall", b.get(8));
            assertEquals("Union_South", b.get(9));
            assertEquals("Vilas_Hall", b.get(10));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




}
