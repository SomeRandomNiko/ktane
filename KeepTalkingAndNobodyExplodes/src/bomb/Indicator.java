package bomb;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Indicator {
	private final String[] labels = {
			"SND", "CLR", "CAR", "IND", "FRQ", "SIG", "NSA", "MSA", "TRN", "BOB", "FRK" };
	private String label;
	private Font labelFont;
	private BufferedImage indicatorFrame;

	public Indicator() {
		try {
			labelFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/modules/button/buttonFont.ttf")).deriveFont(45f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		try {
			indicatorFrame = ImageIO.read(getClass().getResourceAsStream("/indicator/indicatorFrame.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		label = labels[new Random().nextInt(labels.length)];
	}

	public void update(Graphics g) {
		g.drawImage(indicatorFrame, 1500, 500, null);
		g.setColor(Color.WHITE);
		g.setFont(labelFont);
		g.drawString(label, 1700, 565);
	}
}
