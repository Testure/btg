package turing.btg.client;

import net.minecraft.client.render.RenderBlocks;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import org.lwjgl.opengl.GL11;
import turing.btg.api.IMaterialMetaHandler;
import turing.btg.entity.EntityFallingOre;
import turing.btg.material.Material;

public class FallingOreRenderer extends EntityRenderer<EntityFallingOre> {
	public final RenderBlocks renderBlocks = new RenderBlocks();

	public FallingOreRenderer() {
		this.shadowSize = 0.0F;
	}

	@Override
	public void doRender(Tessellator tessellator, EntityFallingOre entity, double x, double y, double z, float yaw, float pt) {
		int ix = MathHelper.floor_double(entity.x);
		int iy = MathHelper.floor_double(entity.y);
		int iz = MathHelper.floor_double(entity.z);
		float brightness = entity.world.getLightBrightness(ix, iy, iz);
		TextureRegistry.blockAtlas.bindTexture();

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glDisable(2896);

		renderBlock(brightness, ix, iy, iz, entity.stoneType.getBaseBlock(), 0);
		renderBlock(brightness, ix, iy, iz, Block.getBlock(entity.blockId), entity.blockMeta);

		GL11.glEnable(2896);
		GL11.glPopMatrix();
	}

	public void renderBlock(float brightness, int x, int y, int z, Block block, int meta) {
		float r = 1.0F;
		float g = 1.0F;
		float b = 1.0F;

		if (meta != 0 && block instanceof IMaterialMetaHandler) {
			int color = Material.MATERIALS.get(((IMaterialMetaHandler) block).getMaterialIDForMeta(meta)).getColor();
			r = ((color >> 16) & 0xFF) / 255F;
			g = ((color >> 8) & 0xFF) / 255F;
			b = (color & 0xFF) / 255F;
		}

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(r * brightness, g * brightness, b * brightness);

		renderBlocks.renderBottomFace(tessellator, block, -0.5, -0.5, -0.5, BlockModelDispatcher.getInstance().getDispatch(block).getBlockTextureFromSideAndMetadata(Side.BOTTOM, meta));

		renderBlocks.renderTopFace(tessellator, block, -0.5, -0.5, -0.5, BlockModelDispatcher.getInstance().getDispatch(block).getBlockTextureFromSideAndMetadata(Side.TOP, meta));

		renderBlocks.renderNorthFace(tessellator, block, -0.5, -0.5, -0.5, BlockModelDispatcher.getInstance().getDispatch(block).getBlockTextureFromSideAndMetadata(Side.NORTH, meta));

		renderBlocks.renderSouthFace(tessellator, block, -0.5, -0.5, -0.5, BlockModelDispatcher.getInstance().getDispatch(block).getBlockTextureFromSideAndMetadata(Side.SOUTH, meta));

		renderBlocks.renderWestFace(tessellator, block, -0.5, -0.5, -0.5, BlockModelDispatcher.getInstance().getDispatch(block).getBlockTextureFromSideAndMetadata(Side.WEST, meta));

		renderBlocks.renderEastFace(tessellator, block, -0.5, -0.5, -0.5, BlockModelDispatcher.getInstance().getDispatch(block).getBlockTextureFromSideAndMetadata(Side.EAST, meta));

		tessellator.draw();
	}
}
