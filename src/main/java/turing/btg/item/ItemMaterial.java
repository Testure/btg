package turing.btg.item;

import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;
import turing.btg.BTG;
import turing.btg.api.ColoredTexture;
import turing.btg.api.IColored;
import turing.btg.api.ICustomDescription;
import turing.btg.api.IMaterialMetaHandler;
import turing.btg.material.Material;
import turing.btg.material.MaterialIconSet;
import turing.btg.material.MaterialItemType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ItemMaterial extends Item implements ICustomDescription, IMaterialMetaHandler, IColored {
	public final MaterialItemType type;
	private final int handlerID;

	public ItemMaterial(MaterialItemType type, int id, int handlerID) {
		super(type.name, id);
		this.type = type;
		this.handlerID = handlerID;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		withTags(ItemTags.NOT_IN_CREATIVE_MENU);
	}

	@Override
	public int getHandlerID() {
		return handlerID;
	}

	@Override
	public ColoredTexture[] getTextures(ItemStack stack) {
		int id = getMaterialIDForMeta(stack.getMetadata());
		if (Material.MATERIALS.get(id) == null) return new ColoredTexture[0];
		MaterialIconSet iconSet = Material.MATERIALS.get(id).getIconSet();
		IconCoordinate overlay = iconSet.getOverlayTextureIndexForItemType(type);
		IconCoordinate index = iconSet.getTextureIndexForItemType(type);
		ColoredTexture[] textures = new ColoredTexture[overlay != null ? 2 : 1];

		textures[0] = new ColoredTexture(index, Material.MATERIALS.get(id).getColor());

		if (overlay != null) {
			textures[1] = new ColoredTexture(overlay, -1);
		}

		return textures;
	}

	@Override
	public String getTranslatedName(ItemStack itemstack) {
		if (Material.MATERIALS.get(getMaterialIDForMeta(itemstack.getMetadata())) != null)
		return I18n.getInstance().translateKeyAndFormat(getLanguageKey(itemstack) + ".name", I18n.getInstance().translateNameKey("material." + Material.MATERIALS.get(getMaterialIDForMeta(itemstack.getMetadata())).name));
		else return "";
	}

	@Override
	public String getTranslatedDescription(ItemStack stack) {
		if (Material.MATERIALS.get(getMaterialIDForMeta(stack.getMetadata())) != null)
		return I18n.getInstance().translateKeyAndFormat(getLanguageKey(stack) + ".desc", I18n.getInstance().translateNameKey("material." + Material.MATERIALS.get(getMaterialIDForMeta(stack.getMetadata())).name).toLowerCase());
		else return "";
	}

	@Override
	public String[] getTooltips(ItemStack stack, boolean isShift, boolean isCtrl) {
		Material material = Material.MATERIALS.get(getMaterialIDForMeta(stack.getMetadata()));
		if (material != null && !material.getChemicalFormula().isEmpty()) {
			return new String[]{TextFormatting.formatted(material.getChemicalFormula(), TextFormatting.LIGHT_GRAY)};
		} else return null;
	}
}
