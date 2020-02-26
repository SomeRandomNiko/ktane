package main.bomb.modules.button;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import main.GameWindow;
import main.bomb.Bomb;
import main.bomb.modules.Hitbox;
import main.bomb.modules.Module;

public class Button extends Module {
	final String[] buttonColors = {
			"blue", "red", "white", "yellow" };
	final String[] buttonTexts = {
			"PRESS", "HOLD", "DETONATE", "ABORT" };
	final Color[] lightColors = {
			Color.BLUE, Color.YELLOW, Color.RED, Color.WHITE };

	private String color;
	private String text;
	private Color lightColor;
	BufferedImage pressedImage;
	BufferedImage unpressedImage;
	Font buttonFont;
	private Hitbox hitbox;
	int strWidth;
	int strHeight;
	boolean hold;
	int millisPassed;
	boolean drawLight = false;
	boolean canBePressed = true;
	Clip press;
	Clip release;

	public Button(int moduleIndex) {
		super(moduleIndex);

		// Generate random Color and text
		color = buttonColors[new Random().nextInt(4)];
		text = buttonTexts[new Random().nextInt(4)];
		lightColor = lightColors[new Random().nextInt(lightColors.length)];

		hitbox = new Hitbox(getModuleOffset()[0] + 118, getModuleOffset()[1] + 72, 264, 264);

		// Create the font
		try {
			buttonFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/modules/button/buttonFont.ttf"));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		// Open the images
		try {
			pressedImage = ImageIO.read(getClass().getResourceAsStream("/modules/button/" + color + "Pushed.png"));
			unpressedImage = ImageIO.read(getClass().getResourceAsStream("/modules/button/" + color + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Open audio files
		try {
			press = AudioSystem.getClip();
			release = AudioSystem.getClip();
			press.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/modules/button/buttonPress.wav")));
			release.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/modules/button/buttonRelease.wav")));
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}

		setHitboxes(new Hitbox[] {
				hitbox });

		// Determine if the button has to be held or clicked
		if (color.equals("blue") && text.equals("ABORT"))
			hold = true;
		else if (Bomb.getBatteries().getCount() > 1 && text.equals("DETONATE"))
			hold = false;
		else if (color.equals("white") && Bomb.getIndicator().getLabel().equals("CAR"))
			hold = true;
		else if (Bomb.getBatteries().getCount() > 2 && Bomb.getIndicator().getLabel().equals("FRK"))
			hold = false;
		else if (color.equals("yellow"))
			hold = true;
		else if (color.equals("red") && text.equals("HOLD"))
			hold = false;
		else hold = true;
	}

	/**
	 * Update the Graphics and Functions of the button
	 */
	public void update(Graphics g) {

		// If the button is held down draw the pressedImage
		if (getHitboxes()[0].isClick()) {
			if (canBePressed) {
				press.stop();
				press.setMicrosecondPosition(0);
				press.start();
				canBePressed = false;
			}
			g.drawImage(pressedImage, getModuleOffset()[0], getModuleOffset()[1], null);
			buttonFont = buttonFont.deriveFont(40f);
			g.setFont(buttonFont);

		} else {
			g.drawImage(unpressedImage, getModuleOffset()[0], getModuleOffset()[1], null);
			buttonFont = buttonFont.deriveFont(45f);
			g.setFont(buttonFont);
			if (!canBePressed) {
				release.stop();
				release.setMicrosecondPosition(0);
				release.start();
				canBePressed = true;
			}
		}

		// Draw the light
		if (drawLight) {
			g.setColor(lightColor);
			g.fillRect(getModuleOffset()[0] + 141, getModuleOffset()[1] + 401, 217, 43);
		}

		// Draw the text
		g.setFont(buttonFont);
		FontMetrics metrics = g.getFontMetrics(buttonFont);
		strWidth = metrics.stringWidth(text);
		strHeight = metrics.getHeight();
		if (color.equals("white"))
			g.setColor(Color.BLACK);
		else g.setColor(Color.WHITE);
		g.drawString(text, getModuleOffset()[0] + 118 + 132 - strWidth / 2, getModuleOffset()[1] + 72 + 132 + strHeight / 4);

		// Logic of the button
		if (!isSolved()) {
			if (getHitboxes()[0].isClick()) {
				millisPassed += GameWindow.getFrameTime();
				if (hold && millisPassed > 1000) {
					drawLight = true;
				} else if (!hold && millisPassed > 1000) {
					Bomb.setExplode(true);
				}
			} else {
				if (!hold && millisPassed > 0 && millisPassed < 1000) {
					setSolved(true);
					drawLight = false;

				} else if (hold && millisPassed > 1000) {
					if (lightColor.equals(Color.BLUE) && Bomb.getTimer().getTimerString().contains("4")) {
						setSolved(true);
						drawLight = false;
					} else if (lightColor.equals(Color.WHITE) && Bomb.getTimer().getTimerString().contains("1")) {
						setSolved(true);
						drawLight = false;
					} else if (lightColor.equals(Color.YELLOW) && Bomb.getTimer().getTimerString().contains("5")) {
						setSolved(true);
						drawLight = false;
					} else if (Bomb.getTimer().getTimerString().contains("1")) {
						setSolved(true);
						drawLight = false;
					} else {
						Bomb.setExplode(true);
					}
				} else if (hold && millisPassed > 0 && millisPassed < 1000) {
					Bomb.setExplode(true);
				}
				millisPassed = 0;
			}
		}

	}
}
