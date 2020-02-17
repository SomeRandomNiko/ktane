package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import modules.Module;
import modules.Wires;

public class MouseClickListener implements MouseListener {

	private Module[] modules;

	@Override
	public void mouseClicked(MouseEvent e) {
		int[] xy = { e.getX(), e.getY() };
		int moduleIndex = Coordinates.getModuleIndex(xy);
		if (moduleIndex >= 0) {

			// Wires
			if (Wires.class.isInstance(modules[moduleIndex])) {
				int wireIndex = ((Wires) modules[moduleIndex]).getWireIndex(xy);
				if (moduleIndex >= 0 && moduleIndex <= 5 && wireIndex >= 0 && wireIndex <= 5) {
					((Wires) modules[moduleIndex]).getWires()[wireIndex].setCut(true);
				}
			}
		}
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
	public Module[] getModules() {
		return modules;
	}

	/**
	 * @param modules
	 *            the modules to set
	 */
	public void setModules(Module[] modules) {
		this.modules = modules;
	}

}
