package turing.btg.modularui.widgets;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.Slot;
import turing.btg.gui.GuiTextures;
import turing.btg.modularui.api.IModularUITile;
import turing.btg.modularui.impl.SlotWidget;

public class WidgetItemSlot extends WidgetSlot<ItemStack> {
	public WidgetItemSlot(int id) {
		super(id, GuiTextures.SLOT);
	}

	@Override
	public Slot createContainerSlot(IModularUITile tile) {
		return new SlotWidget(this, tile);
	}
}
