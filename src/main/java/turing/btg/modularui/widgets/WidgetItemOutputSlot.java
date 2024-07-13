package turing.btg.modularui.widgets;

import net.minecraft.core.item.ItemStack;

public class WidgetItemOutputSlot extends WidgetItemSlot {
	public WidgetItemOutputSlot(int id) {
		super(id);
	}

	@Override
	public boolean canInsertIntoSlot(ItemStack item) {
		return false;
	}
}
