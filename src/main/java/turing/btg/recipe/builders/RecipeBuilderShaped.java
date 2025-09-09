package turing.btg.recipe.builders;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import turing.btg.recipe.entries.RecipeEntryCraftingShapedTools;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderBase;

import java.util.HashMap;

public class RecipeBuilderShaped extends RecipeBuilderBase {
	protected String[] shape;
	protected int width;
	protected int height;
	protected boolean consumeContainer = false;
	protected final HashMap<Character, RecipeSymbol> symbolShapeMap = new HashMap<>();

	public RecipeBuilderShaped(String modID) {
		super(modID);
	}

	public RecipeBuilderShaped(String modID, String... shape) {
		super(modID);
		setShape(shape);
	}

	public RecipeBuilderShaped setShape(String... shape) {
		this.height = shape.length;
		this.width = shape[0].length();

		for (int y = 0; y < this.height; y++) {
			this.width = Math.max(this.width, shape[y].length());
		}

		String[] internalShape = new String[height];
		for (int y = 0; y < internalShape.length; y++) {
			StringBuilder builder = new StringBuilder();
			String row = shape[y];
			for (int x = 0; x < width; x++) {
				if (x >= row.length()) {
					builder.append(" ");
				} else {
					builder.append(row.charAt(x));
				}
			}
			internalShape[y] = builder.toString();
		}

		this.shape = internalShape;
		return this;
	}

	public RecipeBuilderShaped setConsumeContainers(boolean consume) {
		this.consumeContainer = consume;
		return this;
	}

	public RecipeBuilderShaped addInput(char templateSymbol, String itemGroup) {
		return addInput(templateSymbol, new RecipeSymbol(itemGroup));
	}

	public  RecipeBuilderShaped addInput(char templateSymbol, ItemStack stack) {
		return addInput(templateSymbol, new RecipeSymbol(stack));
	}

	public  RecipeBuilderShaped addInput(char templateSymbol, IItemConvertible item, int meta) {
		ItemStack stack = item.getDefaultStack();
		stack.setMetadata(meta);
		return addInput(templateSymbol, new RecipeSymbol(stack));
	}

	public  RecipeBuilderShaped addInput(char templateSymbol, IItemConvertible item) {
		return addInput(templateSymbol, item, 0);
	}

	public RecipeBuilderShaped addInput(char templateSymbol, RecipeSymbol symbol) {
		symbolShapeMap.put(templateSymbol, symbol);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void create(String recipeID, ItemStack outputStack) {
		RecipeSymbol[] recipe = new RecipeSymbol[height * width];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Character ch = null;
				if (shape[y].length() > x) {
					ch = shape[y].charAt(x);
				}
				RecipeSymbol symbol = symbolShapeMap.get(ch);
				if (symbol == null) {
					recipe[x + y * width] = null;
				} else {
					if (symbol.getItemGroup() == null) {
						RecipeSymbol s = new RecipeSymbol(symbol.getStack());
						((Recipe) s).setSymbol(ch == null ? ' ' : ch);
						recipe[x + y * width] = s;
					} else {
						recipe[x + y * width] = new RecipeSymbol(ch == null ? ' ' : ch, symbol.getStack(), symbol.getItemGroup());
					}
				}
			}
		}

		((RecipeGroup<RecipeEntryCrafting<?, ?>>) RecipeBuilder.getRecipeGroup(modID, "workbench", new RecipeSymbol(Blocks.WORKBENCH.getDefaultStack()))
			).register(recipeID, new RecipeEntryCraftingShapedTools(width, height, recipe, outputStack, consumeContainer));
	}
}
