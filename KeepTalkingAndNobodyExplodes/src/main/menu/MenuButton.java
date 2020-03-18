package main.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;

public class MenuButton extends JButton implements MouseListener {
	private static final long serialVersionUID = 1L;
	private int xpos = 0;
	private int ypos = 0;
	private int width = 0;
	private int height = 0;

	private boolean hover;
	private boolean pressed;
	private boolean canBeClicked = true;
	private int mouseWheelRotation;

	public MenuButton(int x, int y, int width, int height) {
		this.xpos = x;
		this.ypos = y;
		this.width = width;
		this.height = height;
		pressed = false;
		hover = false;
		setBorderPainted(false);
		setFocusable(true);
		setContentAreaFilled(false);
		setSize(this.width, this.height);
		setVisible(true);
		setLocation(this.xpos, this.ypos);
		addMouseListener(this);
		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				mouseWheelRotation = e.getWheelRotation();
			}
		});
	}

	/**
	 * @return the mouseWheelRotation
	 */
	public int getMouseWheelRotation() {
		return mouseWheelRotation;
	}

	/**
	 * @param mouseWheelRotation
	 *            the mouseWheelRotation to set
	 */
	public void resetMouseWheelRotation() {
		mouseWheelRotation = 0;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(xpos, ypos, width, height);
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
		pressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
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
