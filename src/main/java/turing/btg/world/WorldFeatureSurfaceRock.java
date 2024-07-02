package turing.btg.world;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.util.collection.Pair;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import turing.btg.BTG;
import turing.btg.api.IMaterialMetaHandler;
import turing.btg.block.BlockSurfaceRock;
import turing.btg.block.Blocks;

import java.util.Random;

public class WorldFeatureSurfaceRock implements IMaterialMetaHandler {
	private final int handlerID;

	public WorldFeatureSurfaceRock(int handlerID) {
		this.handlerID = handlerID;
	}

	@Override
	public int getHandlerID() {
		return handlerID;
	}

	protected BlockSurfaceRock getBlock() {
		return Blocks.surfaceRock.get(handlerID);
	}

	public boolean generate(World world, Random rand, int x, int y, int z, int material) {
		Pair<Integer, Integer> pos = pickRandomPosUntilValid(x, z, rand, world);
		int pickedX = pos.getLeft();
		int pickedZ = pos.getRight();
		int pickedY = world.getHeightValue(pickedX, pickedZ);
		if (isPosValid(world, pickedX, pickedY, pickedZ)) {
			Block block = getBlock();
			world.setBlockAndMetadata(pickedX, pickedY, pickedZ, block.id, getMetaForMaterialID(material));
			return true;
		}
		return false;
	}

	protected Pair<Integer, Integer> pickRandomPosUntilValid(int x, int z, Random rand, World world) {
		int pickedX = x + rand.nextInt(5) - rand.nextInt(5);
		int pickedZ = z + rand.nextInt(5) - rand.nextInt(5);
		int y = world.getHeightValue(pickedX, pickedZ);
		int i = 0;
		while (!isPosValid(world, pickedX, y, pickedZ)) {
			if (i > 100) break;
			pickedX = x + rand.nextInt(5) - rand.nextInt(5);
			pickedZ = z + rand.nextInt(5) - rand.nextInt(5);
			y = world.getHeightValue(pickedX, pickedZ);
			i++;
		}
		return isPosValid(world, pickedX, y, pickedZ) ? Pair.of(pickedX, pickedZ) : Pair.of(x, z);
	}

	protected boolean isPosValid(World world, int x, int y, int z) {
		Block block = getBlock();
		return (world.getBlockId(x, y, z) == 0 || world.getBlock(x, y, z).hasTag(BlockTags.PLACE_OVERWRITES)) && block.canBlockStay(world, x, y - 1, z) && block.canPlaceOnSurfaceOfBlock(world, x, y - 1, z);
	}
}
