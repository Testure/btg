package turing.btg.block;

import net.minecraft.core.block.BlockSand;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import turing.btg.api.IOreStoneType;
import turing.btg.entity.EntityFallingOre;

import java.util.Random;

public class BlockOreMaterial extends BlockMaterial {
	private final IOreStoneType stoneType;

	public BlockOreMaterial(String key, int id, int handlerID, IOreStoneType stoneType) {
		super(key, id, handlerID);
		this.stoneType = stoneType;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (stoneType.hasGravity()) world.scheduleBlockUpdate(x, y, z, this.id, this.tickRate());
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		if (stoneType.hasGravity()) world.scheduleBlockUpdate(x, y, z, this.id, this.tickRate());
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (stoneType.hasGravity()) tryToFall(world, x, y, z, world.getBlockMetadata(x, y, z));
	}

	@Override
	public int tickRate() {
		return stoneType.hasGravity() ? 3 : 0;
	}

	private void tryToFall(World world, int x, int y, int z, int meta) {
		if (stoneType.hasGravity()) {
			if (BlockSand.canFallBelow(world, x, y - 1, z) && y >= 0) {
				if (BlockSand.fallInstantly || !world.areBlocksLoaded(x - 32, y - 32, z - 32, x + 32, y + 32, z + 32)) {
					world.setBlockWithNotify(x, y, z, 0);
					int i = y;
					while (BlockSand.canFallBelow(world, x, i - 1, z) && i > 0) {
						--i;
					}
					if (i > 0) {
						world.setBlockAndMetadataWithNotify(x, y, z, this.id, meta);
					}
				} else {
					EntityFallingOre falling = new EntityFallingOre(world, x + 0.5F, y + 0.5F, z + 0.5F, this.id, meta, this.stoneType);
					world.entityJoinedWorld(falling);
				}
			}
		}
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		return dropCause != EnumDropCause.IMPROPER_TOOL ? new ItemStack[]{new ItemStack(this, 1, meta)} : null;
	}
}
