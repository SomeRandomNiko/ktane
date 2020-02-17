package modules;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Module {
	private int moduleIndex;
	private BufferedImage frameImage;
	private boolean solved;

	/**
	 * Updates the modules
	 * 
	 * @param g
	 *            the canvas
	 */
	public void drawFrame(Graphics g) {
		if (isSolved()) {
			try {
				setFrameImage(ImageIO.read(getClass().getResourceAsStream("/modules/moduleFrameUnsolved.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		g.drawImage(frameImage, getModuleOffset()[0], getModuleOffset()[1], null);
	}

	public void update(Graphics g) {

	}

	public Module(int moduleIndex) {
		this.moduleIndex = moduleIndex;
		setSolved(false);
		try {
			setFrameImage(ImageIO.read(getClass().getResourceAsStream("/modules/moduleFrameUnsolved.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int[] getModuleOffset() {
		return new int[] { moduleX(moduleIndex), moduleY(moduleIndex) };
	}

	private int moduleX(int index) {
		int ret = 0;
		if (index > 2)
			index -= 3;
		ret = index * 500;
		return ret;
	}

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
	 * @return the frameImage
	 */
	public BufferedImage getFrameImage() {
		return frameImage;
	}

	/**
	 * @param frameImage
	 *            the frameImage to set
	 */
	public void setFrameImage(BufferedImage frameImage) {
		this.frameImage = frameImage;
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

}
