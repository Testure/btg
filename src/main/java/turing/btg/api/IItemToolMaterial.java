package turing.btg.api;

import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.world.World;
import turing.btg.material.Material;
import turing.btg.material.ToolStats;
import turing.enchantmentlib.EnchantmentLib;
import turing.enchantmentlib.api.EnchantmentData;

/** Tool logic implemented here so that tools can extend the proper classes
 *
 */
public interface IItemToolMaterial extends ICustomDescription, IColored, ICraftingTool {
	int getMaterialID();

	default String getMaterialName() {
		Material material = Material.MATERIALS.get(getMaterialID());
		return material.name;
	}

	default ToolStats getStats() {
		Material material = Material.MATERIALS.get(getMaterialID());
		if (material != null && material.hasFlag("tools")) {
			return material.getToolStats();
		}
		return null;
	}

	default String getActualKey() {
		int digits = String.valueOf(getMaterialID()).length();
		return getKey().substring(0, getKey().length() - digits);
	}

	default boolean isSilkTouch() {
		return getStats().getToolMaterial().isSilkTouch();
	}

	default boolean isSword() {
		return false;
	}

	String getKey();

	int getBaseDamage();

	String[] getTooltips();

	int getItemId();

	IToolType getToolType();

	boolean canHarvestBlock(Block block);

	default int getDamage() {
		return getBaseDamage() + (isSword() ? getStats().getToolMaterial().getDamage() * 2 : getStats().getToolMaterial().getDamage());
	}

	default String[] initTooltips() {
		String[] tooltips = new String[3 + (isSilkTouch() ? 1 : 0)];
		tooltips[0] = I18n.getInstance().translateKeyAndFormat("tooltip.btg.efficiency", getStats().getToolMaterial().getEfficiency(false));
		tooltips[1] = I18n.getInstance().translateKeyAndFormat("tooltip.btg.damage", getDamage());
		tooltips[2] = I18n.getInstance().translateKeyAndFormat("tooltip.btg.miningLevel", getStats().getToolMaterial().getMiningLevel());
		int i = 3;
		if (isSilkTouch()) {
			tooltips[i++] = I18n.getInstance().translateKey("tooltip.btg.silkTouch");
		}
		return tooltips;
	}

	default TextFormatting getFormattingForDurability(ItemStack stack) {
		float percentage = ((float) (stack.getMaxDamage() - stack.getMetadata()) / stack.getMaxDamage());
		if (percentage <= 0.25F) return TextFormatting.RED;
		else if (percentage <= 0.5F) return TextFormatting.YELLOW;
		return TextFormatting.LIME;
	}

	@Override
	default String getTranslatedDescription(ItemStack stack) {
		return I18n.getInstance().translateKeyAndFormat(getActualKey() + ".desc", I18n.getInstance().translateNameKey("material." + getMaterialName()).toLowerCase());
	}

	default String getTranslatedName(ItemStack stack) {
		return I18n.getInstance().translateKeyAndFormat(getActualKey() + ".name", I18n.getInstance().translateNameKey("material." + getMaterialName()));
	}

	@Override
	default String[] getTooltips(ItemStack stack, boolean isShift, boolean isCtrl) {
		String durability = I18n.getInstance().translateKeyAndFormat("tooltip.btg.durability", TextFormatting.formatted(String.valueOf(stack.getMaxDamage() - stack.getMetadata()), getFormattingForDurability(stack)), stack.getMaxDamage());
		if (!isShift) {
			return new String[]{durability};
		} else {
			String[] prepared = getTooltips();
			String[] tooltips = new String[prepared.length + 1];
			tooltips[0] = durability;
			System.arraycopy(prepared, 0, tooltips, 1, prepared.length);
			return tooltips;
		}
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	default boolean hitEntity(ItemStack stack, EntityLiving entityLiving, EntityLiving player) {
		stack.damageItem(isSword() ? 1 : 2, player);
		return true;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	default boolean onBlockDestroyed(World world, ItemStack stack, int removedBlockId, int x, int y, int z, EntityLiving entityLiving) {
		Block block = Block.getBlock(removedBlockId);
		if (block != null && (block.getHardness() > 0.0F || isSilkTouch())) {
			stack.damageItem(isSword() ? 2 : 1, entityLiving);
		}
		return true;
	}

	default int getDamageVsEntity(Entity entity) {
		return getDamage();
	}

	default float getStrVsBlock(ItemStack stack, Block block) {
		return canHarvestBlock(block) ? getStats().getToolMaterial().getEfficiency(false) : 1.0F;
	}

	default ToolMaterial getMaterial() {
		return getStats().getToolMaterial();
	}

	default ItemStack getDefaultStack() {
		ItemStack stack = new ItemStack(Item.itemsList[getItemId()], 1);
		for (EnchantmentData enchantment : getStats().getEnchantments()) {
			EnchantmentLib.enchantItem(stack, enchantment);
		}
		return stack;
	}

	default boolean isFull3D() {
		return getToolType().isFull3D();
	}

	@Override
	default ColoredTexture[] getTextures(ItemStack stack) {
		IToolType toolType = getToolType();
		IconCoordinate overlay = toolType.getOverlayTextureIndex();
		IconCoordinate index = TextureRegistry.getTexture(toolType.getTextureIndex());
		int id = getMaterialID();
		if (Material.MATERIALS.get(id) == null) return new ColoredTexture[0];
		ColoredTexture[] textures = new ColoredTexture[index != null ? 2 : 1];
		int baseColor = index != null ? -1 : Material.MATERIALS.get(id).getColor();

		textures[0] = new ColoredTexture(index != null ? index : overlay, baseColor);
		if (index != null) textures[1] = new ColoredTexture(overlay, Material.MATERIALS.get(id).getColor());

		return textures;
	}
}
