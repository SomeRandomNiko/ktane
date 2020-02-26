package main.bomb;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class SerialNumber {
	private String number = "#";
	private BufferedImage serialImage;
	private Font serialNumberFont;

	final String ZEICHENSATZ = "ACBDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public SerialNumber() {
		try {
			serialImage = ImageIO.read(getClass().getResourceAsStream("/serialNumber.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Create the serial number font
		try {
			serialNumberFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/serialNumberFont.ttf")).deriveFont(45f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			number += ZEICHENSATZ.charAt(random.nextInt(36));
		}
		number += random.nextInt(10);
	}

	/**
	 * Update the serial number
	 * 
	 * @param g
	 *            Graphics g
	 */
	public void update(Graphics g) {
		g.drawImage(serialImage, 1600, 850, null);
		g.setColor(Color.black);
		g.setFont(serialNumberFont);
		g.drawString(number, 1610, 925);
	}

	/**
	 * Returns true, if the last digit of the serial number is even
	 * 
	 * @return true, if the last digit of the serial number is even
	 */
	public boolean lastDigitIsEven() {
		return Integer.valueOf(number.charAt(number.length() - 1)) % 2 == 0;
	}

	/**
	 * Returns true if the serial number contains a vowel
	 * 
	 * @return true if the serial number contains a vowel
	 */
	public boolean containsVowels() {
		boolean ret = false;
		for (int i = 0; i < number.length(); i++) {
			switch (number.charAt(i)) {
				case 'A':
				case 'E':
				case 'I':
				case 'O':
				case 'U':
					ret = true;
					break;
			}
		}
		return ret;
	}
}
