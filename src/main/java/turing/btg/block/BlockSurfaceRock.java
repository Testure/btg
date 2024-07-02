package turing.btg.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.world.World;
import turing.btg.api.ICustomDescription;
import turing.btg.api.IMaterialMetaHandler;
import turing.btg.item.Items;
import turing.btg.material.MaterialItemType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockSurfaceRock extends Block implements IMaterialMetaHandler, ICustomDescription {
	protected static List<Integer> validBlockIDs = Arrays.asList(
		Block.grass.id,
		Block.stone.id,
		Block.dirt.id,
		Block.gravel.id,
		Block.sand.id,
		Block.dirtScorched.id,
		Block.grassScorched.id,
		Block.grassRetro.id,
		Block.dirtScorchedRich.id,
		Block.blockSnow.id,
		Block.cobbleStone.id,
		Block.cobbleStoneMossy.id,
		Block.granite.id,
		Block.basalt.id,
		Block.limestone.id
	);

	private final int handlerID;

	public BlockSurfaceRock(String key, int id, int handlerID) {
		super(key, id, Material.sand);
		this.handlerID = handlerID;
		this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.125F, 0.9F);
	}

	@Override
	public String getTranslatedDescription(ItemStack stack) {
		return I18n.getInstance().translateKeyAndFormat(getKey() + ".desc", I18n.getInstance().translateKey("material." + getMaterialIDForMeta(stack.getMetadata()) + ".name"));
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getHandlerID() {
		return handlerID;
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return canBeOn(world.getBlockId(x, y, z));
	}

	@Override
	public boolean canPlaceOnSurfaceOfBlock(World world, int x, int y, int z) {
		return canBeOn(world.getBlockId(x, y, z));
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		return new ItemStack[]{new ItemStack(Items.MATERIAL_ITEMS.get(MaterialItemType.DUST_TINY).get(getHandlerID()), 1, meta)};
	}

	protected boolean canBeOn(int blockID) {
		return validBlockIDs.contains(blockID);
	}
}
