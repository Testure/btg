package turing.btg.client;

import net.minecraft.client.render.block.color.BlockColor;
import net.minecraft.core.world.World;

import java.util.HashMap;
import java.util.Map;

public class BlockColorMaterial extends BlockColor {
	public static final BlockColorMaterial INSTANCE = new BlockColorMaterial();
	private static final Map<Integer, Integer> c = new HashMap<>();

	static {
		c.put(0, 0xFF0000);
		c.put(1, 0x00FF00);
		c.put(2, 0x0000FF);
		c.put(3, 0xFFFF00);
	}

	@Override
	public int getFallbackColor(int meta) {
		return c.get(meta);
	}

	@Override
	public int getWorldColor(World world, int x, int y, int z) {
		return this.getFallbackColor(world.getBlockMetadata(x, y, z));
	}
}
