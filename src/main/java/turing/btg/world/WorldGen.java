package turing.btg.world;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTree;
import turing.btg.BTG;
import turing.btg.block.Blocks;
import useless.terrainapi.api.TerrainAPI;
import useless.terrainapi.initialization.worldtypes.OverworldInitialization;

public class WorldGen implements TerrainAPI {
	public static final WorldFeature rubberTreeFeature = new WorldFeatureTree(Blocks.rubberLeaves.id, Blocks.rubberLog.id, 6);
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

	@Override
	public String getModID() {
		return BTG.MOD_ID;
	}

	@Override
	public void onInitialize() {
		OverworldInitialization.biomeFeatures.addFeature(rubberTreeFeature, -1.0F, 1, rubberTreeBiomes);
		OverworldInitialization.biomeFeatures.addFeature(new WorldFeatureOreVein(), 0.65F, 1, rubberTreeBiomes);

		new OreVein(new int[]{0, 1, 2, 3}, 20, 40, 120, 0.2F, 24);
	}
}
