package main.bomb.modules;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Module {

	private int moduleIndex;

	// Frame images
	private BufferedImage frameImageSolved;
	private BufferedImage frameImageUnsolved;
	private BufferedImage emptyFrame;

	private boolean solved;
	private Hitbox[] hitboxes;

	private boolean empty;

	// Modules solved sound
	Clip moduleSolvedSound;

	/**
	 * Updates the modules
	 * 
	 * @param g
	 *            the canvas
	 */
	public void drawFrame(Graphics g) {

		if (!empty && isSolved()) {
			// Set the "module solved" image
			g.drawImage(frameImageSolved, getModuleOffset()[0], getModuleOffset()[1], null);
		} else if (empty) {
			g.drawImage(emptyFrame, getModuleOffset()[0], getModuleOffset()[1], null);
		} else {
			g.drawImage(frameImageUnsolved, getModuleOffset()[0], getModuleOffset()[1], null);
		}
	}

	/**
	 * Update the module
	 * 
	 * @param g
	 *            the canvas
	 */
	public void update(Graphics g) {
	}

	public Module(int moduleIndex) {
		this.moduleIndex = moduleIndex;
		setSolved(false);

		try {
			frameImageUnsolved = ImageIO.read(getClass().getResourceAsStream("/modules/moduleFrameUnsolved.png"));
			frameImageSolved = ImageIO.read(getClass().getResourceAsStream("/modules/moduleFrameSolved.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			moduleSolvedSound = AudioSystem.getClip();
			moduleSolvedSound.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/modules/moduleSolved.wav")));
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
		}
	}

	public Module(int moduleIndex, boolean empty) {
		this.empty = empty;
		this.moduleIndex = moduleIndex;
		solved = true;

		try {
			emptyFrame = ImageIO.read(getClass().getResourceAsStream("/modules/moduleEmpty.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// GETTERS / SETTERS
	// ------------------------------------------------------------------

	/**
	 * Get the module coordinate offset based on its index
	 * 
	 * @return an int array where [0] is the x and [1] is the y position of the
	 *         module
	 */
	public int[] getModuleOffset() {
		return new int[] { moduleX(moduleIndex), moduleY(moduleIndex) };
	}

	/**
	 * the modules x coorinates Returns based on the index
	 * 
	 * @param index
	 *            the index
	 * @return the modules x coorinates
	 */
	private int moduleX(int index) {
		int ret = 0;
		if (index > 2)
			index -= 3;
		ret = index * 500;
		return ret;
	}

	/**
	 * the modules y coorinates Returns based on the index
	 * 
	 * @param index
	 *            the index
	 * @return the modules y coorinates
	 */
	public int moduleY(int index) {
		int ret = 0;
		if (index > 2)
			ret = 500;
		return ret;
	}

	/**
	 * @return the moduleIndex
	 */
	public int getModuleIndex() {
		return moduleIndex;
	}

	/**
	 * @return the solved
	 */
	public boolean isSolved() {
		return solved;
	}

	/**
	 * @param solved
	 *            the solved to set
	 */
	public void setSolved(boolean solved) {
		if (solved)
			moduleSolvedSound.start();
		this.solved = solved;
	}

	/**
	 * @return the hitboxes
	 */
	public Hitbox[] getHitboxes() {
		return hitboxes;
	}

	/**
	 * @param hitboxes
	 *            the hitboxes to set
	 */
	public void setHitboxes(Hitbox[] hitboxes) {
		this.hitboxes = hitboxes;
	}

	/**
	 * @return the empty
	 */
	public boolean isEmpty() {
		return empty;
	}

}
