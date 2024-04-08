package turing.btg.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.world.World;

public class ItemCreativeSword extends ItemToolSword {
	public static final int BIG_NUM = 857485748;
	public static final ToolMaterial CREATIVE_MATERIAL = new ToolMaterial().setDurability(BIG_NUM).setBlockHitDelay(0).setMiningLevel(BIG_NUM).setDamage(BIG_NUM).setEfficiency(BIG_NUM, BIG_NUM);

	public ItemCreativeSword(String name, int id) {
		super(name, id, CREATIVE_MATERIAL);
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving player) {
		return true;
	}

	@Override
	public boolean onBlockDestroyed(World world, ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
		return true;
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return true;
	}
}
