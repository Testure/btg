package turing.btg.item;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemBucketNoPlace extends ItemBucket {
	public ItemBucketNoPlace(String name, int id) {
		super(name, id, null);
	}

	@Override
	public ItemStack onUseItem(ItemStack stack, World world, EntityPlayer player) {
		return stack;
	}
}
