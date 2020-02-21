package bomb;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import modules.Module;
import modules.wires.Wires;

public class Bomb {

	Timer timer = new Timer(300);
	JFrame frame;
	Module[] modules = new Module[6];
	private boolean solved = false;
	private static SerialNumber serialNumber = new SerialNumber();
	private static boolean explode;

	public Bomb() {
		explode = false;
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

			}
		}
	}

	/**
	 * Update the modules
	 */
	public void update(Graphics g) {

		if (!explode) {
			if (solved) {
				timer.stop();
			}
			// Update the timer
			timer.update(g);
			serialNumber.update(g);
			boolean temp = true;
			for (int i = 0; i < modules.length; i++) {
				if (modules[i] != null) {
					modules[i].update(g);
					modules[i].drawFrame(g);
					if (!modules[i].isSolved()) {
						temp = false;
					}
				}
			}
			solved = temp;

		} else {
			// Black screen if the timer is depleted
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1900, 1000);
		}
	}

	// GETTERS / SETTERS
	// --------------------------------------------------------------------------

	/**
	 * Returns the modules
	 * 
	 * @return the modules
	 */
	public Module[] getModules() {
		return modules;
	}

	/**
	 * @return the explode
	 */
	public boolean isExploded() {
		return explode;
	}

	/**
	 * @param explode
	 *            the explode to set
	 */
	public static void setExplode(boolean explode) {
		Bomb.explode = explode;
	}

	/**
	 * Returns the serial number
	 * 
	 * @return the serial number
	 */
	public static SerialNumber getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @return the timer
	 */
	public Timer getTimer() {
		return timer;
	}

	/**
	 * @return the solved
	 */
	public boolean isSolved() {
		return solved;
	}
}
