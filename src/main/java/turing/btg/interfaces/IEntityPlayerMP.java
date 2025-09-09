package turing.btg.interfaces;

import net.minecraft.core.player.inventory.menu.MenuAbstract;
import turing.btg.modularui.api.IModularUITile;

import java.awt.*;

public interface IEntityPlayerMP {
	void displayModularUI(MenuAbstract container, IModularUITile tile, int x, int y, int z);
}
