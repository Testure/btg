package turing.btg.modularui.impl;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.crafting.ICrafting;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemBucketEmpty;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.player.inventory.slot.Slot;
import sunsetsatellite.catalyst.CatalystFluids;
import sunsetsatellite.catalyst.fluids.api.IItemFluidContainer;
import sunsetsatellite.catalyst.fluids.impl.ContainerFluid;
import sunsetsatellite.catalyst.fluids.interfaces.mixins.IEntityPlayer;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import sunsetsatellite.catalyst.fluids.util.SlotFluid;
import turing.btg.modularui.ModularUI;
import turing.btg.modularui.api.IModularUITile;
import turing.btg.modularui.api.IWidget;
import turing.btg.modularui.widgets.WidgetSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ModularUIContainer extends Container {
	public final IInventory inventory;
	public final IModularUITile tile;
	protected final ModularUI ui;
	public List<SlotFluid> fluidSlots = new ArrayList<>();
	public List<FluidStack> fluidItemStacks = new ArrayList<>();

	public ModularUIContainer(IInventory inventory, IModularUITile tile, ModularUI ui) {
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
	public void updateInventory() {
		super.updateInventory();

		for (int i = 0; i < fluidSlots.size(); i++) {
			FluidStack stack = fluidSlots.get(i).getFluidStack();
			FluidStack stack1 = fluidItemStacks.get(i);
			fluidItemStacks.set(i, stack1);

			for (ICrafting crafting : crafters) {
				((IEntityPlayer) crafting).updateFluidSlot(this, i, stack);
			}
		}

		for (int i = 0; i < inventorySlots.size(); i++) {
			ItemStack stack = inventorySlots.get(i).getStack();
			ItemStack stack1 = inventoryItemStacks.get(i);
			inventoryItemStacks.set(i, stack1);

			for (ICrafting crafting : crafters) {
				crafting.updateInventorySlot(this, i, stack);
			}
		}
	}

	public FluidStack clickFluidSlot(int slotId, int button, boolean shift, boolean control, EntityPlayer player) {
		return null;
	}

	@Override
	public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return null;
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return null;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
		return true;
	}
}
