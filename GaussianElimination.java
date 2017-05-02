/**
 * Author: Colin Koo
 * Professor: Raheeja
 * Description: This program evaluates matrices.
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class GaussianElimination {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner kb = new Scanner(System.in);
		System.out.println("Wouid you like to enter\n"
				+ "1. coefficients of each equation? or\n"
				+ "2. a filename?");
		String input = kb.nextLine();
		double[][] matrix = {};
		// fix later
		if (input.equals("1")){
			matrix = enterCoeffs(kb);
		}
		else if (input.equals("2")){
			matrix = readFile(kb);
		}
		toString(matrix);
		evaluate(matrix);
		solveSimplified(matrix);
	}
	
/*	public static void main(String[] args) throws FileNotFoundException {
		long start = System.currentTimeMillis();
		long end;
		double[][] matrix = largeMatrix(100);
		toString(matrix);
		evaluate(matrix);
		solveSimplified(matrix);
		end = System.currentTimeMillis();
		System.out.println("time elapse: " + (end-start));
	}*/
	
	/**
	 * Reads the file and creates an ArrayList<String> representing a matrix from the data provided in the file.
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 */
	public static double[][] readFile(Scanner kb) throws FileNotFoundException{
		System.out.println("Enter a file name: ");
		FileReader fr = new FileReader(kb.nextLine());
		BufferedReader br = new BufferedReader(fr);

		ArrayList<String> equations = new ArrayList<String>();

		String line = null;
		try {
			while ((line = br.readLine()) != null){
				equations.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getMatrix(equations);
	}

	/**
	 * Allows the user to enter their own matrix equations, to be stored in an ArrayList<String>
	 * @param kb
	 * @return
	 */
	public static double[][] enterCoeffs(Scanner kb){
		System.out.println("Enter \"done\" to finish entering equations.");
		// store all equations
		ArrayList<String> equations = new ArrayList<String>();
		String input = "";

		while (!(input = kb.nextLine()).equals("done")){
			equations.add(input);
		}
		return getMatrix(equations);
	}
	/**
	 * This method converts an ArrayList<String> containing data of a matrix into a double array of type double.
	 * @param equations
	 * @return
	 */
	public static double[][] getMatrix(ArrayList<String> equations){
		//if "1 1 1 5" - 7 char, 3 space
		// "1 2 3" - 5 char, 2 space, 
		int rows = equations.size();					//(equations.get(0).length()/2);
		int cols = equations.get(0).split(" ").length;	//equations.size();

		double[][] matrix = new double[rows][cols];
		String[] convert = {};

		//construct int double arr matrix.
		for (int i = 0; i < rows; ++i){
			convert = equations.get(i).split(" ");
			for (int j = 0; j < cols; ++j){
				matrix[i][j] = Integer.parseInt(convert[j]);
			}
		}
		return matrix;
	}
	/**
	 * This method evaluates a double array matrix with partial pivoting.
	 * First it creates an array of pivots.
	 * Then it loops and for each column, row, it gets the largest scaled ratios then simplifies the other columns with
	 * respect to it.
	 * @param matrix
	 */
	public static void evaluate(double[][] matrix){
		double[] pivots = new double[matrix.length]; //#rows and pivots
		
		double largestCoeff = -1;
		int rows = pivots.length, cols = matrix[0].length;

		//get pivots[](largest coeffs) of each row
		for (int i = 0; i < rows; ++i){	//for ea row
			for (int j = 0; j < cols-1; ++j){	//for ea col exclude answer
				if (Math.abs(matrix[i][j]) >= largestCoeff){
					pivots[i] = matrix[i][j];
					largestCoeff = matrix[i][j];
				}
			}
		}

		System.out.print("Pivots: ");
		for (int z = 0; z < pivots.length; ++z){
			System.out.print(pivots[z] +", ");
		}
		System.out.println();

		boolean[] pivotedPoints = new boolean[rows];	// default false
		int index = 0;
		double coeff = 0;
		int scaleIndex = 0;

		for (int k = 0; k < cols-1; ++k){
			// get coeff=largestCoeff, index=row index of largest- for each row
			coeff = 0;
			System.out.print("Scaled ratios: ");
			for (int l = 0; l < rows; ++l){
				if ((Math.abs(matrix[l][k]/pivots[l]) > coeff) && (!pivotedPoints[l])){	//get largest row coeff, not already scaled
					coeff = Math.abs(matrix[l][k]/pivots[l]); //coeff = coeff of largest row, not already scaled.
					index = l; 		// index of row w/ largest coeff
					System.out.print((Math.rint((coeff * 100))/100) + " < ");
					//				System.out.println(l + "Coeff: " + coeff + " and index: " + index + " coeff2: " + matrix[l][k] + " and pivot: " + pivots[l]);
				}
			}
			System.out.println();
			pivotedPoints[index] = true;

			double scaling = 0;
			//scale relative to index
			for (int m = 0; m < rows; ++m){
				if (m != index){
					scaling = (-1)*(matrix[m][scaleIndex]/matrix[index][scaleIndex]); //what to mult pivot by
					//				System.out.println("scaling: " + (Math.rint((scaling * 10000))/10000));
					for (int n = 0; n < cols; ++n){	//mult each col of others
						matrix[m][n] = matrix[m][n] + (scaling * matrix[index][n]);
						matrix[m][n] = (Math.rint(matrix[m][n] * 100))/100;
					}
				}
			}
			scaleIndex++;
			toString(matrix);
			System.out.println();
		}
	}
	/**
	 * Gets the solutions from the simplified matrix.
	 * @param matrix
	 */
	public static void solveSimplified(double[][] matrix){
		double[] solutions = new double[matrix.length];
		int rows = matrix.length;
		int cols = matrix[0].length;

		for (int i = 0; i < cols-1; ++i){
			for (int j = 0; j < rows; ++j){
				if (matrix[j][i] != 0){
					solutions[i] = (matrix[j][cols-1])/(matrix[j][i]);
				}
			}
		}
		for (int k = 0; k < solutions.length; ++k){
			System.out.println("X" + k + "=" + solutions[k]); 
		}
	}
	/**
	 * Prints the matrix.
	 * @param matrix
	 */
	public static void toString(double[][] matrix){
		for (int i = 0; i < matrix.length; ++i){
			for (int j = 0; j < matrix[0].length; ++j){
				System.out.print("[" + matrix[i][j] + "] " );
			}
			System.out.println();
		}
	}
	/**
	 * Creates a matrix of given size with random integers ranging between 0 and 10
	 * @param size
	 * @return
	 */
	public static double[][] largeMatrix(int size){
		Random rng = new Random();
		double[][] matrix = new double[size][size];
		for (int i = 0; i < size; ++i){
			for (int j = 0; j < size; ++j){
				matrix[i][j] = rng.nextInt(11);	//random values 0-10
			}
		}
		return matrix;
	}
}
