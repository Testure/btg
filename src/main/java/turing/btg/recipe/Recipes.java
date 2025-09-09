package turing.btg.recipe;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.Items;
import turing.btg.BTG;
import turing.btg.recipe.entries.SimpleRecipeEntry;
import turniplabs.halplibe.helper.RecipeBuilder;

public class Recipes {
	public static final RecipeNamespace NAMESPACE = new RecipeNamespace();

	public static void init() {
		Registries.RECIPE_TYPES.register(BTG.MOD_ID + ":machine", SimpleRecipeEntry.class);
		CraftingRecipes.init();
		SimpleRecipeInfo info = new SimpleRecipeInfo();
		info.EUt = 4;
		info.time = 100;
		RecipeMaps.ALLOY_SMELTER.register("test", new SimpleRecipeEntry(new RecipeMapSymbol[]{new RecipeMapSymbol("common_ingots:copper", 2), new RecipeMapSymbol("common_ingots:tin")}, new SimpleRecipeOutput[]{new SimpleRecipeOutput(Items.INGOT_STEEL.getDefaultStack())}, info));
	}

	public static void registerNamespace() {
		for (RecipeMap<?> map : RecipeMap.RECIPE_MAPS) {
			NAMESPACE.register(map.localizationKey, map);
		}
		RecipeBuilder.getRecipeGroup(NAMESPACE, "workbench", new RecipeSymbol(Blocks.WORKBENCH.getDefaultStack()));
		RecipeBuilder.getRecipeGroup(NAMESPACE, "furnace", new RecipeSymbol(Blocks.FURNACE_STONE_IDLE.getDefaultStack()));
		RecipeBuilder.getRecipeGroup(NAMESPACE, "blast_furnace", new RecipeSymbol(Blocks.FURNACE_BLAST_IDLE.getDefaultStack()));
		Registries.RECIPES.register(BTG.MOD_ID, NAMESPACE);
	}
}
