package turing.btg.world;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import turing.btg.config.SurfacePatternConfig;

import java.util.Random;

public class WorldFeatureSurfaceRockPattern extends WorldFeatureSurfaceRock {
	private final SurfacePattern pattern;

	public WorldFeatureSurfaceRockPattern(int handlerID, int patternID) {
		super(handlerID);
		this.pattern = SurfacePatternConfig.PATTERNS.get(patternID);
	}

	@Override
	public boolean generate(World world, Random rand, int baseX, int y, int baseZ, int material) {
		if (pattern != null) {
			int[][] blockIds = pattern.getBlockIDs(rand);
			for (int x = 0; x < blockIds.length; x++) {
				int[] zIds = blockIds[x];
				if (zIds != null) {
					for (int z = 0; z < zIds.length; z++) {
						if (blockIds[x][z] == 1) {
							int X = baseX + x;
							int Z = baseZ + z;
							y = world.getHeightValue(X, Z);
							if (isPosValid(world, X, y, Z)) {
								Block block = getBlock();
								world.setBlockAndMetadata(X, y, Z, block.id, material);
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}
}
