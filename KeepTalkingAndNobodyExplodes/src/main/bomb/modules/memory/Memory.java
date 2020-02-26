package main.bomb.modules.memory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.bomb.modules.Hitbox;
import main.bomb.modules.Module;

public class Memory extends Module {
	private Stage[] stages = new Stage[5];
	BufferedImage memoryImage;
	int stage = 3;

	public Memory(int moduleIndex) {
		super(moduleIndex);
		try {
			memoryImage = ImageIO.read(getClass().getResourceAsStream("/modules/memory/memory.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < stages.length; i++) {
			stages[i] = new Stage();
		}
		Hitbox[] hitboxes = new Hitbox[4];
		for (int i = 0; i < hitboxes.length; i++) {
			hitboxes[i] = new Hitbox(getModuleOffset()[0] + 79 + i * (80 + 8), getModuleOffset()[1] + 334, 78, 86);
		}
		setHitboxes(hitboxes);
	}

	public void update(Graphics g) {
		g.drawImage(memoryImage, getModuleOffset()[0], getModuleOffset()[1], null);
		for (int i = 0; i < stage; i++) {
			g.setColor(Color.GREEN);
			g.fillRect(getModuleOffset()[0] + 361, getModuleOffset()[1] + 271 - i * 44, 42, 36);
		}
	}
}
