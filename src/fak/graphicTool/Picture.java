package fak.graphicTool;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Picture {
	
	private BufferedImage bufferedImage;
	
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
		
	}
	
	public Picture (String path){
		try {
			this.setBufferedImage(ImageIO.read(new File(path)));
			 
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
	
}
