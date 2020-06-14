package gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapColorConfig {

	private Map<Float, Integer> colourMap;

	public MapColorConfig() {
		this.colourMap = new HashMap<>();
	}

	public void addColour(float threshold, int value) {
		this.colourMap.put(threshold, value);
	}

	public int getColour(float threshold) {
		if (this.colourMap.containsKey(threshold)) {
			return this.colourMap.get(threshold);
		}

		List<Float> keys = this.colourMap.keySet().stream().sorted().collect(Collectors.toList());
		for (Float key : keys) {
			if (threshold < key) {
				return this.colourMap.get(key);
			}
		}
		return 0;
	}


}
