// --== CS400 File Header Information ==--
// Name: Fatimah Mohammed
// Email: famohammed2@wisc.edu
// Group and Team: AN Blue
// Group TA: Gary Dahl
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.io.FileNotFoundException;
import java.util.List;

public interface DotReaderInterfaceDW {
    /**
     * inner class that defines the node type
     */
    public class Building {
    }

    /**
     * inner class that defines the edge type
     */
    public class Road {
    }

    /**
    * inner class that defines each node of graph
    */
    public class Node {
    }

    /**
    * inner class that defines each edge of graph
    */
    public class Edge {
    }

    /**
     * parses through the dotfile and loads all the information from each path.
     * The from and to field is parsed along with the cost to get "from" to "to".
     * That info creates a path field.
     * 
     * @param file
     * @return list of building nodes with predecessor and successor information
     * @throws FileNotFoundException when file path cannot be found
     */
    public List<DotReaderDW.Node> readDotfile(String filename) throws FileNotFoundException;
}
