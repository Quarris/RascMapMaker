package rasc;

import util.Direction;
import util.Point;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Province {

	public static final int BOUNDARY = 40;
	public static final int POINT = 6;

	public final RascMap map;

	public final int id;
	public final Random random;
	public final Color color;

	public int x;
	public int y;

	private List<Point> expandableTerritory;
	private List<Point> territory;

	public Province(RascMap map, int id, int x, int y, Color color, long seed) {
		this.map = map;
		this.id = id;
		this.color = color;
		this.random = new Random(seed);

		this.x = x;
		this.y = y;

		this.expandableTerritory = new ArrayList<>();
		this.territory = new ArrayList<>();
	}

	public void addTerritory(Point point) {
		this.territory.add(point);

		for (Direction dir : Direction.values()) {
			if (this.map.canExpandTo(point.offset(dir))) {
				this.expandableTerritory.add(point);
				break;
			}
		}
	}

	public void expandRandomly() {
		int next = this.random.nextInt(this.expandableTerritory.size());

		final Point p = this.expandableTerritory.get(next);
		List<Direction> availableDirs = Arrays.stream(Direction.values())
				.filter(dir -> this.map.canExpandTo(p.offset(dir)))
				.collect(Collectors.toList());

		if (availableDirs.isEmpty()) {
			return;
		}

		Direction expandDir = availableDirs.remove(random.nextInt(availableDirs.size()));

		this.map.setTerritory(p.x + expandDir.xOff, p.y + expandDir.yOff, this);

		if (availableDirs.isEmpty()) {
			this.expandableTerritory.remove(next);
		}
	}

	@Override
	public String toString() {
		return "Province{" +
				"id=" + id +
				", color=" + color +
				", x=" + x +
				", y=" + y +
				'}';
	}
}
