package gui;

import rasc.RascMap;
import rasc.Province;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MapViewer extends JPanel implements KeyListener {

	public RascMap map;
	public MapColorConfig colourMap;

	public boolean placedProvinces = false;

	public MapViewer(RascMap map) {
		this.map = map;
		this.colourMap = new MapColorConfig();
		this.colourMap.addColour(0.5f, Color.BLUE.getRGB());
		this.colourMap.addColour(1f, Color.GREEN.getRGB());

		this.setPreferredSize(new Dimension(map.width, map.height));
		this.addKeyListener(this);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(this.map.asMappedImage(this.colourMap), 0, 0, null);
		g.drawImage(this.map.getProvincesImage(), 0, 0, null);
		for (Province province : this.map.provinces) {
			g.fillOval(province.x - Province.POINT/2, province.y - Province.POINT/2, Province.POINT, Province.POINT);
		}
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == ' ') {
			if (!this.placedProvinces) {
				int failed = 0;
				for (int i = 0; i < 20; i++) {
					if (!this.map.addRandomProvince(50, new Random())) {
						failed++;
					}
				}
				System.out.println("Failed to add " + failed + " random provinces.");
				this.repaint();
				this.placedProvinces = true;
			} else {
				for (int i = 0; i < 10000; i++) {
					for (Province province : this.map.provinces) {
						province.expandRandomly();
					}
				}
				this.repaint();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
