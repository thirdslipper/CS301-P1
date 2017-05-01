import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;


public class GaussianElimination {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner kb = new Scanner(System.in);
		System.out.println("Wouid you like to enter\n"
				+ "1. coefficients of each equation? or\n"
				+ "2. a filename?");
		String input = kb.nextLine();
		int[][] matrix = {};
		if (input.contains(".")){//containsFile(input)){
			File file = new File(input);
			matrix = readFile(file);
		}
		else{
			matrix = enterCoeffs(kb);
		}
		toString(matrix);
	}

	public static int[][] readFile(File file) throws FileNotFoundException{
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
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

	public static int[][] enterCoeffs(Scanner kb){
		System.out.println("Enter \"done\" to finish entering equations.");
		// store all equations
		ArrayList<String> equations = new ArrayList<String>();
		String input = "";

		while (!(input = kb.nextLine()).equals("done")){
			equations.add(input);
		}
		return getMatrix(equations);
	}

	public static int[][] getMatrix(ArrayList<String> equations){
		//if "1 1 1 5" - 7 char, 3 space, 1 ans floor(7/2) = 3; assume row, cols
		// "1 2 3" - 5 char, 2 space, 
		int rows = equations.size();					//(equations.get(0).length()/2);
		int cols = equations.get(0).split(" ").length;	//equations.size();

		int[][] matrix = new int[rows][cols];
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
	
	public static void toString(int[][] matrix){
		for (int i = 0; i < matrix.length; ++i){
			for (int j = 0; j < matrix[0].length; ++j){
				System.out.print("[" + matrix[i][j] + "] " );
			}
			System.out.println();
		}
	}
}

/*	public static boolean containsFile(String input){
for (int i = 0; i < input.length(); ++i){
	if (input.charAt(i) == '.'){
		return true;
	}
}
return false;
}*/