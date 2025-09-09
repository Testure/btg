package turing.btg.recipe.entries;

import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.*;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import turing.btg.recipe.RecipeMapSymbol;
import turing.btg.recipe.SimpleRecipeInfo;
import turing.btg.recipe.SimpleRecipeOutput;

import java.util.Arrays;
import java.util.List;

public class SimpleRecipeEntry extends RecipeEntryBase<RecipeMapSymbol[], SimpleRecipeOutput[], SimpleRecipeInfo> {
	public SimpleRecipeEntry() {}

	public SimpleRecipeEntry(RecipeMapSymbol[] input, SimpleRecipeOutput[] output, SimpleRecipeInfo data) {
		super(input, output, data);
	}

	public boolean matches(RecipeMapSymbol[] symbols) {
		if (symbols.length == 0 || symbols.length != getInput().length) {
			return false;
		}
		for (RecipeMapSymbol symbol : symbols) {
			if (Arrays.stream(getInput()).noneMatch(s -> s.matches(symbol))) {
				return false;
			}
		}
		return true;
	}

	public boolean matchesQuery(SearchQuery query) {
		switch (query.mode) {
			case ALL:
				if (matchesScope(query) && (matchesRecipe(query) || matchesUsage(query))) return true;
			case RECIPE:
				if (matchesScope(query) && matchesRecipe(query)) return true;
			case USAGE:
				if (matchesScope(query) && matchesUsage(query)) return true;
		}
		return false;
	}

	public boolean matchesScope(SearchQuery query) {
		if (query.scope.getLeft() == SearchQuery.SearchScope.NONE) return true;
		if (query.scope.getLeft() == SearchQuery.SearchScope.NAMESPACE) {
			RecipeNamespace namespace = Registries.RECIPES.getItem(query.scope.getRight());
			return namespace == parent.getParent();
		} else if (query.scope.getLeft() == SearchQuery.SearchScope.NAMESPACE_GROUP) {
			RecipeGroup<?> group;
			try {
				group = Registries.RECIPES.getGroupFromKey(query.scope.getRight());
			} catch (IllegalArgumentException e) {
				group = null;
			}
			return group == parent;
		}
		return false;
	}

	public boolean matchesRecipe(SearchQuery query) {
		if (query.query.getLeft() == SearchQuery.QueryType.NAME) {
			for (SimpleRecipeOutput output : getOutput()) {
				String displayName = null;
				if (output.getItem() != null) {
					displayName = output.getItem().getDisplayName();
				} else if (output.getFluid() != null) {
					displayName = output.getFluid().fluid.getName();
				}
				if (displayName != null) {
					if (query.strict && displayName.equalsIgnoreCase(query.query.getRight())) {
						return true;
					} else return !query.strict && displayName.toLowerCase().contains(query.query.getRight().toLowerCase());
				}
			}
		} else if (query.query.getLeft() == SearchQuery.QueryType.GROUP && !query.query.getRight().isEmpty()) {
			for (SimpleRecipeOutput output : getOutput()) {
				if (output.getItem() != null) {
					return new RecipeSymbol(query.query.getRight()).resolve().contains(output.getItem());
				}
				if (output.getFluid() != null) {
					return new RecipeMapSymbol(query.query.getRight()).resolveFluids().contains(output.getFluid());
				}
			}
		}
		return false;
	}

	public boolean matchesUsage(SearchQuery query) {
		for (RecipeMapSymbol symbol : getInput()) {
			if (symbol == null) continue;
			List<ItemStack> items = symbol.resolve();
			if (items != null) {
				if (query.query.getLeft() == SearchQuery.QueryType.NAME) {
					for (ItemStack stack : items) {
						if (query.strict && stack.getDisplayName().equalsIgnoreCase(query.query.getRight())) {
							return true;
						} else if (!query.strict && stack.getDisplayName().toLowerCase().contains(query.query.getRight().toLowerCase())) {
							return true;
						}
					}
				} else if (query.query.getLeft() == SearchQuery.QueryType.GROUP && !query.query.getRight().isEmpty()) {
					List<ItemStack> stacks = new RecipeSymbol(query.query.getRight()).resolve();
					if (stacks == null) return false;
					if (items.stream().anyMatch(stacks::contains)) {
						return true;
					}
				}
			}
			List<FluidStack> fluids = symbol.resolveFluids();
			if (fluids != null) {
				if (query.query.getLeft() == SearchQuery.QueryType.NAME) {
					for (FluidStack stack : fluids) {
						if (query.strict && stack.fluid.getName().equalsIgnoreCase(query.query.getRight())) {
							return true;
						} else if (!query.strict && stack.fluid.getName().toLowerCase().contains(query.query.getRight().toLowerCase())) {
							return true;
						}
					}
				} else if (query.query.getLeft() == SearchQuery.QueryType.GROUP && !query.query.getRight().isEmpty()) {
					List<FluidStack> stacks = new RecipeMapSymbol(query.query.getRight()).resolveFluids();
					if (stacks == null) return false;
					if (fluids.stream().anyMatch(stacks::contains)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
