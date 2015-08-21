package fak.graphicTool;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Picture {
	
	private BufferedImage bufferedImage;
	
	private int height;
	private int width;
	
	private int meanUpperHalf;
	private int medianLowerHalf;
	private int mean;
	private int mode;
	private int variance;
	
	
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
		
	}
	
	public Picture (String path){
		try {
			
			this.bufferedImage = ImageIO.read(new File(path));
			
			this.height = this.bufferedImage.getHeight();
			this.width = this.bufferedImage.getWidth();
			
			this.mean = this.calculateMean();
			this.meanUpperHalf = this.calculateMeanHalfUpper();
			this.medianLowerHalf = this.calculateMedianLowerHalf();
			this.mode = this.calculateMode();
			
			this.variance = this.calculateVariance();
			 
			System.out.println(MessageFormat.format("Mean upper half: {0}", this.meanUpperHalf));
			System.out.println(MessageFormat.format("Median lower half: {0}", this.medianLowerHalf));
			System.out.println(MessageFormat.format("Mode: {0}", this.mode));
			System.out.println(MessageFormat.format("Mean: {0}", this.mean));
			System.out.println(MessageFormat.format("Variance: {0}", this.variance));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Picture (BufferedImage bufferedImage){
		//TODO ..
	}
	
	public ImageIcon getStretchedImage(int width, int height){
		
		return new ImageIcon(bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}
	
	/*
	 * Calculates the mean color of the picture.
	 * @param onlyUpperHalf set true if to calculate the mean of only the upper half of the image.
	 */
	private int calculateMean(boolean onlyUpperHalf){
		
		long pixelCount = 0;
		float bucket = 0;
		
		int consideredHeight = onlyUpperHalf ? this.height/2 : this.height;
		
		for (int y = 0; y < consideredHeight; y++)
		{
		    for (int x = 0; x < this.width; x++)
		    {		    	
		    	pixelCount++;
		        Color c = new Color(this.bufferedImage.getRGB(x, y));
		        bucket += c.getRed() + c.getGreen() + c.getBlue();
		    }
		}
		
		return (int) (((bucket/3) / pixelCount) + 0.5);
	}
	
	/*
	 * Calculates the mean color of the picture.
	 */
	private int calculateMean(){
		return this.calculateMean(false);
	}
	
	/*
	 * Calculates the mean color at the upper half of the picture.
	 */
	private int calculateMeanHalfUpper(){
		return this.calculateMean(true);
	}
	
	/*
	 * Calculates the median color at the bottom half the picture
	 */
	private int calculateMedianLowerHalf(){
		
		List<Float> rgbVector = new ArrayList<Float>();	
		for (int y = this.height/2; y < this.height; y++)
		{
		    for (int x = 0; x < this.width; x++)
		    {		    	
		        Color c = new Color(this.bufferedImage.getRGB(x, y));
	        	rgbVector.add((float) ((c.getRed() + c.getGreen() + c.getBlue()) / 3));
		    }
		}
		
		Collections.sort(rgbVector);
		
		int lenght = rgbVector.size();
		if (lenght % 2 == 0){
		    return (int) (((rgbVector.get(lenght/2) + rgbVector.get(lenght/2 - 1)) / 2) + 0.5);
		}
		else{
			return (int) (rgbVector.get(lenght/2) + 0.5);
		}
	}
	
	/*
	 * Calculates the mode of the picture
	 */
	private int calculateMode(){
		
		int[] counter = new int[256];		
		int higher = counter.length - 1;
		
		for (int y = 0; y < this.height; y++)
		{
		    for (int x = 0; x < this.width; x++)
		    {	
		        Color c = new Color(this.bufferedImage.getRGB(x, y));
		        int averageRGB = (int) (((c.getRed() + c.getGreen() + c.getBlue()) / 3) + 0.5);
		        counter[averageRGB]++;
		        
		        if (counter[averageRGB] > counter[higher]){
					higher = averageRGB;
				}
		    }
		}
		
		return higher;
	}
	
	/*
	 * Calculates the variance of the picture
	 */
	private int calculateVariance(){
		
		int bucketVariance = 0;
		
		for (int y = 0; y < this.height; y++)
		{
		    for (int x = 0; x < this.width; x++)
		    {	
		        Color c = new Color(this.bufferedImage.getRGB(x, y));
		        int averageRGB = (int) (((c.getRed() + c.getGreen() + c.getBlue()) / 3) + 0.5);
		        
		        bucketVariance += Math.pow((averageRGB - this.mean), 2);
		    }
		}
		
		return bucketVariance / (this.height * this.width);
	}
	
}
