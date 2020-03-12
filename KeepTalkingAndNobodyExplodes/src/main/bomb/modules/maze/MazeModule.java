package main.bomb.modules.maze;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.bomb.modules.Hitbox;
import main.bomb.modules.Module;

public class MazeModule extends Module {
	private int[] player = new int[2];
	private int[] flag = new int[2];
	private Maze maze;
	private BufferedImage mazeImage;
	private BufferedImage circleImage;
	private BufferedImage flagImage;
	private Hitbox[] hitboxes = new Hitbox[4];

	public MazeModule(int moduleIndex) {
		super(moduleIndex);
		maze = new Maze();
		hitboxes[0] = new Hitbox(getModuleOffset()[0] + 215, getModuleOffset()[1] + 28, 70, 49);
		hitboxes[1] = new Hitbox(getModuleOffset()[0] + 423, getModuleOffset()[1] + 215, 49, 70);
		hitboxes[2] = new Hitbox(getModuleOffset()[0] + 214, getModuleOffset()[1] + 422, 70, 48);
		hitboxes[3] = new Hitbox(getModuleOffset()[0] + 27, getModuleOffset()[1] + 214, 48, 70);
		setHitboxes(hitboxes);
		try {
			mazeImage = ImageIO.read(getClass().getResource("/modules/maze/mazeImage.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void update(Graphics g) {
		g.drawImage(mazeImage, getModuleOffset()[0], getModuleOffset()[1], null);
	}
}
