package modules.wires;

import java.awt.Graphics;
import java.util.Random;

import bomb.Bomb;
import modules.Hitbox;
import modules.Module;

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
		Hitbox[] hitboxes = new Hitbox[6];

		Random random = new Random();

		// Generates at least 3 Wires
		wireCount = random.nextInt(4) + 3;
		for (int i = 0; i < wireCount; i++) {
			wires[i] = new Wire();
			wires[i].generateRandom(false);
			wires[i].setIndex(i);

			// Set the hitbox
			hitboxes[i] = new Hitbox(getModuleOffset()[0] + i * wires[i].getImage().getWidth() + 40 + 20, getModuleOffset()[1] + 70, wires[i].getImage().getWidth() - 40,
					wires[i].getImage().getHeight() - 140);
		}

		// Generates optional wires
		for (int i = wireCount; i < 6; i++) {
			wires[i] = new Wire();
			wires[i].generateBlank();
			wires[i].setIndex(i);
		}
		setHitboxes(hitboxes);
	}

	/**
	 * Updates the Graphics canvas for the wire module
	 */
	public void update(Graphics g) {

		// Iterates over all the wires in the module
		for (int i = 0; i < wires.length; i++) {

			// Checks if the wire has been cut
			if (getHitboxes() != null && getHitboxes()[i] != null && getHitboxes()[i].isClick() && !wires[i].isCut()) {
				wires[i].setCut(true);
				int wireToCut = 0;
				switch (wireCount) {
					// 3 Wires
					// -----------------------------------------------------------------------
					case 3:
						// Default case
						wireToCut = wireCount - 1;

						// If there are no red wires, cut the second wire.
						if (getRedCount() == 0)
							wireToCut = 1;

						// Else, if the last wire is white, cut the last wire.
						else if (wires[wireCount - 1].getColor().equals("white"))
							wireToCut = wireCount - 1;

						// Else, if there is more than one blue wire, cut the last blue wire
						else if (getBlueCount() > 1) {
							for (int j = wireCount - 1; j >= 0; j--) {
								if (wires[j].getColor().equals("blue")) {
									wireToCut = j;
									break;
								}
							}
						}
						break;
					// 4 Wires
					// -----------------------------------------------------------------------
					case 4:
						wireToCut = 1;
						// If there is more than one red wire and the last digit of the serial number is
						// odd, cut the last red wire
						if (getRedCount() > 1 && !Bomb.getSerialNumber().lastDigitIsEven()) {
							for (int j = wireCount - 1; j >= 0; j--) {
								if (wires[j].getColor().equals("red")) {
									wireToCut = j;
									break;
								}
							}
							// Otherwise, if the last wire is yellow and there are no red wires, cut the
							// first wire
						} else if (wires[wireCount - 1].getColor().equals("yellow") && getRedCount() == 0)
							wireToCut = 0;
						// Otherwise, if there is exactly one blue wire, cut the first wire.
						else if (getBlueCount() == 1)
							wireToCut = 0;
						// If there is more than one yellow wire, cut the last wire.
						else if (getYellowCount() > 1)
							wireToCut = wireCount - 1;
						break;
					// 5 Wires
					// -----------------------------------------------------------------------
					case 5:
						wireToCut = 0;
						// If the last wire is black, and the last digit of the serial number is odd,
						// cut the fourth wire.
						if (wires[wireCount - 1].getColor().equals("black") && !Bomb.getSerialNumber().lastDigitIsEven())
							wireToCut = 3;
						// Otherwise, if there is exactly one red wire and there is more than one yellow
						// wire, cut the first wire.
						else if (getRedCount() == 1 && getYellowCount() > 1)
							wireToCut = 0;
						// Otherwise, if there are no black wires, cut the second wire.
						else if (getBlackCount() == 0)
							wireToCut = 1;
						break;

					// 6 Wires
					// -----------------------------------------------------------------------
					case 6:
						wireToCut = 3;
						// If there are no yellow wires and the last digit of the serial number is odd,
						// cut the third wire
						if (getYellowCount() == 0 && !Bomb.getSerialNumber().lastDigitIsEven())
							wireToCut = 2;
						// Otherwise, if there is exactly one yellow wire and there is more than one
						// white wire, cut the fourth wire.
						else if (getYellowCount() == 1 && getWhiteCount() > 1)
							wireToCut = 3;
						// Otherwise if there are no red wires, cut the last wire.
						else if (getRedCount() == 0)
							wireToCut = wireCount - 1;
						break;
				}
				if (i == wireToCut)
					setSolved(true);
				else {
					System.out.println("EXPLODE");
					Bomb.setExplode(true);
				}
			}

			// Draws the wire image
			try {
				g.drawImage(wires[i].getImage(), getModuleOffset()[0] + wires[i].getIndex() * wires[i].getImage().getWidth() + 40, getModuleOffset()[1], null);
			} catch (NullPointerException e) {
			}
		}

	}

	// GETTERS / SETTERS
	// -----------------------------------------------------------------------

	private int getRedCount() {
		int ret = 0;
		for (Wire x : wires)
			if (x.getColor().equals("red"))
				ret++;
		return ret;
	}

	private int getBlueCount() {
		int ret = 0;
		for (Wire x : wires)
			if (x.getColor().equals("blue"))
				ret++;
		return ret;
	}

	private int getYellowCount() {
		int ret = 0;
		for (Wire x : wires)
			if (x.getColor().equals("yellow"))
				ret++;
		return ret;
	}

	private int getWhiteCount() {
		int ret = 0;
		for (Wire x : wires)
			if (x.getColor().equals("white"))
				ret++;
		return ret;
	}

	private int getBlackCount() {
		int ret = 0;
		for (Wire x : wires)
			if (x.getColor().equals("black"))
				ret++;
		return ret;
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
