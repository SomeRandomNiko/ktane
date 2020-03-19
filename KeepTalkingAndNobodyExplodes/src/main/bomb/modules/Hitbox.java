package main.bomb.modules;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class Hitbox extends JButton implements MouseListener {

	private static final long serialVersionUID = 1L;
	private boolean hover;
	private boolean pressed;
	private boolean canBeClicked = true;

	private int width;
	private int height;
	private int xpos;
	private int ypos;

	public Hitbox(int x, int y, int width, int height) {
		this.xpos = x;
		this.ypos = y;
		this.width = width;
		this.height = height;
		setBorderPainted(false);
		setLocation(this.xpos, this.ypos);
		setFocusable(true);
		setContentAreaFilled(false);
		setSize(this.width, this.height);
		setVisible(true);
		addMouseListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
//		g.setColor(Color.RED);
//		if (hover)
//			g.fillRect(0, 0, width, height);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		hover = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		hover = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
		canBeClicked = true;
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
	public boolean isPressed() {
		return pressed;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return xpos;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return ypos;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.xpos = x;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.ypos = y;
	}
	
	/**
	 * @return the click
	 */
	public boolean isClick() {
		if (pressed && canBeClicked) {
			canBeClicked = false;
			return true;
		}
		return false;
	}

}
