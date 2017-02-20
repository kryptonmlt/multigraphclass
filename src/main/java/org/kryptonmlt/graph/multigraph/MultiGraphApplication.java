package org.kryptonmlt.graph.multigraph;

import org.kryptonmlt.graph.multigraph.generator.StudentGraphCreator;
import org.kryptonmlt.graph.multigraph.pojos.Student;

/**
 *
 * @author Kurt
 */
public class MultiGraphApplication {

    public static void main(String[] args) {
        StudentGraphCreator creator = new StudentGraphCreator();
        int students = 100;
        for (int i = 0; i < students; i++) {
            System.out.println("Student " + i + ":");
            Student student = creator.createRandomStudent();
            System.out.println(student.getStudentGraph().toString());
        }
    }
}
