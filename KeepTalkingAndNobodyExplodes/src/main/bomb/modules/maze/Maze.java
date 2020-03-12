package main.bomb.modules.maze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Maze {

	private int[][] maze = new int[13][13];
	private int mazeIndex;
	private ArrayList<int[]> circles = new ArrayList<>();

	public Maze() {
		mazeIndex = new Random().nextInt(9);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/modules/maze/mazes/" + mazeIndex + ".txt")));

			for (int i = 0; i < maze.length; i++) {
				for (int j = 0; j < maze[i].length; j++) {
					maze[i][j] = reader.read();
					if (maze[i][j] == 2)
						circles.add(new int[] {
								i, j });
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
}
