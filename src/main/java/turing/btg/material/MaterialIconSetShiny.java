package turing.btg.material;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;

public class MaterialIconSetShiny extends MaterialIconSet {
	public MaterialIconSetShiny() {
		super("shiny");
	}

	@Override
	public void initTextures(Minecraft mc) {
		this.blockTexture = TextureRegistry.getTexture("minecraft:block/block_iron_top");
		super.initTextures(mc);
	}
}
