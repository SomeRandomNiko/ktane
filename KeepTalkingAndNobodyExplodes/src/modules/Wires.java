package modules;

import java.awt.Graphics;

import modules.wires.Wire;

public class Wires extends Module {
	private Wire[] wires;
	private int wireCount;

	public Wires(int moduleIndex) {
		super(moduleIndex);
		wires = new Wire[6];
		wireCount = 0;
	}

	/**
	 * Generates a random Wire module
	 */
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

	/**
	 * Updates the Graphics canvas for the wire module
	 */

	public void update(Graphics g) {
		for (int i = 0; i < wires.length; i++) {
			try {
				g.drawImage(wires[i].getImage(), getModuleOffset()[0] + wires[i].getIndex() * wires[i].getImage().getWidth() + 40, getModuleOffset()[1], null);
			} catch (NullPointerException e) {
			}
		}
	}

	/**
	 * Returns the wire index based on its coordinates
	 * 
	 * @param xy
	 *            The x and y coordinates
	 * @return the wire index
	 */
	public int getWireIndex(int[] xy) {
		int moduleIndex = getModuleIndex();
		if (xy[1] > 500)
			moduleIndex -= 3;
		int ret = -1;
		int x = getModuleOffset()[0];
		if (xy[0] >= x + 40 && xy[0] <= 500 * moduleIndex - 40 + 500) {
			ret = (int) ((xy[0] - 500 * moduleIndex - 40) / 70);
		}
		return ret;
	}

	/**
	 * @return the wires
	 */
	public Wire[] getWires() {
		return wires;
	}

	/**
	 * @return the wireCount
	 */
	public int getWireCount() {
		return wireCount;
	}
}
