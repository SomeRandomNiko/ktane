package main;

import java.util.Random;

import modules.Module;
import modules.Wires;

public class KeepTalkingAndNobodyExplodes {

	public static void main(String[] args) {
		generateModules();
	}

	private static void generateModules() {
		Timer timer = new Timer(60);
		Module[] modules = new Module[6];
		for (int i = 0; i < modules.length; i++) {
			switch (new Random().nextInt(1)) {
				case 0:
					modules[i] = new Wires(i);
					((Wires) modules[i]).generateRandom();
			}
		}
		Window window = new Window(modules, timer);
		window.makeScreen();
		window.generateModules();
	}
}
