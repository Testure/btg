package turing.btg.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.item.Item;
import turing.btg.api.IItemToolMaterial;
import turing.btg.material.MaterialIconSet;

public class ItemModelMaterial extends ItemModelColored {
	public ItemModelMaterial(Item item, String namespace, String type) {
		super(item, namespace);
		this.bFull3D = type.contains("stick") || (item instanceof IItemToolMaterial && ((IItemToolMaterial) item).getToolType().isFull3D());
		MaterialIconSet.init(Minecraft.getMinecraft(this));
	}
}
