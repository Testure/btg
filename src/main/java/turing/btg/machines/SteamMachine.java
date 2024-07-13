package turing.btg.machines;

import turing.btg.api.MachineProperties;
import turing.btg.api.SteamMachineBehavior;
import turing.btg.modularui.Themes;
import turing.btg.recipe.RecipeMap;
import turing.btg.recipe.entries.SimpleRecipeEntry;

public class SteamMachine<T extends SimpleRecipeEntry> extends RecipeMapMachine<T> {
	protected final boolean isHighPressure;

	public SteamMachine(String name, MachineProperties properties, RecipeMap<T> map, boolean isHighPressure) {
		super(name, properties, (p, block) -> new SteamMachineBehavior<>(p, map, isHighPressure), map, isHighPressure ? Themes.DEFAULT : Themes.BRONZE);
		this.isHighPressure = isHighPressure;
	}

	public SteamMachine(String name, MachineProperties properties, RecipeMap<T> map) {
		this(name, properties, map, false);
	}
}
