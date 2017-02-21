package org.kryptonmlt.graph.multigraph.generator;

import org.kryptonmlt.graph.multigraph.utils.StudentUtils;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.kryptonmlt.graph.multigraph.pojos.Student;

/**
 *
 * @author Kurt
 */
public class StudentGraphCreator {

    private static final Random RANDOM = new Random();
    private static final float COURSE_PROBABILITY = 0.5f;
    private static final float TOPIC_PROBABILITY_DEGRADE = 0.2f;
    private final int studentRows;
    public static String[] graphLabels;

    public StudentGraphCreator() {
        int rows = 1;//student himself
        for (int c = 0; c < Data.COURSES.length; c++) {
            rows += 1;
            String[] topics = Data.getTopics(c);
            for (int t = 0; t < topics.length; t++) {
                rows += 1;
                String[] assessments = Data.getAssesmentPerTopic(c, t);
                rows += assessments.length;
            }
        }
        this.studentRows = rows;
        graphLabels = new String[this.studentRows];
        int count = 0;
        graphLabels[count] = "Student";
        count++;
        for (int c = 0; c < Data.COURSES.length; c++, count++) {
            graphLabels[count] = Data.COURSES[c];
            count++;
            String[] topics = Data.getTopics(c);
            for (int t = 0; t < topics.length; t++, count++) {
                graphLabels[count] = topics[t];
                count++;
                String[] assessments = Data.getAssesmentPerTopic(c, t);
                for (int a = 0; a < assessments.length; a++, count++) {
                    graphLabels[count] = assessments[a];
                }
                count--;
            }
            count--;
        }

        System.out.println("Matrix size: " + studentRows);
    }

    /**
     * Creates randoms students. Students who do maths perform better in physics
     *
     * @return
     */
    public Student createRandomStudent(String name) {
        Student student = new Student(name);
        //choosing COURSES
        for (int i = 0; i < Data.COURSES.length; i++) {
            float chosen = RANDOM.nextFloat();
            if (chosen > COURSE_PROBABILITY) {
                student.getCourses().add(i);
            }
        }
        //choose topics and assign marks
        for (int course : student.getCourses()) {
            String[] topics = Data.getTopics(course);
            for (int i = 0; i < topics.length; i++) {
                float chosen = RANDOM.nextFloat();
                if (chosen <= 1 - (TOPIC_PROBABILITY_DEGRADE * i)) {
                    student.addTopic(course, i);

                    //assignment marks
                    String[] assessments = Data.getAssesmentPerTopic(course, i);
                    for (int j = 0; j < assessments.length; j++) {
                        float mark;
                        // if physics and student did maths he gets a better mark
                        if (course == 1 && StudentUtils.isMathStudent(student)) {
                            mark = RANDOM.nextFloat() / 2f;
                            mark += 0.5f;
                        } else {
                            mark = RANDOM.nextFloat();
                        }
                        mark = 1.0f;
                        student.addMarksPerTopic(course, i, mark);
                    }
                } else {
                    //no more courses in this section.
                    break;
                }
            }
        }

        //now we create the matrix
        RealMatrix studentMatrix = new Array2DRowRealMatrix(studentRows, studentRows);
        double[][] studentMatrix2 = new double[studentRows][studentRows];

        for (Integer course : student.getCourses()) {
            StudentUtils.addUndirectedEdge(studentMatrix, 0, Data.getCoursePositionInMatrix(course), 1);
            StudentUtils.addUndirectedEdge(studentMatrix2, 0, Data.getCoursePositionInMatrix(course), 1);
            for (Integer topic : student.getTopics().get(course)) {
                StudentUtils.addUndirectedEdge(studentMatrix, Data.getCoursePositionInMatrix(course), Data.getTopicPositionInMatrix(course, topic), 1);
                StudentUtils.addUndirectedEdge(studentMatrix2, Data.getCoursePositionInMatrix(course), Data.getTopicPositionInMatrix(course, topic), 1);
                int m = 0;
                for (Float mark : student.getMarksPerTopic().get(course).get(topic)) {
                    StudentUtils.addUndirectedEdge(studentMatrix, Data.getTopicPositionInMatrix(course, topic), Data.getAssessmentPositionInMatrix(course, topic, m), mark);
                    StudentUtils.addUndirectedEdge(studentMatrix2, Data.getTopicPositionInMatrix(course, topic), Data.getAssessmentPositionInMatrix(course, topic, m), mark);
                    m++;
                }
            }
        }
        student.setStudentGraph(studentMatrix);
        student.setRawData(studentMatrix2);
        System.out.println(Arrays.deepToString(studentMatrix2));
        return student;
    }
}
