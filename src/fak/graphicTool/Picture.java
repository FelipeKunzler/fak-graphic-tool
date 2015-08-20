package fak.graphicTool;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Picture {
	
	private BufferedImage bufferedImage;
	private int meanUpperHalf;
	private int medianLowerHalf;
	
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
		
	}
	
	public Picture (String path){
		try {
			this.setBufferedImage(ImageIO.read(new File(path)));
			this.calculateMean();
			this.calculateMedian();
			 
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
	 * Calculates the average color at the upper half of the picture
	 */
	private void calculateMean(){
		
		int height = this.bufferedImage.getHeight();
		int width = this.bufferedImage.getWidth();
		long pixelCount = 0;
		float bucket = 0;
		
		for (int y = 0; y < height/2; y++)
		{
		    for (int x = 0; x < width; x++)
		    {		    	
		    	pixelCount++;
		        Color c = new Color(this.bufferedImage.getRGB(x, y));
		        bucket += c.getRed() + c.getGreen() + c.getBlue();
		    }
		}
		
		this.meanUpperHalf = (int) (((bucket/3) / pixelCount) + 0.5);
		
		System.out.println(MessageFormat.format("Mean upper half: {0}", meanUpperHalf));
	}
	
	/*
	 * Calculates the median color at the lower half of the picture
	 */
	private void calculateMedian(){
		
		int height = this.bufferedImage.getHeight();
		int width = this.bufferedImage.getWidth();
		
		List<Float> rgbVector = new ArrayList<Float>();	
		for (int y = height/2; y < height; y++)
		{
		    for (int x = 0; x < width; x++)
		    {		    	
		        Color c = new Color(this.bufferedImage.getRGB(x, y));
	        	rgbVector.add((float) ((c.getRed() + c.getGreen() + c.getBlue()) / 3));
		    }
		}
		
		Collections.sort(rgbVector);
		
		int lenght = rgbVector.size();
		if (lenght % 2 == 0){
		    this.medianLowerHalf = (int) (((rgbVector.get(lenght/2) + rgbVector.get(lenght/2 - 1)) / 2) + 0.5);
		}
		else{
			this.medianLowerHalf = (int) (rgbVector.get(lenght/2) + 0.5);
		}
		
		System.out.println(MessageFormat.format("Median lower half: {0}", this.medianLowerHalf));
	}
	
}
