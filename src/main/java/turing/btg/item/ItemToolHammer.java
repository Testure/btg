package turing.btg.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.world.World;
import turing.btg.BTG;
import turing.btg.api.IItemToolMaterial;
import turing.btg.api.IToolType;
import turing.btg.api.ToolType;
import turing.btg.block.BlockOreMaterial;
import turing.btg.material.Material;
import turing.btg.material.MaterialFlag;
import turing.btg.material.MaterialItemType;
import turing.btg.material.OreProperties;

public class ItemToolHammer extends ItemTool implements IItemToolMaterial {
	protected final int materialID;
	protected final String[] tooltips;

	public ItemToolHammer(String name, int id, int materialID) {
		super(name, id, 0, ToolMaterial.wood, BTG.MINEABLE_BY_HAMMER);
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
		Block block = Block.getBlock(i);
		if (block != null && !isSilkTouch()) {
			if (block instanceof BlockOreMaterial) {
				BlockOreMaterial material1 = ((BlockOreMaterial) block);
				int meta = world.getBlockMetadata(j, k, l);
				Material material2 = Material.MATERIALS.get(material1.getMaterialIDForMeta(meta));
				if (material2 != null && material2.hasFlag("ore")) {
					OreProperties properties = material2.getFlagValue(MaterialFlag.ORE);
					ItemStack drop = Material.getItemForMaterial(material2.id, MaterialItemType.CRUSHED_ORE, properties.smeltAmount);
					EntityItem entityItem = new EntityItem(world, j, k, l, drop);
					world.entityJoinedWorld(entityItem);
				}
			}
		}
		return IItemToolMaterial.super.onBlockDestroyed(world, itemstack, i, j, k, l, entityliving);
	}

	@Override
	public float getStrVsBlock(ItemStack itemstack, Block block) {
		if (block instanceof BlockOreMaterial) {
			return getStats().getToolMaterial().getEfficiency(false);
		}
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
	public int getBaseDamage() {
		return 3;
	}

	@Override
	public String[] getTooltips() {
		return tooltips;
	}

	@Override
	public int getItemId() {
		return this.id;
	}

	@Override
	public IToolType getToolType() {
		return ToolType.HAMMER;
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return true;
	}
}