package turing.btg.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.block.ItemBlock;

public class ItemBlockOre extends ItemBlock {
	public ItemBlockOre(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getPlacedBlockMetadata(int i) {
		return i;
	}
}
