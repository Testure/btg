package turing.btg.client;

import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.item.Item;
import turing.btg.BTG;

public class ItemModelBucket extends ItemModelColored {
	public ItemModelBucket(Item item, String namespace) {
		super(item, namespace);
		TextureRegistry.getTexture(BTG.MOD_ID + ":item/bucket_overlay");
	}
}
