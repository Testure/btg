package turing.btg.material;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import turing.btg.BTG;

public class MaterialIconSetMagnetic extends MaterialIconSet {
	public MaterialIconSetMagnetic() {
		super("magnetic", IconSets.METAL);
	}

	@Override
	public void initTextures(Minecraft mc) {
		super.initTextures(mc);
		TextureRegistry.getTexture(BTG.MOD_ID + ":item/magnetic_overlay");
	}

	@Override
	public IconCoordinate getOverlayTextureIndexForItemType(MaterialItemType type) {
		String tex = BTG.MOD_ID + "item:icon_sets/" + name + "/" + type.name + "_overlay";
		if (!textures.contains(tex)) {
			return TextureRegistry.getTexture(BTG.MOD_ID + ":item/magnetic_overlay");
		}
		return super.getOverlayTextureIndexForItemType(type);
	}
}
