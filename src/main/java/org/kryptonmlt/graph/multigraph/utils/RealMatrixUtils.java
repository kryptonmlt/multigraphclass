package org.kryptonmlt.graph.multigraph.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 *
 * @author kurt
 */
public class RealMatrixUtils {

    private RealMatrixUtils() {

    }

    public static void convertToLaplacianMatrix(RealMatrix matrix) {

        for (int i = 0; i < matrix.getRowDimension(); i++) {
            int degree = 0;
            double[] row = matrix.getRow(i);
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                if (row[j] != 0.0) {
                    degree++;
                    matrix.setEntry(i, j, -row[j]);
                }
            }
            matrix.setEntry(i, i, degree);
        }
    }

    public static RealMatrix getLaplacianMatrix(RealMatrix matrix, boolean normalizeRandomWalk) {
        RealMatrix laplacian = new Array2DRowRealMatrix(matrix.getRowDimension(), matrix.getColumnDimension());
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            int degree = 0;
            double[] row = matrix.getRow(i);
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                if (row[j] != 0.0) {
                    degree++;
                    laplacian.setEntry(i, j, -row[j]);
                }
            }
            if (degree != 0 && normalizeRandomWalk) {
                degree = 1;
            }
            laplacian.setEntry(i, i, degree);
        }
        return laplacian;
    }

    public static RealMatrix getDegreeMatrix(RealMatrix matrix) {
        RealMatrix degreeMatrix = new Array2DRowRealMatrix(matrix.getRowDimension(), matrix.getColumnDimension());
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            int degree = 0;
            double[] row = matrix.getRow(i);
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                if (row[j] != 0.0) {
                    degree++;
                }
            }
            degreeMatrix.setEntry(i, i, degree);
        }
        return degreeMatrix;
    }

    public static RealMatrix getInverseDegreeMatrix(RealMatrix matrix) {
        RealMatrix inverseDegreeMatrix = new Array2DRowRealMatrix(matrix.getRowDimension(), matrix.getColumnDimension());
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            int degree = 0;
            double[] row = matrix.getRow(i);
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                if (row[j] != 0.0) {
                    degree++;
                }
            }
            if (degree != 0) {
                inverseDegreeMatrix.setEntry(i, i, 1 / degree);
            }
        }
        return inverseDegreeMatrix;
    }

    public static RealMatrix invertMatrix(RealMatrix matrix) {
        return new LUDecomposition(matrix).getSolver().getInverse();
    }

    public static void printMatrix(String name, RealMatrix matrix) {
        System.out.println("Matrix: " + name);
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            System.out.println(Arrays.toString(matrix.getRow(i)));
        }
    }

    public static List<List<Integer>> get2Clusters(RealMatrix matrix) {
        List<List<Integer>> clusters = new ArrayList<>(2);
        clusters.add(new ArrayList<>());
        clusters.add(new ArrayList<>());

        EigenDecomposition ed = new EigenDecomposition(matrix);
        System.out.println("eigen values size: " + ed.getRealEigenvalues().length);
        System.out.println(Arrays.toString(ed.getRealEigenvalues()));
        RealVector eigenVector = ed.getEigenvector(ed.getRealEigenvalues().length - 2);
        System.out.println("eigenvector size: " + eigenVector.getDimension());
        System.out.println(eigenVector.toString());
        double total = 0;
        for (int e = 0; e < eigenVector.toArray().length; e++) {
            total += eigenVector.toArray()[e];
            double sign = Math.signum(eigenVector.toArray()[e]);
            if (sign < 0) {
                clusters.get(0).add(e);
            } else {
                clusters.get(1).add(e);
            }
        }
        System.out.println("total= " + total);
        return clusters;
    }

    public static List<List<Integer>> get2ClustersNonZero(RealMatrix matrix) {
        List<List<Integer>> clusters = new ArrayList<>(2);
        clusters.add(new ArrayList<>());
        clusters.add(new ArrayList<>());

        EigenDecomposition ed = new EigenDecomposition(matrix);
        System.out.println("eigen values size: " + ed.getRealEigenvalues().length);
        System.out.println(Arrays.toString(ed.getRealEigenvalues()));

        int find = 2;
        int chosen = 0;
        for (int i = ed.getRealEigenvalues().length - 1; i >= 0; i--) {
            if (ed.getRealEigenvalues()[i] != 0) {
                find--;
                if (find == 0) {
                    chosen = i;
                    break;
                }
            }
        }

        RealVector eigenVector = ed.getEigenvector(chosen);
        System.out.println("eigenvector size: " + eigenVector.getDimension());
        System.out.println(eigenVector.toString());
        double total = 0;
        for (int e = 0; e < eigenVector.toArray().length; e++) {
            total += eigenVector.toArray()[e];
            double sign = Math.signum(eigenVector.toArray()[e]);
            if (sign < 0) {
                clusters.get(0).add(e);
            } else {
                clusters.get(1).add(e);
            }
        }
        System.out.println("total= " + total);
        return clusters;
    }

    public static int fitsIn(int x, List<List<Integer>> clusters) {
        for (int i = 0; i < clusters.size(); i++) {
            for (Integer n : clusters.get(i)) {
                if (n == x) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static RealMatrix directProductGraph(RealMatrix A, RealMatrix B) {
        final int m = A.getRowDimension();
        final int n = A.getColumnDimension();
        final int p = B.getRowDimension();
        final int q = B.getColumnDimension();

        RealMatrix out = new Array2DRowRealMatrix(m * p, n * q);
        product(A, B, out);
        return out;
    }

    public static void product(RealMatrix A, RealMatrix B, RealMatrix out) {
        final int m = A.getRowDimension();
        final int n = A.getColumnDimension();
        final int p = B.getRowDimension();
        final int q = B.getColumnDimension();

        if (out == null || out.getRowDimension() != m * p || out.getColumnDimension() != n * q) {
            throw new RuntimeException("Wrong dimensions in Kronecker product");
        }

        for (int i = 0; i < m; i++) {
            final int iOffset = i * p;
            for (int j = 0; j < n; j++) {
                final int jOffset = j * q;
                final double aij = A.getData()[i][j];

                for (int k = 0; k < p; k++) {
                    for (int l = 0; l < q; l++) {
                        out.setEntry(iOffset + k, jOffset + l, aij * B.getData()[k][l]);
                    }
                }

            }
        }
    }

    public static int sizeOfMatrix(RealMatrix m) {
        int size = 0;
        for (int i = 0; i < m.getRowDimension(); i++) {
            for (int j = 0; j < m.getColumnDimension(); j++) {
                if (i != j && m.getEntry(i, j) != 0) {
                    size++;                    
                }
            }
        }
        return size;
    }
}
