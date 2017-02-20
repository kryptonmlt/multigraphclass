package org.kryptonmlt.graph.multigraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.math3.linear.MatrixUtils;
import org.graphstream.graph.Graph;
import org.kryptonmlt.graph.multigraph.generator.StudentGraphCreator;
import org.kryptonmlt.graph.multigraph.pojos.Student;
import org.kryptonmlt.graph.multigraph.utils.DisplayUtils;

/**
 *
 * @author Kurt
 */
public class MultiGraphApplication {

    public static void main(String[] args) {
        StudentGraphCreator creator = new StudentGraphCreator();
        int maxStudents = 100;
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < maxStudents; i++) {
            System.out.println("Student " + i + ":");
            Student student = creator.createRandomStudent();
            System.out.println(student.getStudentGraph().toString());
            students.add(student);
        }
        System.out.println(Arrays.toString(StudentGraphCreator.graphLabels));
        Graph g1 = DisplayUtils.createGraphFromMatrix("0", students.get(0));
        Graph g2 = DisplayUtils.createGraphFromMatrix("50", students.get(50));
        Graph g3 = DisplayUtils.createGraphFromMatrix("70", students.get(70));
        DisplayUtils.displayGraph(g1);
        DisplayUtils.displayGraph(g2);
        DisplayUtils.displayGraph(g3);
    }
}
