package turing.btg.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.FontRenderer;
import net.minecraft.client.render.Lighting;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import turing.btg.BTG;
import turing.btg.api.ColoredTexture;
import turing.btg.api.IColored;

import java.util.Random;

public class ItemModelColored extends ItemModelStandard {
	private final IColored asColored;

	public ItemModelColored(Item item, String namespace) {
		super(item, null);
		assert item instanceof IColored;
		this.asColored = (IColored) item;
	}

	@Override
	public void renderItemIntoGui(Tessellator tessellator, FontRenderer fontrenderer, RenderEngine renderengine, ItemStack itemStack, int x, int y, float brightness, float alpha) {
		if (itemStack != null) {
			ColoredTexture[] textures = asColored.getTextures(itemStack);
			if (textures != null) {
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				GL11.glEnable(2884);
				GL11.glDisable(2896);

				for (ColoredTexture texture : textures) {
					if (texture != null) {
						double uMin = texture.getTexture().getIconUMin();
						double uMax = texture.getTexture().getIconUMax();
						double vMin = texture.getTexture().getIconVMin();
						double vMax = texture.getTexture().getIconVMax();

						texture.getTexture().parentAtlas.bindTexture();
						tessellator.startDrawingQuads();
						if (texture.getColor() != -1) {
							tessellator.setColorRGBA_I(texture.getColor(), (int) (alpha * 255));
						} else {
							tessellator.setColorRGBA_I(-1, (int) (alpha * 255));
						}
						tessellator.addVertexWithUV(x, y + 16, 0.0, uMin, vMax);
						tessellator.addVertexWithUV(x + 16, y + 16, 0.0, uMax, vMax);
						tessellator.addVertexWithUV(x + 16, y, 0.0, uMax, vMin);
						tessellator.addVertexWithUV(x, y, 0.0, uMin, vMin);
						tessellator.draw();
					}
				}

				GL11.glEnable(2896);
				GL11.glEnable(2884);
				GL11.glDisable(3042);
			}
		}
	}

	@Override
	public void renderItemInWorld(Tessellator tessellator, Entity entity, ItemStack itemStack, float brightness, float alpha, boolean worldTransform) {
		GL11.glEnable(3042);
		GL11.glEnable(32826);
		GL11.glBlendFunc(770, 771);

		float f = 9.765625E-4F;
		if (worldTransform) {
			GL11.glTranslatef(-0.5F, -0.5F, 0.03125F);
		}

		ColoredTexture[] textures = asColored.getTextures(itemStack);
		if (textures != null) {
			for (ColoredTexture texture : textures) {
				if (texture != null) {
					if (texture.getColor() != -1) {
						int color = texture.getColor();
						float r = (color >> 16 & 0xFF) / 255F;
						float g = (color >> 8 & 0xFF) / 255F;
						float b = (color & 0xFF) / 255F;
						GL11.glColor4f(r * brightness, g * brightness, b * brightness, alpha);
					} else {
						GL11.glColor4f(brightness, brightness, brightness, alpha);
					}

					texture.getTexture().parentAtlas.bindTexture();
					int tileWidth = texture.getTexture().width;
					float uMin = (float) texture.getTexture().getIconUMin();
					float uMax = (float) texture.getTexture().getIconUMax();
					float vMin = (float) texture.getTexture().getIconVMin();
					float vMax = (float) texture.getTexture().getIconVMax();
					float uDiff = uMin - uMax;
					float vDiff = vMin - vMax;
					float G = 0.0625F * (16.0F / (float) tileWidth);
					float pixelWidth = 1.0F / (float) tileWidth;

					tessellator.startDrawingQuads();
					tessellator.setNormal(0.0F, 0.0F, 1.0F);
					tessellator.addVertexWithUV(0.0, 0.0, 0.0, uMax, vMax);
					tessellator.addVertexWithUV(1.0, 0.0, 0.0, uMin, vMax);
					tessellator.addVertexWithUV(1.0, 1.0, 0.0, uMin, vMin);
					tessellator.addVertexWithUV(0.0, 1.0, 0.0, uMax, vMin);
					tessellator.draw();
					tessellator.startDrawingQuads();
					tessellator.setNormal(0.0F, 0.0F, -1.0F);
					tessellator.addVertexWithUV(0.0, 1.0, -0.0625, uMax, vMin);
					tessellator.addVertexWithUV(1.0, 1.0, -0.0625, uMin, vMin);
					tessellator.addVertexWithUV(1.0, 0.0, -0.0625, uMin, vMax);
					tessellator.addVertexWithUV(0.0, 0.0, -0.0625, uMax, vMax);
					tessellator.draw();
					tessellator.startDrawingQuads();
					tessellator.setNormal(-1.0F, 0.0F, 0.0F);

					int i;
					float texProgress;
					float v;
					float y;
					for(i = 0; i < tileWidth; ++i) {
						texProgress = (float)i * pixelWidth;
						v = uMax + uDiff * texProgress - f;
						y = texProgress;
						tessellator.addVertexWithUV((double)y, 0.0, -0.0625, (double)v, (double)vMax);
						tessellator.addVertexWithUV((double)y, 0.0, 0.0, (double)v, (double)vMax);
						tessellator.addVertexWithUV((double)y, 1.0, 0.0, (double)v, (double)vMin);
						tessellator.addVertexWithUV((double)y, 1.0, -0.0625, (double)v, (double)vMin);
					}

					tessellator.draw();
					tessellator.startDrawingQuads();
					tessellator.setNormal(1.0F, 0.0F, 0.0F);

					for(i = 0; i < tileWidth; ++i) {
						texProgress = (float)i * pixelWidth;
						v = uMax + uDiff * texProgress - f;
						y = texProgress + G;
						tessellator.addVertexWithUV((double)y, 1.0, -0.0625, (double)v, (double)vMin);
						tessellator.addVertexWithUV((double)y, 1.0, 0.0, (double)v, (double)vMin);
						tessellator.addVertexWithUV((double)y, 0.0, 0.0, (double)v, (double)vMax);
						tessellator.addVertexWithUV((double)y, 0.0, -0.0625, (double)v, (double)vMax);
					}

					tessellator.draw();
					tessellator.startDrawingQuads();
					tessellator.setNormal(0.0F, 1.0F, 0.0F);

					for(i = 0; i < tileWidth; ++i) {
						texProgress = (float)i * pixelWidth;
						v = vMax + vDiff * texProgress - f;
						y = texProgress + G;
						tessellator.addVertexWithUV(0.0, (double)y, 0.0, (double)uMax, (double)v);
						tessellator.addVertexWithUV(1.0, (double)y, 0.0, (double)uMin, (double)v);
						tessellator.addVertexWithUV(1.0, (double)y, -0.0625, (double)uMin, (double)v);
						tessellator.addVertexWithUV(0.0, (double)y, -0.0625, (double)uMax, (double)v);
					}

					tessellator.draw();
					tessellator.startDrawingQuads();
					tessellator.setNormal(0.0F, -1.0F, 0.0F);

					for(i = 0; i < tileWidth; ++i) {
						texProgress = (float)i * pixelWidth;
						v = vMax + vDiff * texProgress - f;
						y = texProgress;
						tessellator.addVertexWithUV(1.0, (double)y, 0.0, (double)uMin, (double)v);
						tessellator.addVertexWithUV(0.0, (double)y, 0.0, (double)uMax, (double)v);
						tessellator.addVertexWithUV(0.0, (double)y, -0.0625, (double)uMax, (double)v);
						tessellator.addVertexWithUV(1.0, (double)y, -0.0625, (double)uMin, (double)v);
					}

					tessellator.draw();
				}
			}
		}

		GL11.glDisable(32826);
		GL11.glDisable(3042);
	}

