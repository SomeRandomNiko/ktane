package bomb;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import modules.Module;
import modules.wires.Wires;

public class Bomb {

	Timer timer = new Timer(60);
	JFrame frame;
	Module[] modules = new Module[6];

	static Clip explosionSound;

	private static SerialNumber serialNumber = new SerialNumber();
	private static boolean explode = false;

	public Bomb() {
		try {
			explosionSound = AudioSystem.getClip();
			explosionSound.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/timer/explosion.wav")));
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
		}
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
	 * Draws everything
	 */
	public void update(Graphics g) {
		if (!explode) {
			// Update the timer
			timer.update(g);
			serialNumber.update(g);
			for (int i = 0; i < modules.length; i++) {
				if (modules[i] != null) {
					modules[i].drawFrame(g);
					modules[i].update(g);
				}
			}
		} else {
			// Black screen if the timer is depleted
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1900, 1000);
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

	/**
	 * @return the explode
	 */
	public static boolean isExploded() {
		return explode;
	}

	/**
	 * @param explode
	 *            the explode to set
	 */
	public static void setExplode(boolean explode) {
		if (explode) {
			explosionSound.start();
		}
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
}
