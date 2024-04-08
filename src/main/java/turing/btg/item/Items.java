package turing.btg.item;

import net.minecraft.core.item.Item;
import turing.btg.BTG;
import turing.btg.BTGConfig;
import turing.btg.material.MaterialItemType;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.helper.TextureHelper;

public class Items {
	protected static int NextID;

	public static Item resin;
	public static Item creativeSword;

	public static void init() {
		NextID = BTGConfig.config.getInt("StartingItemID");

		resin = ItemHelper.createItem(BTG.MOD_ID, new Item("stickyResin", NextID++).setIconIndex(TextureHelper.getOrCreateItemTextureIndex(BTG.MOD_ID, "sticky_resin.png")));
		creativeSword = ItemHelper.createItem(BTG.MOD_ID, new ItemCreativeSword("creativeSword", NextID++).setIconIndex(TextureHelper.getOrCreateItemTextureIndex(BTG.MOD_ID, "nano_saber.png")));
	}
}
