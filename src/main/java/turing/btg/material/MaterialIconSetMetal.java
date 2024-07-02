package turing.btg.material;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.Side;

public class MaterialIconSetMetal extends MaterialIconSet {
	public MaterialIconSetMetal() {
		super("metal", null);
	}

	@Override
	public void initTextures(Minecraft mc) {
		this.blockTexture = TextureRegistry.getTexture("minecraft:block/block_iron_side");
		super.initTextures(mc);
	}

	@Override
	public IconCoordinate getTextureIndexForItemType(MaterialItemType type) {
		if (type == MaterialItemType.INGOT) {
			return TextureRegistry.getTexture("minecraft:item/ingot_iron");
		}
		return super.getTextureIndexForItemType(type);
	}
}
