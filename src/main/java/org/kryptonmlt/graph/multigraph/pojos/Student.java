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
public class Student {

    private String name;
    private List<Integer> courses = new ArrayList<>();
    //maps course to topics
    private Map<Integer, List<Integer>> topics = new HashMap<>();
    //maps course to 
    private Map<Integer, Map<Integer, List<Float>>> marksPerTopic = new HashMap<>();
    private RealMatrix studentGraph;
    private double[][] rawData;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getCourses() {
        return courses;
    }

    public Map<Integer, List<Integer>> getTopics() {
        return topics;
    }

    public void addTopic(int course, int topic) {
        List<Integer> topicsChosen = topics.get(course);
        if (topicsChosen == null) {
            topics.put(course, new ArrayList<>());
            topicsChosen = topics.get(course);
        }
        topicsChosen.add(topic);
    }

    public Map<Integer, Map<Integer, List<Float>>> getMarksPerTopic() {
        return marksPerTopic;
    }

    /**
     * Mark has to be between 0 to 1
     *
     * @param course
     * @param topic
     * @param mark
     */
    public void addMarksPerTopic(int course, int topic, float mark) {
        Map<Integer, List<Float>> topicsChosen = marksPerTopic.get(course);
        if (topicsChosen == null) {
            marksPerTopic.put(course, new HashMap<>());
            topicsChosen = marksPerTopic.get(course);
        }
        List<Float> assessmentsChosen = topicsChosen.get(topic);
        if (assessmentsChosen == null) {
            topicsChosen.put(topic, new ArrayList<>());
            assessmentsChosen = topicsChosen.get(topic);
        }
        assessmentsChosen.add(mark);
    }

    public RealMatrix getStudentGraph() {
        return studentGraph;
    }

    public void setStudentGraph(RealMatrix studentGraph) {
        this.studentGraph = studentGraph;
    }

    public double[][] getRawData() {
        return rawData;
    }

    public void setRawData(double[][] rawData) {
        this.rawData = rawData;
    }

}
