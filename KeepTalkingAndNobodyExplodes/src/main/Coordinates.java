package main;

public class Coordinates {
	public static int moduleX(int index) {
		int ret = 0;
		if (index > 2)
			index -= 3;
		ret = index * 500;
		return ret;
	}

	public static int moduleY(int index) {
		int ret = 0;
		if (index > 2)
			ret = 500;
		return ret;
	}

	public static int getModuleIndex(int[] xy) {
		int ret = 0;
		if (xy[0] <= 1500 && xy[1] <= 1000) {
			if (xy[1] > 500) {
				ret = 3;
			}
			ret += (int) (xy[0] / 500);
		} else ret = -1;
		return ret;
	}

	public static int getWireIndex(int[] xy) {
		int moduleIndex = getModuleIndex(xy);
		if (xy[1] > 500)
			moduleIndex -= 3;
		int ret = -1;
		int x = moduleX(moduleIndex);
		System.out.println("Module X coord: " + x);
		if (xy[0] >= x + 40 && xy[0] <= 500 * moduleIndex - 40 + 500) {
			ret = (int) ((xy[0] - 500 * moduleIndex - 40) / 70);
		}
		return ret;
	}
}
