package turing.btg.recipe;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.item.Item;
import turing.btg.api.IChanceFunction;
import turing.btg.gui.GuiTextures;
import turing.btg.modularui.GuiTexture;
import turing.btg.modularui.api.ProgressMoveType;
import turing.btg.recipe.entries.SimpleRecipeEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeMap<T extends SimpleRecipeEntry> extends RecipeGroup<T> {
	public static final List<RecipeMap<?>> RECIPE_MAPS = new ArrayList<>();

	public final String localizationKey;
	public IChanceFunction chanceFunction = (chance, boost, tier) -> chance + (boost * tier);

	private final int minInputs, maxInputs;
	private final int minOutputs, maxOutputs;
	private final int minFluidInputs, maxFluidInputs;
	private final int minFluidOutputs, maxFluidOutputs;

	protected Map<Integer, GuiTexture> slotOverlays = new HashMap<>();
	protected GuiTexture progressBarTexture = GuiTextures.PROGRESS_ARROW;
	protected ProgressMoveType moveType = ProgressMoveType.HORIZONTAL;

	public RecipeMap(String key, int minInputs, int maxInputs, int minFluidInputs, int maxFluidInputs, int minOutputs, int maxOutputs, int minFluidOutputs, int maxFluidOutputs) {
		super(null);
		this.localizationKey = key;
		this.minInputs = minInputs;
		this.maxInputs = maxInputs;
		this.minOutputs = minOutputs;
		this.maxOutputs = maxOutputs;
		this.minFluidInputs = minFluidInputs;
		this.maxFluidInputs = maxFluidInputs;
		this.minFluidOutputs = minFluidOutputs;
		this.maxFluidOutputs = maxFluidOutputs;
		RECIPE_MAPS.add(this);
	}

	public RecipeMap<T> setProgressStyle(GuiTexture progressBarTexture, ProgressMoveType moveType) {
		this.progressBarTexture = progressBarTexture;
		this.moveType = moveType;
		return this;
	}

	public GuiTexture getProgressBarTexture() {
		return progressBarTexture;
	}

	public ProgressMoveType getProgressBarMoveType() {
		return moveType;
	}

	public int getMinInputs() {
		return minInputs;
	}

	public int getMaxInputs() {
		return maxInputs;
	}

	public int getMinOutputs() {
		return minOutputs;
	}

	public int getMaxOutputs() {
		return maxOutputs;
	}

	public int getMinFluidInputs() {
		return minFluidInputs;
	}

	public int getMaxFluidInputs() {
		return maxFluidInputs;
	}

	public int getMinFluidOutputs() {
		return minFluidOutputs;
	}

	public int getMaxFluidOutputs() {
		return maxFluidOutputs;
	}
}
