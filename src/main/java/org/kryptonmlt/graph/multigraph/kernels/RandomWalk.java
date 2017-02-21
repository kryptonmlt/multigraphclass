package org.kryptonmlt.graph.multigraph.kernels;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.kryptonmlt.graph.multigraph.utils.RealMatrixUtils;

/**
 *
 * @author kurt
 */
public class RandomWalk {

    public static final int MAX_K = 5;

    public static double calculateKWalk(RealMatrix a, RealMatrix b, double lambda) {
        RealMatrix Ax = RealMatrixUtils.directProductGraph(a, b);
        //RealMatrix Ax = new Array2DRowRealMatrix(KroneckerOperation.product(a.getData(), b.getData()));
        return calculateKWalk(Ax, RealMatrixUtils.sizeOfMatrix(a), RealMatrixUtils.sizeOfMatrix(b), lambda);
    }

    public static double calculateKWalk(RealMatrix Ax, int size1, int size2, double lambda) {

        //construct e transpose
        RealMatrix e = new Array2DRowRealMatrix(Ax.getRowDimension(), 1);
        for (int i = 0; i < e.getRowDimension(); i++) {
            e.setEntry(i, 0, 1);
        }

        //construct e
        RealMatrix eT = new Array2DRowRealMatrix(1, Ax.getColumnDimension());
        for (int i = 0; i < eT.getColumnDimension(); i++) {
            eT.setEntry(0, i, 1);
        }

        RealMatrix previousAxk = MatrixUtils.createRealIdentityMatrix(Ax.getRowDimension());
        for (int k = 0; k < MAX_K; k++) {
            if (k > 0) {
                previousAxk = previousAxk.multiply(Ax);
            }
            previousAxk.scalarMultiply(lambda / CombinatoricsUtils.factorial(k));
        }
        RealMatrix first = eT.multiply(previousAxk);
        RealMatrix result = first.multiply(e);
        return result.scalarMultiply(1.0 / (size1 * size2)).getEntry(0, 0);
    }

}
