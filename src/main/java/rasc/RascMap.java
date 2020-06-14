package rasc;

import gui.MapColorConfig;
import util.Point;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class RascMap {

	public int width, height;
	public float[][] landmass;
	public int[][] provinceMap;
	public List<Province> provinces;

	public RascMap(float[][] landmass) {
		this.width = landmass.length;
		this.height = landmass[0].length;
		this.landmass = landmass;
		this.provinces = new ArrayList<>();
		this.provinceMap = new int[this.width][this.height];

		for (int[] a : this.provinceMap) {
			Arrays.fill(a, -1);
		}
	}

	public BufferedImage asBWImage() {
		BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_BYTE_GRAY);
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				float pixel = this.landmass[x][y];
				Color c = new Color(pixel, pixel, pixel);
				image.setRGB(x, y, c.getRGB());
			}
		}
		return image;
	}

	public BufferedImage asMappedImage(MapColorConfig config) {
		BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				float pixel = this.landmass[x][y];
				image.setRGB(x, y, config.getColour(pixel));
			}
		}
		return image;
	}

	public void setTerritory(int x, int y, Province province) {
		if (!this.canExpandTo(new Point(x, y))) {
			return;
		}

		this.provinceMap[x][y] = province.id;
		province.addTerritory(new Point(x, y));
	}

	public boolean canExpandTo(Point point) {
		if (point.x < 0 || point.x > this.width-1 || point.y < 0 || point.y > this.height-1) {
			return false;
		}
		return this.provinceMap[point.x][point.y] == -1 && this.landmass[point.x][point.y] > 0.5f;
	}

	public BufferedImage getProvincesImage() {
		BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				int provinceID = this.provinceMap[x][y];
				if (provinceID == -1) {
					image.setRGB(x, y, new Color(0f, 0f, 0f, 0f).getRGB());
					continue;
				}

				Province province = this.provinces.get(provinceID);
				image.setRGB(x, y, province.color.getRGB());
			}
		}
		return image;
	}

	public boolean addRandomProvince(int tries, Random random) {
		Color color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
		for (int i = 0; i < tries; i++) {
			int x = random.nextInt(this.width);
			int y = random.nextInt(this.height);
			if (this.addProvince(x, y, color, new Random().nextLong())) {
				return true;
			}
		}

		return false;
	}

	public boolean addProvince(int x, int y, Color color, long seed) {
		if (this.landmass[x][y] < 0.5f) {
			return false;
		}

		for (Province province : this.provinces) {
			float dx = x - province.x;
			float dy = y - province.y;
			if (dx * dx + dy * dy < Province.BOUNDARY) {
				return false;
			}
		}

		Province p = new Province(this, this.provinces.size(), x, y, color, seed);
		this.provinces.add(p);
		this.setTerritory(x, y, p);
		return true;
	}
}
