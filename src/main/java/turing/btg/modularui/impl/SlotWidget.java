package turing.btg.modularui.impl;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import turing.btg.modularui.widgets.WidgetItemSlot;

public class SlotWidget extends Slot {
	protected WidgetItemSlot widget;

	public SlotWidget(WidgetItemSlot widget, IInventory inventory) {
		super(inventory, widget.getSlotId(), widget.getX() + 1, widget.getY() + 1);
		this.widget = widget;
	}

	@Override
	public boolean canPutStackInSlot(ItemStack stack) {
		return widget.canInsertIntoSlot(stack);
	}
}
