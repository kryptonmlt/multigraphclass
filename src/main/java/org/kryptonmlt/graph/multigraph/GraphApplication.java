package org.kryptonmlt.graph.multigraph;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.linear.RealMatrix;
import org.kryptonmlt.graph.multigraph.generator.StudentTrajectoryGraphCreator;
import org.kryptonmlt.graph.multigraph.kernels.simrank.SimRank;
import org.kryptonmlt.graph.multigraph.pojos.StudentTrajectory;
import org.kryptonmlt.graph.multigraph.utils.DisplayUtils;
import org.kryptonmlt.graph.multigraph.utils.RealMatrixUtils;

/**
 *
 * @author Kurt
 */
public class GraphApplication {

    public static void main(String[] args) {
        StudentTrajectoryGraphCreator creator = new StudentTrajectoryGraphCreator();
        int maxStudents = 20;
        List<StudentTrajectory> students = new ArrayList<>();
        for (int i = 0; i < maxStudents; i++) {
            System.out.println("Student " + i + ":");
            StudentTrajectory student = creator.createRandomStudent("" + i);
            System.out.println(student.getStudentGraph().toString());
            students.add(student);
        }

        for (int s = 0; s < 2; s++) {
            StudentTrajectory student = students.get(s);
            System.out.println("student: " + s);
            RealMatrixUtils.printMatrix("Original", student.getStudentGraph());
            RealMatrix normalizedLaplacian = RealMatrixUtils.getLaplacianMatrix(student.getStudentGraph(), true);
            RealMatrixUtils.printMatrix("NormalizedLaplacian", normalizedLaplacian);
            int coursesOfStudent = student.getCourseLectures().keySet().size();
            if (coursesOfStudent == 0) {
                coursesOfStudent++;
            }
            DisplayUtils.displayGraph(DisplayUtils.createGraphFromMatrix("" + s, student.getStudentGraph(), RealMatrixUtils.getClustersNonZero(normalizedLaplacian, coursesOfStudent, false), StudentTrajectoryGraphCreator.graphLabels));
            System.out.println("finished drawing student " + s);
        }
        SimRank simRank = new SimRank(students.get(0).getStudentGraph(), students.get(1).getStudentGraph());
        System.out.println("Similarity 0 to 1 : " + simRank.similarity());
        //System.out.println(RandomWalk.calculateKWalk(students.get(0).getStudentGraph(), students.get(1).getStudentGraph(), 0.9));
    }

    public String[] createAllLabels(int maxStudents) {
        String[] allStudentsLabels = new String[maxStudents * StudentTrajectoryGraphCreator.graphLabels.length];
        for (int i = 0, j = 0; i < allStudentsLabels.length; i++) {
            int mod = i % StudentTrajectoryGraphCreator.graphLabels.length;
            if (mod == 0) {
                allStudentsLabels[i] = "Student: " + j;
                j++;
            } else {
                allStudentsLabels[i] = StudentTrajectoryGraphCreator.graphLabels[mod];
            }
        }
        return allStudentsLabels;
    }
}
