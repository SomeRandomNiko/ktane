package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import modules.Module;
import modules.Wires;

public class Window extends JPanel {

	private static final long serialVersionUID = 1L;

	JFrame frame;
	Module[] modules = new Module[6];
	Timer timer = new Timer(60);

	public Window() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(this);
		frame.setTitle("Keep Talking and Nobody Explodes");
		frame.setLocation(0, 0);
		frame.setVisible(true);
	}

	/**
	 * Adjusts the screen size to account for the insets and sets the Background
	 * color
	 */
	public void makeScreen() {

		Insets i = frame.getInsets();
		frame.setSize(1900 + i.left + i.right, 1000 + i.top + i.bottom);
		setBackground(new Color(0x545454));
		timer.start();
	}

	/**
	 * Generates the modules
	 */
	public void generateModules() {
		for (int i = 0; i < modules.length; i++) {
			switch (new Random().nextInt(1)) {

				// Wires
				case 0:
					modules[i] = new Wires(i);
					((Wires) modules[i]).generateRandom();
					for (int j = 0; j < modules[i].getHitboxes().length; j++)
						if (modules[i].getHitboxes()[j] != null)
							add(modules[i].getHitboxes()[j]);
			}
		}
	}

	/**
	 * Draws everything
	 */
	public void paint(Graphics g) {
		super.paint(g);

		// Update the timer
		timer.update(g);
		if (!timer.isDepleted()) {
			for (int i = 0; i < modules.length; i++) {
				if (modules[i] != null) {
					modules[i].drawFrame(g);
					modules[i].update(g);
				}
			}
		} else {
			// Black screen if the timer is depleted
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		// Frametime
		pause(10);
		repaint();
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
	 * Returns the modules
	 * 
	 * @return the modules
	 */
	public Module[] getModules() {
		return modules;
	}
}
