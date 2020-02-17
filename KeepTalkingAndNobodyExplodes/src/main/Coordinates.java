package main;

public class Coordinates {


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
}
