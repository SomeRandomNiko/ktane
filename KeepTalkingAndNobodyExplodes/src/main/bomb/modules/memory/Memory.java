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

import main.bomb.Bomb;
import main.bomb.modules.Hitbox;
import main.bomb.modules.Module;

public class Memory extends Module {
	private Stage[] stages = new Stage[5];
	BufferedImage memoryImage;
	int stage = 0;
	Font displayFont;
	Font buttonFont;
	boolean stillClicked = false;

	private class Stage {
		private int display;
		private HashMap<Integer, Integer> buttonMap = new HashMap<>();
		private int stageIndex;

		public Stage(int index) {
			Random random = new Random();
			int number;
			for (int i = 0; buttonMap.size() < 4;) {
				number = random.nextInt(4) + 1;
				if (!buttonMap.containsKey(number)) {
					buttonMap.put(number, i + 1);
					i++;
				}
			}
			this.stageIndex = index;
			display = random.nextInt(4) + 1;
		}

		public int getButtonToPress() {
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
							return buttonMap.get(4);
						case 2:
						case 4:
							return Memory.this.getStage(1).getButtonToPress();
						case 3:
							return 1;
						default:
							return -1;
					}

				case 3:
					switch (display) {
						case 1:
							return Memory.this.getStage(2).getButtonMap().get(Memory.this.getStage(2).getButtonToPress());
						case 2:
							return Memory.this.getStage(1).getButtonMap().get(Memory.this.getStage(1).getButtonToPress());
						case 3:
							return 3;
						case 4:
							return buttonMap.get(4);
						default:
							return -1;
					}

				case 4:
					switch (display) {
						case 1:
							return Memory.this.getStage(1).getButtonToPress();
						case 2:
							return 1;
						case 3:
						case 4:
							return Memory.this.getStage(2).getButtonToPress();
						default:
							return -1;
					}

				case 5:
					switch (display) {
						case 1:
							return Memory.this.getStage(1).getButtonMap().get(Memory.this.getStage(1).getButtonToPress());
						case 2:
							return Memory.this.getStage(2).getButtonMap().get(Memory.this.getStage(2).getButtonToPress());
						case 3:
							return Memory.this.getStage(4).getButtonMap().get(Memory.this.getStage(4).getButtonToPress());
						case 4:
							return Memory.this.getStage(3).getButtonMap().get(Memory.this.getStage(3).getButtonToPress());
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
		 * @return the buttonMap
		 */
		public HashMap<Integer, Integer> getButtonMap() {
			return buttonMap;
		}
	}

	public Memory(int moduleIndex) {
		super(moduleIndex);
		try {
			memoryImage = ImageIO.read(getClass().getResourceAsStream("/modules/memory/memory.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

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

		for (int i = 0; i < stage; i++) {
			g.setColor(Color.GREEN);
			g.fillRect(getModuleOffset()[0] + 361, getModuleOffset()[1] + 271 - i * 44, 42, 36);
		}
		if (!isSolved()) {
			g.setFont(displayFont);
			g.setColor(Color.WHITE);
			g.drawString(stages[stage].getDisplayString(), getModuleOffset()[0] + 150, getModuleOffset()[1] + 270);
			g.setFont(buttonFont);

			for (int i = 0; i < stages[stage].getButtonMap().size(); i++) {
				g.setColor(new Color(0x8e7d6b));
				g.drawString("8", getModuleOffset()[0] + 97 + i * (80 + 8), getModuleOffset()[1] + 405);
				g.setColor(Color.WHITE);
				g.drawString(stages[stage].getButtonMap().get(i + 1).toString(), getModuleOffset()[0] + 97 + i * (80 + 8), getModuleOffset()[1] + 405);
			}
		} else {
			for (int i = 0; i < 4; i++) {
				g.setFont(buttonFont);
				g.setColor(new Color(0x8e7d6b));
				g.drawString("8", getModuleOffset()[0] + 97 + i * (80 + 8), getModuleOffset()[1] + 405);
			}
		}

		if (stage > 4) {
			setSolved(true);
		}

		if (!getHitboxes()[0].isClick() && !getHitboxes()[1].isClick() && !getHitboxes()[2].isClick() && !getHitboxes()[3].isClick())
			stillClicked = false;

		if (!stillClicked && !isSolved()) {
			for (int i = 0; i < getHitboxes().length; i++) {
				if (getHitboxes()[i].isClick()) {
					if (stages[stage].getButtonToPress() == i + 1) {
						stage++;
						if (stage == 5)
							setSolved(true);

						stillClicked = true;
					} else {
						Bomb.setExplode(true);
					}
				}
			}
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
