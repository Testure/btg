package turing.btg.enchantments;

import net.minecraft.core.block.BlockLog;
import net.minecraft.core.data.gamerule.TreecapitatorHelper;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.world.World;
import turing.enchantmentlib.api.Enchantment;
import turing.enchantmentlib.api.IEnchantFilter;

public class EnchantmentTreeCapitator extends Enchantment {
	public static final IEnchantFilter AXE = stack -> stack.getItem() instanceof ItemToolAxe;

	@Override
	public boolean beforeDestroyBlock(ItemStack stack, World world, int x, int y, int z, EntityPlayer player, boolean willDestroy) {
		if (!world.isClientSide && !player.isSneaking()) {
			if (world.getBlock(x, y, z) instanceof BlockLog) {
				return !(new TreecapitatorHelper(world, x, y, z, player)).chopTree();
			}
		}
		return true;
	}

	@Override
	public IEnchantFilter[] getApplicableFilters() {
		return new IEnchantFilter[]{AXE};
	}
}
