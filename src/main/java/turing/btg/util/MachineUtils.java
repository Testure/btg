package turing.btg.util;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import com.mojang.nbt.Tag;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.catalyst.fluids.util.FluidStack;

public class MachineUtils {
	public static ListTag serializeItemInventory(ItemStack[] stacks) {
		ListTag items = new ListTag();
		for (ItemStack stack : stacks) {
			CompoundTag tag = new CompoundTag();
			if (stack != null) stack.writeToNBT(tag);
			items.addTag(tag);
		}
		return items;
	}

	public static ListTag serializeFluidInventory(FluidStack[] stacks) {
		ListTag items = new ListTag();
		for (FluidStack stack : stacks) {
			CompoundTag tag = new CompoundTag();
			if (stack != null) stack.writeToNBT(tag);
			items.addTag(tag);
		}
		return items;
	}

	public static ItemStack[] deserializeItemInventory(ListTag tag) {
		ItemStack[] inv = new ItemStack[tag.tagCount()];
		for (int i = 0; i < tag.tagCount(); i++) {
			Tag<?> item = tag.tagAt(i);
			if (item instanceof CompoundTag) {
				inv[i] = ItemStack.readItemStackFromNbt((CompoundTag) item);
			}
		}
		return inv;
	}

	public static FluidStack[] deserializeFluidInventory(ListTag tag) {
		FluidStack[] inv = new FluidStack[tag.tagCount()];
		for (int i = 0; i < tag.tagCount(); i++) {
			Tag<?> fluid = tag.tagAt(i);
			if (fluid instanceof CompoundTag) {
				inv[i] = new FluidStack((CompoundTag) fluid);
			}
		}
		return inv;
	}
}
