package turing.btg.block;

import net.minecraft.core.block.BlockSaplingBase;
import net.minecraft.core.world.World;
import turing.btg.world.WorldFeatureRubberTree;
import turing.btg.world.WorldGen;

import java.util.Random;

public class BlockSaplingRubber extends BlockSaplingBase {
	public BlockSaplingRubber(String key, int id) {
		super(key, id);
	}

	@Override
	public void growTree(World world, int x, int y, int z, Random rand) {
		WorldFeatureRubberTree tree = WorldGen.rubberTreeFeature;
		world.setBlock(x, y, z, 0);
		if (!tree.generate(world, rand, x, y, z, true)) {
			world.setBlock(x, y, z, this.id);
		}
	}
}
