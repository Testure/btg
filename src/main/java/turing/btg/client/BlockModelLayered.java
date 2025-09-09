package turing.btg.client;

import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import turing.btg.block.BlockMaterial;
import turing.btg.material.MaterialIconSet;

public class BlockModelLayered<T extends BlockMaterial> extends BlockModelStandard<T> {
	private final Block<?> base;
	private final int baseMeta;
	private final IconCoordinate particleTextureIndex;

	public BlockModelLayered(T block, Block<?> base, IconCoordinate particleTextureIndex, int baseMeta) {
		super(block);
		this.base = base;
		this.baseMeta = baseMeta;
		this.particleTextureIndex = particleTextureIndex;
	}

	public BlockModelLayered(T block, Block<?> base, IconCoordinate particleTextureIndex) {
		this(block, base, particleTextureIndex, 0);
	}

	@Override
	public boolean render(Tessellator tessellator, int x, int y, int z) {
		return renderBlocks.renderStandardBlock(tessellator, BlockModelDispatcher.getInstance().getDispatch(this.base), this.base.getBounds(), x, y, z) && renderStandardBlock(tessellator, this.block.getBounds(), x, y, z);
	}

	@Override
	public boolean renderNoCulling(Tessellator tessellator, int x, int y, int z) {
		renderBlocks.renderAllFaces = true;
		boolean val = renderStandardBlock(tessellator, this.base.getBounds(), x, y, z) && renderStandardBlock(tessellator, this.block.getBounds(), x, y, z);
		renderBlocks.renderAllFaces = false;
		return val;
	}

	@Override
	public boolean renderWithOverrideTexture(Tessellator tessellator, int x, int y, int z, IconCoordinate textureIndex) {
		boolean val = renderStandardBlock(tessellator, this.base.getBounds(), x, y, z);
		renderBlocks.overrideBlockTexture = textureIndex;
		val &= renderStandardBlock(tessellator, this.block.getBounds(), x, y, z);
		renderBlocks.overrideBlockTexture = null;
		return val;
	}

	@Override
	public void renderBlockOnInventory(Tessellator tessellator, int metadata, float brightness, float alpha, Integer lightmapCorrdinate) {
		BlockModelDispatcher.getInstance().getDispatch(this.base).renderBlockOnInventory(tessellator, this.baseMeta, brightness, alpha, lightmapCorrdinate);
		super.renderBlockOnInventory(tessellator, metadata, brightness, alpha, lightmapCorrdinate);
	}

	@Override
	public boolean shouldItemRender3d() {
		return true;
	}

	@Override
	public float getItemRenderScale() {
		return 0.25F;
	}

	@Override
	public IconCoordinate getBlockTexture(WorldSource worldSource, int i, int j, int k, Side side) {
		return getBlockTextureFromSideAndMetadata(side, worldSource.getBlockMetadata(i, j, k));
	}

	@Override
	public IconCoordinate getBlockOverbrightTexture(WorldSource worldSource, int x, int y, int z, int meta) {
		return null;
	}

	@Override
	public IconCoordinate getBlockOverbrightTextureFromSideAndMeta(Side side, int meta) {
		return null;
	}

	@Override
	public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int meta) {
		MaterialIconSet iconSet = this.block.getIconSet(meta);
		return iconSet != null ? iconSet.getOreTextureIndex() : null;
	}

	@Override
	public IconCoordinate getParticleTexture(Side side, int meta) {
		return particleTextureIndex;
	}

	public Block getBase() {
		return base;
	}

	public int getBaseMeta() {
		return baseMeta;
	}
}
