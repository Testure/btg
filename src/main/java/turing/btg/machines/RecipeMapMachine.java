package turing.btg.machines;

import turing.btg.api.Machine;
import turing.btg.api.MachineBehavior;
import turing.btg.api.MachineProperties;
import turing.btg.block.BlockMachine;
import turing.btg.modularui.Themes;
import turing.btg.modularui.api.ITheme;
import turing.btg.recipe.RecipeMap;
import turing.btg.recipe.entries.SimpleRecipeEntry;
import turing.btg.util.Function2;

public class RecipeMapMachine<T extends SimpleRecipeEntry> extends Machine {
	protected final RecipeMap<T> recipeMap;

	public RecipeMapMachine(String name, MachineProperties properties, Function2<MachineProperties, BlockMachine, MachineBehavior> behaviorSupplier, RecipeMap<T> map, ITheme theme) {
		super(name, properties, (p, b) -> {
			MachineBehavior behavior = behaviorSupplier.apply(p, b);
			if (map.getMaxInputs() + map.getMaxOutputs() > 0) behavior.initializeInventory(map.getMaxInputs(), map.getMaxOutputs());
			if (map.getMaxFluidInputs() + map.getMaxFluidOutputs() > 0) behavior.initializeTank(map.getMaxFluidInputs(), map.getMaxFluidOutputs());
			return behavior;
		}, theme);
		this.recipeMap = map;
	}

	public RecipeMapMachine(String name, MachineProperties properties, Function2<MachineProperties, BlockMachine, MachineBehavior> behaviorSupplier, RecipeMap<T> map) {
		this(name, properties, behaviorSupplier, map, Themes.DEFAULT);
	}

	public RecipeMap<T> getRecipeMap() {
		return recipeMap;
	}
}
