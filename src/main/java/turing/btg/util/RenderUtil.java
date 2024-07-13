package turing.btg.util;

import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

public class RenderUtil {
	private static BlockModel<Block> standardBlockModel;

	public static BlockModel<Block> getStandardBlockModel() {
		if (standardBlockModel == null) {
			standardBlockModel = BlockModelDispatcher.getInstance().getDispatch(Block.oreRedstoneGlowingStone);
		}
		return standardBlockModel;
	}

	public static void renderFullCube(Tessellator tessellator, int x, int y, int z, IconCoordinate texture, int color) {
		float r = ((color >> 16) & 255) / 255.0F;
		float g = ((color >> 8) & 255) / 255.0F;
		float b = (color & 255) / 255.0F;
		BlockModelStandard.renderBlocks.overrideBlockTexture = texture;
		BlockModelStandard.renderBlocks.renderStandardBlock(tessellator, getStandardBlockModel(), Block.dirt, x, y, z, r, g, b);
		BlockModelStandard.renderBlocks.overrideBlockTexture = null;
		tessellator.setColorOpaque_F(BlockModelStandard.renderBlocks.colorRedTopLeft, BlockModelStandard.renderBlocks.colorRedTopLeft, BlockModelStandard.renderBlocks.colorRedTopLeft);
	}

	public static void renderFullCube(Tessellator tessellator, int x, int y, int z, IconCoordinate texture) {
		renderFullCube(tessellator, x, y, z, texture, -1);
	}

	public static void renderSide(Tessellator tessellator, int x, int y, int z, Side side, IconCoordinate texture, int color, boolean overbright) {
		float r = ((color >> 16) & 255) / 255.0F;
		float g = ((color >> 8) & 255) / 255.0F;
		float b = (color & 255) / 255.0F;
		if (LightmapHelper.isLightmapEnabled()) {
			tessellator.setColorOpaque_I(-1);
			int lmc = overbright ? LightmapHelper.getLightmapCoord(15, 15) : BlockModelStandard.renderBlocks.lightmapCoordTopLeft;
			tessellator.setLightmapCoord(lmc);
		} else {
			float brightness = overbright ? 1.0F : BlockModelStandard.renderBlocks.colorRedTopLeft;
			tessellator.setColorOpaque_F(brightness, brightness, brightness);
		}
		BlockModelStandard.renderBlocks.overrideBlockTexture = texture;
		BlockModelStandard.renderBlocks.renderSide(tessellator, getStandardBlockModel(), Block.dirt, x, y, z, r, g, b, side, 0);
		BlockModelStandard.renderBlocks.overrideBlockTexture = null;
	}

	public static void renderSide(Tessellator tessellator, int x, int y, int z, Side side, IconCoordinate texture, boolean overbright) {
		renderSide(tessellator, x, y, z, side, texture, -1, overbright);
	}

	public static void renderSide(Tessellator tessellator, int x, int y, int z, Side side, IconCoordinate texture, int color) {
		renderSide(tessellator, x, y, z, side, texture, color, false);
	}

	public static void renderSide(Tessellator tessellator, int x, int y, int z, Side side, IconCoordinate texture) {
		renderSide(tessellator, x, y, z, side, texture, -1);
	}

	public static void renderFullCubeInventory(Tessellator tessellator, float brightness, float alpha, @Nullable Integer lightmapCoordinate, IconCoordinate texture, int color) {
		float r = ((color >> 16) & 255) / 255.0F;
		float g = ((color >> 8) & 255) / 255.0F;
		float b = (color & 255) / 255.0F;

		GL11.glColor4f(r * brightness, g * brightness, b * brightness, alpha);

		renderSideInventory(tessellator, Side.BOTTOM, texture, lightmapCoordinate);
		renderSideInventory(tessellator, Side.TOP, texture, lightmapCoordinate);
		renderSideInventory(tessellator, Side.NORTH, texture, lightmapCoordinate);
		renderSideInventory(tessellator, Side.SOUTH, texture, lightmapCoordinate);
		renderSideInventory(tessellator, Side.WEST, texture, lightmapCoordinate);
		renderSideInventory(tessellator, Side.EAST, texture, lightmapCoordinate);

		GL11.glColor4f(brightness, brightness, brightness, alpha);
	}

