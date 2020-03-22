package main.bomb;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Indicator {
	// Possible labels
	private final String[] labels = { "CLR", "CAR", "IND", "FRQ", "SIG", "NSA", "TRN", "FRK" };
	private String label;
	private Font labelFont;
	private BufferedImage indicatorFrame;

	public Indicator() {
		// Creathe the Font
		try {
			labelFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/modules/button/buttonFont.ttf")).deriveFont(45f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		// Open the image
		try {
			indicatorFrame = ImageIO.read(getClass().getResourceAsStream("/indicator/indicatorFrame.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Random label
		label = labels[new Random().nextInt(labels.length)];
	}

	public void update(Graphics g) {
		g.drawImage(indicatorFrame, 1500, 500, null);
		g.setColor(Color.WHITE);
		g.setFont(labelFont);
		g.drawString(label, 1700, 565);
	}

	// GETTERS / SETTERS
	// -----------------------------------------------------------------

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
}
