package modules;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Hitbox;

public class Module {
	private int moduleIndex;
	private BufferedImage frameImageSolved;
	private BufferedImage frameImageUnsolved;
	private boolean solved;
	private Hitbox[] hitboxes;

	/**
	 * Updates the modules
	 * 
	 * @param g
	 *            the canvas
	 */
	public void drawFrame(Graphics g) {

		if (isSolved()) {
			// Set the "module solved" image
			g.drawImage(frameImageSolved, getModuleOffset()[0], getModuleOffset()[1], null);
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
	}

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
	 * @param moduleIndex
	 *            the moduleIndex to set
	 */
	public void setModuleIndex(int moduleIndex) {
		this.moduleIndex = moduleIndex;
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

}
