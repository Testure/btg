package turing.btg.modularui.api;

import net.minecraft.core.player.inventory.IInventory;
import sunsetsatellite.catalyst.fluids.api.IFluidInventory;
import turing.btg.modularui.ModularUI;

public interface IModularUITile extends IInventory, IFluidInventory {
	ModularUI createUI();
}
