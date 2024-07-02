package turing.btg.api;

import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;

public class ColoredTexture {
	protected IconCoordinate texture;
	protected int color;

	public ColoredTexture(IconCoordinate texture, int color) {
		this.texture = texture;
		this.color = color;
	}

	public ColoredTexture(String texture, int color) {
		this(TextureRegistry.getTexture(texture), color);
	}

	public ColoredTexture(String namespace, String texturePath, int color) {
		this(namespace + ":item/" + texturePath, color);
	}

	public IconCoordinate getTexture() {
		return texture;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setTexture(IconCoordinate texture) {
		this.texture = texture;
	}
}
