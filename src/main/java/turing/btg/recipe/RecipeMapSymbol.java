package turing.btg.recipe;

import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import sunsetsatellite.catalyst.fluids.util.FluidStack;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapSymbol extends RecipeSymbol {
	private FluidStack fluidStack;

	public RecipeMapSymbol(char symbol, ItemStack stack, String itemGroup, int amount) {
		super(symbol, stack, itemGroup, amount);
	}

	public RecipeMapSymbol(FluidStack stack, int amount) {
		super((ItemStack) null, amount);
		this.fluidStack = stack;
	}

	public RecipeMapSymbol(FluidStack stack) {
		this(stack, stack.amount);
	}

	public RecipeMapSymbol(String itemGroup, int amount) {
		super(itemGroup, amount);
	}

	public RecipeMapSymbol(String itemGroup) {
		super(itemGroup);
	}

	public RecipeMapSymbol(List<ItemStack> override) {
		super(override);
	}

	public RecipeMapSymbol(ItemStack stack) {
		super(stack, stack.stackSize);
	}

	public RecipeMapSymbol(List<ItemStack> override, int amount) {
		super(override, amount);
	}

	public RecipeMapSymbol(ItemStack stack, int amount) {
		super(stack, amount);
	}

	public FluidStack getFluidStack() {
		return fluidStack;
	}

	public List<FluidStack> resolveFluids() {
		if (fluidStack != null && getItemGroup() == null) {
			FluidStack s = fluidStack.copy();
			s.amount *= getAmount();
			return Collections.singletonList(s);
		} else if (getItemGroup() != null && fluidStack == null) {
			return Registries.ITEM_GROUPS.getItem(getItemGroup()).stream().filter(stack -> {
				if (stack.getItem() instanceof ItemBlock) {
					if (((ItemBlock) stack.getItem()).getBlock() instanceof BlockFluid) {
						return true;
					}
				}
				return false;
			}).map(stack -> new FluidStack((BlockFluid) ((ItemBlock) stack.getItem()).getBlock(), getAmount())).collect(Collectors.toList());
		} else if (getItemGroup() != null) {
			List<FluidStack> stacks = Registries.ITEM_GROUPS.getItem(getItemGroup()).stream().filter(stack -> {
				if (stack.getItem() instanceof ItemBlock) {
					if (((ItemBlock) stack.getItem()).getBlock() instanceof BlockFluid) {
						return true;
					}
				}
				return false;
			}).map(stack -> new FluidStack((BlockFluid) ((ItemBlock) stack.getItem()).getBlock(), getAmount())).collect(Collectors.toList());
			FluidStack stack = fluidStack.copy();
			stack.amount *= getAmount();
			stacks.add(stack);
			return stacks;
		} else {
			return null;
		}
	}

	public boolean matches(FluidStack stack) {
		if (stack != null) {
			List<FluidStack> stacks = resolveFluids();

			if (stacks != null) {
				for (FluidStack resolvedStack : stacks) {
					if (resolvedStack.getLiquid() == stack.getLiquid()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean matches(RecipeMapSymbol other) {
		if (other == null) return false;
		if (equals(other)) return true;
		List<ItemStack> items = resolve();
		List<FluidStack> fluids = resolveFluids();
		if (items != null) {
			if (items.stream().anyMatch(this::matches)) return true;
		}
		if (fluids != null) {
			if (fluids.stream().anyMatch(this::matches)) return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		boolean base = super.equals(o);
		if (base && o instanceof RecipeMapSymbol) {
			RecipeMapSymbol other = (RecipeMapSymbol) o;
			if ((other.fluidStack == null) != (fluidStack == null)) {
				return false;
			} else if (fluidStack != null && !fluidStack.isStackEqual(other.fluidStack)) {
				return false;
			}
		}
		return base;
	}

	@Override
	public RecipeSymbol copy() {
		RecipeMapSymbol symbol = new RecipeMapSymbol(getSymbol(), getStack(), getItemGroup(), getAmount());
		symbol.fluidStack = fluidStack;
		return symbol;
	}
}
