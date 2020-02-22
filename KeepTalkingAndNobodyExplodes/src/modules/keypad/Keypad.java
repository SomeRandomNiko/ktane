package modules.keypad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import bomb.Bomb;
import modules.Hitbox;
import modules.Module;

public class Keypad extends Module {

	ArrayList<Integer> symbolList = new ArrayList<>();
	ArrayList<Integer> inter = new ArrayList<>();
	private Key[] keys;
	private int row;
	private int order = 0;
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
				hitboxes[i] = new Hitbox(getModuleOffset()[0] + 50 + (i < 2 ? i : i - 2) * (160 + 40), getModuleOffset()[1] + 68 + (i < 2 ? 0 : 1) * (160 + 40), 183, 183);
			} else {
				i--;
			}
		}
		for (int x : symbols[row])
			if (symbolList.contains(x))
				inter.add(x);
		setHitboxes(hitboxes);
	}

	public void update(Graphics g) {
		g.drawImage(keypad, getModuleOffset()[0], getModuleOffset()[1], null);
		for (int i = 0; i < keys.length; i++) {
			g.drawImage(keys[i].getSymbol(), getModuleOffset()[0] + 62 + (i < 2 ? i : i - 2) * (160 + 40), getModuleOffset()[1] + 90 + (i < 2 ? 0 : 1) * (160 + 40), null);
			
			if(keys[i].isPressed()) {
				g.setColor(new Color(0x06c000));
				g.fillRect(getModuleOffset()[0] + 126 + (i < 2 ? i : i - 2) * 200, getModuleOffset()[1] + 85 + (i < 2 ? 0 : 1) * 200, 32, 14);
			}
			
			if (getHitboxes()[i].isClick() && !keys[i].isPressed()) {
				if (isInOrder(keys[i].getSymbolNumber(), order)) {
					keys[i].setPressed(true);
					order++;
				} else {
					Bomb.setExplode(true);
				}
			}
			
			if(order >= 4) {
				setSolved(true);
			}
		}
	}

	private boolean isInOrder(int symbolNumber, int order) {
		boolean ret = false;
		if (inter.get(order) == symbolNumber) {
			ret = true;
		}
		return ret;
	}
}
