package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;

import modules.Wires;

public class Window extends JPanel {

	private static final long serialVersionUID = 1L;

	static int transx;
	static int transy;
	JFrame frame;
	Wires[] modules;

	public Window() {
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

	}

	public void generateModules() {
		modules = new Wires[6];
		for (int i = 0; i < modules.length; i++) {
			modules[i] = new Wires(i);
			modules[i].generateRandom();
		}
		MouseClickListener listener = new MouseClickListener();
		listener.setModules(modules);
		addMouseListener(listener);
	}

	public void paint(Graphics g) {
		super.paint(g);

		for (int i = 0; i < modules.length; i++) {
			if (modules[i] != null)
				modules[i].update(g);
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

	public Wires[] getModules() {
		return modules;
	}
}
