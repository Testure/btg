package turing.btg.recipe;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.item.Item;
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
		RecipeMaps.ALLOY_SMELTER.register("test", new SimpleRecipeEntry(new RecipeMapSymbol[]{new RecipeMapSymbol("common_ingots:copper", 2), new RecipeMapSymbol("common_ingots:tin")}, new SimpleRecipeOutput[]{new SimpleRecipeOutput(Item.ingotSteel.getDefaultStack())}, info));
	}

	public static void registerNamespace() {
		for (RecipeMap<?> map : RecipeMap.RECIPE_MAPS) {
			NAMESPACE.register(map.localizationKey, map);
		}
		RecipeBuilder.getRecipeGroup(NAMESPACE, "workbench", new RecipeSymbol(Block.workbench.getDefaultStack()));
		RecipeBuilder.getRecipeGroup(NAMESPACE, "furnace", new RecipeSymbol(Block.furnaceStoneIdle.getDefaultStack()));
		RecipeBuilder.getRecipeGroup(NAMESPACE, "blast_furnace", new RecipeSymbol(Block.furnaceBlastIdle.getDefaultStack()));
		Registries.RECIPES.register(BTG.MOD_ID, NAMESPACE);
	}
}
