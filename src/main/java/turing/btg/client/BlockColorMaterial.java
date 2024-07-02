package turing.btg.client;

import net.minecraft.client.render.block.color.BlockColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import turing.btg.api.IMaterialMetaHandler;
import turing.btg.material.Material;

import java.util.HashMap;
import java.util.Map;

public class BlockColorMaterial extends BlockColor implements IMaterialMetaHandler {
	private final int handlerID;

	public BlockColorMaterial(int handlerID) {
		this.handlerID = handlerID;
	}

	@Override
	public int getHandlerID() {
		return handlerID;
	}

	@Override
	public int getFallbackColor(int meta) {
		int id = getMaterialIDForMeta(meta);
		return Material.MATERIALS.get(id) != null ? Material.MATERIALS.get(id).getColor() : -1;
	}

	@Override
	public int getWorldColor(WorldSource world, int x, int y, int z) {
		return this.getFallbackColor(world.getBlockMetadata(x, y, z));
	}
}
