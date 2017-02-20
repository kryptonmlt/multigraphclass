package org.kryptonmlt.graph.multigraph.generator;

/**
 *
 * @author Kurt
 */
public class Data {

    public static String[] COURSES = {"Maths", "Physics", "English"};
    public static final int ASSESSMENTS_PER_COURSE = 3;
    public static final int TOPICS_PER_COURSE = 3;

    public static String[] getTopics(int course) {
        String[] topics;
        switch (course) {
            case 0:
                topics = new String[]{"Algebra", "Linear", "Quadratic"};
                break;
            case 1:
                topics = new String[]{"Gravity", "Temperature", "Newton"};
                break;
            case 2:
                topics = new String[]{"Past", "Present", "Future"};
                break;
            default:
                topics = null;
        }
        return topics;
    }

    public static String[] getAssesmentPerTopic(int course, int topic) {
        String[] topics = {"Video", "MultipleChoice", "Assesment"};
        return topics;
    }

    public static int getStudentInMatrix() {
        return 0;
    }

    public static int getCoursePositionInMatrix(int course) {
        return 1 + course;
    }

    public static int getTopicPositionInMatrix(int course, int topic) {
        return 1 + COURSES.length + (course * TOPICS_PER_COURSE * ASSESSMENTS_PER_COURSE) + (topic * ASSESSMENTS_PER_COURSE) + (1 * topic);
    }

    public static int getAssessmentPositionInMatrix(int course, int topic, int assessment) {
        return getTopicPositionInMatrix(course, topic) + assessment + 1;
    }

}
