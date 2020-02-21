package modules.keypad;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Key {
	private boolean pressed;
	private int symbolNumber;
	private int index;
	private BufferedImage symbol;

	public Key(int index, int symbol) {
		this.index = index;
		this.symbolNumber = symbol;

		try {
			this.symbol = ImageIO.read(getClass().getResourceAsStream("/modules/keypad/symbols/" + symbolNumber + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// GETTERS / SETTERS
	// --------------------------------------------------------------------------

	/**
	 * @return the pressed
	 */
	public boolean isPressed() {
		return pressed;
	}

	/**
	 * @return the symbol
	 */
	public int getSymbolNumber() {
		return symbolNumber;
	}

	/**
	 * @return the symbol
	 */
	public BufferedImage getSymbol() {
		return symbol;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param pressed
	 *            the pressed to set
	 */
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
}
