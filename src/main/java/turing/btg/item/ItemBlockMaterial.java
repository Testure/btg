package turing.btg.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;
import turing.btg.api.ICustomDescription;
import turing.btg.api.IMaterialMetaHandler;
import turing.btg.material.Material;

public class ItemBlockMaterial extends ItemBlock implements ICustomDescription, IMaterialMetaHandler {
	private final Block block;

	public ItemBlockMaterial(Block block) {
		super(block);
		this.block = block;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		withTags(ItemTags.NOT_IN_CREATIVE_MENU);
	}

	@Override
	public int getHandlerID() {
		return ((IMaterialMetaHandler) block).getHandlerID();
	}

	@Override
	public int getPlacedBlockMetadata(int i) {
		return i;
	}

	@Override
	public String getTranslatedName(ItemStack itemstack) {
		return I18n.getInstance().translateKeyAndFormat(getLanguageKey(itemstack) + ".name", I18n.getInstance().translateNameKey("material." + Material.MATERIALS.get(getMaterialIDForMeta(itemstack.getMetadata())).name));
	}

	@Override
	public String getTranslatedDescription(ItemStack stack) {
		return I18n.getInstance().translateKeyAndFormat(stack.getItemKey() + ".desc", I18n.getInstance().translateNameKey("material." + Material.MATERIALS.get(getMaterialIDForMeta(stack.getMetadata())).name));
	}

	@Override
	public String[] getTooltips(ItemStack stack, boolean isShift, boolean isCtrl) {
		Material material = Material.MATERIALS.get(getMaterialIDForMeta(stack.getMetadata()));
		if (material != null && !material.getChemicalFormula().isEmpty()) {
			return new String[]{TextFormatting.formatted(material.getChemicalFormula(), TextFormatting.LIGHT_GRAY)};
		} else return null;
	}
}
