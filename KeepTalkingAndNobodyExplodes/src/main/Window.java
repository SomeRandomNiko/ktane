package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import modules.Module;
import modules.Wires;

public class Window extends JPanel {

	private static final long serialVersionUID = 1L;
	JFrame frame;
	Module[] modules;
	Timer timer;

	public Window(Module[] modules, Timer timer) {
		this.modules = modules;
		this.timer = timer;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.add(this);
		frame.setTitle("Keep Talking and Nobody Explodes");
		frame.setLocation(0, 0);
		frame.setVisible(true);
	}

	public void makeScreen() {
		Insets i = frame.getInsets();
		frame.setSize(1900 + i.left + i.right, 1000 + i.top + i.bottom);
		setBackground(new Color(0x545454));
		MouseClickListener listener = new MouseClickListener();
		listener.setModules(modules);
		addMouseListener(listener);
		timer.start();
	}

	public void generateModules() {
		for (int i = 0; i < modules.length; i++) {
			switch (new Random().nextInt(1)) {
				case 0:
					modules[i] = new Wires(i);
					((Wires) modules[i]).generateRandom();
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		timer.update(g);
		if (!timer.isDepleted()) {
			for (int i = 0; i < modules.length; i++) {
				if (modules[i] != null) {
					modules[i].drawFrame(g);
					modules[i].update(g);
				}
			}
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		pause(10);
		repaint();
	}

	private void pause(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Module[] getModules() {
		return modules;
	}
}
