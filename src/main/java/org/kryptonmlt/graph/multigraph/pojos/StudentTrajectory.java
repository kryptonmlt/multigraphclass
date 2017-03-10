package org.kryptonmlt.graph.multigraph.pojos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.linear.RealMatrix;

/**
 *
 * @author Kurt
 */
public class StudentTrajectory {

    private String name;
    private final Map<Integer, List<Integer>> coursesLectures = new HashMap<>();
    
    private final List<Integer> conceptsTaught = new ArrayList<>();
    private final List<Integer> conceptsRequired = new ArrayList<>();
    private RealMatrix studentGraph;

    public StudentTrajectory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<Integer, List<Integer>> getCourseLectures() {
        return coursesLectures;
    }

    public List<Integer> getConceptsTaught() {
        return conceptsTaught;
    }

    public List<Integer> getConceptsRequired() {
        return conceptsRequired;
    }

    public RealMatrix getStudentGraph() {
        return studentGraph;
    }

    public void setStudentGraph(RealMatrix studentGraph) {
        this.studentGraph = studentGraph;
    }

}
