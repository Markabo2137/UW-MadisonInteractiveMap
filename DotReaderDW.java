// --== CS400 File Header Information ==--
// Name: Fatimah Mohammed
// Email: famohammed2@wisc.edu
// Group and Team: AN Blue
// Group TA: Gary Dahl
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.io.FileNotFoundException;
import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/*
 * this class reads the dotfile that defines edge, nodes
 * and their types
 */
public class DotReaderDW implements DotReaderInterfaceDW {

    /**
     * parses through the dotfile and loads all the information from each path.
     * The from and to field is parsed along with the cost to get "from" to "to".
     * That info creates a path field.
     *
     * @param filename@return list of building nodes with predecessor and successor
     *                        information
     * @throws FileNotFoundException when file path cannot be found
     */
    @Override
    public List<Node> readDotfile(String filename) throws FileNotFoundException {
        // use scanner to read from the specified file, and store results in posts
        Map<String, Node> nodes = new HashMap<>();

        Scanner in = null;
        try {
            in = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File could not be found.");
        }

        // go through each line in file and parse informations
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] parts = line.split("->");

            if (parts.length == 2) {
                // extract the building names and cost from the line
                String fromName = parts[0].trim();
                String toName = parts[1].split("\\[")[0].trim();
                int cost = Integer.parseInt(parts[1].split("\\[label=\"")[1].split("\"]")[0]);

                // create the buildings that the line contains
                Building from = new Building(fromName);
                Building to = new Building(toName);

                // create pairs to keep track of names of pred nodes
                Node pred = nodes.get(fromName);
                if (pred == null) {
                    pred = new Node(from);
                    nodes.put(fromName, pred);
                }

                // create pairs to keep track of names of succ nodes
                Node succ = nodes.get(toName);
                if (succ == null) {
                    succ = new Node(to);
                    nodes.put(toName, succ);
                }

                // create Road and Edge objects using the extracted information
                Road road = new Road(cost);

                Edge edge = new Edge(road, pred, succ);

                // add the edge to the roadsLeaving list of the pred node
                pred.roadsLeaving.add(edge);

                // add the edge to the roadsEntering list of the succ node
                succ.roadsEntering.add(edge);
            }
        }
        return new ArrayList<>(nodes.values());
    }

    /**
     * inner class that defines the edge type
     */
    public class Road extends Number {
        int cost;

        public Road(int cost) {
            this.cost = cost;
        }

        /**
         * returns the cost that it is to go from building
         * (Buildin from) to another building (Building to)
         *
         * @return cost of road
         */
        public int getRoadCost() {
            return cost;
        }

        /**
         * Returns the value of the specified number as an {@code int}.
         *
         * @return the numeric value represented by this object after conversion
         *         to type {@code int}.
         */
        @Override
        public int intValue() {
            return 0;
        }

        /**
         * Returns the value of the specified number as a {@code long}.
         *
         * @return the numeric value represented by this object after conversion
         *         to type {@code long}.
         */
        @Override
        public long longValue() {
            return 0;
        }

        /**
         * Returns the value of the specified number as a {@code float}.
         *
         * @return the numeric value represented by this object after conversion
         *         to type {@code float}.
         */
        @Override
        public float floatValue() {
            return 0;
        }

        /**
         * Returns the value of the specified number as a {@code double}.
         *
         * @return the numeric value represented by this object after conversion
         *         to type {@code double}.
         */
        @Override
        public double doubleValue() {
            return cost;
        }
    }

    /**
     * inner class that defines the node type
     */
    public class Building {
        String buildingName;

        public Building(String buildingName) {
            this.buildingName = buildingName;
        }

        public String toString() {
            return buildingName;
        }

        /**
         * returns the name of the building
         *
         * @return name of building
         */
        public String getBuildingName() {
            return buildingName;
        }
    }

    /**
     * inner class that defines each node of graph
     */
    public class Node {
        public Building building;
        public List<Edge> roadsLeaving = new ArrayList<>();
        public List<Edge> roadsEntering = new ArrayList<>();

        public Node(Building building) {
            this.building = building;
        }
    }

    /**
     * inner class that defines each edge of graph
     */
    public class Edge {
        public Road road; // the weight or cost of this edge
        public Node predecessor;
        public Node successor;

        public Edge(Road road, Node pred, Node succ) {
            this.road = road;
            this.predecessor = pred;
            this.successor = succ;
        }
    }

    public static void main(String[] a) {
        DotReaderDW test = new DotReaderDW();
        List<Node> nodes = null;

        try {
            nodes = test.readDotfile("src/locations.gv");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return;
        }

        for (Node node : nodes) {
            List<Edge> roadsLeaving = node.roadsLeaving;
            List<Edge> roadsEntering = node.roadsEntering;

            System.out.println("********");
            System.out.println("This is building " + node.building.buildingName);

            System.out.println("ROADS LEAVING:");
            for (Edge edge : roadsLeaving) {
                System.out.println(edge.predecessor.building.buildingName + " has a road leaving that leads to "
                        + edge.successor.building.buildingName);
                System.out.println("This edge has a cost of " + edge.road.cost);
            }

            System.out.println("ROADS ENTERING:");
            for (Edge edge : roadsEntering) {
                System.out.println(edge.predecessor.building.buildingName + " has a road leaving that leads to "
                        + edge.successor.building.buildingName);
                System.out.println("This edge has a cost of " + edge.road.cost);
            }

            System.out.println("********");
        }
    }
}
