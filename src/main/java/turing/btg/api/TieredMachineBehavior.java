package turing.btg.api;

import turing.btg.block.BlockMachine;
import turing.btg.machines.TieredMachine;
import turing.btg.recipe.RecipeMap;
import turing.btg.recipe.entries.SimpleRecipeEntry;

public class TieredMachineBehavior<T extends SimpleRecipeEntry> extends RecipeMapMachineBehavior<T> {
	protected final int tier;

	public TieredMachineBehavior(MachineProperties properties, RecipeMap<T> map, BlockMachine block) {
		super(properties, map);
		this.tier = ((TieredMachine<?>) block.machine).getTierFromBlock(block);
	}

	public int getTier() {
		return tier;
	}
}
