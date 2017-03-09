package org.kryptonmlt.graph.multigraph.kernels.simrank;

import java.util.HashSet;
import java.util.Set;
import org.apache.commons.math3.linear.RealMatrix;

/**
 *
 * @author Kurt
 */
public class SimRank {

    private float c = 0.6f;
    private int k = 5;
    private RealMatrix a;
    private RealMatrix b;

    public SimRank(RealMatrix a, RealMatrix b) {
        this.a = a;
        this.b = b;
    }

    /**
     * assume n*n matrix
     *
     * @return
     */
    public float similarity() {
        return s(0, 0, 0);
    }

    public void computeSimRank(int i, int j, int iter) {
        Set<Integer> currentA = inNeighbors(a, i);
        Set<Integer> currentB = inNeighbors(b, j);
        Set<Integer> nextA = new HashSet<>();
        Set<Integer> nextB = new HashSet<>();

        float similarity = 0;
        while (iter-- > 0) {
            if (currentA.isEmpty() || currentB.isEmpty()) {
                continue;
            }
            float sum = 0;
            for (Integer ai : currentA) {
                for (Integer bj : currentB) {
                    if (ai.intValue() == bj.intValue()) {
                        sum += 1f;
                    } else if (iter < k) {
                        sum += s(ai, bj, iter + 1);
                    }
                }
            }
            sum *= (c / (currentA.size() * currentB.size()));
        }
    }

    private float s(int i, int j, int iter) {
        Set<Integer> inA = inNeighbors(a, i);
        Set<Integer> inB = inNeighbors(b, j);
        inA.remove(0);
        inB.remove(0);
        
        if (inA.isEmpty() || inB.isEmpty()) {
            return 0f;
        }

        float sum = 0;
        for (Integer ai : inA) {
            for (Integer bj : inB) {
                if (ai.intValue() == bj.intValue()) {
                    sum += 1f;
                }
                if (iter < k) {
                    sum += s(ai, bj, iter + 1);
                }
            }
        }
        return sum * (c / (inA.size() * inB.size()));
    }

    private Set<Integer> inNeighbors(RealMatrix a, int n) {
        Set<Integer> in = new HashSet<>();
        double[] col = a.getColumn(n);
        for (int i = 0; i < col.length; i++) {
            if (col[i] != 0) {
                in.add(i);
            }
        }
        return in;
    }

    private Set<Integer> outNeighbors(RealMatrix a, int n) {
        Set<Integer> out = new HashSet<>();
        double[] row = a.getRow(n);
        for (int i = 0; i < row.length; i++) {
            if (row[i] != 0) {
                out.add(i);
            }
        }
        return out;
    }

    public void computeSimRank() {
        int n = a.getData().length;
        int iter = ((int) Math.abs(Math.log((double) n) / Math.log((double) 10))) + 1;
        //computeSimRank(iter);
    }
}
