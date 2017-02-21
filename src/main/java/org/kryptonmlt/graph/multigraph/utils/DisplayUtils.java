package org.kryptonmlt.graph.multigraph.utils;

import java.awt.Color;
import java.util.List;
import org.apache.commons.math3.linear.RealMatrix;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.kryptonmlt.graph.multigraph.generator.StudentGraphCreator;

/**
 *
 * @author kurt
 */
public class DisplayUtils {

    private DisplayUtils() {

    }

    public static Graph createGraphFromMatrix(String name, RealMatrix studentGraph, List<List<Integer>> clusters) {
        double[][] matrix = studentGraph.getData();
        Graph graph = new SingleGraph(name);
        StringBuilder css = new StringBuilder();
        css.append("node { text-size: 20; } edge { text-size: 20; } ");
        if (clusters != null) {
            for (int c = 0; c < clusters.size(); c++) {
                Color color = new Color((int) (Math.random() * 0x1000000));
                css.append("node.cluster" + c + " {fill-color: rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ");} ");
            }
        }
        graph.addAttribute("ui.stylesheet", css.toString());
        //add nodes
        for (int i = 0; i < matrix.length; i++) {
            Node n = graph.addNode("" + i);
            if (i == 0) {
                n.addAttribute("ui.label", "Student: " + name);
            } else {
                n.addAttribute("ui.label", "" + StudentGraphCreator.graphLabels[i]);
            }
            if (clusters != null) {
                n.addAttribute("ui.class", "cluster" + RealMatrixUtils.fitsIn(i, clusters));
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i != j && matrix[i][j] != 0) {
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
