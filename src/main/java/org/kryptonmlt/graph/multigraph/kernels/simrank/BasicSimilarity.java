package org.kryptonmlt.graph.multigraph.kernels.simrank;

import org.apache.commons.math3.linear.RealMatrix;

/**
 *
 * @author Kurt
 */
public class BasicSimilarity {

    private RealMatrix a;
    private RealMatrix b;

    public BasicSimilarity(RealMatrix a, RealMatrix b) {
        this.a = a;
        this.b = b;
    }

    /**
     * assume n*n matrix
     *
     * @return
     */
    public float similarity() {
        double[][] aRaw = a.getData();
        double[][] bRaw = b.getData();

        float similarity = 0;
        float total = 0;
        for (int i = 0; i < aRaw.length; i++) {
            for (int j = 0; j < aRaw.length; j++) {
                for (int k = 0; k < bRaw.length; k++) {
                    for (int l = 0; l < bRaw.length; l++) {
                        if (aRaw[i][j] != 0 && bRaw[i][j] != 0) {
                            similarity += 1f;
                            total += 1f;
                        } else if (aRaw[i][j] == 0 && bRaw[i][j] == 0) {

                        } else {
                            total += 1f;
                        }
                    }
                }
            }
        }
        return similarity / total;
    }
}
