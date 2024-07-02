package turing.btg.api;

import net.minecraft.core.item.ItemStack;

public interface ICraftingTool {
	default ItemStack getDamagedItem(ItemStack originalStack) {
		ItemStack stack = originalStack.copy();
		stack.damageItem(1, null);
		return stack;
	}

	default void onCraft(ItemStack stack) {}
}
