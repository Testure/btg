package turing.btg.item;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;

public interface ICustomDescription {
	default String[] getTooltips(ItemStack stack, boolean isShift, boolean isCtrl) {
		return new String[0];
	}

	default String getTranslatedDescription(ItemStack stack) {
		return I18n.getInstance().translateDescKey(stack.getItemName());
	}
}
