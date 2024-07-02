package turing.btg.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.world.World;
import turing.btg.api.IItemToolMaterial;
import turing.btg.api.IToolType;
import turing.btg.api.ToolType;

public class ItemToolFile extends ItemTool implements IItemToolMaterial {
	protected final int materialID;
	protected final String[] tooltips;

	public ItemToolFile(String name, int id, int materialID) {
		super(name, id, 0, ToolMaterial.wood, null);
		this.materialID = materialID;
		this.tooltips = initTooltips();
		this.setMaxDamage(getStats().getToolMaterial().getDurability());
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving player) {
		return IItemToolMaterial.super.hitEntity(itemstack, entityliving, player);
	}

	@Override
	public boolean onBlockDestroyed(World world, ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
		return IItemToolMaterial.super.onBlockDestroyed(world, itemstack, i, j, k, l, entityliving);
	}

	@Override
	public float getStrVsBlock(ItemStack itemstack, Block block) {
		return IItemToolMaterial.super.getStrVsBlock(itemstack, block);
	}

	@Override
	public int getDamageVsEntity(Entity entity) {
		return IItemToolMaterial.super.getDamageVsEntity(entity);
	}

	@Override
	public boolean isSilkTouch() {
		return IItemToolMaterial.super.isSilkTouch();
	}

	@Override
	public String getTranslatedName(ItemStack itemstack) {
		return IItemToolMaterial.super.getTranslatedName(itemstack);
	}

	@Override
	public String getTranslatedDescription(ItemStack itemstack) {
		return IItemToolMaterial.super.getTranslatedDescription(itemstack);
	}

	@Override
	public ItemStack getDefaultStack() {
		return IItemToolMaterial.super.getDefaultStack();
	}

	@Override
	public boolean isFull3D() {
		return IItemToolMaterial.super.isFull3D();
	}

	@Override
	public int getMaterialID() {
		return materialID;
	}

	@Override
	public IToolType getToolType() {
		return ToolType.FILE;
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return true;
	}

	@Override
	public int getItemId() {
		return this.id;
	}

	@Override
	public String[] getTooltips() {
		return tooltips;
	}

	@Override
	public int getBaseDamage() {
		return 2;
	}
}
