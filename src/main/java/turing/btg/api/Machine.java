package turing.btg.api;

import turing.btg.BTG;
import turing.btg.block.BlockMachine;
import turing.btg.block.Blocks;
import turing.btg.client.BlockModelMachine;
import turing.btg.item.ItemBlockMachine;
import turing.btg.machines.Machines;
import turing.btg.modularui.Themes;
import turing.btg.modularui.api.ITheme;
import turing.btg.util.Function2;
import turniplabs.halplibe.helper.BlockBuilder;

public class Machine {
	public final String name;
	public final MachineProperties properties;
	public final Function2<MachineProperties, BlockMachine, MachineBehavior> behaviorSupplier;
	public ITheme theme;
	private BlockMachine block;

	public Machine(String name, MachineProperties properties, Function2<MachineProperties, BlockMachine, MachineBehavior> behaviorSupplier, ITheme theme) {
		this.name = name;
		this.properties = properties;
		this.behaviorSupplier = behaviorSupplier;
		this.theme = theme;
		Machines.MACHINES.put(name, this);
	}

	public Machine(String name, MachineProperties properties, Function2<MachineProperties, BlockMachine, MachineBehavior> behaviorSupplier) {
		this(name, properties, behaviorSupplier, Themes.DEFAULT);
	}

	public Machine setTheme(ITheme theme) {
		this.theme = theme;
		return this;
	}

	public void registerBlocks() {
		this.block = new BlockBuilder(BTG.MOD_ID)
			.setBlockModel(BlockModelMachine::new)
			.setItemBlock(b -> new ItemBlockMachine((BlockMachine) b))
			.build(new BlockMachine(name, Blocks.NextMachineID++, behaviorSupplier, properties, this));
	}

	public BlockMachine[] getBlocks() {
		return new BlockMachine[]{block};
	}
}
