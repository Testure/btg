package turing.btg.material;

import com.google.common.base.Preconditions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import turing.btg.BTG;
import turing.btg.api.IToolType;
import turing.btg.api.ToolType;
import turing.btg.item.Items;
import turniplabs.halplibe.util.DirectoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialIconSet {
	public static final Map<String, MaterialIconSet> ICON_SETS = new HashMap<>();
	public static boolean didInit;

	public final String name;
	protected final MaterialIconSet parent;
	protected final List<String> textures;
	protected IconCoordinate blockTexture;

	protected MaterialIconSet(String name, MaterialIconSet parent) {
		this.name = name.toLowerCase();
		this.parent = parent;
		Preconditions.checkArgument(!ICON_SETS.containsKey(this.name), "MaterialIconSet '" + this.name + "' already exists!");
		ICON_SETS.put(this.name, this);
		textures = new ArrayList<>();
	}

	protected MaterialIconSet(String name) {
		this(name, IconSets.METAL);
	}

	public IconCoordinate getTextureIndexForItemType(MaterialItemType type) {
		String tex = BTG.MOD_ID + ":item/icon_sets/" + name + "/" + type.name;
		return textures.contains(tex) ? TextureRegistry.getTexture(tex) : parent != null ? parent.getTextureIndexForItemType(type) : null;
	}

	public IconCoordinate getOverlayTextureIndexForItemType(MaterialItemType type) {
		String tex = BTG.MOD_ID + ":item/icon_sets/" + name + "/" + type.name + "_overlay";
		return textures.contains(tex) ? TextureRegistry.getTexture(tex) : parent != null ? parent.getOverlayTextureIndexForItemType(type) : null;
	}

	public IconCoordinate getTextureIndicesForBlock() {
		return blockTexture != null ? blockTexture : parent != null ? parent.blockTexture != null ? parent.blockTexture : null : null;
	}

	public IconCoordinate getOverlayTextureIndexForBlock() {
		String tex = BTG.MOD_ID + ":block/icon_sets/" + name + "/block_overlay";
		if (textures.contains(tex)) {
			return TextureRegistry.getTexture(tex);
		} else if (parent == null) return null;
		return parent.getOverlayTextureIndexForBlock();
	}

	public IconCoordinate getOreTextureIndex() {
		String tex = BTG.MOD_ID + ":block/icon_sets/" + name + "/ore";
		return textures.contains(tex) ? TextureRegistry.getTexture(tex) : parent != null ? parent.getOreTextureIndex() : null;
	}

	public void initTextures(Minecraft mc) {
		if (blockTextureExists(mc, "block")) {
			String tex = BTG.MOD_ID + ":block/icon_sets/" + name + "/block";
			TextureRegistry.getTexture(tex);
			blockTexture = BTG.getBlockTexture(getPath("block"));
			textures.add(tex);
		}
		if (blockTextureExists(mc, "ore")) {
			String tex = BTG.MOD_ID + ":block/icon_sets/" + name + "/ore";
			TextureRegistry.getTexture(tex);
			textures.add(tex);
		}
		if (blockTextureExists(mc, "block_overlay")) {
			String tex = BTG.MOD_ID + ":block/icon_sets/" + name + "/block_overlay";
			TextureRegistry.getTexture(tex);
			textures.add(tex);
		}
		for (MaterialItemType type : MaterialItemType.ITEM_TYPES) {
			if (itemTextureExists(mc, type.name)) {
				String tex = BTG.MOD_ID + ":item/icon_sets/" + name + "/" + type.name;
				TextureRegistry.getTexture(tex);
				textures.add(tex);
			}
			if (itemTextureExists(mc, type.name + "_overlay")) {
				String tex = BTG.MOD_ID + ":item/icon_sets/" + name + "/" + type.name + "_overlay";
				TextureRegistry.getTexture(tex);
				textures.add(tex);
			}
		}
	}

	protected String getPath(String name) {
		return "icon_sets/" + this.name + "/" + name;
	}

	protected boolean itemTextureExists(Minecraft mc, String name) {
		String path = "icon_sets/" + this.name + "/" + name + ".png";
		return mc.textureManager.texturePacks.getResourceAsStream(DirectoryManager.getItemTextureDirectory(BTG.MOD_ID) + path) != null;
	}

	protected boolean blockTextureExists(Minecraft mc, String name) {
		String path = "icon_sets/" + this.name + "/" + name + ".png";
		return mc.textureManager.texturePacks.getResourceAsStream(DirectoryManager.getBlockTextureDirectory(BTG.MOD_ID) + path) != null;
	}

	public static void init(Minecraft mc) {
		if (didInit) return;
		didInit = true;
		TextureRegistry.register("bucket_overlay", TextureRegistry.itemAtlas);
		for (MaterialIconSet iconSet : ICON_SETS.values()) {
			iconSet.initTextures(mc);
		}
		if (!Items.TOOLS.get(ToolType.SWORD).isEmpty()) {
			for (IToolType toolType : ToolType.TYPES) {
				toolType.getOverlayTextureIndex();
				TextureRegistry.getTexture(toolType.getTextureIndex());
			}
		}
	}
}
