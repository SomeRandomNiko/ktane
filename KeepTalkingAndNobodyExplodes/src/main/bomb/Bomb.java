package main.bomb;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import main.bomb.modules.Module;
import main.bomb.modules.button.Button;
import main.bomb.modules.keypad.Keypad;
import main.bomb.modules.maze.MazeModule;
import main.bomb.modules.memory.Memory;
import main.bomb.modules.wires.Wires;

public class Bomb {

	JFrame frame;
	private boolean solved;
	private static Timer timer;
	private static SerialNumber serialNumber;
	private static boolean explode;
	private static Batteries batteries;
	private static Indicator indicator;
	Module[] modules;
	private int moduleCount;

	public Bomb(int moduleCount, int seconds) {
		timer = new Timer(seconds);
		this.moduleCount = moduleCount;
		explode = false;
		solved = false;
		serialNumber = new SerialNumber();
		batteries = new Batteries();
		indicator = new Indicator();
		modules = new Module[6];
		generateModules();
	}

	/**
	 * Generates the modules
	 */
	public void generateModules() {
		for (int i = 0; i < moduleCount; i++) {
			int random = new Random().nextInt(5);
			switch (random) {

				// Wires
				case 0:
					modules[i] = new Wires(i);
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
				case 4:
					modules[i] = new MazeModule(i);
					break;
			}
		}

		for (int i = moduleCount; i < modules.length; i++) {
			modules[i] = new Module(i, true);
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
	 */
	public static void explode() {
		explode = true;
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
