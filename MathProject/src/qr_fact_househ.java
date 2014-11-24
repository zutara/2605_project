import java.util.Arrays;

import org.apache.commons.math3.linear.*;

public class qr_fact_househ {
    private RealVector v, u;
    private RealMatrix I,A,Q,R,H,An,Hn,Qi,Ri;

    public qr_fact_househ(RealMatrix A) {
        this.A = A;
        An = A;
        H = mathproject.identityMatrix(A.getRowDimension());
        househ(A);
    }
    
    private void househ(RealMatrix A) {
        int inc;
        int index = 0;
        I = mathproject.identityMatrix(A.getRowDimension());
        if (A.isSquare()) {
            inc = A.getColumnDimension() - 1;
        } else {
            inc = A.getColumnDimension();
        }
        for (int top = 0; top < inc; top++) { // first entry of the matrix/vector
            // zero out the stuff above top
            System.out.println("top: " + top);
            System.out.println("index: " + index);
            RealVector subMatrix = A.getColumnVector(top);
            System.out.println("submatrix: " + subMatrix);
            if (index < top) {
                for (int zero = 0; zero < top; zero++) {
                    subMatrix.setEntry(zero, 0);
                }
                index++;
            }
            double magSubMatrix = mathproject.magnitude(subMatrix);
            if (subMatrix.getEntry(top) <= 0) {
                //subMatrixB[0] = subMatrix[0] + magSubMatrix;
                subMatrix.addToEntry(top, magSubMatrix);
            } else {
                subMatrix.addToEntry(top, -magSubMatrix);
            }
            System.out.println("submatrix after addition: " + subMatrix);
            RealVector unit = subMatrix.mapDivide(magSubMatrix);
            int unitLength = unit.getDimension();
            double[][] M = new double[unitLength][unitLength];
            for(int j = 0; j < unitLength; j++) {
                for(int i = 0; i < unitLength; i++) {
                    M[i][j] = unit.getEntry(j);
                }
            }
            RealMatrix unitTrans = new Array2DRowRealMatrix(M);
            RealMatrix HHunit = unitTrans;
            for (int x = 0; x <unitTrans.getRowDimension(); x++) {
                for (int y = 0; y < unitTrans.getColumnDimension(); y++) {
                    HHunit.multiplyEntry(x, y, unit.getEntry(x));
                }
            }
//            Double[][] HHunit = new Double[unitLength][unitLength];
//            for(int a = 0; a < unitLength; a++) {
//               for(int b = 0; a < unitLength; b++) {
//                   HHunit[a][b] = unit.getEntry(0) * getRow(unitTrans);
//               }
//            }
            HHunit = HHunit.scalarMultiply(2);
            Hn = I.subtract(HHunit);
            An = Hn.multiply(An);
            H = H.multiply(Hn);
        }
        Qi = H;
        Ri = An;
        double[][] rparam = new double[A.getColumnDimension()][A.getColumnDimension()];
        for (int rows = 0; rows < A.getColumnDimension(); rows++) {
            for (int cols = 0; cols < A.getColumnDimension(); cols++) {
                rparam[rows][cols] = Ri.getEntry(rows, cols);
            }
        }
        R = new Array2DRowRealMatrix(rparam);
        double[][] qparam = new double[A.getRowDimension()][A.getColumnDimension()];
        for (int rows = 0; rows < A.getRowDimension(); rows++) {
            for (int cols = 0; cols < A.getColumnDimension(); cols++) {
                qparam[rows][cols] = Qi.getEntry(rows, cols);
            }
        }
        Q = new Array2DRowRealMatrix(qparam);
    }
    
    public RealMatrix getQ() {
        return Q;
    }
    
    public RealMatrix getR() {
        return R;
    }
}
