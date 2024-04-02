package turing.btg;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DyeColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turing.btg.block.Blocks;
import turing.btg.item.Items;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class BTG implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "btg";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ConfigHandler config;

	@Override
    public void onInitialize() {
		LOGGER.info(MOD_ID + " has initialized!");
    }

	@Override
	public void beforeGameStart() {
		Blocks.init();
		Items.init();
	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void initNamespaces() {
		RecipeNamespace namespace = new RecipeNamespace();
		namespace.register("workbench", Registries.RECIPES.WORKBENCH);
		Registries.RECIPES.register(MOD_ID, namespace);
	}

	@Override
	public void onRecipesReady() {
		Registries.ITEM_GROUPS.getItem("minecraft:logs").add(Blocks.rubberLog.getDefaultStack());
		Registries.ITEM_GROUPS.getItem("minecraft:leaves").add(Blocks.rubberLeaves.getDefaultStack());

		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(Blocks.rubberLog.getDefaultStack())
			.create("rubberLogToPlanks", new ItemStack(Block.planksOakPainted, 4, DyeColor.DYE_YELLOW.blockMeta));
	}
}
