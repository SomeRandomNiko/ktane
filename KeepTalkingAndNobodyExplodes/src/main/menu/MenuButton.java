package main.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class MenuButton extends JButton implements MouseListener {
	private static final long serialVersionUID = 1L;

	private String text;
	private int xpos = 0;
	private int ypos = 0;
	private int width = 0;
	private int height = 0;

	private boolean hover;
	private boolean click;

	public MenuButton(String text, int x, int y, int width, int height) {
		this.xpos = x;
		this.ypos = y;
		this.width = width;
		this.height = height;
		this.text = text;
		click = false;
		hover = false;
		setBorderPainted(false);
		setFocusable(true);
		setContentAreaFilled(false);
		setSize(this.width, this.height);
		setVisible(true);
		setLocation(this.xpos, this.ypos);
		addMouseListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setFont(new Font("Consolas", Font.PLAIN, 70));
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		if (hover)
			g.setColor(Color.WHITE);
		g.drawString(text, 70, 70);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		hover = true;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		hover = false;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		click = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		click = false;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(width, height);
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(width, height);
	}

	/**
	 * @return the hover
	 */
	public boolean isHover() {
		return hover;
	}

	/**
	 * @return the click
	 */
	public boolean isClick() {
		return click;
	}
}
