package turing.btg.interfaces;

import net.minecraft.core.player.inventory.Container;
import turing.btg.modularui.api.IModularUITile;

public interface IEntityPlayerMP {
	void displayModularUI(Container container, IModularUITile tile, int x, int y, int z);
}
