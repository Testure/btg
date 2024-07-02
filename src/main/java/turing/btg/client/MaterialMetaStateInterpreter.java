package turing.btg.client;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.WorldSource;
import org.useless.dragonfly.model.blockstates.processed.MetaStateInterpreter;

import java.util.HashMap;

public class MaterialMetaStateInterpreter extends MetaStateInterpreter {
	@Override
	public HashMap<String, String> getStateMap(WorldSource worldSource, int x, int y, int z, Block block, int meta) {
		HashMap<String, String> map = new HashMap<>();
		map.put("material", String.valueOf(meta));
		return map;
	}
}
