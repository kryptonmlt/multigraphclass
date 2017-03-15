package org.kryptonmlt.graph.multigraph.generator;

/**
 *
 * @author Kurt
 */
public class ConceptData {

    public static String[] COURSES = {"Maths", "Physics", "English", "Maths-A"};

    public static String[] LECTURES = {"Algebra", "Linear", "Quadratic", "Gravity", "Temperature", "Newton", "Past", "Present", "Future"};

    public static Integer[] getLectures(int course) {
        Integer[] lectures;
        switch (course) {
            case 0:
                //Maths - Algebra, Linear, Quadratic
                lectures = new Integer[]{0, 1, 2};
                break;
            case 1:
                //Physics - Gravity, Temperature, Newton
                lectures = new Integer[]{3, 4, 5};
                break;
            case 2:
                //English - Past, Present, Future
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
                // Algebra - add, subtraction, equation
                concepts = new Integer[]{0, 1, 2};
                break;
            case 1:
                //Linear - add, subtraction, equation, plot
                concepts = new Integer[]{0, 1, 2, 3};
                break;
            case 2:
                //Quadratic -  add, subtraction, equation, plot, real, imaginary
                concepts = new Integer[]{0, 1, 2, 3, 4, 5};
                break;
            case 3:
                //Gravity - plot, force, mass
                concepts = new Integer[]{3, 6, 7};
                break;
            case 4:
                //Temperature - celcsius, kelvin
                concepts = new Integer[]{8, 9};
                break;
            case 5:
                //Newton - plot, laws of motion
                concepts = new Integer[]{3, 10};
                break;
            case 6:
                //Past - past tense, grammar
                concepts = new Integer[]{11, 14};
                break;
            case 7:
                //Present - present tense, grammar
                concepts = new Integer[]{12, 14};
                break;
            case 8:
                //Future - future tense, grammar
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
                // Algebra - 
                concepts = new Integer[]{};
                break;
            case 1:
                //Linear - add, subtraction
                concepts = new Integer[]{0, 1};
                break;
            case 2:
                //Quadratic - add, subtraction
                concepts = new Integer[]{0, 1};
                break;
            case 3:
                //Gravity - plot, real
                concepts = new Integer[]{3, 4};
                break;
            case 4:
                //Temperature - plot, real
                concepts = new Integer[]{3, 4};
                break;
            case 5:
                //Newton - plot, real, celcsius
                concepts = new Integer[]{3, 4, 7, 8};
                break;
            case 6:
                //Past - grammar
                concepts = new Integer[]{14};
                break;
            case 7:
                //Present - grammar
                concepts = new Integer[]{14};
                break;
            case 8:
                //Future - grammar
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
