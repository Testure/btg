package turing.btg.modularui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;

public class GuiTexture {
	protected int textureId;
	protected String texturePath;
	protected int width;
	protected int height;

	public GuiTexture(String texturePath, int width, int height) {
		this.texturePath = texturePath;
		this.width = width;
		this.height = height;
	}

	public GuiTexture(String texturePath, int size) {
		this(texturePath, size, size);
	}

	public int getTextureId() {
		if (textureId == 0) {
			textureId = Minecraft.getMinecraft().textureManager.loadTexture(texturePath).id();
		}
		return textureId;
	}

	public void bindTexture() {
		Minecraft.getMinecraft().textureManager.bindTexture(getTextureId());
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void draw(int x, int y, float scale) {
		float actualScale = scale / width;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture();
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x, y + height, 0, 0, width * actualScale);
		tessellator.addVertexWithUV(x + width, y + height, 0, width * actualScale, width * actualScale);
		tessellator.addVertexWithUV(x + width, y, 0, width * actualScale, 0);
		tessellator.addVertexWithUV(x, y, 0, 0, 0);
		tessellator.draw();
	}

	public void draw(int x, int y) {
		draw(x, y, 1F);
	}
}
