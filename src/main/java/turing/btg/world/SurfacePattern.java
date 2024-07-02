package turing.btg.world;

import turing.btg.config.SurfacePatternConfig;

import java.util.Random;

public class SurfacePattern {
	public final int id;
	private final String pattern;

	public SurfacePattern(int id, String pattern) {
		this.id = id;
		this.pattern = pattern;
	}

	public int[][] getBlockIDs(Random rand) {
		int[][] array = new int[SurfacePatternConfig.PATTERN_LENGTH][];
		if (pattern.equalsIgnoreCase("random")) {
			for (int x = 0; x < SurfacePatternConfig.PATTERN_LENGTH; x++) {
				array[x] = new int[SurfacePatternConfig.PATTERN_LENGTH];
				for (int z = 0; z < SurfacePatternConfig.PATTERN_LENGTH; z++) {
					array[x][z] = rand.nextInt(7) == 0 ? 1 : 0;
				}
			}
		} else {
			String[] lines = pattern.split(",");
			int length = Math.min(lines.length, SurfacePatternConfig.PATTERN_LENGTH);
			for (int l = 0; l < length; l++) {
				String line = lines[l];
				array[l] = new int[length];
				for (int i = 0; i < length; i++) {
					if (line.length() >= i) {
						char c = line.charAt(i);
						array[l][i] = c == '#' ? 1 : 0;
					}
				}
			}
		}
		return array;
	}
}
