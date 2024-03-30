package turing.btg.block;

import net.minecraft.core.block.BlockLog;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import turing.btg.item.Items;

public class BlockLogRubber extends BlockLog {
	public BlockLogRubber(String key, int id) {
		super(key, id);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(Side side, int data) {
		return data >= 3 ? this.atlasIndices[side.getId()] : super.getBlockTextureFromSideAndMetadata(side, data);
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		ItemStack[] items = new ItemStack[2];
		items[0] = new ItemStack(this, 1);
		if (dropCause != EnumDropCause.PICK_BLOCK && dropCause != EnumDropCause.SILK_TOUCH && meta >= 3 && world.rand.nextInt(3) == 0) {
			items[1] = new ItemStack(Items.resin, world.rand.nextInt(2) + 1);
		}
		return items;
	}
}
