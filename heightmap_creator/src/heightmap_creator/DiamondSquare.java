package heightmap_creator;

import java.util.Scanner;

public class DiamondSquare {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		System.out.println("------------------------");
		System.out.println("Diamond Square Generator");
		System.out.println("------------------------\n");
		
		
		System.out.print("Enter an image size: ");
		int size = input.nextInt();
		input.close();

		if (size % 2 == 0) size++;
		
		
		System.out.println("Generating Heightmap...");
		
		int[][] heightMap = new int[size][size];
		heightMap[0][0] = (int) (Math.random() * size);
		heightMap[0][size-1] = (int) (Math.random() * size);
		heightMap[size-1][0] = (int) (Math.random() * size);
		heightMap[size-1][size-1] = (int) (Math.random() * size);
		
		int center = size/2;
		doDiamondSquare(heightMap, center, center, center);
		
		System.out.println("Creating image...");
		
		int max = 0;
		for (int r = 0; r < size; r++){
			for (int c = 0; c < size; c++){
				if (heightMap[r][c] > max) max = heightMap[r][c];
			}
		}
		
		HeightMapCreator heightMapCreator = new HeightMapCreator(heightMap, max);
		heightMapCreator.writeToFile("terrain", HeightMapCreator.TYPE_PNG);
		
		System.out.println("Done!");

	}
	
	
	public static void doDiamondSquare(int heightMap[][], int cRow, int cCol, int size){
		if (heightMap[cRow][cCol] != 0) return;
		
		calculateMidpoint(heightMap, cRow, cCol, size);
		calculateEdges(heightMap,cRow,cCol,size);
		
		int halfSize = size/2;
		doDiamondSquare(heightMap, cRow - halfSize, cCol - halfSize, halfSize);
		doDiamondSquare(heightMap, cRow + halfSize, cCol - halfSize, halfSize);
		doDiamondSquare(heightMap, cRow - halfSize, cCol + halfSize, halfSize);
		doDiamondSquare(heightMap, cRow + halfSize, cCol + halfSize, halfSize);
	}
	
	//TODO Implement using a for loop and trig functions instead
	public static void calculateMidpoint(int heightMap[][], int cRow, int cCol, int size){
		int minRow = cRow - size;
		int maxRow = cRow + size;
		
		int minCol = cCol - size;
		int maxCol = cCol + size;
		
		//Get average of 4 surrounding points
		float avg = 0;
		avg += heightMap[minRow][minCol];
		avg += heightMap[minRow][maxCol];
		avg += heightMap[maxRow][minCol];
		avg += heightMap[maxRow][maxCol];
		avg /= 4.;
		
		//Set center to average plus multiplier
		heightMap[cRow][cCol] = (int) (avg + Math.random()*size);
	}
	
	public static void calculateEdges(int heightMap[][], int cRow, int cCol, int size){
		int minRow = cRow - size;
		int maxRow = cRow + size;
		
		int minCol = cCol - size;
		int maxCol = cCol + size;
		
		
		//Average 4 surrounding points for all diamond centers
		//TODO Find a way to make this without such repetitive code
		int outVal = 0;
		float div = 3.0f;
		if (minRow - size > 0 && heightMap[minRow - size][cCol] != 0){
			outVal = heightMap[minRow-size][cCol];
			div = 4;
		}
		heightMap[minRow][cCol] = (int) (((float)(heightMap[minRow][minCol] + heightMap[minRow][maxCol] + heightMap[cRow][cCol] + outVal))/div);
		
		outVal = 0;
		div = 3.0f;
		if (minCol - size > 0 && heightMap[cRow][minCol - size] != 0){
			outVal = heightMap[cRow][minCol - size];
			div = 4;
		}
		heightMap[cRow][minCol] = (int) (((float)(heightMap[minRow][minCol] + heightMap[maxRow][minCol] + heightMap[cRow][cCol] + outVal))/div);
		
		outVal = 0;
		div = 3.0f;
		if (maxRow + size < heightMap.length && heightMap[maxRow + size][cCol] != 0){
			outVal = heightMap[maxRow+size][cCol];
			div = 4;
		}
		heightMap[maxRow][cCol] = (int) (((float)(heightMap[maxRow][minCol] + heightMap[maxRow][maxCol] + heightMap[cRow][cCol] + outVal))/div);
		
		outVal = 0;
		div = 3.0f;
		if (maxCol + size < heightMap[0].length && heightMap[cRow][maxCol + size] != 0){
			outVal = heightMap[cRow][maxCol + size];
			div = 4;
		}
		heightMap[cRow][maxCol] = (int) (((float)(heightMap[minRow][maxCol] + heightMap[maxRow][maxCol] + heightMap[cRow][cCol] + outVal))/div);
		
		
		//Add random
		heightMap[minRow][cCol] += Math.random()*size;
		heightMap[cRow][minCol] += Math.random()*size;
		heightMap[maxRow][cCol] += Math.random()*size;
		heightMap[cRow][maxCol] += Math.random()*size;
	}
	
	

}
