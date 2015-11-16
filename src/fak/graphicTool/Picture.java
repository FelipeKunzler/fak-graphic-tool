package fak.graphicTool;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Picture {
	
	private BufferedImage bufferedImage;
	private BufferedImage originalBufferedImage;
		
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
	
	public int getHeight() {
		return this.bufferedImage.getHeight();
	}
	
	public int getWidth() {
		return this.bufferedImage.getWidth();
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
		for (int y = 0; y < this.getHeight(); y++)
		{
		    for (int x = 0; x < this.getWidth(); x++)
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
		
		int consideredHeight = onlyUpperHalf ? this.getHeight()/2 : this.getHeight();
		
		for (int y = 0; y < consideredHeight; y++)
		{
		    for (int x = 0; x < this.getWidth(); x++)
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
		
		int consideredY = onlyLowerHalf ? this.getHeight()/2 : 0;
		
		List<Float> grayScaleVector = new ArrayList<Float>();	
		for (int y = consideredY; y < this.getHeight(); y++)
		{
		    for (int x = 0; x < this.getWidth(); x++)
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
		
		for (int y = 0; y < this.getHeight(); y++)
		{
		    for (int x = 0; x < this.getWidth(); x++)
		    {	
		        Color c = new Color(this.bufferedImage.getRGB(x, y));
		        int grayScale = (int) (((c.getRed() + c.getGreen() + c.getBlue()) / 3) + 0.5);
		        
		        bucketVariance += Math.pow((grayScale - this.mean), 2);
		    }
		}
		
		return bucketVariance / (this.getHeight() * this.getWidth());
	}
	
	/*
	 * Calculates the histogram values.
	 */
	private int[] calculateHistogramValues(){
		
		int[] histogramValues = new int[256];		
		for (int y = 0; y < this.getHeight(); y++)
		{
		    for (int x = 0; x < this.getWidth(); x++)
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
	
	/*
	 * Rotates the picture clockwise.
	 */
	public void rotate() {
		rotate(false);
	}
	
	/*
	 * Rotates the picture.
	 * @param counterclockwise True: rotates counterclockwise; False: rotates clockwise.
	 */
	public void rotate(boolean counterclockwise) {
	    
		BufferedImage rotatedImg = new BufferedImage(this.getHeight(), this.getWidth(), BufferedImage.TYPE_INT_ARGB);
	    
	    for(int x = 0; x < rotatedImg.getWidth(); x++){
	        for(int y = 0; y < rotatedImg.getHeight(); y++){
	        	int newY = counterclockwise ? x : rotatedImg.getWidth() - x - 1 ;
	        	int newX = counterclockwise ? rotatedImg.getHeight() - y - 1 : y;
	        	
	            rotatedImg.setRGB(x, y, this.bufferedImage.getRGB(newX, newY));
	        }
	    }
	    
	    this.bufferedImage = rotatedImg;
	    
		this.initialize();
	}
	
	/*
	 * Resize the picture.
	 * @param scale Has to be greater than zero.
	 */
	public void resize(double scale) {
	    
		BufferedImage resizedImg = new BufferedImage((int) (this.getWidth()*scale), (int) (this.getHeight()*scale), BufferedImage.TYPE_INT_ARGB);
	    
	    for(int x = 0; x < resizedImg.getWidth(); x++){
	        for(int y = 0; y < resizedImg.getHeight(); y++){
	        	
	        	int newXcolor = (int) (x / scale);
	        	int newYcolor = (int) (y / scale);
	        		        	
	        	resizedImg.setRGB(x, y, this.bufferedImage.getRGB(newXcolor, newYcolor));
	        }
	    }
	    
	    this.bufferedImage = resizedImg;
	    
		this.initialize();
	}
	
	/*
	 * Mirror the picture horizontally.
	 */
	public void mirror() {
		mirror(false);
	}
	
	/*
	 * Rotates the picture.
	 * @param vertically True: mirror vertically; False: mirror horizontally.
	 */
	public void mirror(boolean vertically) {

		BufferedImage mirrorImg = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
	    
	    for(int x = 0; x < mirrorImg.getWidth(); x++){
	        for(int y = 0; y < mirrorImg.getHeight(); y++){
	        	
	        	int newX = vertically ? x : mirrorImg.getWidth() - x - 1;
	        	int newY = vertically ? mirrorImg.getHeight() - y - 1 : y;
	        		        	
	        	mirrorImg.setRGB(x, y, this.bufferedImage.getRGB(newX, newY));
	        }
	    }
	    
	    this.bufferedImage = mirrorImg;
	    
		this.initialize();
	}
	
	/*
	 * Moves the picture.
	 * @param x Number of pixels to move on the axis X.
	 * @param y Number of pixels to move on the axis Y.
	 */
	public void move(int moveX, int moveY){
		
		BufferedImage mirrorImg = new BufferedImage(this.getWidth() + moveX, 
				this.getHeight() + moveY, BufferedImage.TYPE_INT_ARGB);
	    
	    for(int x = moveX; x < mirrorImg.getWidth(); x++){
	        for(int y = moveY; y < mirrorImg.getHeight(); y++){
	        		        		        	
	        	mirrorImg.setRGB(x, y, this.bufferedImage.getRGB(x - moveX, y - moveY));
	        }
	    }
	    
	    this.bufferedImage = mirrorImg;
	    
		this.initialize();
	}
	
	/*
	 * Applies thresholding to the picture.
	 * @param threshold Number given to be applied.
	 */
	public void thresholding(int threshold){
			    
		BufferedImage thresholdImg = new BufferedImage(this.getWidth(), 
				this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
	    for(int x = 0; x < thresholdImg.getWidth(); x++){
	        for(int y = 0; y < thresholdImg.getHeight(); y++){

	        	Color c = new Color(this.bufferedImage.getRGB(x, y));
	        	int grayScale = (int) (((c.getRed() + c.getGreen() + c.getBlue()) / 3) + 0.5);
	        	
	        	int newRGB;
	        	if (grayScale >= threshold){
	        		newRGB = Color.BLACK.getRGB();
	        	}
	        	else {
	        		newRGB = Color.WHITE.getRGB();
	        	}
	        		
	        	thresholdImg.setRGB(x, y, newRGB);
	        }
	    }
	    
	    this.bufferedImage = thresholdImg;

		this.initialize();
	}
	
	/*
	 * Applies guassian filter that prepares the image to have its borders detected.
	 */
	private void gaussianFilter(){
		
		int[][] kernel = {
				{1,2,1}, 
				{2,4,2}, 
				{1,2,1} 
		};
	 	
		BufferedImage newImg = new BufferedImage(this.getWidth(), 
				this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		int height = this.getHeight();
		int width = this.getWidth();
		int i,j;
		double v = 0, z = 0;
		
		for (int y = 1; y < height -1; y++) {
			
			for (int x = 1; x < width -1; x++) {
				
				z = 0;
				for (i = 0; i < 3; i++){
					
					for (j = 0; j < 3; j++){
						
						Color c = new Color(this.bufferedImage.getRGB(x + (i-1), y + (j-1)));
						v = (((c.getRed() + c.getGreen() + c.getBlue()) / 3));

						z += v * kernel[i][j];
					}
				}
	        	
				int newColor = ((int) (z/16));
				Color c = new Color(newColor, newColor, newColor);
	        	newImg.setRGB(x, y, c.getRGB());
			}
		}
		
	    this.bufferedImage = newImg;

		this.initialize();
	}
	
	/*
	 * Detect borders
	 * @param xKernel Kernel in X axis.
	 * @param yKernel Kernel in Y axis.
	 * @param threshold Threshold to be used.
	 */
	private void detectBorders(int[][] xKernel, int[][] yKernel, int threshold) {
		
		this.gaussianFilter();
		
		BufferedImage newImg = new BufferedImage(this.getWidth(), 
				this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		int height = this.getHeight();
		int width = this.getWidth();
		int i,j;
		double v, gx, gy, g = 0;
		
		for (int y = 1; y < height -1; y++) {
			
			for (int x = 1; x < width -1; x++) {
				
				gx = gy = 0;
				for (i = 0; i < 3; i++){
					
					for (j = 0; j < 3; j++){
						
						Color c = new Color(this.bufferedImage.getRGB(x + (i-1), y + (j-1)));
						v = (((c.getRed() + c.getGreen() + c.getBlue()) / 3));

						gx += v * xKernel[i][j];
						gy += v * yKernel[i][j];
					}
				}
				
				g = Math.sqrt(Math.pow(gx, 2) + Math.pow(gy, 2));
				int newRGB = Color.BLACK.getRGB();
	        	if (g > threshold)
	        		newRGB = Color.WHITE.getRGB();	        		
	        		
	        	newImg.setRGB(x, y, newRGB);
			}
		}
		
	    this.bufferedImage = newImg;

		this.initialize();
	}
	
	/*
	 * Applies Roberts' borders detection method.
	 */
	public void detectBordersRoberts(){
		
		int[][] xKernel = { {0,0,0}, {0,-1,0}, {0,0,1} };
	 	int[][] yKernel = { {0,0,0}, {0,0,-1}, {0,1,0} };
	 	int threshold = 20;
	 	
		this.detectBorders(xKernel, yKernel, threshold);
	}
	
	/*
	 * Applies Sobel's borders detection method.
	 */
	public void detectBordersSobel(){
		
		int[][] xKernel = { {1,0,-1}, {2,0,-2}, {1,0,-1} };
	 	int[][] yKernel = { {1,2,1}, {0,0,0}, {-1,-2,-1} };
	 	int threshold = 100;
	 	
		this.detectBorders(xKernel, yKernel, threshold); 
	}
	
	private void binarize(){
		this.binarize(50);
	}
	
	/*
	 * Binarizes the picture, if the grayScale of the pixel is 
	 * greater or equal to a given threshold, gets black, white otherwise.
	 */
	private void binarize(int threshold){
		
		int height = this.getHeight();
		int width = this.getWidth();

		BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		for (int y = 0; y < height; y++) {
			
			for (int x = 0; x < width; x++) {
				
				Color c = new Color(this.bufferedImage.getRGB(x, y));
				int grayScale = (((c.getRed() + c.getGreen() + c.getBlue()) / 3));
								
				if (grayScale < threshold){
					
					newImg.setRGB(x, y, Color.BLACK.getRGB());
				}
				else{
					newImg.setRGB(x, y, Color.WHITE.getRGB());
				}
	        }
	    }
	
	    this.bufferedImage = newImg;
	}
	
	/*
	 * Applies erosion method.
	 */
	public void erosion(){
			
		this.binarize();
		
		int height = this.getHeight();
		int width = this.getWidth();

		BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		for (int y = 0; y < height; y++) {
			
			for (int x = 0; x < width; x++) {
				
				Color c = new Color(this.bufferedImage.getRGB(x, y));
				if (c.getRGB() == Color.WHITE.getRGB()){
					
					newImg.setRGB(x, y, Color.WHITE.getRGB());
					
					if (x > 0) newImg.setRGB(x-1, y, Color.WHITE.getRGB());
					if (y > 0) newImg.setRGB(x, y-1, Color.WHITE.getRGB());
					if (x < width - 1) newImg.setRGB(x+1, y, Color.WHITE.getRGB());
	                if (y < height - 1) newImg.setRGB(x, y+1, Color.WHITE.getRGB());
	                
				}
				else {
					newImg.setRGB(x, y, Color.BLACK.getRGB());
				}
	        }
	    }
		
	    this.bufferedImage = newImg;

		this.initialize();
	}
	
	/*
	 * Applies dilation method.
	 */
	public void dilation(){
		
		this.binarize();
		
		int height = this.getHeight();
		int width = this.getWidth();

		BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		for (int y = 0; y < height; y++) {
			
			for (int x = 0; x < width; x++) {
				
				Color c = new Color(this.bufferedImage.getRGB(x, y));
				if (c.getRGB() == Color.BLACK.getRGB()){
					
					newImg.setRGB(x, y, Color.BLACK.getRGB());
					
	                if (x > 0) newImg.setRGB(x-1, y, Color.BLACK.getRGB());
	                if (y > 0) newImg.setRGB(x, y-1, Color.BLACK.getRGB());
	                if (x < width - 1) newImg.setRGB(x+1, y, Color.BLACK.getRGB());
	                if (y < height - 1) newImg.setRGB(x, y+1, Color.BLACK.getRGB());
	                
				}
				else {
					newImg.setRGB(x, y, Color.WHITE.getRGB());
				}
			}				
	    }
		
	    this.bufferedImage = newImg;

		this.initialize();
	}
	
	/*
	 * Applies opening method.
	 */
	public void opening(){
		
		this.erosion();
		this.dilation();
	}
	
	/*
	 * Applies closing method.
	 */
	public void closing(){
		
		this.dilation();
		this.erosion();
	}

	private Point getFirstBlackPixel(){

		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {
				
				Color c = new Color(this.bufferedImage.getRGB(x, y));
				if (c.getRGB() == Color.BLACK.getRGB()){
	                return new Point(x, y);
				}
			}				
	    }
		
		return new Point(-1, -1);
	}
	
	/*
	 * Extracts the rectangle's area and perimeter.
	 */
	public void extractRetangle(Frame frame){

		this.binarize();
		
		// Gets the pixel where the rectangle starts.
		Point rectanglePixel = getFirstBlackPixel();		
		
		// Gets the size of sideY and sideX by iterating the img to a certain direction.
		int sizeSideY=0,sizeSideX=0;
		for (int y = rectanglePixel.y; y < this.getHeight(); y++) {
			Color c = new Color(this.bufferedImage.getRGB(rectanglePixel.x, y));
			if (c.getRGB() == Color.BLACK.getRGB()){
				sizeSideY++;
			}
			else{
				break;
			}
		}
		
		for (int x = rectanglePixel.x; x < this.getWidth(); x++) {
			Color c = new Color(this.bufferedImage.getRGB(x, rectanglePixel.y));
			if (c.getRGB() == Color.BLACK.getRGB()){
				sizeSideX++;
			}
			else{
				break;
			}
		}
		
		int area = sizeSideX * sizeSideY;
		int perimeter = (sizeSideX * 2) + (sizeSideY * 2);
		
		String message = String.format(Messages.getString("MainWindow.rectangleSuccess.text"), area, perimeter);
		JOptionPane.showMessageDialog(frame, message, 
				Messages.getString("MainWindow.mnRectangle.text"), JOptionPane.INFORMATION_MESSAGE);
	}
	
	/*
	 * Extracts the smaller circle's area;
	 * The biggest circle's perimeter;
	 * Both circles' circularity.
	 */
	public void extractTwoCircles(Frame frame){
		
		this.binarize();
		BufferedImage originalImg = Picture.copyImage(this.bufferedImage);
		
		// Gets the pixel where the 1 st circle starts.
		Point circle1 = getFirstBlackPixel();
		
		int perimeter1 = perimeterObject(circle1.x, circle1.y);		
		int area1 = floodfill(circle1.x, circle1.y, Color.RED, Color.BLACK);
		double circularity1 = Math.pow(perimeter1, 2) / (4 * Math.PI * area1);
		
		// Gets the pixel where the 2 st circle starts.
		Point circle2 = getFirstBlackPixel();
		
		int perimeter2 = perimeterObject(circle2.x, circle2.y);		
		int area2 = floodfill(circle2.x, circle2.y, Color.RED, Color.BLACK);
		double circularity2 = Math.pow(perimeter2, 2) / (4 * Math.PI * area2);
		
		this.bufferedImage = originalImg;
		
		int biggerArea = area1 > area2 ? area1 : area2;
		int smallerPerimeter = perimeter1 < perimeter2 ? perimeter1 : perimeter2;
		double biggerCircularity, smallerCircularity;
		
		if (area1 > area2){
			biggerCircularity = circularity1;
			smallerCircularity = circularity2;
		}
		else{
			biggerCircularity = circularity2;
			smallerCircularity = circularity1;
		}
		
		System.out.println("1 - A: " + area1 + " P: " + perimeter1 + " C: " + circularity1);
		System.out.println("2 - A: " + area2 + " P: " + perimeter2 + " C: " + circularity2);
		
		String message = String.format(Messages.getString("MainWindow.circlesSuccess.text"), biggerArea, biggerCircularity, smallerPerimeter, smallerCircularity);
		JOptionPane.showMessageDialog(frame, message, 
				Messages.getString("MainWindow.mnTwoCircles.text"), JOptionPane.INFORMATION_MESSAGE);
		
		this.initialize();
	}
			
	/*
	 * Paints objects that are inside a given threshold.
	 */
	public void extractMultipleObjects(int minArea, int maxArea, Frame frame){
		
		this.binarize(200);
		
		Point obj = getFirstBlackPixel();
		while (obj.x >= 0 && obj.y >=0){
			
			// Paint all objects BLUE
			int area = floodfill(obj.x, obj.y, Color.BLUE, Color.BLACK);
			if (area >= minArea && area <= maxArea){
				
				// If the object's area is inside the given threshold, paints it RED
				floodfill(obj.x, obj.y, Color.RED, Color.BLUE);
			}
			
			obj = getFirstBlackPixel();
		}
		
		// Revert blue objects to black.
		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {
				
				if (this.bufferedImage.getRGB(x, y) == Color.BLUE.getRGB()){
	                this.bufferedImage.setRGB(x, y, Color.BLACK.getRGB());
				}
			}				
	    }
		
		this.initialize();
	}	

	private int floodfill (int x, int y, Color newColor, Color oldColor){
		
		int area = 0;
		List<Point> queue = new LinkedList<Point>();
		queue.add(new Point(x, y));
		while (!queue.isEmpty()) {
						
			Point p = queue.remove(0);
			if (this.bufferedImage.getRGB(p.x, p.y) == oldColor.getRGB()){
				
				this.bufferedImage.setRGB(p.x, p.y, newColor.getRGB());
				area++;
				
                queue.add(new Point(p.x + 1, p.y));
                queue.add(new Point(p.x - 1, p.y));
                queue.add(new Point(p.x, p.y + 1));
                queue.add(new Point(p.x, p.y - 1));
			}		
		}

		return area;
	}
	
	/*
	 * Gets an object's perimeter.
	 */
	private int perimeterObject(int x, int y){
		BufferedImage originalImg = Picture.copyImage(this.bufferedImage);
		int perimeter = perimeterObject(x, y, 0);
		
		// Reverts red border.
		this.bufferedImage = originalImg;
		
		return perimeter;
	}
	
	/*
	 * Gets an object's perimeter.
	 */
	private int perimeterObject(int x, int y, int count){
		
		int nextX, nextY;
		
		nextX = x-1; nextY = y-1;
		if (isOnBorder(nextX, nextY)){
			return paintPixel(nextX, nextY, count);
		}
		
		nextX = x; nextY = y-1;
		if (isOnBorder(nextX, nextY)){
			return paintPixel(nextX, nextY, count);
		}
		
		nextX = x+1; nextY = y-1;
		if (isOnBorder(nextX, nextY)){
			return paintPixel(nextX, nextY, count);
		}
		
		nextX = x+1; nextY = y;
		if (isOnBorder(nextX, nextY)){
			return paintPixel(nextX, nextY, count);
		}
		
		nextX = x+1; nextY = y+1;
		if (isOnBorder(nextX, nextY)){
			return paintPixel(nextX, nextY, count);
		}
		
		nextX = x; nextY = y+1;
		if (isOnBorder(nextX, nextY)){
			return paintPixel(nextX, nextY, count);
		}
		
		nextX = x-1; nextY = y+1;
		if (isOnBorder(nextX, nextY)){
			return paintPixel(nextX, nextY, count);
		}
		
		nextX = x-1; nextY = y;
		if (isOnBorder(nextX, nextY)){
			return paintPixel(nextX, nextY, count);
		}

		return count;
	}
	
	/*
	 * Paints the given pixel red. And verifies this pixel as well.
	 */
	private int paintPixel (int x, int y, int count){

		this.bufferedImage.setRGB(x, y, Color.RED.getRGB());
		return perimeterObject(x, y, count+1);
	}
	
	/*
	 * Verifies if a given pixel is black and has more than two white neighbors
	 */
	private boolean isOnBorder(int x, int y){
		
		Color c = new Color(this.bufferedImage.getRGB(x, y));
		if (c.getRGB() != Color.BLACK.getRGB()){
			return false;
		}
		
		int whiteNeighbors = 0;
		whiteNeighbors += isWhite(x-1, y-1);	
		whiteNeighbors += isWhite(x, y-1);
		whiteNeighbors += isWhite(x+1, y-1);
		whiteNeighbors += isWhite(x+1, y);
		whiteNeighbors += isWhite(x+1, y+1);
		whiteNeighbors += isWhite(x, y+1);
		whiteNeighbors += isWhite(x-1, y+1);
		whiteNeighbors += isWhite(x-1, y);
		
		return whiteNeighbors >= 2;
	}
	
	/*
	 * Verifies if a given pixel is white.
	 */
	private int isWhite(int x, int y){
		
		Color c = new Color(this.bufferedImage.getRGB(x, y));
		if (c.getRGB() == Color.WHITE.getRGB()){
			return 1;
		}
		
		return 0;
	}
}
