package main.bomb;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;

import main.bomb.modules.Module;
import main.bomb.modules.button.Button;
import main.bomb.modules.keypad.Keypad;
import main.bomb.modules.memory.Memory;
import main.bomb.modules.wires.Wires;

public class Bomb {

	JFrame frame;
	Module[] modules = new Module[6];
	private boolean solved;
	private static Timer timer = new Timer(300);
	private static SerialNumber serialNumber;
	private static boolean explode;
	private static Batteries batteries;
	private static Indicator indicator;


	public Bomb() {
		explode = false;
		solved = false;
		serialNumber = new SerialNumber();
		batteries = new Batteries();
		indicator = new Indicator();
		generateModules();
	}

	/**
	 * Generates the modules
	 */
	public void generateModules() {
		for (int i = 0; i < modules.length; i++) {
			switch (new Random().nextInt(4)) {

				// Wires
				case 0:
					modules[i] = new Wires(i);
					((Wires) modules[i]).generateRandom();
					break;
				case 1:
					modules[i] = new Keypad(i);
					break;
				case 2:
					modules[i] = new Button(i);
					break;
				case 3:
					modules[i] = new Memory(i); 
					break;

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
			batteries.update(g);
			indicator.update(g);

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

	public static Batteries getBatteries() {
		return batteries;
	}

	/**
	 * @return the timer
	 */
	public static Timer getTimer() {
		return timer;
	}

	/**
	 * @return the solved
	 */
	public boolean isSolved() {
		return solved;
	}

	/**
	 * @return the indicator
	 */
	public static Indicator getIndicator() {
		return indicator;
	}


}
