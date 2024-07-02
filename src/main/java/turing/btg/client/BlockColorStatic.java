package turing.btg.client;

import net.minecraft.client.render.block.color.BlockColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;

public class BlockColorStatic extends BlockColor {
	private final int color;

	public BlockColorStatic(int color) {
		this.color = color;
	}

	@Override
	public int getFallbackColor(int i) {
		return color;
	}

	@Override
	public int getWorldColor(WorldSource world, int i, int j, int k) {
		return color;
	}
}
