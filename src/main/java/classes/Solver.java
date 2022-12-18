package classes;

import interfaces.solverInterface;

/**
 *
 * @author Camila & Erik
 */
public class Solver implements solverInterface {
    
    private double[][] incompleteMatrix;
    private double[] coefficientMatrix;
    private int rows;
    private int columns;
        
        public Solver(double[][] a, double[] b) {
		if (a == null || b == null)
			throw new NullPointerException();
		if (a.length != b.length)
			throw new IllegalArgumentException();
		incompleteMatrix = a;
		coefficientMatrix = b;
		rows = a.length;
		columns = a[0].length;
	}

    @Override
    public boolean hasSolutions() {
        return getIncompleteRank() == getCompleteRank();
    }

    @Override
    public int getSolutionsNumber() {
        if (!hasSolutions())
                return 0;
        if (getIncompleteRank() == columns)
                return 1;
        return Integer.MAX_VALUE;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public double[][] getIncompleteMatrix() {
        double[][] incompleteMatrix = new double[rows][columns];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				incompleteMatrix[i][j] = this.incompleteMatrix[i][j];
        return incompleteMatrix;
    }

    @Override
    public double[][] getCompleteMatrix() {
        double[][] completeMatrix = new double[rows][columns + 1];
        for (int i = 0; i < rows; i++)
                for (int j = 0; j < columns ; j++)
                        completeMatrix[i][j] = incompleteMatrix[i][j];
        for (int i = 0; i < rows; i++)
                completeMatrix[i][columns] = coefficientMatrix[i];
        return completeMatrix;
    }
    
    public double[] getCoefficientMatrix() {
		double[] coefficientMatrix = new double[this.coefficientMatrix.length];
		for (int i = 0; i < coefficientMatrix.length; i++)
			coefficientMatrix[i] = this.coefficientMatrix[i];
		return coefficientMatrix;
    }

    @Override
    public double[] solveSystem(double[][] A, double[] b) {
        double[] x = initialize(new double[A[0].length]);
        for (int k = 0; k < 1000; k++) {
                for (int i = 0; i < A.length; i++) {
                        double x0 = 0;
                        for (int j = 0; j < A.length; j++) 
                                if (i != j) 
                                        x0 += A[i][j] * x[j];
                        x[i] = (b[i] - x0)/A[i][i];
                }
        }
        return x;
    }
    
    private double[] initialize(double[] output) {
		for (int i = 0; i < output.length; i++)
			output[i] = 0;
		return output;
	}

    @Override
    public int getIncompleteRank() {
       double[][] reduced = gaussElimination(getIncompleteMatrix());	
		int rank = 0;
		for (int i = 0; i < reduced.length; i++)
			for (int j = 0; j < reduced[0].length; j++)
				if (reduced[i][j] != 0) {
					rank++;
					break;
				}
	return rank;
    }

    public static double[][] gaussElimination(double[][] A){
		for (int k = 0; k < A[0].length; k++) {
			finalReduce(A);
			for (int i = k + 1; i < A.length; i++){
				double aik = A[i][k]/A[k][k];
				A[i][k] = 0;
				for (int j = k + 1; j < A[0].length; j++) {
					A[i][j] = A[i][j] - (aik * A[k][j]);
				}
				
			}
		}	
		finalReduce(A);
		return A;
    }
    
    private static void finalReduce(double[][] A) {
		for (int i = 0; i < A.length - 1; i++)
			for (int j = 0; j < A[0].length; j++)
				if (A[i][j] != 0)
					break;
				else if (j == A[0].length - 1) 
					for (int k = 0; k < A[0].length; k++) {
						A[i][k] = A[i+1][k];
						A[i+1][k] = 0;
					}				
    }
    
    @Override
    public int getCompleteRank() {
        double[][] reduced = gaussElimination(getCompleteMatrix());
		int rank = 0;
		for (int i = 0; i < reduced.length; i++)
			for (int j = 0; j < reduced[0].length; j++)
				if (reduced[i][j] != 0d) {
					rank++;
					break;
				}
	return rank;
    }

    @Override
    public double[] solveSystem() {
        	if (!hasSolutions() || getSolutionsNumber() == Integer.MAX_VALUE)
			throw new IllegalStateException("Impossible system or infinite solutions detected");
		
		double[][] A = gaussElimination(getCompleteMatrix());
		double[] x = new double[getCompleteRank()];
		x = initialize(x);
		for (int i = A.length - 1; i >= 0; i--){
			double s = A[i][A[0].length - 1];
			for (int j = i + 1; j < A[0].length; j++)
				if (j < x.length)
					s -= A[i][j]*x[j];
			if (i < x.length)
				x[i] = s/A[i][i];
		}
		
		return x;
    }
    
}
