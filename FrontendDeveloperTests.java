// --== CS400 Project 3 Information ==--
// Name: Bailey Kau
// Email: bkau@wisc.edu
// Group and Team: AN Blue
// Group TA: Gary Dahl
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Text;

import static org.junit.jupiter.api.Assertions.*;

public class FrontendDeveloperTests {

    /**
     * JUnit Tester to check that the user input of 9 exits the application
     */
    @Test
    public void frontendTest1() {
        TextUITester tester = new TextUITester("9");
        try {
            Scanner scanner = new Scanner(System.in);
            AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
            DijkstraBackendBD backend = new DijkstraBackendBD(graph);
            FrontendFD frontend = new FrontendFD(scanner, backend);
            frontend.runCommandLoop();
            String output = tester.checkOutput();
            scanner.close();

            assertEquals(true, output.contains("**** Goodbye, enjoy your map! ****"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * JUnit Tester to check if the application adds and removes Memorial Union as a building
     */
    @Test
    public void frontendTest2() {
        TextUITester tester = new TextUITester("2\nMemorial Union\n3\nMemorial Union");
        try {
            Scanner scanner = new Scanner(System.in);
            AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
            DijkstraBackendBD backend = new DijkstraBackendBD(graph);
            FrontendFD frontend = new FrontendFD(scanner, backend);
            frontend.runCommandLoop();
            String output = tester.checkOutput();
            scanner.close();

            assertTrue(output.contains("**** You Selected to Add a Location ****"));
            assertTrue(output.contains("Enter the name of the building:"));
            assertTrue(output.contains("**** You Selected to Delete a Location ****"));
            assertTrue(output.contains("Enter the name of the building:"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * JUnit Tester to check if the application can view list of locations
     */
    @Test
    public void frontendTest3() {
        TextUITester tester = new TextUITester("2\nMemorial Union\n6\n");
        try {
            Scanner scanner = new Scanner(System.in);
            AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
            DijkstraBackendBD backend = new DijkstraBackendBD(graph);
            FrontendFD frontend = new FrontendFD(scanner, backend);
            frontend.runCommandLoop();
            String output = tester.checkOutput();
            scanner.close();

            assertTrue(output.contains("**** You Selected to Add a Location ****"));
            assertTrue(output.contains("Enter the name of the building:"));
            assertTrue(output.contains("**** You Selected to List the Locations in Map ****"));
            assertTrue(output.contains("Your Map is Empty!"));
            assertTrue(output.contains("In Your Map: "));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * JUnit Tester to check if the application adds and deletes a road
     */
    @Test
    public void frontendTest4() {
        TextUITester tester = new TextUITester("2\nMemorial Union\n2\nCollege Library\n7\nMemorial Union\nCollege Library\n10\n8\nMemorial Union\nCollege Library");
        try {
            Scanner scanner = new Scanner(System.in);
            AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
            DijkstraBackendBD backend = new DijkstraBackendBD(graph);
            FrontendFD frontend = new FrontendFD(scanner, backend);
            frontend.runCommandLoop();
            String output = tester.checkOutput();
            scanner.close();

            assertTrue(output.contains("**** You Selected to Add a Road to the Map ****"));
            assertTrue(output.contains("Enter the start location: "));
            assertTrue(output.contains("Enter the end location: "));
            assertTrue(output.contains("Enter the time to travel: "));
            assertTrue(output.contains("**** You Selected to Delete a Road From the Map ****"));
            assertTrue(output.contains("Enter the start location: "));
            assertTrue(output.contains("Enter the end location: "));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * JUnit Tester to check if the find all paths under weight works correctly
     */
    @Test
    public void frontendTest5() {
        TextUITester tester = new TextUITester("2\nMemorial Union\n2\nCollege Library\n5\nMemorial Union\nCollege Library\n5\nCollegeLibrary\n10");
        try {
            Scanner scanner = new Scanner(System.in);
            AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
            DijkstraBackendBD backend = new DijkstraBackendBD(graph);
            FrontendFD frontend = new FrontendFD(scanner, backend);
            frontend.runCommandLoop();
            String output = tester.checkOutput();
            scanner.close();

            assertTrue(output.contains("**** You Selected to Find All Paths Under Weight ****"));
            assertTrue(output.contains("Enter the maximum time you’re willing to travel, as an integer:"));
            assertTrue(output.contains("Enter your starting location:"));
            assertTrue(output.contains("You could travel to these locations in under 10 minutes:"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Code Review JUnit Tester of Backend Developer addBuilding and addEdge methods
     */
    @Test
    public void CodeReviewOfBackendDeveloperTest1() {
        try {
            AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
            DijkstraBackendBD bd = new DijkstraBackendBD(graph);

            assertTrue(bd.addBuilding("Roselands"));
            assertTrue(bd.addBuilding("Primrose View"));
            assertTrue(bd.addBuilding("London End"));
            assertTrue(bd.addBuilding("Woodlandslide"));

            assertEquals(4, bd.getBuildingCount());

            // starts off with 0 edges
            assertEquals(0, bd.getEdgeCount());

            // adds an edge, so edge count should be updated
            assertTrue(bd.insertEdge("Roselands", "Woodlandslide", 5));
            assertEquals(1, bd.getEdgeCount());

            // since these buildings don't exist, return false
            assertFalse(bd.insertEdge("Thornton Lodge", "Ghost Town", 5));
            assertEquals(1, bd.getEdgeCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Code Review JUnit Tester of Backend Developer getBuildingNames methods
     */
    @Test
    public void CodeReviewOfBackendDeveloperTest2() {
        try {
            AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
            DijkstraBackendBD bd = new DijkstraBackendBD(graph);

            bd.addBuilding("Frozen Lodge");
            bd.addBuilding("Cherry House");
            bd.addBuilding("Meadowside");

            assertEquals("[Cherry House, Frozen Lodge, Meadowside]", bd.getBuildingNames().toString());

            // tests the alphabetical ranking
            bd.addBuilding("Sweltering End");
            assertEquals("[Cherry House, Frozen Lodge, Meadowside, Sweltering End]", bd.getBuildingNames().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Integration JUnit Tester
     */
    @Test
    public void IntegrationTest1() {
        try {
            TextUITester tester = new TextUITester("1\nlocations.gv\n9");
            DotReaderDW reader = new DotReaderDW();
            AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
            DijkstraBackendBD backend = new DijkstraBackendBD(graph);
            Scanner scanner = new Scanner(System.in);
            FrontendFD frontend = new FrontendFD(scanner, backend);

            frontend.runCommandLoop();
            String output = tester.checkOutput();
            scanner.close();

            assertTrue(output.contains("**** You have Selected to Print the MST of the Graph ****"));
            assertTrue(output.contains("Grainger_Hall - Vilas_Hall : 8.0\n" +
                    "Memorial_Library - Memorial_Union : 6.0\n" +
                    "Grainger_Hall - Memorial_Union : 4.0\n" +
                    "Memorial_Union - Helen_C_White_Hall : 2.0\n" +
                    "Memorial_Union - Science_Hall : 5.0\n" +
                    "Engineering_Hall - Bascom_Hall : 7.0\n" +
                    "Memorial_Union - Bascom_Hall : 5.0\n" +
                    "Union_South - Bascom_Hall : 2.0\n" +
                    "Sterling_Hall - Humanities_Building : 10.0\n" +
                    "Science_Hall - Humanities_Building : 9.0"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Integration JUnit Tester
     */
    @Test
    public void IntegrationTest2() {
        try {
            TextUITester tester = new TextUITester("1\nlocations.gv\n4\nScience_Hall\nMemorial_Library");
            DotReaderDW reader = new DotReaderDW();
            AlgorithmExecutionAE graph = new AlgorithmExecutionAE();
            DijkstraBackendBD backend = new DijkstraBackendBD(graph);
            Scanner scanner = new Scanner(System.in);
            FrontendFD frontend = new FrontendFD(scanner, backend);

            frontend.runCommandLoop();
            String output = tester.checkOutput();
            scanner.close();

            assertTrue(output.contains("The shortest path between Science_Hall and Memorial_Library is 11.0 minutes!"));
            assertTrue(output.contains("Science_Hall -> Memorial_Union -> Memorial_Library"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
