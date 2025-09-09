package turing.btg.modularui.impl;

import sunsetsatellite.catalyst.fluids.api.IFluidInventory;
import sunsetsatellite.catalyst.fluids.util.Fluid;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import sunsetsatellite.catalyst.fluids.util.SlotFluid;
import turing.btg.modularui.widgets.WidgetFluidSlot;

public class SlotFluidWidget extends SlotFluid {
	protected final WidgetFluidSlot widget;

	public SlotFluidWidget(WidgetFluidSlot widget, IFluidInventory inventory) {
		super(inventory, widget.getSlotId(), widget.getX() + 1, widget.getY() + 1);
		this.widget = widget;
	}

	@Override
	public boolean isFluidValid(Fluid stack) {
		return widget.canInsertIntoSlot(new FluidStack(stack));
	}
}
