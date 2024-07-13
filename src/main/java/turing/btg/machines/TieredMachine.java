package turing.btg.machines;

import turing.btg.BTG;
import turing.btg.BTGTextures;
import turing.btg.api.MachineProperties;
import turing.btg.api.TieredMachineBehavior;
import turing.btg.block.BlockMachine;
import turing.btg.block.Blocks;
import turing.btg.client.BlockModelMachine;
import turing.btg.recipe.RecipeMap;
import turing.btg.recipe.entries.SimpleRecipeEntry;
import turniplabs.halplibe.helper.BlockBuilder;

public class TieredMachine<T extends SimpleRecipeEntry> extends RecipeMapMachine<T> {
	protected final int tiers;
	protected final BlockMachine[] blocks;

	public TieredMachine(String name, int tiers, MachineProperties properties, RecipeMap<T> map) {
		super(name, properties, (p, block) -> new TieredMachineBehavior<>(p, map, block), map);
		this.tiers = tiers;
		this.blocks = new BlockMachine[tiers];
	}

	@Override
	public void registerBlocks() {
		for (int i = 0; i < tiers; i++) {
			blocks[i] = new BlockBuilder(BTG.MOD_ID)
				.setBlockModel(BlockModelMachine::new)
				.build(new BlockMachine(name + "_" + i, Blocks.NextMachineID++, behaviorSupplier, properties.copy().setCasing(BTGTextures.CASINGS[i]), this));
		}
	}

	@Override
	public BlockMachine[] getBlocks() {
		return blocks;
	}

	public int getTierFromBlock(BlockMachine block) {
		for (int i = 0; i < tiers; i++) {
			if (blocks[i] == block) return i;
		}
		return 0;
	}
}
