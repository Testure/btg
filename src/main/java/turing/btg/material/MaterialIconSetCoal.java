package turing.btg.material;

import net.minecraft.client.render.stitcher.IconCoordinate;

public class MaterialIconSetCoal extends MaterialIconSet {
	protected MaterialIconSetCoal() {
		super("coal", IconSets.ROUGH);
	}

	@Override
	public IconCoordinate getOreTextureIndex() {
		return IconSets.DIAMOND.getOreTextureIndex();
	}

	@Override
	public IconCoordinate getTextureIndicesForBlock() {
		return IconSets.LAPIS.getTextureIndicesForBlock();
	}
}
