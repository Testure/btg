package turing.btg.api;

import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.item.Item;
import turing.btg.BTG;
import turing.btg.util.Function3;

public interface IToolType {
	String getName();

	Function3<String, Integer, Integer, Item> getConstructor();

	default String getTextureIndex() {
		return BTG.MOD_ID + ":item/tools/handle_" + getName();
	}

	default String[] getCapabilities() {
		return new String[]{getName()};
	}

	default IconCoordinate getOverlayTextureIndex() {
		return BTG.getItemTexture("tools/" + getName());
	}

	default boolean isFull3D() {
		return false;
	}

	default boolean requiresTag() {
		return false;
	}

	default boolean isVanillaType() {
		return false;
	}
}
