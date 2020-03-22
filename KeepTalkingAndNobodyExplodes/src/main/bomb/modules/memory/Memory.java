package main.bomb.modules.memory;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

import main.Timing;
import main.bomb.Bomb;
import main.bomb.modules.Hitbox;
import main.bomb.modules.Module;

public class Memory extends Module {
	private Stage[] stages = new Stage[5];
	BufferedImage memoryImage;
	int stage = 0;
	Font displayFont;
	Font buttonFont;

	// Delay between stages
	Timing timing = new Timing(2000);

	boolean drawBlank = false;

	private class Stage {
		private int display;
		private HashMap<Integer, Integer> positionByLabel = new HashMap<>();
		private HashMap<Integer, Integer> labelByPosition = new HashMap<>();
		private int stageIndex;

		// Generate random Stages
		public Stage(int index) {
			Random random = new Random();
			int number;
			for (int i = 0; positionByLabel.size() < 4;) {
				number = random.nextInt(4) + 1;
				if (!positionByLabel.containsKey(number)) {
					positionByLabel.put(number, i + 1);
					labelByPosition.put(i + 1, number);
					i++;
				}
			}
			this.stageIndex = index;
			display = random.nextInt(4) + 1;
		}

		/**
		 * Determines the position of the Button to press
		 * 
		 * @return the position of the button to press
		 */
		public int getButtonPosToPress() {
			switch (stageIndex + 1) {
				case 1:
					switch (display) {
						case 1:
							return 2;
						case 2:
							return 2;
						case 3:
							return 3;
						case 4:
							return 4;
					}

				case 2:
					switch (display) {
						case 1:
							return getPositionByLabel(4);
						case 2:
						case 4:
							return Memory.this.getStage(1).getButtonPosToPress();
						case 3:
							return 1;
						default:
							return -1;
					}

				case 3:
					switch (display) {
						case 1:
							return getPositionByLabel(Memory.this.getStage(2).getLabelByPosition(Memory.this.getStage(2).getButtonPosToPress()));
						case 2:
							return getPositionByLabel(Memory.this.getStage(1).getLabelByPosition(Memory.this.getStage(1).getButtonPosToPress()));
						case 3:
							return 3;
						case 4:
							return getPositionByLabel(4);
						default:
							return -1;
					}

				case 4:
					switch (display) {
						case 1:
							return Memory.this.getStage(1).getButtonPosToPress();
						case 2:
							return 1;
						case 3:
						case 4:
							return Memory.this.getStage(2).getButtonPosToPress();
						default:
							return -1;
					}

				case 5:
					switch (display) {
						case 1:
							return getPositionByLabel(Memory.this.getStage(1).getLabelByPosition(Memory.this.getStage(1).getButtonPosToPress()));
						case 2:
							return getPositionByLabel(Memory.this.getStage(2).getLabelByPosition(Memory.this.getStage(2).getButtonPosToPress()));
						case 3:
							return getPositionByLabel(Memory.this.getStage(4).getLabelByPosition(Memory.this.getStage(4).getButtonPosToPress()));
						case 4:
							return getPositionByLabel(Memory.this.getStage(3).getLabelByPosition(Memory.this.getStage(3).getButtonPosToPress()));
						default:
							return -1;
					}
				default:
					return -1;
			}
		}

		// GETTERS / SETTERS
		// ----------------------------------------------------------------------

		/**
		 * @return the display
		 */
		public String getDisplayString() {
			return String.valueOf(display);
		}

		/**
		 * @return the position by label
		 */
		public int getPositionByLabel(int label) {
			return positionByLabel.get(label);
		}

		/**
		 * @return the label by position
		 */
		public int getLabelByPosition(int position) {
			return labelByPosition.get(position);
		}

		/**
		 * @return the button HashMap
		 */
		public HashMap<Integer, Integer> getButtonMap() {
			return positionByLabel;
		}
	}

	public Memory(int moduleIndex) {
		super(moduleIndex);
		try {
			memoryImage = ImageIO.read(getClass().getResourceAsStream("/modules/memory/memory.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Generate stages
		for (int i = 0; i < stages.length; i++) {
			stages[i] = new Stage(i);
		}

		Hitbox[] hitboxes = new Hitbox[4];
		for (int i = 0; i < hitboxes.length; i++) {
			hitboxes[i] = new Hitbox(getModuleOffset()[0] + 79 + i * (80 + 8), getModuleOffset()[1] + 334, 78, 86);
		}

		// Create the font
		try {
			displayFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/modules/button/buttonFont.ttf")).deriveFont(200f);
			buttonFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/timer/timerFont.ttf")).deriveFont(50f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		setHitboxes(hitboxes);
	}

	public void update(Graphics g) {
		g.drawImage(memoryImage, getModuleOffset()[0], getModuleOffset()[1], null);

		// Draw the stage lights
		for (int i = 0; i < stage; i++) {
			g.setColor(Color.GREEN);
			g.fillRect(getModuleOffset()[0] + 361, getModuleOffset()[1] + 271 - i * 44, 42, 36);
		}

		// Draw the display number
		if (!isSolved() && !timing.counting()) {
			g.setFont(displayFont);
			g.setColor(Color.WHITE);
			g.drawString(stages[stage].getDisplayString(), getModuleOffset()[0] + 150, getModuleOffset()[1] + 270);
			g.setFont(buttonFont);

			if (stages[stage].getButtonMap() != null)
				// Draw the buttons
				for (int i = 0; i < stages[stage].getButtonMap().size(); i++) {
					g.setColor(new Color(0x8e7d6b));
					g.drawString("8", getModuleOffset()[0] + 97 + i * (80 + 8), getModuleOffset()[1] + 405);
					g.setColor(Color.WHITE);
					g.drawString(String.valueOf(stages[stage].getLabelByPosition(i + 1)), getModuleOffset()[0] + 97 + i * (80 + 8), getModuleOffset()[1] + 405);
				}
		} else {
			drawBlank(g);
		}

		// If the correct button was pressed, go to the next stage. If the stage is 5,
		// set the module to be solved
		if (!isSolved()) {
			for (int i = 0; i < getHitboxes().length; i++) {
				if (getHitboxes()[i].isClick()) {
					if (stages[stage].getButtonPosToPress() == i + 1) {
						timing.start();
						stage++;
						if (stage == 5)
							setSolved();

					} else {
						Bomb.explode();
					}
				}
			}
		}
	}

	// Draw a blank module
	private void drawBlank(Graphics g) {
		for (int i = 0; i < 4; i++) {
			g.setFont(buttonFont);
			g.setColor(new Color(0x8e7d6b));
			g.drawString("8", getModuleOffset()[0] + 97 + i * (80 + 8), getModuleOffset()[1] + 405);
		}
	}

	/**
	 * Returns the Stage based on the index, starting with 1
	 * 
	 * @param index
	 *            the index of the stage
	 * @return the Stage based on the index
	 */
	public Stage getStage(int index) {
		return stages[index - 1];
	}

}
