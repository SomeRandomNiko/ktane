package main.bomb.modules.keypad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

import main.bomb.Bomb;
import main.bomb.modules.Hitbox;
import main.bomb.modules.Module;

public class Keypad extends Module {

	// Contains all the symbols of the selected row
	ArrayList<Integer> symbolList = new ArrayList<>();

	// Contains the 4 Symbols in order
	ArrayList<Integer> sortedSymbols = new ArrayList<>();

	private Key[] keys;

	private int row;
	private int order = 0;

	private BufferedImage keypad;

	// Contains all the available symbols
	private final int[][] availableSymbols = {
			{
					10, 24, 11, 23, 19, 21, 6 },
			{
					1, 10, 6, 8, 15, 21, 4 },
			{
					13, 20, 8, 17, 26, 11, 15 },
			{
					22, 5, 12, 19, 17, 4, 16 },
			{
					7, 16, 12, 6, 5, 3, 14 },
			{
					22, 1, 9, 25, 7, 2, 18 } };

	public Keypad(int moduleIndex) {
		super(moduleIndex);

		// Open the keypad image
		try {
			keypad = ImageIO.read(getClass().getResourceAsStream("/modules/keypad/keypad.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Hitbox[] hitboxes = new Hitbox[6];
		keys = new Key[4];

		// Select a random row
		row = new Random().nextInt(6);

		// Set the keys
		for (int i = 0; symbolList.size() < 4; i++) {

			int symbol = new Random().nextInt(7);

			if (!symbolList.contains(availableSymbols[row][symbol])) {

				symbolList.add(availableSymbols[row][symbol]);
				keys[i] = new Key(i, symbolList.get(i));

				hitboxes[i] = new Hitbox(getModuleOffset()[0] + 50 + (i < 2 ? i : i - 2) * (160 + 40), getModuleOffset()[1] + 68 + (i < 2 ? 0 : 1) * (160 + 40), 183, 183);
			} else {
				i--;
			}
		}

		// Sort the symbols
		for (int x : availableSymbols[row])
			if (symbolList.contains(x))
				sortedSymbols.add(x);

		setHitboxes(hitboxes);
	}

	/**
	 * Update the module
	 */
	public void update(Graphics g) {

		// Draw the keypad
		g.drawImage(keypad, getModuleOffset()[0], getModuleOffset()[1], null);

		// Draw the symbols
		for (int i = 0; i < keys.length; i++) {
			g.drawImage(keys[i].getSymbol(), getModuleOffset()[0] + 62 + (i < 2 ? i : i - 2) * (160 + 40), getModuleOffset()[1] + 90 + (i < 2 ? 0 : 1) * (160 + 40), null);

			// If the correct key is pressed, draw the green light
			if (keys[i].isPressed()) {
				g.setColor(new Color(0x06c000));
				g.fillRect(getModuleOffset()[0] + 126 + (i < 2 ? i : i - 2) * 200, getModuleOffset()[1] + 85 + (i < 2 ? 0 : 1) * 200, 32, 14);
			}

			// If a key is pressed, check the order, otherwise explode
			if (getHitboxes()[i].isPressed() && !keys[i].isPressed()) {

				if (isInOrder(keys[i].getSymbolNumber(), order)) {
					keys[i].setPressed(true);
					order++;
				} else {
					Bomb.setExplode(true);
				}
			}

			// If all the keys are pressed, set the module to be solved
			if (order == 4)
				setSolved(true);
		}
	}

	/**
	 * Checks if the pressed key is in the correct order
	 * 
	 * @param symbolNumber
	 *            the number of the symbol
	 * @param order
	 *            the order in which the key is pressed
	 * @return true, if the key was pressed in the correct order
	 */
	private boolean isInOrder(int symbolNumber, int order) {
		boolean ret = false;

		if (sortedSymbols.get(order) == symbolNumber)
			ret = true;

		return ret;
	}
}
