package turing.btg.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolHoe;
import turing.btg.api.IItemToolMaterial;
import turing.btg.api.IToolType;
import turing.btg.api.ToolType;

public class ItemToolHoeMaterial extends ItemToolHoe implements IItemToolMaterial {
	private final int materialID;
	private final String[] tooltips;

	public ItemToolHoeMaterial(String name, int id, int materialID) {
		super(name, id, ToolMaterial.wood);
		this.materialID = materialID;
		this.setMaxDamage(getStats().getToolMaterial().getDurability());
		this.tooltips = initTooltips();
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving player) {
		return IItemToolMaterial.super.hitEntity(itemstack, entityliving, player);
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
	public String getTranslatedName(ItemStack itemstack) {
		return IItemToolMaterial.super.getTranslatedName(itemstack);
	}

	@Override
	public String getTranslatedDescription(ItemStack itemstack) {
		return IItemToolMaterial.super.getTranslatedDescription(itemstack);
	}

	@Override
	public boolean isSilkTouch() {
		return IItemToolMaterial.super.isSilkTouch();
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
	public IToolType getToolType() {
		return ToolType.HOE;
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return true;
	}

	@Override
	public int getMaterialID() {
		return materialID;
	}

	@Override
	public int getBaseDamage() {
		return 0;
	}

	@Override
	public String[] getTooltips() {
		return this.tooltips;
	}

	@Override
	public int getItemId() {
		return this.id;
	}
}
