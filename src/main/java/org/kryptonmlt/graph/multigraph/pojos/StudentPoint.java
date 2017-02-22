package org.kryptonmlt.graph.multigraph.pojos;

import org.apache.commons.math3.ml.clustering.DoublePoint;

/**
 *
 * @author kurt
 */
public class StudentPoint extends DoublePoint {

    private int id;

    public StudentPoint(int id, double[] point) {
        super(point);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
