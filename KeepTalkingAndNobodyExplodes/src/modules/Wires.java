package modules;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import modules.wires.Wire;

public class Wires {
	private Wire[] wires;
	private BufferedImage frameImage;
	private int moduleIndex;
	private boolean solved;
	private int wireCount;

	public Wires(int moduleIndex) {
		wires = new Wire[6];
		solved = false;
		this.moduleIndex = moduleIndex;
		try {
			frameImage = ImageIO.read(getClass().getResourceAsStream("/modules/moduleFrameUnsolved.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		wireCount = 0;
	}

	public void generateRandom() {
		for (int i = 0; i < 3; i++) {
			wires[i] = new Wire();
			wires[i].generateRandom(false);
			wires[i].setIndex(i);
			wireCount++;
		}
		for (int i = 3; i < 6; i++) {
			wires[i] = new Wire();
			wires[i].generateRandom(true);
			if (!wires[i].getColor().equals("blank"))
				wireCount++;
			wires[i].setIndex(i);
		}
	}

	public void update(Graphics g) {
		int modOffx = Coordinates.moduleX(moduleIndex);
		int modOffy = Coordinates.moduleY(moduleIndex);
		g.drawImage(frameImage, modOffx, modOffy, null);
		for (int i = 0; i < wires.length; i++) {
			try {
				g.drawImage(wires[i].getImage(), modOffx + wires[i].getIndex() * wires[i].getImage().getWidth() + 40, modOffy, null);
			} catch (NullPointerException e) {
			}
		}
	}

	/**
	 * @return the wires
	 */
	public Wire[] getWires() {
		return wires;
	}

	/**
	 * @return the frameImage
	 */
	public BufferedImage getFrameImage() {
		return frameImage;
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
	 * @return the wireCount
	 */
	public int getWireCount() {
		return wireCount;
	}

	/**
	 * @param wires
	 *            the wires to set
	 */
	public void setWires(Wire[] wires) {
		this.wires = wires;
	}

	/**
	 * @param frameImage
	 *            the frameImage to set
	 */
	public void setFrameImage(BufferedImage frameImage) {
		this.frameImage = frameImage;
	}

	/**
	 * @param moduleIndex
	 *            the moduleIndex to set
	 */
	public void setModuleIndex(int moduleIndex) {
		this.moduleIndex = moduleIndex;
	}

	/**
	 * @param solved
	 *            the solved to set
	 */
	public void setSolved(boolean solved) {
		this.solved = solved;
	}

	/**
	 * @param wireCount
	 *            the wireCount to set
	 */
	public void setWireCount(int wireCount) {
		this.wireCount = wireCount;
	}
}
