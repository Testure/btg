package turing.btg.recipe.entries;

import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.SearchQuery;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCraftingShaped;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryCrafting;
import turing.btg.api.ICraftingTool;

import java.util.List;
import java.util.Objects;

public class RecipeEntryCraftingShapedTools extends RecipeEntryCraftingShaped {
	public RecipeEntryCraftingShapedTools(int recipeWidth, int recipeHeight, RecipeSymbol[] input, ItemStack output, boolean consumeContainerItem) {
		super(recipeWidth, recipeHeight, input, output, consumeContainerItem);
	}

	@Override
	public boolean matches(InventoryCrafting inventory) {
		for(int i = 0; i <= 3 - this.recipeWidth; ++i) {
			for(int j = 0; j <= 3 - this.recipeHeight; ++j) {
				if (this.isRecipeMatching(inventory, i, j, true)) {
					return true;
				}

				if (this.isRecipeMatching(inventory, i, j, false)) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean matchesUsage(SearchQuery query) {
		RecipeSymbol[] symbols = this.getInput();

		for (RecipeSymbol symbol : symbols) {
			if (symbol != null) {
				List<ItemStack> stacks = symbol.resolve();
				if (query.query.getLeft() == SearchQuery.QueryType.NAME) {
					for (ItemStack stack : stacks) {
						if (query.strict && stack.getDisplayName().equalsIgnoreCase(query.query.getRight())) {
							return true;
						}
						if (!query.strict && stack.getDisplayName().toLowerCase().contains(query.query.getRight().toLowerCase())) {
							return true;
						}
					}
				} else if (query.query.getLeft() == SearchQuery.QueryType.GROUP && !Objects.equals(query.query.getRight(), "")) {
					List<ItemStack> groupStacks = new RecipeSymbol(query.query.getRight()).resolve();
					if (groupStacks == null) return false;

					return stacks.stream().anyMatch(stack -> {
						if (stack.getItem() instanceof ICraftingTool) {
							return groupStacks.stream().anyMatch(groupStack -> groupStack.getItem() instanceof ICraftingTool && groupStack.itemID == stack.itemID);
						} else {
							return groupStacks.contains(stack);
						}
					});
				}
			}
		}

		return false;
	}

	protected boolean isRecipeMatching(InventoryCrafting inventory, int i, int j, boolean flag) {
		for(int k = 0; k < 3; ++k) {
			for(int l = 0; l < 3; ++l) {
				int i1 = k - i;
				int j1 = l - j;
				RecipeSymbol symbol = null;
				if (i1 >= 0 && j1 >= 0 && i1 < this.recipeWidth && j1 < this.recipeHeight) {
					if (flag) {
						symbol = this.getInput()[this.recipeWidth - i1 - 1 + j1 * this.recipeWidth];
					} else {
						symbol = this.getInput()[i1 + j1 * this.recipeWidth];
					}
				}

				ItemStack stack = inventory.getItemStackAt(k, l);
				if (stack != null || symbol != null) {
					if (stack == null || symbol == null) {
						return false;
					}
					if (stack.getItem() instanceof ICraftingTool && stack.getMetadata() > 0) {
						stack = stack.copy();
						stack.setMetadata(0);
					}
					if (!symbol.matches(stack)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	@Override
	public ItemStack[] onCraftResult(InventoryCrafting inventorycrafting) {
		ItemStack[] returnStack = new ItemStack[9];

		for (int i = 0; i < inventorycrafting.getSizeInventory(); ++i) {
			ItemStack itemStack = inventorycrafting.getStackInSlot(i);
			if (itemStack != null) {
				inventorycrafting.decrStackSize(i, 1);
				if (!this.consumeContainerItem && itemStack.getItem().hasContainerItem()) {
					inventorycrafting.setInventorySlotContents(i, new ItemStack(itemStack.getItem().getContainerItem()));
				}
				if (itemStack.getItem() instanceof ICraftingTool) {
					ICraftingTool tool = (ICraftingTool) itemStack.getItem();
					ItemStack newStack = tool.getDamagedItem(itemStack);
					inventorycrafting.setInventorySlotContents(i, newStack);
					tool.onCraft(newStack);
				}
			}
		}

		return returnStack;
	}
}
