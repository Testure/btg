package turing.btg.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.lang.I18n;

public class ItemBlockOre extends ItemBlock implements ICustomDescription {
	public ItemBlockOre(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getPlacedBlockMetadata(int i) {
		return i;
	}

	@Override
	public String getTranslatedName(ItemStack itemstack) {
		return I18n.getInstance().translateKeyAndFormat(getLanguageKey(itemstack) + ".name", I18n.getInstance().translateNameKey("material." + itemstack.getMetadata()));
	}

	@Override
	public String getTranslatedDescription(ItemStack stack) {
		return I18n.getInstance().translateKeyAndFormat(stack.getItemName() + ".desc", I18n.getInstance().translateNameKey("material." + stack.getMetadata()));
	}
}
