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

		player[0] = new Random().nextInt(6);
		player[1] = new Random().nextInt(6);

		do {
			flag[0] = new Random().nextInt(6);
			flag[1] = new Random().nextInt(6);
		} while (player[0] == flag[0] && player[1] == flag[1]);

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/modules/maze/mazes/maze" + mazeIndex + ".txt")));
			String zeile;
			String[] temp;
			System.out.println("MazeIndex:" + mazeIndex);
			for (int i = 0; (zeile = reader.readLine()) != null; i++) {
				temp = zeile.split(";");
				for (int j = 0; j < temp.length; j++) {
					maze[i][j] = Integer.valueOf(temp[j]);
					System.out.print(maze[i][j] + " ");
				}
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

	public void moveLeft() {
		System.out.println(maze[player[0]][player[1]]);
		if (maze[player[0]][player[1]] != 1 && maze[player[0]][player[1]] != 3)
			player[0]--;
		else
			Bomb.setExplode(true);
	}

	public void moveRight() {
		System.out.println(maze[player[0] + 1][player[1]]);
		if (maze[player[0] + 1][player[1]] != 1 && maze[player[0]][player[1]] != 3)
			player[0]++;
		else
			Bomb.setExplode(true);
	}

	public void moveUp() {
		System.out.println(maze[player[0]][player[1]]);
		if (maze[player[0]][player[1]] != 2 && maze[player[0]][player[1]] != 3)
			player[1]--;
		else
			Bomb.setExplode(true);
	}

	public void moveDown() {
		System.out.println(maze[player[0]][player[1] + 1]);
		if (maze[player[0]][player[1] + 1] != 2 && maze[player[0]][player[1]] != 3)
			player[1]++;
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
	 * @return the mazeIndex
	 */
	public int getMazeIndex() {
		return mazeIndex;
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
