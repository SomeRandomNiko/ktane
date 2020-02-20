package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;
import bomb.Bomb;

public class GameWindow extends JPanel {
	private static final long serialVersionUID = 1L;

	JFrame frame;
	Bomb bomb;
	Menu menu;

	private static boolean ingame;

	public GameWindow() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Keep Talking and Nobody Explodes");
		frame.setLocation(0, 0);
		setLayout(null);

		bomb = new Bomb();
		menu = new Menu();

		bomb.generateModules();
	}

	/**
	 * Adjusts the screen size to account for the insets and sets the Background
	 * color
	 */
	public void makeWindow() {
		Insets in = frame.getInsets();
		frame.setSize(1900 + in.left + in.right, 1000 + in.top + in.bottom);
		setBackground(new Color(0x545454));
		frame.setVisible(true);

		add(menu.getPlayButton());
		add(menu.getQuitButton());
		frame.add(this);
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (ingame) {
			if (!bomb.getTimer().isRunning()) {
				
				startGame(g);
			}
			remove(menu.getPlayButton());
			remove(menu.getQuitButton());
			bomb.update(g);
		} else {
			menu.update(g);
		}
		repaint();
		pause(10);
	}

	private void startGame(Graphics g) {
		for (int i = 0; i < bomb.getModules().length; i++) {
			for (int j = 0; j < bomb.getModules()[i].getHitboxes().length; j++) {
				if (bomb.getModules()[i].getHitboxes()[j] != null)
					add(bomb.getModules()[i].getHitboxes()[j]);
			}

		}
		bomb.getTimer().start();
	}

	/**
	 * Lets the thread sleep for 'ms' milliseconds
	 * 
	 * @param ms
	 *            milliseconds to sleep
	 */
	private void pause(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the ingame
	 */
	public static boolean isIngame() {
		return ingame;
	}

	/**
	 * @param ingame
	 *            the ingame to set
	 */
	public static void setIngame(boolean ingame) {
		GameWindow.ingame = ingame;
	}

}
