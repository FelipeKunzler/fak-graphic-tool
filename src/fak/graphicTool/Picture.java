package fak.graphicTool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Picture {
	
	private BufferedImage bufferedImage;
	private BufferedImage originalBufferedImage;
	
	private int height;
	private int width;
	
	private int meanUpperHalf;
	private int median;
	private int medianLowerHalf;
	private int mean;
	private int mode;
	private int variance;
	private int histogramValues[];
	
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	
	public int getMeanUpperHalf() {
		return this.meanUpperHalf;
	}
	
	public int getMedian() {
		return this.median;
	}
	
	public int getMedianLowerHalf() {
		return this.medianLowerHalf;
	}
	
	public int getMean() {
		return this.mean;
	}

	public int getMode() {
		return this.mode;
	}
	
	public int getVariance() {
		return this.variance;
	}
	
	public int[] getHistogramValues(){
		return this.histogramValues;
	}
	
	public ImageIcon getStretchedImage(int viewWidth, int viewHeight){
		
		return new ImageIcon(bufferedImage.getScaledInstance(viewWidth, viewHeight, Image.SCALE_SMOOTH));
	}
	
	public Picture (String path){
		try {
			this.originalBufferedImage = ImageIO.read(new File(path));
			
			this.bufferedImage = Picture.copyImage(this.originalBufferedImage);
			initialize();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Picture (BufferedImage bufferedImage){
		this.originalBufferedImage = bufferedImage;
		
		this.bufferedImage = Picture.copyImage(this.originalBufferedImage);
		initialize();
	}
	
	public static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
	
	public void restoreOriginalImage(){
		
		this.bufferedImage = Picture.copyImage(this.originalBufferedImage);
		initialize();
	}
	
	/*
	 * Modify the picture according to the given exercise.
	 * @param exercise char corresponding to a specific exercise; 'a', 'b', 'c', 'd' or 'e' only.
	 */
	public void modifyImage(char exercise){
		for (int y = 0; y < this.height; y++)
		{
		    for (int x = 0; x < this.width; x++)
		    {		    	
		        Color c = new Color(this.bufferedImage.getRGB(x, y));
		        int grayScale = (int) (((c.getRed() + c.getGreen() + c.getBlue()) / 3) + 0.5);
		        
		        int newValue = 0;
		        
		        switch (exercise) {
					case 'a':
						newValue = modifyPixelExerciseA(grayScale);
						break;
					case 'b':
						newValue = modifyPixelExerciseB(grayScale);
						break;
					case 'c':
						newValue = modifyPixelExerciseC(grayScale);
						break;
					case 'd':
						newValue = modifyPixelExerciseD(grayScale);
						break;
					case 'e':
						newValue = modifyPixelExerciseE(grayScale);
						break;
				}
		        
		        if (newValue != grayScale){
	        		this.bufferedImage.setRGB(x, y, newValue);
	        	} 
		    }
		}
		
		// Update all the contents and statistics.
		this.initialize();
	}
	
	/*
	 * Initializes the contents of the class.
	 */
	private void initialize(){
		
		this.height = this.bufferedImage.getHeight();
		this.width = this.bufferedImage.getWidth();
		
		this.histogramValues = this.calculateHistogramValues();
		this.mean = this.calculateMean();
		this.meanUpperHalf = this.calculateMeanHalfUpper();
		this.median = this.calculateMedian();
		this.medianLowerHalf = this.calculateMedianLowerHalf();
		this.mode = this.calculateMode();
		this.variance = this.calculateVariance();
	}
	
	/*
	 * Modify the picture according to the following:
	 * "a)	Valores maiores ou iguais a média de toda a imagem recebem preto."
	 * Pixels' grayscale that are greater or equal than the mean of the whole image changes to black.
	 * 
	 * @param grayScale value of the pixel.
	 * @return new value of the pixel.
	 */
	private int modifyPixelExerciseA(int grayScale){
		if (grayScale >= this.mean){
        	return Color.BLACK.getRGB();
        }
		
		return grayScale;
	}
	
	/*
	 * Modify the picture according to the following:
	 * "b)	Valores maiores ou iguais a moda de toda a imagem recebem 150."
	 * Pixels' grayscale that are greater or equal than the mode of the whole image changes to 150.
	 * 
	 * @param grayScale value of the pixel.
	 * @return new value of the pixel.
	 */
	private int modifyPixelExerciseB(int grayScale){
		if (grayScale >= this.mode){
        	return (int) Math.pow(150, 3)/3;
        }
		
		return grayScale;
	}
	
	/*
	 * Modify the picture according to the following:
	 * "c)	Valores maiores ou iguais a mediana de toda a imagem recebem branco."
	 * Pixels' grayscale that are greater or equal than the median of the whole image changes to white.
	 * 
	 * @param grayScale value of the pixel.
	 * @return new value of the pixel.
	 */
	private int modifyPixelExerciseC(int grayScale){
		if (grayScale >= this.median){
        	return Color.WHITE.getRGB();
        }
		
		return grayScale;
	}
	
	/*
	 * Modify the picture according to the following:
	 * "d)	Valores menores que a média de toda a imagem recebem 100."
	 * Pixels' grayscale that are less than the mean of the whole image changes to 100.
	 * 
	 * @param grayScale value of the pixel.
	 * @return new value of the pixel.
	 */
	private int modifyPixelExerciseD(int grayScale){
		if (grayScale >= this.mean){
        	return (int) Math.pow(100, 3)/3;
        }
		
		return grayScale;
	}
	
	/*
	 * Modify the picture according to the following:
	 * "e)	Valores maiores que a mediana de toda a imagem recebem 255 e menores que a média recebem 0."
	 * Pixels' grayscale that are greater than the median of the whole image changes to 100, if less than the mean, changes to 0."
	 * 
	 * @param grayScale value of the pixel.
	 * @return new value of the pixel.
	 */
	private int modifyPixelExerciseE(int grayScale){
		if (grayScale > this.median){
        	return (int) Math.pow(255, 3)/3;
        }
		else if (grayScale < this.mean){
			return 0;
		}
		
		return grayScale;
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
	 * Calculates the median color of the picture.
	 */
	private int calculateMedian(boolean onlyLowerHalf){
		
		int consideredY = onlyLowerHalf ? this.height/2 : 0;
		
		List<Float> grayScaleVector = new ArrayList<Float>();	
		for (int y = consideredY; y < this.height; y++)
		{
		    for (int x = 0; x < this.width; x++)
		    {		    	
		        Color c = new Color(this.bufferedImage.getRGB(x, y));
		        grayScaleVector.add((float) ((c.getRed() + c.getGreen() + c.getBlue()) / 3));
		    }
		}
		
		Collections.sort(grayScaleVector);
		
		int lenght = grayScaleVector.size();
		if (lenght % 2 == 0){
		    return (int) (((grayScaleVector.get(lenght/2) + grayScaleVector.get(lenght/2 - 1)) / 2) + 0.5);
		}
		else{
			return (int) (grayScaleVector.get(lenght/2) + 0.5);
		}
	}
	
	/*
	 * Calculates the median color of the picture.
	 */
	private int calculateMedian(){
		return this.calculateMedian(false);
	}
	
	/*
	 * Calculates the median color at the bottom half the picture
	 */
	private int calculateMedianLowerHalf(){
		return this.calculateMedian(true);
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
		        int grayScale = (int) (((c.getRed() + c.getGreen() + c.getBlue()) / 3) + 0.5);
		        
		        bucketVariance += Math.pow((grayScale - this.mean), 2);
		    }
		}
		
		return bucketVariance / (this.height * this.width);
	}
	
	/*
	 * Calculates the histogram values.
	 */
	private int[] calculateHistogramValues(){
		
		int[] histogramValues = new int[256];		
		for (int y = 0; y < this.height; y++)
		{
		    for (int x = 0; x < this.width; x++)
		    {	
		        Color c = new Color(this.bufferedImage.getRGB(x, y));
		        int grayScale = (int) (((c.getRed() + c.getGreen() + c.getBlue()) / 3) + 0.5);
		        histogramValues[grayScale]++;
		    }
		}
				
		return histogramValues;
	}
	
	/*
	 * Calculates the mode of the picture
	 */
	private int calculateMode(){
		
		int mode = 0;
        for (int i = 0; i < this.histogramValues.length; i++) {
        	if (histogramValues[i] > histogramValues[mode]){
        		mode = i;
        	}
		}
        
        return mode;
	}
	
}
