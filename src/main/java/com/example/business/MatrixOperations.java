package com.example.business;

import Jama.Matrix;


public class MatrixOperations {
		
	 /**
     * Computes the dot product of two vectors.  Both must be either row or column vectors. 
     * 
     * @param m1 
     * @param m2 
     * @return the dot product of the two vectors. 
     */ 
    public static double dotproduct(Matrix m1, Matrix m2) { 
 
        Matrix m1colVector = makeColumnVector(m1); 
        Matrix m2colVector = makeColumnVector(m2); 
 
        int n = m1colVector.getRowDimension(); 
        if (n != m2colVector.getRowDimension()) { 
            throw new IllegalArgumentException("m1 and m2 must have the same number of elements."); 
        } 
 
        double scalarProduct = 0; 
        for (int row = 0; row < n; row++) { 
            scalarProduct += m1colVector.get(row, 0) * m2colVector.get(row, 0); 
        } 
 
        return scalarProduct;  
    } 
	
	/**
     * Transforms the given matrix into a column vector, that is, a matrix with one column. 
     * The matrix must be a vector (row or column) to begin with. 
     * 
     * @param m 
     * @return <code>m.transpose()</code> if m is a row vector, 
     *         <code>m</code> if m is a column vector. 
     * @throws IllegalArgumentException if m is not a row vector or a column vector. 
     */ 
    public static Matrix makeColumnVector(Matrix m) { 
        if (isColumnVector(m)) 
            return m; 
        else if (isRowVector(m)) 
            return m.transpose(); 
        else 
            throw new IllegalArgumentException("m is not a vector."); 
    } 
    
    /**
     * Determines if a given matrix is a row vector, that is, it has only one row. 
     * 
     * @param m the matrix. 
     * @return whether the given matrix is a row vector (whether it has only one row). 
     */ 
    public static boolean isRowVector(Matrix m) { 
        return m.getRowDimension() == 1; 
    } 
    
    /**
     * Determines if a given matrix is a column vector, that is, it has only one column. 
     * 
     * @param m the matrix. 
     * @return whether the given matrix is a column vector (whether it has only one column). 
     */ 
    public static boolean isColumnVector(Matrix m) { 
        return m.getColumnDimension() == 1; 
    } 
}
