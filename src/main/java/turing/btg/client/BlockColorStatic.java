package turing.btg.client;

import net.minecraft.client.render.block.color.BlockColor;
import net.minecraft.core.world.World;

public class BlockColorStatic extends BlockColor {
	protected final int color;

	public BlockColorStatic(int color) {
		this.color = color;
	}

	@Override
	public int getFallbackColor(int i) {
		return this.color;
	}

	@Override
	public int getWorldColor(World world, int i, int j, int k) {
		return this.color;
	}
}
