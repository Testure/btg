package turing.btg.modularui.widgets;

import sunsetsatellite.catalyst.fluids.util.FluidStack;

public class WidgetFluidOutputSlot extends WidgetFluidSlot {
	public WidgetFluidOutputSlot(int id) {
		super(id);
	}

	@Override
	public boolean canInsertIntoSlot(FluidStack item) {
		return false;
	}
}
