package turing.btg.recipe;

import net.minecraft.core.item.ItemStack;
import sunsetsatellite.catalyst.fluids.util.FluidStack;

public class SimpleRecipeOutput {
	protected ItemStack item;
	protected FluidStack fluid;
	protected float chance = 1.0F;

	public SimpleRecipeOutput() {}

	public SimpleRecipeOutput(ItemStack stack, float chance) {
		this.item = stack;
		this.chance = chance;
	}

	public SimpleRecipeOutput(ItemStack stack) {
		this(stack, 1.0F);
	}

	public SimpleRecipeOutput(FluidStack stack, float chance) {
		this.fluid = stack;
		this.chance = chance;
	}

	public SimpleRecipeOutput(FluidStack stack) {
		this(stack, 1.0F);
	}

	public float getChance() {
		return chance;
	}

	public ItemStack getItem() {
		return item;
	}

	public FluidStack getFluid() {
		return fluid;
	}
}
