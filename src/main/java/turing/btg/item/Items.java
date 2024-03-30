package turing.btg.item;

import net.minecraft.core.item.Item;
import turing.btg.BTG;
import turing.btg.material.MaterialItemType;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.helper.TextureHelper;

public class Items {
	protected static int NextID;

	public static Item resin;

	public static void init() {
		NextID = BTG.config.getInt("Starting_Item_ID");

		resin = ItemHelper.createItem(BTG.MOD_ID, new Item("stickyResin", NextID++).setIconIndex(TextureHelper.getOrCreateItemTextureIndex(BTG.MOD_ID, "sticky_resin.png")));
	}
}
