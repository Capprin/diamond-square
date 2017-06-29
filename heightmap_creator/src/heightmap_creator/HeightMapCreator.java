package heightmap_creator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author Juice
 * 
 */
public class HeightMapCreator {
	
	//Stores image
	private BufferedImage image;
	
	/**
	 * File extension for PNG
	 */
	public static final String TYPE_PNG = "png";
	
	/**
	 * File extension for JPG
	 */
	public static final String TYPE_JPG = "jpg";
	
	/**
	 * File extension for GIF
	 */
	public static final String TYPE_GIF = "gif";
	
	
	/**
	 * Constructs a new HeightMapCreator
	 * @param heightArray The 2D array of heights for this heightmap
	 * @param maxHeight The int used as the maximum height in the array
	 */
	public HeightMapCreator(int[][] heightArray, int maxHeight){
		//Set up image as greyscale of array size
		image = new BufferedImage(heightArray[0].length, heightArray.length, BufferedImage.TYPE_USHORT_GRAY);
		
		//Fill up each pixel
		for (int r = 0; r < heightArray.length; r++){
			for (int c = 0; c < heightArray[0].length; c++){
				//Calculates RGB value (scaled based on maxHeight) for each provided point
				int rgbColor = (int)(heightArray[r][c] / ((double)maxHeight) * 255);
				image.setRGB(c,r,new Color(rgbColor,rgbColor,rgbColor).getRGB()); //Sets image to RGB color stored
			}
		}
	}
	
	/**
	 * Does file writing of heightmap
	 * @param fileName The name of the file to write to (without an extension)
	 * @param fileType The type (file extension) of the file
	 */
	public void writeToFile(String fileName, String fileType){
		//Open outFile
		File outFile = new File(fileName + "." + fileType);
		
		try{
			//Write image to file
			ImageIO.write(image, fileType, outFile);
		}
		catch(IOException e){
			//Print any errors
			System.err.println("File output failed:");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Test method
	 */
	public static void main(String[] args){
		System.out.println("------------------");
		System.out.println("Height Map Creator");
		System.out.println("------------------");
		
		System.out.println("\nGenerating heightmap...");
		
		//Generate a gradient height map
		int[][] heightMap = new int[128][128];
		for (int r = 0; r < heightMap.length; r++){
			for (int c = 0; c < heightMap[0].length; c++){
				heightMap[r][c] = 128-r;
			}
		}
		
		System.out.println("Generating test.png...");
		
		//Use HeightMapCreator to make the image
		HeightMapCreator heightMapImage = new HeightMapCreator(heightMap,128);
		heightMapImage.writeToFile("test",HeightMapCreator.TYPE_PNG);
		
		System.out.println("Done!");
	}
}
