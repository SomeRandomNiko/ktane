package main.bomb.modules.maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import main.bomb.Bomb;

public class Maze {

	private int[][] maze = new int[6][6];
	private int mazeIndex;
	private int[] player = new int[2];
	private int[] flag = new int[2];
	private ArrayList<int[]> circles = new ArrayList<>();

	public Maze() {
		mazeIndex = new Random().nextInt(9);
		setCirclePos();

		// Random player position
		player[0] = new Random().nextInt(6);
		player[1] = new Random().nextInt(6);

		// Random flag position
		do {
			flag[0] = new Random().nextInt(6);
			flag[1] = new Random().nextInt(6);
		} while (player[0] == flag[0] && player[1] == flag[1]);

		// Read the maze from the file into the array
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/modules/maze/mazes/maze" + mazeIndex + ".txt")));
			char read;
			for (int i = 0; i < maze.length; i++) {
				for (int j = 0; (read = (char) reader.read()) != -1 && j < maze[i].length; j++) {
					if (Character.isDigit(read)) {
						maze[i][j] = read - 48;
					} else {
						j--;
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the cricle pos for each maze
	 */
	private void setCirclePos() {
		switch (mazeIndex) {
			case 0:
				circles.add(new int[] { 0, 1 });
				circles.add(new int[] { 5, 2 });
				break;
			case 1:
				circles.add(new int[] { 4, 1 });
				circles.add(new int[] { 1, 3 });
				break;
			case 2:
				circles.add(new int[] { 3, 3 });
				circles.add(new int[] { 5, 3 });
				break;
			case 3:
				circles.add(new int[] { 0, 0 });
				circles.add(new int[] { 0, 3 });
				break;
			case 4:
				circles.add(new int[] { 4, 2 });
				circles.add(new int[] { 3, 5 });
				break;
			case 5:
				circles.add(new int[] { 4, 0 });
				circles.add(new int[] { 2, 4 });
				break;
			case 6:
				circles.add(new int[] { 1, 0 });
				circles.add(new int[] { 1, 5 });
				break;
			case 7:
				circles.add(new int[] { 3, 0 });
				circles.add(new int[] { 2, 3 });
				break;
			case 8:
				circles.add(new int[] { 2, 1 });
				circles.add(new int[] { 0, 4 });
				break;
		}

	}

	// 0 - No walls
	// 1 - left wall
	// 2 - top wall
	// 3 - left and top wall

	/**
	 * Move the player up
	 */
	public void moveUp() {
		if (player[1] - 1 >= 0 && maze[player[1]][player[0]] != 2 && maze[player[1]][player[0]] != 3)
			player[1]--;
		else
			Bomb.setExplode(true);
	}

	/**
	 * Move the player down
	 */
	public void moveDown() {
		if (player[1] + 1 < 6 && maze[player[1] + 1][player[0]] != 2 && maze[player[1] + 1][player[0]] != 3)
			player[1]++;
		else
			Bomb.setExplode(true);
	}

	/**
	 * Move the player left
	 */
	public void moveLeft() {
		if (player[0] - 1 >= 0 && maze[player[1]][player[0]] != 1 && maze[player[1]][player[0]] != 3)
			player[0]--;
		else
			Bomb.setExplode(true);
	}

	/**
	 * Move the player right
	 */
	public void moveRight() {
		if (player[0] + 1 < 6 && maze[player[1]][player[0] + 1] != 1 && maze[player[1]][player[0] + 1] != 3)
			player[0]++;
		else
			Bomb.setExplode(true);
	}

	// GETTERS / SETTERS
	// ---------------------------------------------------------------------------------------
	/**
	 * @return the maze
	 */
	public int[][] getMaze() {
		return maze;
	}

	/**
	 * @return the circles
	 */
	public ArrayList<int[]> getCircles() {
		return circles;
	}

	/**
	 * @return the player
	 */
	public int[] getPlayer() {
		return player;
	}

	/**
	 * @return the flag
	 */
	public int[] getFlag() {
		return flag;
	}
}
