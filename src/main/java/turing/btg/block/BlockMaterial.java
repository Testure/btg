package turing.btg.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import turing.btg.api.IMaterialMetaHandler;
import turing.btg.material.MaterialIconSet;

import javax.annotation.Nullable;

public class BlockMaterial extends Block implements IMaterialMetaHandler {
	private final int handlerID;

	public BlockMaterial(String key, int id, int handlerID) {
		super(key, id, Material.metal);
		this.handlerID = handlerID;
	}

	@Override
	public int getHandlerID() {
		return handlerID;
	}

	@Nullable
	public MaterialIconSet getIconSet(int meta) {
		int id = getMaterialIDForMeta(meta);
		return turing.btg.material.Material.MATERIALS.get(id) != null ? turing.btg.material.Material.MATERIALS.get(id).getIconSet() : null;
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		return new ItemStack[]{new ItemStack(this, 1, meta)};
	}
}
