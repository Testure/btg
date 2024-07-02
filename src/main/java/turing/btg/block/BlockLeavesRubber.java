package turing.btg.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLayerLeaves;
import net.minecraft.core.block.BlockLeavesBase;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import net.minecraft.core.world.wind.WindManager;

import java.util.Random;

public class BlockLeavesRubber extends BlockLeavesBase {
	public BlockLeavesRubber(String key, int id) {
		super(key, id, Material.leaves);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		super.updateTick(world, x, y, z, rand);
		if (rand.nextInt(128) == 0 && world.seasonManager.getCurrentSeason() != null && world.seasonManager.getCurrentSeason().hasFallingLeaves) {
			for (int i = -1; i > -16; --i) {
				int id = world.getBlockId(x, y + i, z);
				if (id != 0) {
					break;
				}

				if (Blocks.rubberLeaves.canPlaceBlockAt(world, x, y + i, z) && Block.blocksList[world.getBlockId(x, y + i - 1, z)].blockMaterial.blocksMotion()) {
					world.setBlockWithNotify(x, y + i, z, Blocks.rubberLeaves.id);
					break;
				}

				if (world.getBlockId(x, y + i, z) == Blocks.rubberLeaves.id) {
					((BlockLayerLeaves) Block.layerLeavesOak).accumulate(world, x, y + i, z);
					break;
				}
			}
		}
	}

	@Override
	protected Block getSapling() {
		return Blocks.rubberSapling;
	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		if (world.seasonManager.getCurrentSeason() != null && world.seasonManager.getCurrentSeason().hasFallingLeaves) {
			WindManager wind = world.getWorldType().getWindManager();
			float intensity = wind.getWindIntensity(world, x, y, z);
			if (rand.nextInt((int)(40F + 200F * (1F - intensity))) == 0) {
				world.spawnParticle("fallingleaf", x, y - 0.1F, z, 0, 0, 0, 0);
			}
		}
	}
}
