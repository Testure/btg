package turing.btg.modularui.widgets;

import sunsetsatellite.catalyst.fluids.util.FluidStack;
import turing.btg.gui.GuiTextures;
import turing.btg.modularui.api.IModularUITile;
import turing.btg.modularui.impl.SlotFluidWidget;

public class WidgetFluidSlot extends WidgetSlot<FluidStack> {
	public WidgetFluidSlot(int id) {
		super(id, GuiTextures.FLUID_SLOT);
	}

	@Override
	public Object createContainerSlot(IModularUITile tile) {
		return new SlotFluidWidget(this, tile);
	}
}
