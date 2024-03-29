package turing.btg.world;

import java.util.*;

public class OreVein {
	public static final List<OreVein> VEINS = new ArrayList<>();

	private final int[] oreIds;
	private final int minY;
	private final int maxY;
	private final int chance;
	private final int radiusX;
	private final int radiusZ;
	private final float density;
	private final Map<String, Object> properties;

	public OreVein(int[] oreIds, int minY, int maxY, int chance, float density, int radiusX, int radiusZ, Map<String, Object> properties) {
		this.oreIds = oreIds;
		this.minY = minY;
		this.maxY = maxY;
		this.chance = chance;
		this.density = density;
		this.radiusX = radiusX;
		this.radiusZ = radiusZ;
		this.properties = properties != null ? properties : new HashMap<>();
		VEINS.add(this);
	}

	public OreVein(int[] oreIds, int minY, int maxY, int chance, float density, int radiusX, int radiusZ) {
		this(oreIds, minY, maxY, chance, density, radiusX, radiusZ, null);
	}

	public OreVein(int[] oreIds, int minY, int maxY, int chance, float density, int radius, Map<String, Object> properties) {
		this(oreIds, minY, maxY, chance, density, radius, radius, properties);
	}

	public OreVein(int[] oreIds, int minY, int maxY, int chance, float density, int radius) {
		this(oreIds, minY, maxY, chance, density, radius, radius, null);
	}

	public int[] getOreIds() {
		return oreIds;
	}

	public int getChance() {
		return chance;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getRadiusX() {
		return radiusX;
	}

	public int getRadiusZ() {
		return radiusZ;
	}

	public float getDensity() {
		return density;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}
}
