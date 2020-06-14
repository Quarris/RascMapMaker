package util;

public enum Direction {

	UP(0, -1),
	DOWN(0, 1),
	LEFT(-1, 0),
	RIGHT(1, 0);

	public final int xOff;
	public final int yOff;

	Direction(int xOff, int yOff) {
		this.xOff = xOff;
		this.yOff = yOff;
	}
}
