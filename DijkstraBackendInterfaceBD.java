// --== CS399 File Header Information ==--
// Name: Matthew Wang
// Email: mewang@wisc.edu
// Group and Team: AN blue
// Group TA: Florian Heimerl
// Lecturer: Gary Dahl
// Notes to Grader: haiiii :3

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * The interface for the Backend class, outlines the methods required for Backend
 */
public interface DijkstraBackendInterfaceBD extends GraphADT<DotReaderDW.Building, DotReaderDW.Road> {

    // public DijkstraBackendInterfaceBD (AlgorithmExecutionInterfaceAE graph)

    // AE extended functionality
    public ArrayList<DotReaderDW.Building> findBuildingsUnderCost(String start, int weight);

    public String printMST();

    // extended Backend functionality, finds all possible paths given a maximum weight
    public ArrayList<String> getBuildingNames();

    // add/removes (from GraphADT.java)
    public boolean addBuilding(String buildingName);

    public boolean deleteBuilding(String buildingName);

    public boolean insertEdge(String pred, String succ, int road);

    public boolean removeEdge(String pred, String succ);

    // getters/search (from GraphADT.java)
    public int getBuildingCount();

    public int getEdgeCount();

    public boolean searchBuilding(String toFind);

    public boolean searchEdge(String pred, String succ);

    // Use DataWrangler’s DotReader class. Construct a graph from its paths variable.
    public void setGraphFromFile(String filename) throws FileNotFoundException;

    // Dijkstra's functionalities (from GraphADT.java)
    public List<DotReaderDW.Building> findShortestPath(String start, String end);

    public double findShortestPathCost(String start, String end);
}
