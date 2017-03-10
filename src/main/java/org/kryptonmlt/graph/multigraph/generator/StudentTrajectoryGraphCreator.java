package org.kryptonmlt.graph.multigraph.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.kryptonmlt.graph.multigraph.pojos.StudentTrajectory;

/**
 *
 * @author Kurt
 */
public class StudentTrajectoryGraphCreator {

    private static final Random RANDOM = new Random();
    private static final float COURSE_PROBABILITY = 0.5f;
    private static final float TOPIC_PROBABILITY_DEGRADE = 0.2f;
    private final int studentRows;
    public static String[] graphLabels;

    public StudentTrajectoryGraphCreator() {
        int rows = 1 + ConceptData.COURSES.length + ConceptData.LECTURES.length + ConceptData.CONCEPTS.length;//student himself

        this.studentRows = rows;
        graphLabels = new String[this.studentRows];
        int count = 0;
        graphLabels[count] = "Student";
        count++;
        for (int i = 0; i < ConceptData.COURSES.length; i++, count++) {
            graphLabels[count] = ConceptData.COURSES[i];
        }
        for (int i = 0; i < ConceptData.LECTURES.length; i++, count++) {
            graphLabels[count] = ConceptData.LECTURES[i];
        }
        for (int i = 0; i < ConceptData.CONCEPTS.length; i++, count++) {
            graphLabels[count] = ConceptData.CONCEPTS[i];
        }

        System.out.println("Matrix size: " + studentRows);
    }

    /**
     * Creates randoms students. Students who do maths perform better in physics
     *
     * @return
     */
    public StudentTrajectory createRandomStudent(String name) {
        StudentTrajectory student = new StudentTrajectory(name);
        //choosing COURSES
        for (int i = 0; i < ConceptData.COURSES.length; i++) {
            float chosen = RANDOM.nextFloat();
            if (chosen > COURSE_PROBABILITY) {
                student.getCourseLectures().put(i, new ArrayList<>());
            }
        }
        //choose topics and assign marks
        for (Integer course : student.getCourseLectures().keySet()) {
            Integer[] lectures = ConceptData.getLectures(course);
            for (int i = 0; i < lectures.length; i++) {
                float chosen = RANDOM.nextFloat();
                if (chosen <= 1 - (TOPIC_PROBABILITY_DEGRADE * i)) {
                    student.getCourseLectures().get(course).add(lectures[i]);
                } else {
                    //no more courses in this section.
                    break;
                }
            }
        }

        //now we create the matrix
        RealMatrix studentMatrix = new Array2DRowRealMatrix(studentRows, studentRows);

        for (Integer course : student.getCourseLectures().keySet()) {
            studentMatrix.setEntry(0, ConceptData.getCoursePositionInMatrix(course), 1.0f);
            Integer previousLecture = null;
            for (Integer lecture : student.getCourseLectures().get(course)) {
                if (previousLecture == null) {
                    studentMatrix.setEntry(ConceptData.getCoursePositionInMatrix(course), ConceptData.getLecturePositionInMatrix(lecture), 1.0f);
                } else {
                    studentMatrix.setEntry(ConceptData.getLecturePositionInMatrix(previousLecture), ConceptData.getLecturePositionInMatrix(lecture), 1.0f);
                }
                previousLecture = lecture;
                
                //add concepts
                for (Integer concept : ConceptData.getConceptsRequired(lecture)) {
                    studentMatrix.setEntry(ConceptData.getConceptPositionInMatrix(concept), ConceptData.getLecturePositionInMatrix(lecture), 1.0f);
                }
                
                for (Integer concept : ConceptData.getConceptsTaught(lecture)) {
                    studentMatrix.setEntry(ConceptData.getLecturePositionInMatrix(lecture), ConceptData.getConceptPositionInMatrix(concept), 1.0f);                    
                }
            }
        }
        student.setStudentGraph(studentMatrix);
        System.out.println(Arrays.deepToString(studentMatrix.getData()));
        return student;
    }
}
