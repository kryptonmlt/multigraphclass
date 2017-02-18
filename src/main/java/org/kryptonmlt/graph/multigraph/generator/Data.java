package org.kryptonmlt.graph.multigraph.generator;

/**
 *
 * @author Kurt
 */
public class Data {

    public static String[] courses = {"Maths", "Physics", "English"};

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

}
