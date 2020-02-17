package modules.wires;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Wire {
	private String color;
	private boolean cut;
	private int index;
	private BufferedImage image;

	public Wire() {
		color = "";
		cut = false;
	}

	public void generateRandom(boolean blankAllowed) {
		String[] colors = { "red", "blue", "green", "yellow", "white" };
		if (blankAllowed && new Random().nextBoolean()) {
			// Blank / not Blank
			generateBlank();
			return;
		}
		color = colors[new Random().nextInt(5)];
		cut = false;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/modules/wires/" + color + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateBlank() {
		color = "blank";
		cut = false;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/modules/wires/" + color + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @return the cut
	 */
	public boolean isCut() {
		return cut;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @param cut
	 *            the cut to set
	 */
	public void setCut(boolean cut) {
		this.cut = cut;
		try {
			if (!color.equals("blank"))
				image = ImageIO.read(getClass().getResourceAsStream("/modules/wires/" + color + "Cut" + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}
