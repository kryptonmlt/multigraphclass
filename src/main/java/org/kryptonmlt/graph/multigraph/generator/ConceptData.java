package org.kryptonmlt.graph.multigraph.generator;

/**
 *
 * @author Kurt
 */
public class ConceptData {

    public static String[] COURSES = {"Maths", "Physics", "English"};

    public static String[] LECTURES = {"Algebra", "Linear", "Quadratic", "Gravity", "Temperature", "Newton", "Past", "Present", "Future"};

    public static Integer[] getLectures(int course) {
        Integer[] lectures;
        switch (course) {
            case 0:
                lectures = new Integer[]{0, 1, 2};
                break;
            case 1:
                lectures = new Integer[]{3, 4, 5};
                break;
            case 2:
                lectures = new Integer[]{6, 7, 8};
                break;
            default:
                lectures = null;
        }
        return lectures;
    }

    public static String[] CONCEPTS = {"add", "subtraction", "equation", "plot",
        "real", "imaginary", "force", "mass", "celcsius", "kelvin", "laws of motion",
        "past tense", "present tense", "future tense", "grammar"};

    public static Integer[] getConceptsTaught(int lecture) {
        Integer[] concepts;
        switch (lecture) {
            case 0:
                concepts = new Integer[]{0, 1, 2};
                break;
            case 1:
                concepts = new Integer[]{0, 1, 2, 3};
                break;
            case 2:
                concepts = new Integer[]{0, 1, 2, 3, 4, 5};
                break;
            case 3:
                concepts = new Integer[]{3, 6, 7};
                break;
            case 4:
                concepts = new Integer[]{8, 9};
                break;
            case 5:
                concepts = new Integer[]{3, 10};
                break;
            case 6:
                concepts = new Integer[]{11, 14};
                break;
            case 7:
                concepts = new Integer[]{12, 14};
                break;
            case 8:
                concepts = new Integer[]{13, 14};
                break;
            default:
                concepts = null;
        }
        return concepts;
    }

    public static Integer[] getConceptsRequired(int lecture) {
        Integer[] concepts;
        switch (lecture) {
            case 0:
                concepts = new Integer[]{};
                break;
            case 1:
                concepts = new Integer[]{0, 1};
                break;
            case 2:
                concepts = new Integer[]{0, 1};
                break;
            case 3:
                concepts = new Integer[]{3, 4};
                break;
            case 4:
                concepts = new Integer[]{3, 4};
                break;
            case 5:
                concepts = new Integer[]{3, 4, 7, 8};
                break;
            case 6:
                concepts = new Integer[]{14};
                break;
            case 7:
                concepts = new Integer[]{14};
                break;
            case 8:
                concepts = new Integer[]{14};
                break;
            default:
                concepts = null;
        }
        return concepts;
    }

    public static int getStudentInMatrix() {
        return 0;
    }

    public static int getCoursePositionInMatrix(int course) {
        return 1 + course;
    }

    public static int getLecturePositionInMatrix(int lecture) {
        return 1 + COURSES.length + lecture;
    }

    public static int getConceptPositionInMatrix(int concept) {
        return 1 + COURSES.length + LECTURES.length + concept;
    }

}
