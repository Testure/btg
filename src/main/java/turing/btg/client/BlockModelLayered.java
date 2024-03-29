package turing.btg.client;

import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.core.block.Block;
import turing.btg.mixin.BlockModelRenderBlocksAccessor;

public class BlockModelLayered extends BlockModelRenderBlocks {
	private final Block base;
	private final int baseMeta;
	private final int particleTextureIndex;

	public BlockModelLayered(Block base, int particleTextureIndex, int baseMeta) {
		super(0);
		this.base = base;
		this.baseMeta = baseMeta;
		this.particleTextureIndex = particleTextureIndex;
	}

	public BlockModelLayered(Block base, int particleTextureIndex) {
		this(base, particleTextureIndex, 0);
	}

	@Override
	public boolean render(Block block, int x, int y, int z) {
		return BlockModelRenderBlocksAccessor.getRenderBlocks().renderStandardBlock(this.base, x, y, z) && BlockModelRenderBlocksAccessor.getRenderBlocks().renderStandardBlock(block, x, y, z);
	}

	@Override
	public boolean renderNoCulling(Block block, int x, int y, int z) {
		return BlockModelRenderBlocksAccessor.getRenderBlocks().renderBlockAllFaces(this.base, 0, x, y, z) && BlockModelRenderBlocksAccessor.getRenderBlocks().renderBlockByRenderType(block, 0, x, y, z);
	}

	@Override
	public boolean renderWithOverrideTexture(Block block, int x, int y, int z, int textureIndex) {
		return BlockModelRenderBlocksAccessor.getRenderBlocks().renderStandardBlock(this.base, x, y, z) && BlockModelRenderBlocksAccessor.getRenderBlocks().renderBlockUsingTexture(block, 0, x, y, z, textureIndex);
	}

	@Override
	public boolean shouldItemRender3d() {
		return true;
	}

	@Override
	public float getItemRenderScale() {
		return 0.25F;
	}

	public Block getBase() {
		return base;
	}

	public int getBaseMeta() {
		return baseMeta;
	}

	public int getParticleTextureIndex() {
		return particleTextureIndex;
	}
}
