package main;

public class Timing {
	private long ms;
	private long startingTime = 0;
	private boolean counting = false;

	public Timing(int ms) {
		this.ms = ms;
	}

	// Start the countdown
	public void start() {
		if (startingTime == 0) {
			startingTime = System.nanoTime();
			counting = true;
		}
	}

	// Return true if it is still counting
	public boolean counting() {
		if (counting) {
			if (System.nanoTime() > startingTime + ms * 1E6) {
				counting = false;
				startingTime = 0;
			}
		}
		return counting;
	}
}
