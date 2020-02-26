package main.bomb;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Batteries {
	private int count;
	private BufferedImage batteryImage;

	public Batteries() {
		count = new Random().nextInt(5);

		try {
			batteryImage = ImageIO.read(getClass().getResourceAsStream("/batteries/batteries" + count + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void update(Graphics g) {
		g.drawImage(batteryImage, 1517, 170, null);
	}

	// GETTERS / SETTERS
	// ------------------------------------------------------------------------------
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
}