	public static void renderFullCubeInventory(Tessellator tessellator, float brightness, float alpha, @Nullable Integer lightmapCoordinate, IconCoordinate texture) {
		renderFullCubeInventory(tessellator, brightness, alpha, lightmapCoordinate, texture, -1);
	}

	public static void renderSideInventory(Tessellator tessellator, Side side, IconCoordinate texture, @Nullable Integer lightmapCoordinate) {
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		switch (side) {
			case BOTTOM:
				tessellator.startDrawingQuads();
				if (LightmapHelper.isLightmapEnabled() && lightmapCoordinate != null) {
					tessellator.setLightmapCoord(lightmapCoordinate);
				}
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				BlockModelStandard.renderBlocks.renderBottomFace(tessellator, Block.dirt, 0, 0, 0, texture);
				tessellator.draw();
				break;
			case TOP:
				tessellator.startDrawingQuads();
				if (LightmapHelper.isLightmapEnabled() && lightmapCoordinate != null) {
					tessellator.setLightmapCoord(lightmapCoordinate);
				}
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				BlockModelStandard.renderBlocks.renderTopFace(tessellator, Block.dirt, 0, 0, 0, texture);
				tessellator.draw();
				break;
			case NORTH:
				tessellator.startDrawingQuads();
				if (LightmapHelper.isLightmapEnabled() && lightmapCoordinate != null) {
					tessellator.setLightmapCoord(lightmapCoordinate);
				}
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				BlockModelStandard.renderBlocks.renderNorthFace(tessellator, Block.dirt, 0, 0, 0, texture);
				tessellator.draw();
				break;
			case SOUTH:
				tessellator.startDrawingQuads();
				if (LightmapHelper.isLightmapEnabled() && lightmapCoordinate != null) {
					tessellator.setLightmapCoord(lightmapCoordinate);
				}
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				BlockModelStandard.renderBlocks.renderSouthFace(tessellator, Block.dirt, 0, 0, 0, texture);
				tessellator.draw();
				break;
			case WEST:
				tessellator.startDrawingQuads();
				if (LightmapHelper.isLightmapEnabled() && lightmapCoordinate != null) {
					tessellator.setLightmapCoord(lightmapCoordinate);
				}
				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				BlockModelStandard.renderBlocks.renderWestFace(tessellator, Block.dirt, 0, 0, 0, texture);
				tessellator.draw();
				break;
			case EAST:
				tessellator.startDrawingQuads();
				if (LightmapHelper.isLightmapEnabled() && lightmapCoordinate != null) {
					tessellator.setLightmapCoord(lightmapCoordinate);
				}
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				BlockModelStandard.renderBlocks.renderEastFace(tessellator, Block.dirt, 0, 0, 0, texture);
				tessellator.draw();
				break;
		}
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	public static void resetGLColor() {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static void drawTexture(int x, int y, int width, int height, float scale, int textureScale) {
		float actualScale = scale / textureScale;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x, y + height, 0, 0, width * actualScale);
		tessellator.addVertexWithUV(x + width, y + height, 0, width * actualScale, width * actualScale);
		tessellator.addVertexWithUV(x + width, y, 0, width * actualScale, 0);
		tessellator.addVertexWithUV(x, y, 0, 0, 0);
		tessellator.draw();
	}

	public static void drawTexture(int x, int y, int u, int v, int width, int height, float scale, int textureWidth, int textureHeight) {
		float uScale = scale / textureWidth;
		float vScale = scale / textureHeight;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double)(x + 0), (double)(y + height), 0, (double)((float)(u + 0) * uScale), (double)((float)(v + height) * vScale));
		tessellator.addVertexWithUV((double)(x + width), (double)(y + height), 0, (double)((float)(u + width) * uScale), (double)((float)(v + height) * vScale));
		tessellator.addVertexWithUV((double)(x + width), (double)(y + 0), 0, (double)((float)(u + width) * uScale), (double)((float)(v + 0) * vScale));
		tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), 0, (double)((float)(u + 0) * uScale), (double)((float)(v + 0) * vScale));
		tessellator.draw();
	}

	public static void drawTexture(int x, int y, int u, int v, int width, int height, float scale) {
		drawTexture(x, y, u, v, width, height, scale, width, height);
	}

	public static void drawTexture(int x, int y, int width, int height, float scale) {
		drawTexture(x, y, width, height, scale, width);
	}
}
