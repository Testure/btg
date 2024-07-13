package turing.btg.api;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;

public interface IOverlayShower {
	boolean shouldShowOverlay(ItemStack stack, Block block);
}
