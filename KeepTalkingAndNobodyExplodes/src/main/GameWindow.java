package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.bomb.Bomb;
import main.menu.Menu;

public class GameWindow extends JPanel {
	private static final long serialVersionUID = 1L;

	JFrame frame;
	private Bomb bomb;
	private Menu menu;
	Insets in;
	static boolean ingame;
	private static int frametime = 10;

	// Window Constructor
	public GameWindow() {
		ingame = false;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Keep Talking and Nobody Explodes");
		frame.pack();
		in = frame.getInsets();
		setLayout(null);
		setBackground(new Color(0x545454));
		frame.setSize(1900 + in.left + in.right, 1000 + in.top + in.bottom);
		frame.add(this);
		frame.setVisible(true);

	}

	/**
	 * Removes all menu components from the window, generates a bomb and its
	 * modules, adds the hitboxes and starts the timer
	 */
	public void startGame() {
		removeAll();
		bomb = new Bomb(menu.getModuleCount(), menu.getTimerSeconds());
		ingame = true;
		addHitboxes();
		Bomb.getTimer().start();
	}

	/**
	 * Adds the hitboxes to the game window
	 */
	private void addHitboxes() {
		for (int i = 0; i < bomb.getModules().length; i++) {
			if (!bomb.getModules()[i].isEmpty()) {
				for (int j = 0; j < bomb.getModules()[i].getHitboxes().length; j++) {
					if (bomb.getModules()[i].getHitboxes()[j] != null)
						add(bomb.getModules()[i].getHitboxes()[j]);
				}
			}
		}
	}

	/**
	 * Removes all game components from the game window and creates the menu
	 */
	public void startMenu() {
		removeAll();
		menu = new Menu();

		add(menu.getPlayButton());
		add(menu.getQuitButton());
		add(menu.getModuleMinusButton());
		add(menu.getModulePlusButton());
		add(menu.getTimerButton());

		ingame = false;
	}

	/**
	 * Draws the menu and the game
	 */
	public void paint(Graphics g) {
		super.paint(g);

		if (ingame) {
			if (bomb != null)
				bomb.update(g);
		} else {
			if (menu != null)
				menu.update(g);
		}
		repaint();
		pause(frametime);
	}

	/**
	 * Lets the thread sleep for 'ms' milliseconds
	 * 
	 * @param ms
	 *            milliseconds to sleep
	 */
	void pause(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// GETTERS / SETTERS
	// ------------------------------------------------------------------------------------

	/**
	 * @return the bomb
	 */
	public Bomb getBomb() {
		return bomb;
	}

	/**
	 * @return the menu
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * @return the frametime
	 */
	public static int getFrameTime() {
		return frametime;
	}
}
