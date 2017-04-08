package com.example.business;
import java.util.Arrays;

import Jama.*;
import Jama.util.*;

public class PredictBean {

	public static void main(String[] args) {
		
		//MultipleRegression mRegression = new MultipleRegression();		
		String csvFile = "C:\\Users\\Nishant\\Desktop\\Fall16\\MachineLearning\\FeaturesMatrix.csv";
		double stepSize = 7e-1;
		double tolerance = 2.5e4;
		//CsvReader reader = new CsvReader(csvFile, ",");
		double[][] featureArray = null;
		//change this
		Matrix featureMatrix = new Matrix(featureArray);		
		Matrix outputPriceMat = getOutputPriceColumn(featureMatrix);
		featureMatrix = stripPriceColumn(featureMatrix, 0);
		featureMatrix = getFeatureMatrixData(featureMatrix);
		Matrix initialWeights = new Matrix(featureMatrix.getColumnDimension(), 1, 1);
		//double[][] init = {{580000}, {250}, {-60000},{60000},{20000},{500000}} 
		//Matrix predictedOutput = mRegression.predictOutput(featureMatrix, weights);
		//predictedOutput.print(predictedOutput.getColumnDimension(), 10);		
		//double[][] predictedWeights = mRegression.perform_Regression_Gradient_Descent(featureMatrix, outputPriceMat, initialWeights, stepSize, tolerance);
				
		double[][] predictedWeights = predictOpenMethod(initialWeights, featureMatrix, outputPriceMat);
		printPredictedPrices(predictedWeights, featureMatrix, outputPriceMat);	
	}
	
	public Matrix predictOutput(Matrix features, Matrix weights){
		return features.times(weights);
	}
	
	public static Matrix stripPriceColumn(Matrix featureMatrix, int columnToBeDeleted){		
		int numRows = featureMatrix.getRowDimension(); 
        int numCols = featureMatrix.getColumnDimension(); 
        Matrix strippedFeatureMat = new Matrix(numRows, numCols - 1); 
        for (int mj = 0, m2j = 0; mj < numCols; mj++) { 
            if (mj == columnToBeDeleted) 
                continue;  // skips incrementing m2j 
            for (int i = 0; i < numRows; i++) { 
                strippedFeatureMat.set(i, m2j, featureMatrix.get(i, mj)); 
            } 
            m2j++; 
        } 
        return strippedFeatureMat; 
	}
	
	public static Matrix getOutputPriceColumn(Matrix featureMatrix){		
		Matrix outputPriceMat = featureMatrix.getMatrix(0, featureMatrix.getRowDimension() - 1, 0 , 0);		
		return outputPriceMat;
	}	
	
	public static Matrix getFeatureMatrixData(Matrix featureMatrix){
		
		double[] constants = new double[featureMatrix.getRowDimension()];
		Arrays.fill(constants, 1);
		Matrix constantMat = new Matrix(constants, constants.length);

		int originalFeatureNumRows = featureMatrix.getRowDimension();
		int originalFeatureNumCols = featureMatrix.getColumnDimension();
		int constantNumRows = constantMat.getRowDimension();
		int constantNumCols = constantMat.getColumnDimension();

		if (originalFeatureNumRows != constantNumRows)
			throw new IllegalArgumentException(
					"Number of rows must be identical to column-append.");

		Matrix finalFeatureMat = new Matrix(originalFeatureNumRows, originalFeatureNumCols + constantNumCols);
		finalFeatureMat.setMatrix(0, constantNumRows - 1, 0, constantNumCols - 1, constantMat);
		finalFeatureMat.setMatrix(0, originalFeatureNumRows - 1, constantNumCols, originalFeatureNumCols + constantNumCols - 1, featureMatrix);
		return finalFeatureMat;
	}
	
	public double featureDerivative(Matrix errors, Matrix feature){
		return 2 * MatrixOperations.dotproduct(errors, feature);
	}
	
	public double[][] perform_Regression_Gradient_Descent(Matrix featureMatrix, Matrix output, Matrix initialWeights, double stepSize, double tolerance){
		
		boolean converged = false;		
		double[][] weights = initialWeights.getArray();
		int count = 0;
		while(converged != true){
			Matrix predictions = predictOutput(featureMatrix, initialWeights);
			Matrix errors = predictions.minus(output);
			double gradient_Sum_Squares = 0;			
			for(int i = 0 ; i < weights.length; i++){
				double derivative = featureDerivative(errors, featureMatrix.getMatrix(0, featureMatrix.getRowDimension() - 1, i, i) );
				gradient_Sum_Squares += (derivative * derivative);
				weights[i][0] = weights[i][0] - (stepSize * derivative);
			}
			
			double gradientMagnitude = Math.sqrt(gradient_Sum_Squares);
			if(gradientMagnitude < tolerance){
				converged = true;
			}
			System.out.println("No of Iteration " + (count++));
		}
		
		return weights;		
	}
	
	public static void printPredictedPrices(double[][] weights, Matrix featureMatrix, Matrix ouMatrix){
		double price = 0; 
		double[][] featureArray = featureMatrix.getArray();
		for(int i = 0; i < featureArray[0].length; i++){
			price += weights[i][0] * featureArray[6][i];
		}
		System.out.println("The predicted Price is : " + price + "Original Price " + ouMatrix.get(6, 0));
	}
	
	public static double[][] predictOpenMethod(Matrix weights, Matrix features, Matrix output){
		Matrix c = features.transpose();
		return ((c.times(features)).inverse()).times(c).times(output).getArray();
	}
}

