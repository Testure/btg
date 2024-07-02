package turing.btg.api;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;

public interface ICustomDescription {
	default String[] getTooltips(ItemStack stack, boolean isShift, boolean isCtrl) {
		return null;
	}

	default String getTranslatedDescription(ItemStack stack) {
		return I18n.getInstance().translateDescKey(stack.getItemKey());
	}
}
