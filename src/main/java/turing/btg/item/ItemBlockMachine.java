package turing.btg.item;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import turing.btg.api.ICustomDescription;
import turing.btg.api.MachineProperties;
import turing.btg.block.BlockMachine;

import java.util.ArrayList;
import java.util.List;

public class ItemBlockMachine extends ItemBlock implements ICustomDescription {
	protected final MachineProperties properties;

	public ItemBlockMachine(BlockMachine block) {
		super(block);
		properties = block.getProperties();
	}

	@Override
	public String[] getTooltips(ItemStack stack, boolean isShift, boolean isCtrl) {
		List<String> tooltips = new ArrayList<>();
		properties.addTooltips(stack, tooltips, isShift, isCtrl);
		return !tooltips.isEmpty() ? tooltips.toArray(new String[0]) : null;
	}
}
