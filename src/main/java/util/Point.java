package util;

import java.util.Objects;

public class Point {

	public final int x;
	public final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point offset(int x, int y) {
		return new Point(this.x + x, this.y + y);
	}

	public Point offset(Direction direction) {
		return new Point(this.x + direction.xOff, this.y + direction.yOff);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Point point = (Point) o;
		return x == point.x &&
				y == point.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
