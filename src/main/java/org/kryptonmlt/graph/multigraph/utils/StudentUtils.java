package org.kryptonmlt.graph.multigraph.utils;

import org.apache.commons.math3.linear.RealMatrix;
import org.kryptonmlt.graph.multigraph.generator.StudentGraphCreator;
import org.kryptonmlt.graph.multigraph.pojos.Student;

/**
 *
 * @author Kurt
 */
public class StudentUtils {

    private StudentUtils() {

    }

    public static boolean isMathStudent(Student student) {
        return StudentUtils.isCourseStudent(0, student);
    }

    public static boolean isCourseStudent(int course, Student student) {
        return student.getCourses().contains(course);
    }

    public static void addUndirectedEdge(RealMatrix m, int i, int j, double v) {
        System.out.println(i + "," + j + " m:" + v);
        System.out.println(StudentGraphCreator.graphLabels[i] + "," + StudentGraphCreator.graphLabels[j] + " m:" + v);
        //m.addToEntry(i, j, v);
        m.setEntry(i, j, v);
        if (i != j) {
            m.setEntry(j, i, v);
            //m.addToEntry(j, i, v);
        }
    }

    public static void addUndirectedEdge(double[][] m, int i, int j, double v) {
        m[i][j] = v;
        if (i != j) {
            m[j][i] = v;
        }
    }
}
