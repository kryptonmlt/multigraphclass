package org.kryptonmlt.graph.multigraph.generator;

import java.util.Random;
import java.util.UUID;
import org.kryptonmlt.graph.multigraph.pojos.Student;

/**
 *
 * @author Kurt
 */
public class StudentGraphCreator {
    
    private Random random = new Random();
    
    public StudentGraphCreator(){
        
    }
    
    public Student createRandomStudent(){
        Student student = new Student(UUID.randomUUID().toString());
        
        return student;
    }
}
