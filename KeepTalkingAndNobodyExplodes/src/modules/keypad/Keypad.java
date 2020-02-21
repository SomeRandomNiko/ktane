package modules.keypad;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import modules.Hitbox;
import modules.Module;

public class Keypad extends Module {

	ArrayList<Integer> symbolList = new ArrayList<>();
	private Key[] keys;
	private int row;
	private BufferedImage keypad;
	private final int[][] symbols = {
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

		try {
			keypad = ImageIO.read(getClass().getResourceAsStream("/modules/keypad/keypad.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Hitbox[] hitboxes = new Hitbox[6];
		keys = new Key[4];
		row = new Random().nextInt(4);
		for (int i = 0; symbolList.size() < 4; i++) {
			int symbol = new Random().nextInt(7);
			if (!symbolList.contains(symbols[row][symbol])) {
				symbolList.add(symbols[row][symbol]);
				keys[i] = new Key(i, symbolList.get(i));
				hitboxes[i] = new Hitbox(getModuleOffset()[0] + (i < 2 ? i : i - 2) * (174 + 16) + 61, getModuleOffset()[1] + (i < 2 ? 0 : 1) * (174 + 16) + 83, 174, 174);
			} else {
				i--;
			}
		}
		setHitboxes(hitboxes);
	}

	public void update(Graphics g) {
		g.drawImage(keypad, getModuleOffset()[0], getModuleOffset()[1], null);
		for (int i = 0; i < keys.length; i++) {
			g.drawImage(keys[i].getSymbol(), getModuleOffset()[0] + (i < 2 ? i : i - 2) * (174 + 16) + 61, getModuleOffset()[1] + (i < 2 ? 0 : 1) * (174 + 16) + 83, 174, 174, null);
		}
	}
}
