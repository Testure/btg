package turing.btg.api;

import net.minecraft.core.item.ItemStack;

public interface IColored {
	ColoredTexture[] getTextures(ItemStack stack);
}
