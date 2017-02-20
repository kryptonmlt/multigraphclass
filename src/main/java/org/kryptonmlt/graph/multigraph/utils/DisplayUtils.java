package org.kryptonmlt.graph.multigraph.utils;

import org.apache.commons.math3.linear.RealMatrix;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.kryptonmlt.graph.multigraph.generator.StudentGraphCreator;
import org.kryptonmlt.graph.multigraph.pojos.Student;

/**
 *
 * @author kurt
 */
public class DisplayUtils {

    private DisplayUtils() {

    }

    public static Graph createGraphFromMatrix(String name, Student student) {
        double[][] matrix = student.getStudentGraph().getData();
        Graph graph = new SingleGraph(name);
        graph.addAttribute("ui.stylesheet", "node { text-size: 20; } edge { text-size: 20; }");
        //add nodes
        for (int i = 0; i < matrix.length; i++) {
            Node n = graph.addNode("" + i);
            n.addAttribute("ui.label", "" + StudentGraphCreator.graphLabels[i]);
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > 0) {
                    if (i < j) {
                        Edge e = graph.addEdge(i + "" + j, "" + i, "" + j, false);
                        e.addAttribute("ui.label", matrix[i][j]);
                    }
                }
            }
        }
        return graph;
    }

    public static void displayGraph(Graph graph) {
        Viewer viewer = graph.display();
        viewer.enableAutoLayout();
    }
}
