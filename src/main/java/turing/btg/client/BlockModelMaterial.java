package turing.btg.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import turing.btg.api.IMaterialMetaHandler;
import turing.btg.block.BlockMaterial;
import turing.btg.material.Material;
import turing.btg.material.MaterialIconSet;
import turing.btg.material.Materials;

public class BlockModelMaterial<T extends BlockMaterial> extends BlockModelStandard<T> {
	public BlockModelMaterial(T block) {
		super(block);
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
	public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
		MaterialIconSet set = this.block.getIconSet(data);
		return set != null ? set.getTextureIndicesForBlock() : BLOCK_TEXTURE_UNASSIGNED;
	}

	public IconCoordinate getOverlayIndex(int materialID) {
		Material material = Material.MATERIALS.get(materialID);
		if (material == null) return null;
		return material.getIconSet().getOverlayTextureIndexForBlock();
	}

	private IconCoordinate getOverlayIndex(int x, int y, int z) {
		return getOverlayIndex(getMeta(x, y, z) + (Materials.iMETA_LIMIT * getHandlerID(x, y, z)));
	}

	private int getMeta(int x, int y, int z) {
		World world = Minecraft.getMinecraft(this).theWorld;
		return world.getBlockMetadata(x, y, z);
	}

	private int getHandlerID(int x, int y, int z) {
		World world = Minecraft.getMinecraft(this).theWorld;
		Block block = world.getBlock(x, y, z);
		if (block instanceof IMaterialMetaHandler) {
			return ((IMaterialMetaHandler) block).getHandlerID();
		}
		return 0;
	}

	private boolean renderOverlay(Tessellator tessellator, int x, int y, int z) {
		IconCoordinate index = getOverlayIndex(x, y, z);
		if (index == null) return true;
		renderBlocks.overrideBlockTexture = index;
		boolean rendered = renderBlocks.renderStandardBlock(tessellator, ((BlockModel<Block>) ((BlockModel<?>) this)), this.block, x, y, z, 1.0F, 1.0F, 1.0F);
		renderBlocks.overrideBlockTexture = null;
		return rendered;
	}

	@Override
	public boolean render(Tessellator tessellator, int x, int y, int z) {
		return super.render(tessellator, x, y, z) && renderOverlay(tessellator, x, y, z);
	}

	@Override
	public boolean renderNoCulling(Tessellator tessellator, int x, int y, int z) {
		return super.renderNoCulling(tessellator, x, y, z) && renderOverlay(tessellator, x, y, z);
	}

	@Override
	public boolean renderWithOverrideTexture(Tessellator tessellator, int x, int y, int z, IconCoordinate textureIndex) {
		return super.renderWithOverrideTexture(tessellator, x, y, z, textureIndex) && renderOverlay(tessellator, x, y, z);
	}
}
