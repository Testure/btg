package turing.btg.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import turing.btg.BTG;
import turing.btg.world.SurfacePattern;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SurfacePatternConfig extends ConfigHandler {
	public static final int PATTERN_LENGTH = 6;
	public static final Map<Integer, SurfacePattern> PATTERNS = new HashMap<>();

	static {
		File folder = new File(CONFIG_DIR.getPath() + "/patterns");
		initFolder(folder);

		int val = initDefaults("patterns");
		if (val == 0) {
			BTG.LOGGER.error("Could not load default surface generator patterns! This will cause serious issues with ore generation!");
		} else if (val == 2) BTG.LOGGER.info("Loaded default surface generator patterns");

		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.getPath().endsWith(".json")) {
					JsonObject patternJson = readConfig(folder, file.getName());
					if (patternJson != null) {
						SurfacePattern pattern = patternFromJson(patternJson);
						PATTERNS.put(pattern.id, pattern);
					}
				}
			}
		}
	}

	private static SurfacePattern patternFromJson(JsonObject json) {
		JsonElement jsonPattern = json.get("pattern");
		String pattern;
		if (jsonPattern.isJsonArray()) {
			JsonArray array = jsonPattern.getAsJsonArray();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < PATTERN_LENGTH; i++) {
				if (array.size() <= i) break;
				if (i != 0) builder.append(",");
				builder.append(array.get(i).getAsString());
			}
			pattern = builder.toString();
		} else pattern = jsonPattern.getAsString();
		return new SurfacePattern(json.get("pattern_id").getAsInt(), pattern);
	}

	public static void init() {}
}
