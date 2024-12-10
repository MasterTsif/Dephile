package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool
{
	public BufferedImage scaleImage(BufferedImage originalImage, int width, int height)
	{
		BufferedImage resizedImage = new BufferedImage( width, height, originalImage.getType() );
		Graphics2D g2 = resizedImage.createGraphics();
		g2.drawImage( originalImage, 0, 0, width, height, null );
		g2.dispose();
		return resizedImage;
	}
}

