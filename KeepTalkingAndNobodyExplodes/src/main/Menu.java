package main;

import java.awt.Graphics;
public class Menu {

	private MenuButton playButton;
	private MenuButton quitButton;
	

	public Menu() {
		playButton = new MenuButton("PLAY", 950 - 150, 300, 300, 100);
		quitButton = new MenuButton("QUIT", 950 - 150, 450, 300, 100);

		
	}

	public void update(Graphics g) {
		
		if (playButton.isClick()) {
			GameWindow.setIngame(true);
		} else if (quitButton.isClick())
			System.exit(0);
	}

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
}
