// --== CS400 File Header Information ==--
// Name: Matthew Wang
// Email: mewang@wisc.edu
// Group and Team: AN blue
// Group TA: Florian Heimerl
// Lecturer: Gary Dahl
// Notes to Grader: haiiii :3

import java.io.FileNotFoundException;
import java.util.*;

/**
 * The Backend Developer will use the DijkstraExtendedAE developed by the AE to
 * search, add, and delete locations from the graph/map, as well as list all
 * possible routes from one place to another, by utilizing the DW Object classes as the data
 * in the AE nodes.
 */
public class DijkstraBackendBD extends DijkstraGraphAE<DotReaderDW.Building, DotReaderDW.Road>
        implements DijkstraBackendInterfaceBD {

    protected AlgorithmExecutionAE graph;
    protected DotReaderDW dw = new DotReaderDW();

    public DijkstraBackendBD(AlgorithmExecutionAE graph) throws IllegalArgumentException {

        this.graph = graph;
    }

    /**
     * getter method for the names of the buildings contained within the graph
     *
     * @return an ArrayList of the names of the buildings contained within the graph
     */
    @Override
    public ArrayList<String> getBuildingNames() {

        ArrayList<String> keyString = new ArrayList<>();

        Set<DotReaderDW.Building> keys = graph.nodes.keySet();
        Iterator<DotReaderDW.Building> it = keys.iterator();

        while (it.hasNext()) {
            keyString.add(it.next().buildingName);
        }

        Collections.sort(keyString);

        return keyString;
    }

    /**
     * adds a building that does not exist in the graph into the graph
     *
     * @param buildingName the building to be removed
     * @return true if the building was inserted into the graph, false if the
     * building is already contained within the graph
     */
    @Override
    public boolean addBuilding(String buildingName) {
        DotReaderDW.Building building =  dw.new Building(buildingName);
        return graph.insertNode(building);
    }

    /**
     * deletes a building that exists in the graph
     *
     * @param buildingName the building to be removed
     * @return true if the building was deleted into the graph, false if the
     * building is not contained within the graph
     */
    @Override
    public boolean deleteBuilding(String buildingName) {

        Set<DotReaderDW.Building> keys = graph.nodes.keySet();
        Iterator<DotReaderDW.Building> it = keys.iterator();

        while (it.hasNext()) {
            DotReaderDW.Building curr = it.next();
            if (curr.buildingName.equals(buildingName)) {
                return graph.removeNode(curr);
            }
        }
        return false;
    }

    /**
     * inserts or updates the cost of an edge in the graph
     *
     * @param pred the building the edge exits, the predecessor
     * @param succ the building the edge enters, the successor
     * @param cost the weight of the edge
     * @return true  if the edge was inserted or updated in the graph, false
     * if the predecessor or successor is not found in the graph
     */
    @Override
    public boolean insertEdge(String pred, String succ, int cost) {

        DotReaderDW.Building predB = null;
        DotReaderDW.Building succB = null;

        Set<DotReaderDW.Building> keys = graph.nodes.keySet();
        Iterator<DotReaderDW.Building> it = keys.iterator();

        while (it.hasNext()) {
            DotReaderDW.Building curr = it.next();
            if (curr.buildingName.equals(pred)) {
                predB = curr;
            }
            if (curr.buildingName.equals(succ)) {
                succB = curr;
            }
        }

        DotReaderDW.Road road = dw.new Road(cost);
        try {
            return graph.insertEdge(predB, succB, road);
        } catch (NullPointerException e) {
            return false;
        }

    }

    /**
     * removes an edge in the graph
     *
     * @param pred the building the edge exits, the predecessor
     * @param succ the building the edge enters, the successor
     * @return true if the edge was removed, false if the
     * predecessor or successor is not found in the graph
     */
    @Override
    public boolean removeEdge(String pred, String succ) {

        DotReaderDW.Building predB = null;
        DotReaderDW.Building succB = null;

        Set<DotReaderDW.Building> keys = graph.nodes.keySet();
        Iterator<DotReaderDW.Building> it = keys.iterator();

        while (it.hasNext()) {
            DotReaderDW.Building curr = it.next();
            if (curr.buildingName.equals(pred)) {
                predB = curr;
            }
            if (curr.buildingName.equals(succ)) {
                succB = curr;
            }
        }

        return graph.removeEdge(predB, succB);
    }

    /**
     * getter method for the amount of buildings in the graph
     *
     * @return the number of buildings in the graph
     */
    @Override
    public int getBuildingCount() {
        return graph.getNodeCount();
    }

    /**
     * getter method for the amount of edges in the graph
     *
     * @return the number of edges in the graph
     */
    @Override
    public int getEdgeCount() {
        return graph.getEdgeCount();
    }

    /**
     * search method for buildings in the graph
     *
     * @param buildingName the building to be searched for
     * @return true if the building exists in the graph, false if not
     */
    @Override
    public boolean searchBuilding(String buildingName) {

        Set<DotReaderDW.Building> keys = graph.nodes.keySet();
        Iterator<DotReaderDW.Building> it = keys.iterator();

        while (it.hasNext()) {
            DotReaderDW.Building curr = it.next();
            if (curr.buildingName.equals(buildingName)) {
                return graph.containsNode(curr);
            }
        }
        return false;
    }

    /**
     * search method for edges in the graph
     *
     * @param pred the building the edge exists, the predecessor
     * @param succ the building the edge enters, the successor
     * @return true if the edge exists in the graph, false if not
     */
    @Override
    public boolean searchEdge(String pred, String succ) {

        DotReaderDW.Building predB = null;
        DotReaderDW.Building succB = null;

        Set<DotReaderDW.Building> keys = graph.nodes.keySet();
        Iterator<DotReaderDW.Building> it = keys.iterator();

        while (it.hasNext()) {
            DotReaderDW.Building curr = it.next();
            if (curr.buildingName.equals(pred)) {
                predB = curr;
            }
            if (curr.buildingName.equals(succ)) {
                succB = curr;
            }
        }

        return graph.containsEdge(predB, succB);
    }

    /**
     * creates buildings and edges from information give from
     * the file
     *
     * @param filename the file for data to be loaded from
     * @throws FileNotFoundException if the file from
     *                                       the filename cannot be found (the exception will be thrown
     *                                       from the dreadDotFile() method
     */
    @Override
    public void setGraphFromFile(String filename) throws FileNotFoundException {

            DotReaderDW dw = new DotReaderDW();
            Iterator<DotReaderDW.Edge> nl;
            Iterator<DotReaderDW.Node> n;
            DotReaderDW.Edge curr;

            List<DotReaderDW.Node> info = dw.readDotfile(filename);

            // adds the building into the graph
            for (DotReaderDW.Node node : info) {
                this.addBuilding(node.building.getBuildingName());
            }


            for (DotReaderDW.Node node : info) {
                nl = node.roadsLeaving.iterator();

                for (int i = 0; i < node.roadsLeaving.size(); i++) {
                    curr = nl.next();
                    DotReaderDW.Building pre = curr.predecessor.building;
                    DotReaderDW.Building succ = curr.successor.building;

                    this.insertEdge(pre.buildingName, succ.buildingName, curr.road.cost);

                    }
                }
        }

    /**
     * caller method for Dijkstra's shortestPathData() method
     *
     * @param start the building which you start with
     * @param end   the building which you want to reach
     * @return a List of the buildings of the shortest possible path
     * given the edges
     */
    public List<DotReaderDW.Building> findShortestPath(String start, String end) {

        DotReaderDW.Building startB = dw.new Building(start);
        DotReaderDW.Building endB = dw.new Building(end);

        Set<DotReaderDW.Building> keys = graph.nodes.keySet();
        Iterator<DotReaderDW.Building> it = keys.iterator();

        while (it.hasNext()) {
            DotReaderDW.Building curr = it.next();
            if (curr.buildingName.equals(start)) {
                startB = curr;
            }
            if (curr.buildingName.equals(end)) {
                endB = curr;
            }
        }

        return graph.shortestPathData(startB, endB);
    }

    /**
     * caller method for Dijkstra's shortestPathCost() method
     *
     * @param start the building which you start with
     * @param end   the building which you want to reach
     * @return the cost of the shortest possible path
     */
    public double findShortestPathCost(String start, String end) {

        DotReaderDW.Building startB = null;
        DotReaderDW.Building endB = null;

        Set<DotReaderDW.Building> keys = graph.nodes.keySet();

        for (DotReaderDW.Building curr : keys) {
            if (curr.getBuildingName().equals(start)) {
                startB = curr;
            }
            if (curr.getBuildingName().equals(end)) {
                endB = curr;
            }
        }

        return graph.shortestPathCost(startB, endB);
    }

    /**
     * extended AE functionality; finds all possible paths given a maximum weight
     *
     * @param start  the building which you start with
     * @param weight the "ceiling" weight
     * @return an ArrayList of the buildings reachable given the maximum cost
     */
    public ArrayList<DotReaderDW.Building> findBuildingsUnderCost(String start, int weight) {
        DotReaderDW.Building startB = dw.new Building(start);

        Set<DotReaderDW.Building> keys = graph.nodes.keySet();
        Iterator<DotReaderDW.Building> it = keys.iterator();

        while (it.hasNext()) {
            DotReaderDW.Building curr = it.next();
            if (curr.buildingName.equals(start)) {
                startB = curr;
            }
        }
        return graph.findUnderCost(startB, weight);
    }

    /**
     * extended AE functionality; prints the MST
     *
     * @return a String representing the MST, which reaches every
     * building in the graph
     */
    public String printMST() {
        return graph.printMST();
    }
}

