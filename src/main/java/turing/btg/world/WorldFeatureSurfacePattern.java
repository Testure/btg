package turing.btg.world;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import turing.btg.config.SurfacePatternConfig;

import java.util.Random;

public class WorldFeatureSurfacePattern extends WorldFeature {
	private final int blockId;
	private final int blockMeta;
	private final SurfacePattern pattern;

	public WorldFeatureSurfacePattern(int blockId, int blockMeta, int patternId) {
		this.blockId = blockId;
		this.blockMeta = blockMeta;
		this.pattern = SurfacePatternConfig.PATTERNS.get(patternId);
	}

	@Override
	public boolean generate(World world, Random rand, int baseX, int y, int baseZ) {
		if (pattern != null) {
			int[][] blockIds = pattern.getBlockIDs(rand);
			for (int x = 0; x < SurfacePatternConfig.PATTERN_LENGTH; x++) {
				for (int z = 0; z < SurfacePatternConfig.PATTERN_LENGTH; z++) {
					if (blockIds[x][z] == 1) {
						int X = baseX + x;
						int Z = baseZ + z;
						y = world.getHeightValue(X, Z);
						if (isBlockValid(world, X, y, Z)) {
							world.setBlockAndMetadata(X, y, Z, blockId, blockMeta);
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	public boolean isBlockValid(World world, int x, int y, int z) {
		return (world.isAirBlock(x, y, z) || world.getBlock(x, y, z).hasTag(BlockTags.PLACE_OVERWRITES)) && Block.getBlock(blockId).canBlockStay(world, x, y, z);
	}
}
