// --== CS400 Project 3 Information ==--
// Name: Bailey Kau
// Email: bkau@wisc.edu
// Group and Team: AN Blue
// Group TA: Gary Dahl
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Hashtable;

public interface FrontendInterfaceFD {
    //public FrontendInterfaceFD(Scanner userInput, BackendInterface backend)
    public void loadFile();
    public void addLocation();
    public void removeLocation();
    public void findShortestPath();
    public void findAllPathsUnder();
    public void listAllLocations();
    public void addRoad();
    public void deleteRoad();
    public void runCommandLoop();
    //public static void main(String[] args);
}
