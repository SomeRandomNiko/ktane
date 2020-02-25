package modules.button;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import bomb.Bomb;
import modules.Hitbox;
import modules.Module;

public class Button extends Module {
	final String[] buttonColors = {
			"blue", "red", "white", "yellow" };
	final String[] buttonTexts = {
			"PRESS", "HOLD", "DETONATE", "ABORT" };
	final String[] lightColors = {
			"blue", "yellow", "red", "white" };

	private String color;
	private String text;
	private String lightColor;
	BufferedImage pressedImage;
	BufferedImage unpressedImage;
	Font buttonFont;
	private Hitbox hitbox;
	int strWidth;
	int strHeight;
	boolean hold;

	public Button(int moduleIndex) {
		super(moduleIndex);
		color = buttonColors[new Random().nextInt(4)];
		text = buttonTexts[new Random().nextInt(4)];
		hitbox = new Hitbox(getModuleOffset()[0] + 118, getModuleOffset()[1] + 72, 264, 264);

		try {
			buttonFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/modules/button/buttonFont.ttf"));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		try {
			pressedImage = ImageIO.read(getClass().getResourceAsStream("/modules/button/" + color + "Pushed.png"));
			unpressedImage = ImageIO.read(getClass().getResourceAsStream("/modules/button/" + color + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setHitboxes(new Hitbox[] {
				hitbox });
		
		if(color.equals("blue") && text.equals("ABORT"))
			hold = true;
		else if(Bomb.getBatteries().getCount() > 1 && text.equals("DETONATE"))
			hold = false;
		// TODO
		
	}

	public void update(Graphics g) {

		if (getHitboxes()[0].isClick()) {
			g.drawImage(pressedImage, getModuleOffset()[0], getModuleOffset()[1], null);
			buttonFont = buttonFont.deriveFont(40f);
			g.setFont(buttonFont);
		} else {
			g.drawImage(unpressedImage, getModuleOffset()[0], getModuleOffset()[1], null);
			buttonFont = buttonFont.deriveFont(45f);
			g.setFont(buttonFont);
		}

		g.setFont(buttonFont);
		FontMetrics metrics = g.getFontMetrics(buttonFont);
		strWidth = metrics.stringWidth(text);
		strHeight = metrics.getHeight();
		if (color.equals("white"))
			g.setColor(Color.BLACK);
		else g.setColor(Color.WHITE);

		g.drawString(text, getModuleOffset()[0] + 118 + 132 - strWidth / 2, getModuleOffset()[1] + 72 + 132 + strHeight / 4);

		
	}

}
