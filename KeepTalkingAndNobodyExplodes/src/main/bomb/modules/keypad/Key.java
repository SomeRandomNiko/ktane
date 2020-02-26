package main.bomb.modules.keypad;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Key {
	private boolean pressed;
	private int symbolNumber;
	private int index;
	private BufferedImage symbol;
	Clip keyPress;

	public Key(int index, int symbol) {
		this.index = index;
		this.symbolNumber = symbol;

		// Open the symbol image
		try {
			this.symbol = ImageIO.read(getClass().getResourceAsStream("/modules/keypad/symbols/" + symbolNumber + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Open the key press sound
		try {
			keyPress = AudioSystem.getClip();
			keyPress.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/modules/keypad/keyClick.wav")));
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

	// GETTERS / SETTERS
	// --------------------------------------------------------------------------

	/**
	 * @return the pressed
	 */
	public boolean isPressed() {
		if (pressed)
			keyPress.start();
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
