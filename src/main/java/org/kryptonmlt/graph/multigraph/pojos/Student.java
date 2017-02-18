package org.kryptonmlt.graph.multigraph.pojos;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.linear.RealMatrix;

/**
 *
 * @author Kurt
 */
public class Student {
    
    private String name;    
    private List<Integer> courses = new ArrayList<>();
    private List<Integer> topics = new ArrayList<>();
    private List<List<Integer>> marksPerTopic = new ArrayList<>();
    private RealMatrix studentGraph;
    
    public Student(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getCourses() {
        return courses;
    }

    public List<Integer> getTopics() {
        return topics;
    }

    public List<List<Integer>> getMarksPerTopic() {
        return marksPerTopic;
    }

    public RealMatrix getStudentGraph() {
        return studentGraph;
    }

    public void setStudentGraph(RealMatrix studentGraph) {
        this.studentGraph = studentGraph;
    }
    
}
