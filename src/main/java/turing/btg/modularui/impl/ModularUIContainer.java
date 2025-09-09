package turing.btg.modularui.impl;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuContainer;
import net.minecraft.core.player.inventory.slot.Slot;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import sunsetsatellite.catalyst.fluids.util.SlotFluid;
import turing.btg.modularui.ModularUI;
import turing.btg.modularui.api.IModularUITile;
import turing.btg.modularui.api.IWidget;
import turing.btg.modularui.widgets.WidgetSlot;

import java.util.ArrayList;
import java.util.List;

public class ModularUIContainer extends MenuContainer {
	public final ContainerInventory inventory;
	public final IModularUITile tile;
	protected final ModularUI ui;
	public List<SlotFluid> fluidSlots = new ArrayList<>();
	public List<FluidStack> fluidItemStacks = new ArrayList<>();

	public ModularUIContainer(ContainerInventory inventory, IModularUITile tile, ModularUI ui) {
		super(null, inventory);
		this.inventory = inventory;
		this.tile = tile;
		this.ui = ui;

		for (IWidget widget : ui.getWidgets()) {
			if (widget instanceof WidgetSlot) {
				addSlot(((WidgetSlot<?>) widget).createContainerSlot(tile));
			}
		}

		if (ui.hasPlayerInventory) {
			for (int y = 0; y < 3; y++) {
				for (int x = 0; x < 9; x++) {
					addSlot(new Slot(inventory, x + y * 9 + 9, ui.invX + x * 18, ui.invY + y * 18));
				}
			}

			for (int i = 0; i < 9; i++) {
				addSlot(new Slot(inventory, i, ui.invX + i * 18, ui.invY + 58));
			}
		}
	}

	public SlotFluid getFluidSlot(int id) {
		return fluidSlots.get(id);
	}

	public void putFluidInSlot(int id, FluidStack stack) {
		getFluidSlot(id).putStack(stack);
	}

	protected void addSlot(Object o) {
		if (o instanceof Slot) {
			addSlot((Slot) o);
		} else if (o instanceof SlotFluid) {
			SlotFluid slot = (SlotFluid) o;
			slot.slotNumber = fluidSlots.size();
			fluidSlots.add(slot);
			fluidItemStacks.add(null);
		}
	}

	@Override
	public void broadcastChanges() {
		super.broadcastChanges();

		/*for (int i = 0; i < fluidSlots.size(); i++) {
			FluidStack stack = fluidSlots.get(i).getFluidStack();
			FluidStack stack1 = fluidItemStacks.get(i);
			fluidItemStacks.set(i, stack1);

			for (ICrafting crafting : crafters) {
				((Player) crafting).updateFluidSlot(this, i, stack);
			}
		}

		for (int i = 0; i < inventorySlots.size(); i++) {
			ItemStack stack = inventorySlots.get(i).getStack();
			ItemStack stack1 = inventoryItemStacks.get(i);
			inventoryItemStacks.set(i, stack1);

			for (ICrafting crafting : crafters) {
				crafting.updateInventorySlot(this, i, stack);
			}
		}*/
	}

	public FluidStack clickFluidSlot(int slotId, int button, boolean shift, boolean control, Player player) {
		return null;
	}

	@Override
	public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, Player entityPlayer) {
		return null;
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, Player entityPlayer) {
		return null;
	}
}
