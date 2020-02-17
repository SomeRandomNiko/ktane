package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import modules.Wires;

public class MouseClickListener implements MouseListener {

	private Wires[] modules;

	@Override
	public void mouseClicked(MouseEvent e) {
		int[] xy = { e.getX(), e.getY() };
		int moduleIndex = Coordinates.getModuleIndex(xy);
		int wireIndex = Coordinates.getWireIndex(xy);
		if (moduleIndex >= 0 && moduleIndex <= 5 && wireIndex >= 0 && wireIndex <= 5) {
			modules[moduleIndex].getWires()[wireIndex].setCut(true);
		}
		System.out.println("WireIndex: " + wireIndex);
		System.out.println("ModuleIndex: " + moduleIndex);
		System.out.println("Coordinates: " + xy[0] + ", " + xy[1]);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the modules
	 */
	public Wires[] getModules() {
		return modules;
	}

	/**
	 * @param modules
	 *            the modules to set
	 */
	public void setModules(Wires[] modules) {
		this.modules = modules;
	}

}
