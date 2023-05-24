// --== CS400 Project 3 Information ==--
// Name: Bailey Kau
// Email: bkau@wisc.edu
// Group and Team: AN Blue
// Group TA: Gary Dahl
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FrontendFD implements FrontendInterfaceFD {
    Scanner userInput;
    DijkstraBackendBD backend;
    AlgorithmExecutionAE graph;

    public FrontendFD(Scanner userInput, DijkstraBackendBD backend) {
        this.userInput = new Scanner(System.in);
        graph = new AlgorithmExecutionAE();
        this.backend = new DijkstraBackendBD(graph);
    }

    @Override
    public void runCommandLoop() {
        System.out.println("**** Welcome to Traffic Map! ****");
        String command = "\0";
        while (!command.equals("10")) { //main loop continues until user chooses to quit
            //display menu of choices
            System.out.println("Select an option by entering one of the following numbers:");
            System.out.println("    1. Load Map from File");
            System.out.println("    2. Add a Location");
            System.out.println("    3. Remove a Location");
            System.out.println("    4. Find Shortest Path");
            System.out.println("    5. Find All Paths Under Weight");
            System.out.println("    6. View List of Locations");
            System.out.println("    7. Add a Road");
            System.out.println("    8. Delete a Road");
            System.out.println("    9. Print MST");
            System.out.println("    10. Exit Application");

            // read in user's choice and trim away any whitespace
            System.out.println("Enter selection: ");
            String input = userInput.nextLine().trim();
            if (input.length() == 0) {
                command = "\0";
            }
            command = input;
            switch (command) {
                case "1":
                    loadFile();
                    break;

                case "2":
                    addLocation();
                    break;

                case "3":
                    removeLocation();
                    break;

                case "4":
                    findShortestPath();
                    break;

                case "5":
                    findAllPathsUnder();
                    break;

                case "6":
                    listAllLocations();
                    break;

                case "7":
                    addRoad();
                    break;

                case "8":
                    deleteRoad();
                    break;

                case "9":
                    System.out.println("**** You have Selected to Print the MST of the Graph ****");
                    System.out.println(backend.printMST());
                    break;

                case "10":
                    System.out.println("**** Goodbye, enjoy your map! ****");
                    break;

                default:
                    System.out.println("Didn't recognize that. Please Try Again.");
                    break;
            }
        }
    }

    @Override
    public void loadFile() {
        System.out.println();
        System.out.println("**** You Selected to Add the Data from a File ****");
        System.out.println("Enter the name of the file:");
        String input = userInput.nextLine().trim();

        //While loop to check if the user input is valid
        while (input != null) {
            try {
                backend.setGraphFromFile(input);
                System.out.println("You successfully added a file!");
                input = null;
            }
            catch (Exception e) {
                System.out.println("That was not a valid file. Enter the name of another file:");
                input = userInput.nextLine().trim();
            }
        }
    }

    @Override
    public void addLocation() {
        System.out.println();
        System.out.println("**** You Selected to Add a Location ****");
        System.out.println("Enter the name of the building:");
        String input = userInput.nextLine().trim();

        //While loop to check if the user input is valid
        while (backend.searchBuilding(input)) {
            System.out.println("That building is already on the map. Please enter a new building.");
            input = userInput.nextLine().trim();
        }

        //Uses addBuilding from backend
        backend.addBuilding(input);
        System.out.println("**** You Have Added " + input + " to the graph. ****");
    }

    @Override
    public void removeLocation() {
        System.out.println();
        System.out.println("**** You Selected to Delete a Location ****");

        System.out.println("Enter the name of the building:");
        String input = userInput.nextLine().trim();

        //While loop to check if the user input is valid
        while (!backend.searchBuilding(input)) {
            System.out.println("That building is not on the map. Please enter a valid building.");
            input = userInput.nextLine().trim();
        }

        //Uses deleteBuilding from backend
        backend.deleteBuilding(input);
        System.out.println("**** You Have Deleted " + input + " from the graph. ****");
    }

    @Override
    public void findShortestPath() {
        System.out.println();
        System.out.println("**** You Selected to Find the Shortest Path ****");
        System.out.println("Enter the start location: ");
        String start = userInput.nextLine().trim();

        //While loop to check if the user input is valid
        while (!backend.searchBuilding(start)) {
            System.out.println("That building is not on the map. Please enter a valid building.");
            start = userInput.nextLine().trim();
        }

        System.out.println("Enter the end location: ");
        String end = userInput.nextLine().trim();

        //While loop to check if the user input is valid
        while (!backend.searchBuilding(end)) {
            System.out.println("That building is not on the map. Please enter a valid building.");
            end = userInput.nextLine().trim();
        }

        //Uses findShortestPath from backend
        try {
            List<DotReaderDW.Building> path = backend.findShortestPath(start, end);
            double pathCost = backend.findShortestPathCost(start, end);
            System.out.println("The shortest path between " + start + " and " + end + " is " + pathCost + " minutes!");

            //Make a string containing all buildings in the list and print it
            String buildingList = "";
            for (int i = 0; i < path.size() - 1; i++) {
                buildingList += path.get(i) + " -> ";
            }
            buildingList += path.get(path.size() - 1);
            System.out.println(buildingList);
            System.out.println();
        } catch (NoSuchElementException e) {
            System.out.println("There are no paths from the start location to the end.");
            System.out.println();
        }
    }

    @Override
    public void findAllPathsUnder() {
        System.out.println("");
        System.out.println("**** You Selected to Find All Paths Under Weight ****");
        System.out.println("Enter the maximum time you’re willing to travel, as an integer:");
        int maxTime = userInput.nextInt();
        //While loop to check if the user input is valid
        while (maxTime < 0) {
            System.out.println("The time to travel must be greater than 0. Please enter a valid time to travel.");
            maxTime= userInput.nextInt();
        }

        System.out.println("Enter your starting location: ");
        userInput.nextLine();
        String input = userInput.nextLine().trim();
        //While loop to check if the user input is valid
        while (!backend.searchBuilding(input)) {
            System.out.println(backend.searchBuilding(input));
            System.out.println("That building is not on the map. Please enter a valid building.");
            input = userInput.nextLine().trim();
        }

        //Uses findBuildingsUnderCost() from backend
        ArrayList<DotReaderDW.Building> underCost = backend.findBuildingsUnderCost(input, maxTime);
        System.out.println("You could travel to these locations in under " + maxTime + " minutes:");
        for (DotReaderDW.Building building: underCost) {
            System.out.println(building);
        }
    }

    @Override
    public void listAllLocations() {
        System.out.println("");
        System.out.println("**** You Selected to List the Locations in Map ****");
        //Uses getAllBuildings from backend
        List listBuildings = backend.getBuildingNames();

        //Check if there are any buildings in the list
        if (listBuildings.isEmpty()) {
            System.out.println("Your Map is Empty!");
        }

        //Otherwise print all the buildings on the map
        else {
            System.out.println("In Your Map: ");
            for (Object building : listBuildings) {
                System.out.println(building);
            }
        }
    }

    @Override
    public void addRoad() {
        System.out.println("");
        System.out.println("**** You Selected to Add a Road to the Map ****");
        System.out.println("Enter the start location: ");
        String start = userInput.nextLine().trim();
        //While loop to check if the user input is valid
        while (!backend.searchBuilding(start)) {
            System.out.println("That building is not on the map. Please enter a valid building.");
            start = userInput.nextLine().trim();
        }

        System.out.println("Enter the end location: ");
        String end = userInput.nextLine().trim();
        //While loop to check if the user input is valid
        while (!backend.searchBuilding(end)) {
            System.out.println("That building is not on the map. Please enter a valid building.");
            end = userInput.nextLine().trim();
        }

        System.out.println("Enter the time to travel: ");
        int weight = userInput.nextInt();
        //While loop to check if the user input is valid
        while (weight < 0) {
            System.out.println("The time to travel must be greater than 0. Please enter a valid time to travel.");
            weight = userInput.nextInt();
        }

        //Uses insertPath() from backend
        backend.insertEdge(start, end, weight);
        backend.insertEdge(end, start, weight);
        System.out.println("It takes " + weight + " minutes to travel between " + start + " to " + end + "!");
        userInput.nextLine();
    }

    @Override
    public void deleteRoad() {
        System.out.println("");
        System.out.println("**** You Selected to Delete a Road From the Map ****");
        System.out.println("Enter the start location: ");
        String start = userInput.nextLine().trim();
        //While loop to check if the user input is valid
        while (!backend.searchBuilding(start)) {
            System.out.println("That building is not on the map. Please enter a valid building.");
            start = userInput.nextLine().trim();
        }

        System.out.println("Enter the end location: ");
        String end = userInput.nextLine().trim();
        //While loop to check if the user input is valid
        while (!backend.searchBuilding(end)) {
            System.out.println("That building is not on the map. Please enter a valid building.");
            end = userInput.nextLine().trim();
        }

        //Uses deletePath() from backend
        backend.removeEdge(start, end);
        backend.removeEdge(end, start);
        System.out.println("Road between " + start + " and " + end + " has been deleted.");
    }

    public static void main(String[] args) {
        //Placeholder for algorithm engineer code
        AlgorithmExecutionAE graph;
        graph = new AlgorithmExecutionAE();
        //Placeholder for backend developer code
        DijkstraBackendBD backend = new DijkstraBackendBD(graph);
        //Use frontend code to drive the text-base user interface
        Scanner scanner = new Scanner(System.in);
        FrontendFD frontend = new FrontendFD(scanner, backend);
        frontend.runCommandLoop();
    }
}
