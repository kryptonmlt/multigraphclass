package org.kryptonmlt.graph.multigraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.math3.linear.RealMatrix;
import org.kryptonmlt.graph.multigraph.generator.StudentGraphCreator;
import org.kryptonmlt.graph.multigraph.kernels.simrank.BasicSimilarity;
import org.kryptonmlt.graph.multigraph.kernels.simrank.SimRank;
import org.kryptonmlt.graph.multigraph.pojos.Student;
import org.kryptonmlt.graph.multigraph.utils.DisplayUtils;
import org.kryptonmlt.graph.multigraph.utils.RealMatrixUtils;

/**
 *
 * @author Kurt
 */
public class MultiGraphApplication {

    public static void main(String[] args) {
        StudentGraphCreator creator = new StudentGraphCreator();
        int maxStudents = 20;
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < maxStudents; i++) {
            System.out.println("Student " + i + ":");
            Student student = creator.createRandomStudent("" + i);
            System.out.println(student.getStudentGraph().toString());
            students.add(student);
        }

        for (int s = 0; s < 2; s++) {
            Student student = students.get(s);
            System.out.println("student: " + s);
            RealMatrixUtils.printMatrix("Original", student.getStudentGraph());
            //RealMatrix laplacian = RealMatrixUtils.getLaplacianMatrix(student.getStudentGraph(), false);
            //RealMatrixUtils.printMatrix("Laplacian", laplacian);
            //DisplayUtils.displayGraph(DisplayUtils.createGraphFromMatrix("Lap " + s, student.getStudentGraph(), RealMatrixUtils.get2Clusters(laplacian)));
            //DisplayUtils.displayGraph(DisplayUtils.createGraphFromMatrix("LapNon0 " + s, student.getStudentGraph(), RealMatrixUtils.get2ClustersNonZero(laplacian)));
            RealMatrix normalizedLaplacian = RealMatrixUtils.getLaplacianMatrix(student.getStudentGraph(), true);
            RealMatrixUtils.printMatrix("NormalizedLaplacian", normalizedLaplacian);
            //DisplayUtils.displayGraph(DisplayUtils.createGraphFromMatrix("Nlap " + s, student.getStudentGraph(), RealMatrixUtils.get2Clusters(normalizedLaplacian)));
            int coursesOfStudent = student.getCourses().size();
            if (coursesOfStudent == 0) {
                coursesOfStudent++;
            }
            DisplayUtils.displayGraph(DisplayUtils.createGraphFromMatrix("" + s, student.getStudentGraph(), RealMatrixUtils.getClustersNonZero(normalizedLaplacian, coursesOfStudent, false), StudentGraphCreator.graphLabels));
            System.out.println("finished drawing student " + s);
        }

        //RealMatrix allStudents = RealMatrixUtils.joinStudentMatrices(students);
        //RealMatrix normalizedLaplacian = RealMatrixUtils.getLaplacianMatrix(allStudents, true);
        //DisplayUtils.displayGraph(DisplayUtils.createGraphFromMatrix("0", allStudents, RealMatrixUtils.getClustersNonZero(normalizedLaplacian, 2, false), allStudentsLabels));
        //System.out.println(RandomWalk.calculateKWalk(students.get(0).getStudentGraph(), students.get(1).getStudentGraph(), 0.9));
        SimRank simRank = new SimRank(students.get(0).getStudentGraph(), students.get(1).getStudentGraph());
        System.out.println("Similarity 0 to 1 : " + simRank.similarity());
        SimRank simRank2 = new SimRank(students.get(0).getStudentGraph(), students.get(0).getStudentGraph());
        System.out.println("Similarity 0 to 0 : " + simRank2.similarity());
        SimRank simRank3 = new SimRank(students.get(1).getStudentGraph(), students.get(0).getStudentGraph());
        System.out.println("Similarity 1 to 0 : " + simRank3.similarity());
        SimRank simRank4 = new SimRank(students.get(1).getStudentGraph(), students.get(1).getStudentGraph());
        System.out.println("Similarity 1 to 1 : " + simRank4.similarity());
        BasicSimilarity basicSim = new BasicSimilarity(students.get(0).getStudentGraph(), students.get(1).getStudentGraph());
        System.out.println("BasicSimilarity 0 to 1 : " + basicSim.similarity());
        BasicSimilarity basicSim2 = new BasicSimilarity(students.get(0).getStudentGraph(), students.get(0).getStudentGraph());
        System.out.println("BasicSimilarity 0 to 0 : " + basicSim2.similarity());
        //System.out.println(RandomWalk.calculateKWalk(students.get(0).getStudentGraph(), students.get(1).getStudentGraph(), 0.9));
    }

    public String[] createAllLabels(int maxStudents) {
        String[] allStudentsLabels = new String[maxStudents * StudentGraphCreator.graphLabels.length];
        for (int i = 0, j = 0; i < allStudentsLabels.length; i++) {
            int mod = i % StudentGraphCreator.graphLabels.length;
            if (mod == 0) {
                allStudentsLabels[i] = "Student: " + j;
                j++;
            } else {
                allStudentsLabels[i] = StudentGraphCreator.graphLabels[mod];
            }
        }
        return allStudentsLabels;
    }
}
