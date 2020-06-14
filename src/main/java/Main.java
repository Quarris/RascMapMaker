import gui.MapViewer;
import noise.PerlinNoise;
import rasc.RascMap;

import javax.swing.*;


public class Main {

	public static void main(String[] args) {
		PerlinNoise noise = new PerlinNoise(323269);
		RascMap map = new RascMap(genCircle(600));//noise.genPerlinNoise(noise.genWhiteNoiseMap(800, 600), 7, 0.4f, 0.6f));

		MapViewer viewer = new MapViewer(map);
		viewer.setVisible(true);

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		frame.setLocationRelativeTo(null);
		frame.setContentPane(viewer);
		frame.addKeyListener(viewer);
		frame.pack();

		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public static float[][] genCircle(int size) {
		float[][] map = new float[size][size];
		for (int x = -size/2; x < size/2; x++) {
			for (int y = -size/2; y < size/2; y++) {
				int i = x+size/2;
				int j = y+size/2;
				if (x*x + y*y <= size*size/4) {
					map[i][j] = 1f;
				} else {
					map[i][j] = 0f;
				}
			}
		}
		return map;
	}
}
