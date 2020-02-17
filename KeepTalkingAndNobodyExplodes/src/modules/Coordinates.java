package modules;

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
}
