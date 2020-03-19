package main.bomb.modules.maze;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.bomb.modules.Hitbox;
import main.bomb.modules.Module;

public class MazeModule extends Module {
	private Maze maze;
	private BufferedImage mazeImage;
	private BufferedImage circleImage;
	private BufferedImage playerImage;
	private BufferedImage flagImage;
	private Hitbox[] hitboxes = new Hitbox[4];
	boolean canBePressed = true;

	public MazeModule(int moduleIndex) {
		super(moduleIndex);
		maze = new Maze();

		// Add hitboxes
		hitboxes[0] = new Hitbox(getModuleOffset()[0] + 215, getModuleOffset()[1] + 28, 70, 49);
		hitboxes[1] = new Hitbox(getModuleOffset()[0] + 423, getModuleOffset()[1] + 215, 49, 70);
		hitboxes[2] = new Hitbox(getModuleOffset()[0] + 214, getModuleOffset()[1] + 422, 70, 48);
		hitboxes[3] = new Hitbox(getModuleOffset()[0] + 27, getModuleOffset()[1] + 214, 48, 70);
		setHitboxes(hitboxes);

		// Load the images
		try {
			mazeImage = ImageIO.read(getClass().getResource("/modules/maze/mazeImage.png"));
			circleImage = ImageIO.read(getClass().getResource("/modules/maze/circleImage.png"));
			flagImage = ImageIO.read(getClass().getResource("/modules/maze/flagImage.png"));
			playerImage = ImageIO.read(getClass().getResource("/modules/maze/playerImage.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Update the graphics and functionality of the module
	 */
	public void update(Graphics g) {
		g.drawImage(mazeImage, getModuleOffset()[0], getModuleOffset()[1], null);

		if (!isSolved())
			g.drawImage(playerImage, getModuleOffset()[0] + 99 + maze.getPlayer()[0] * 51, getModuleOffset()[1] + 99 + maze.getPlayer()[1] * 51, null);
		g.drawImage(flagImage, getModuleOffset()[0] + 99 + maze.getFlag()[0] * 51, getModuleOffset()[1] + 99 + maze.getFlag()[1] * 51, null);

		for (int i = 0; i < maze.getCircles().size(); i++) {
			g.drawImage(circleImage, getModuleOffset()[0] + 99 + maze.getCircles().get(i)[0] * 51, getModuleOffset()[1] + 99 + maze.getCircles().get(i)[1] * 51, null);
		}

		// Move the player
		if (!isSolved()) {
			if (getHitboxes()[0].isPressed()) {
				if (canBePressed) {
					canBePressed = false;
					maze.moveUp();
				}
			} else if (getHitboxes()[1].isPressed()) {
				if (canBePressed) {
					canBePressed = false;
					maze.moveRight();
				}
			} else if (getHitboxes()[2].isPressed()) {
				if (canBePressed) {
					canBePressed = false;
					maze.moveDown();
				}
			} else if (getHitboxes()[3].isPressed()) {
				if (canBePressed) {
					canBePressed = false;
					maze.moveLeft();
				}
			} else {
				canBePressed = true;
			}

			// If the player is at the flag set the module to be solved
			if (maze.getFlag()[0] == maze.getPlayer()[0] && maze.getFlag()[1] == maze.getPlayer()[1])
				setSolved(true);
		}
	}
}
