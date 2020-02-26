package main.bomb.modules.memory;

import java.util.ArrayList;
import java.util.Random;

public class Stage {
	private int display;
	private ArrayList<Integer> buttons = new ArrayList<>();

	public Stage() {
		Random random = new Random();
		int number;
		while (buttons.size() < 4) {
			number = random.nextInt(4);
			if (!buttons.contains(number))
				buttons.add(number);
		}
		display = random.nextInt(5) + 1;
	}

	// GETTERS / SETTERS
	// ----------------------------------------------------------------------

	/**
	 * @return the display
	 */
	public int getDisplay() {
		return display;
	}

	/**
	 * @return the buttons
	 */
	public ArrayList<Integer> getButtons() {
		return buttons;
	}
}
