package org.kryptonmlt.graph.multigraph.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.kryptonmlt.graph.multigraph.pojos.Student;
import org.kryptonmlt.graph.multigraph.pojos.StudentPoint;

/**
 *
 * @author kurt
 */
public class RealMatrixUtils {

    private static final int MAXIMUM_ITERATIONS = 100000;

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
        try {
            System.out.println("Matrix: " + name);
            PrintWriter writer = new PrintWriter(name + ".txt", "UTF-8");
            for (int i = 0; i < matrix.getRowDimension(); i++) {
                //System.out.println(Arrays.toString(matrix.getRow(i)));
                writer.println(Arrays.toString(matrix.getRow(i)));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<List<Integer>> getClustersNonZero(RealMatrix matrix, int k, boolean useZero) {

        if (k == 0) {
            throw new IllegalArgumentException("K cannot be equal to zero");
        }

        List<List<Integer>> clusters = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            clusters.add(new ArrayList<>());
        }

        EigenDecomposition ed = new EigenDecomposition(matrix);
        System.out.println("eigen values size: " + ed.getRealEigenvalues().length);
        System.out.println(Arrays.toString(ed.getRealEigenvalues()));

        //int find = k;
        int find = 2;
        int chosen = 0;
        if (useZero) {
            chosen = ed.getRealEigenvalues().length - k;
        } else {
            for (int i = ed.getRealEigenvalues().length - 1; i >= 0; i--) {
                if (ed.getRealEigenvalues()[i] != 0) {
                    find--;
                    if (find == 0) {
                        chosen = i;
                        break;
                    }
                }
            }
        }

        RealVector eigenVector = ed.getEigenvector(chosen);
        List<Clusterable> values = new ArrayList<>();
        System.out.println("eigenvector size: " + eigenVector.getDimension());
        System.out.println(eigenVector.toString());
        double total = 0;
        for (int e = 0; e < eigenVector.toArray().length; e++) {
            total += eigenVector.toArray()[e];
            double[] point = {eigenVector.toArray()[e]};
            StudentPoint p = new StudentPoint(e, point);
            values.add(p);
        }
        KMeansPlusPlusClusterer kmeans = new KMeansPlusPlusClusterer(k, MAXIMUM_ITERATIONS);
        List<CentroidCluster> result = kmeans.cluster(values);
        for (int c = 0; c < result.size(); c++) {
            for (Object sp : result.get(c).getPoints()) {
                clusters.get(c).add(((StudentPoint) sp).getId());
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

    /**
     * Counts edges
     * @param m
     * @return 
     */
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

    public static RealMatrix joinStudentMatrices(List<Student> students) {
        List<RealMatrix> matrices = new ArrayList<>();
        students.forEach((s) -> {
            matrices.add(s.getStudentGraph());
        });
        return RealMatrixUtils.joinMatrices(matrices);
    }

    public static RealMatrix joinMatrices(List<RealMatrix> matrices) {
        RealMatrix realMatrix = new Array2DRowRealMatrix(matrices.get(0).getRowDimension() * matrices.size(), matrices.get(0).getColumnDimension() * matrices.size());
        for (int m = 0; m < matrices.size(); m++) {
            RealMatrix matrix = matrices.get(m);
            realMatrix.setSubMatrix(matrix.getData(), m * matrix.getRowDimension(), m * matrix.getColumnDimension());
            //connect students
            for (int i = 0; i < matrices.size(); i++) {
                realMatrix.setEntry(m * matrix.getRowDimension(), i * matrix.getColumnDimension(), 0);
            }
        }
        return realMatrix;
    }
}
