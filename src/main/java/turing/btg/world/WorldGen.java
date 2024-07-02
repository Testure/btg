package turing.btg.world;

import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import turing.btg.BTG;
import useless.terrainapi.api.TerrainAPI;
import useless.terrainapi.initialization.worldtypes.OverworldInitialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldGen implements TerrainAPI {
	public static final WorldFeatureRubberTree rubberTreeFeature = new WorldFeatureRubberTree();
	public static final Map<String, List<Integer>> GENERATED_ORE_VEINS = new HashMap<>();
	public static final Map<String, Integer> GENERATED_SURFACE_ROCKS = new HashMap<>();
	public static final Biome[] rubberTreeBiomes = new Biome[]{
		Biomes.OVERWORLD_PLAINS,
		Biomes.OVERWORLD_TAIGA,
		Biomes.OVERWORLD_GRASSLANDS,
		Biomes.OVERWORLD_MEADOW,
		Biomes.OVERWORLD_FOREST,
		Biomes.OVERWORLD_BOREAL_FOREST,
		Biomes.OVERWORLD_SEASONAL_FOREST,
		Biomes.OVERWORLD_SWAMPLAND,
		Biomes.OVERWORLD_RAINFOREST,
		Biomes.OVERWORLD_OUTBACK_GRASSY,
		Biomes.OVERWORLD_BIRCH_FOREST
	};
	public static final Biome[] allBiomes = new Biome[]{
		Biomes.OVERWORLD_PLAINS,
		Biomes.OVERWORLD_TAIGA,
		Biomes.OVERWORLD_GRASSLANDS,
		Biomes.OVERWORLD_MEADOW,
		Biomes.OVERWORLD_FOREST,
		Biomes.OVERWORLD_BOREAL_FOREST,
		Biomes.OVERWORLD_SEASONAL_FOREST,
		Biomes.OVERWORLD_SWAMPLAND,
		Biomes.OVERWORLD_RAINFOREST,
		Biomes.OVERWORLD_OUTBACK_GRASSY,
		Biomes.OVERWORLD_BIRCH_FOREST,
		Biomes.NETHER_NETHER,
		Biomes.OVERWORLD_RETRO,
		Biomes.OVERWORLD_DESERT,
		Biomes.OVERWORLD_SHRUBLAND,
		Biomes.OVERWORLD_GLACIER,
		Biomes.OVERWORLD_HELL,
		Biomes.OVERWORLD_OUTBACK,
		Biomes.OVERWORLD_SWAMPLAND_MUDDY,
		Biomes.OVERWORLD_TUNDRA,
		Biomes.PARADISE_PARADISE
	};

	@Override
	public String getModID() {
		return BTG.MOD_ID;
	}

	@Override
	public void onInitialize() {
		OverworldInitialization.biomeFeatures.addFeature(rubberTreeFeature, -1.0F, 1, rubberTreeBiomes);
		OverworldInitialization.biomeFeatures.addFeature(new WorldFeatureOreVein(), 0.65F, 100, allBiomes);
	}

	public static void onGenerateOreVein(int x, int y, int z) {
		String key = x / 16 + "," + z / 16;
		List<Integer> list = GENERATED_ORE_VEINS.get(key);
		List<Integer> add;
		if (list == null) add = new ArrayList<>();
		else add = list;
		add.add(y);
		if (list == null) GENERATED_ORE_VEINS.put(key, add);
	}
}
