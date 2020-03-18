package main.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Menu {
	private MenuButton playButton;
	private MenuButton quitButton;
	private MenuButton plusButton;
	private MenuButton minusButton;
	private MenuButton timerButton;

	private BufferedImage menuBackground;
	private BufferedImage menuPlay;
	private BufferedImage menuQuit;
	private BufferedImage menuPlus;
	private BufferedImage menuMinus;
	private BufferedImage[] menuLights = new BufferedImage[6];

	private Font timerFont;

	private int timerSeconds = 300;

	private int moduleCount = 6;

	public Menu() {
		playButton = new MenuButton(1058, 528, 607, 188);
		quitButton = new MenuButton(1058, 754, 607, 193);
		plusButton = new MenuButton(91, 491, 171, 152);
		minusButton = new MenuButton(91, 653, 171, 154);
		timerButton = new MenuButton(104, 164, 676, 243);

		try {
			menuBackground = ImageIO.read(getClass().getResourceAsStream("/menu/Menu.png"));
			menuPlay = ImageIO.read(getClass().getResourceAsStream("/menu/MenuPlay.png"));
			menuQuit = ImageIO.read(getClass().getResourceAsStream("/menu/MenuQuit.png"));
			menuPlus = ImageIO.read(getClass().getResourceAsStream("/menu/MenuPlus.png"));
			menuMinus = ImageIO.read(getClass().getResourceAsStream("/menu/MenuMinus.png"));

			for (int i = 0; i < menuLights.length; i++) {
				menuLights[i] = ImageIO.read(getClass().getResourceAsStream("/menu/lights/MenuLight" + (i + 1) + ".png"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			timerFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/timer/timerFont.ttf")).deriveFont(150f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the menu
	 * 
	 * @param g
	 *            Graphics
	 */
	public void update(Graphics g) {
		if (playButton.isPressed())
			g.drawImage(menuPlay, 0, 0, null);
		else if (quitButton.isPressed())
			g.drawImage(menuQuit, 0, 0, null);
		else if (plusButton.isPressed())
			g.drawImage(menuPlus, 0, 0, null);
		else if (minusButton.isPressed())
			g.drawImage(menuMinus, 0, 0, null);
		else
			g.drawImage(menuBackground, 0, 0, null);

		g.drawImage(menuLights[moduleCount - 1], 0, 0, null);

		g.setFont(timerFont);
		g.setColor(new Color(0x868686));
		g.drawString("88:88", 180, 360);
		g.setColor(new Color(0xb30000));
		g.drawString(getTimerString(), 180, 360);

		if (plusButton.isClick() && moduleCount < 6)
			moduleCount++;
		else if (minusButton.isClick() && moduleCount > 1)
			moduleCount--;

		int mouseWheelTemp = timerButton.getMouseWheelRotation();
		if (mouseWheelTemp > 0) {
			System.out.println("DOWN");
			if (timerSeconds - 10 > 0)
				timerSeconds -= 10;
		} else if (mouseWheelTemp < 0) {
			System.out.println("UP");
			if (timerSeconds + 10 <= 20 * 60)
				timerSeconds += 10;
		}
		timerButton.resetMouseWheelRotation();

	}

	private String getTimerString() {
		int min = 0;
		int sec = 0;
		min = timerSeconds / 60;
		sec = timerSeconds % 60;
		String ret = "";
		if (min < 10)
			ret += "0";
		ret += String.valueOf(min + ":");
		if (sec < 10)
			ret += "0";
		ret += sec;
		return ret;
	}

	// GETTERS / SETTERS
	// --------------------------------------------------------------------------

	/**
	 * @return the playButton
	 */
	public MenuButton getPlayButton() {
		return playButton;
	}

	/**
	 * @return the quitButton
	 */
	public MenuButton getQuitButton() {
		return quitButton;
	}

	/**
	 * @return the modulePlusButton
	 */
	public MenuButton getModulePlusButton() {
		return plusButton;
	}

	/**
	 * @return the moduleMinusButton
	 */
	public MenuButton getModuleMinusButton() {
		return minusButton;
	}

	/**
	 * @return the moduleCount
	 */
	public int getModuleCount() {
		return moduleCount;
	}

	/**
	 * @return the timerButton
	 */
	public MenuButton getTimerButton() {
		return timerButton;
	}

	/**
	 * @return the timerSeconds
	 */
	public int getTimerSeconds() {
		return timerSeconds;
	}
}