	@Override
	public void renderAsItemEntity(Tessellator tessellator, @Nullable Entity entity, Random random, ItemStack itemstack, int renderCount, float yaw, float brightness, float partialTick) {
		Minecraft mc = Minecraft.getMinecraft(this);
		if (mc.fullbright || this.itemfullBright) brightness = 1.0F;

		EntityRenderDispatcher renderDispatcher = EntityRenderDispatcher.instance;
		ColoredTexture[] textures = asColored.getTextures(itemstack);
		if (textures != null) {
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			for (ColoredTexture texture : textures) {
				if (texture != null) {
					texture.getTexture().parentAtlas.bindTexture();

					if (texture.getColor() != -1) {
						Lighting.disable();
						int color = texture.getColor();
						float r = (color >> 16 & 0xFF) / 255F;
						float g = (color >> 8 & 0xFF) / 255F;
						float b = (color & 0xFF) / 255F;
						GL11.glColor4f(r * brightness, g * brightness, b * brightness, 1.0F);
					} else {
						GL11.glColor4f(brightness, brightness, brightness, 1.0F);
					}

					if (mc.gameSettings.items3D.value) {
						GL11.glPushMatrix();
						GL11.glScaled(1.0D, 1.0D, 1.0D);
						GL11.glRotated(yaw,0.0D, 1.0D, 0.0D);
						GL11.glTranslated(-0.5D, 0.0D, -0.05D * (renderCount - 1));
						for (int i = 0; i < renderCount; i++) {
							GL11.glPushMatrix();
							GL11.glTranslated(0.0D, 0.0D, 0.1D * i);
							renderItem(tessellator, renderDispatcher.itemRenderer, itemstack, entity, brightness, false);
							GL11.glPopMatrix();
						}
						GL11.glPopMatrix();
					} else {
						for (int i = 0; i < renderCount; i++) {
							GL11.glPushMatrix();

							if (i > 0) {
								GL11.glTranslatef((random.nextFloat() * 2.0F - 1.0F) * 0.3F, (random.nextFloat() * 2.0F - 1.0F) * 0.3F, (random.nextFloat() * 2.0F - 1.0F) * 0.3F);
							}

							GL11.glRotatef(180.0F - renderDispatcher.viewLerpYaw, 0.0F, 1.0F, 0.0F);
							renderFlat(tessellator, texture.getTexture());
							GL11.glPopMatrix();
						}
					}
				}
			}
		}
	}
}
