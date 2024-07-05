package turing.btg.recipe;

import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.Sys;
import turing.btg.BTG;
import turing.btg.api.IOreStoneType;
import turing.btg.api.IToolType;
import turing.btg.api.ToolType;
import turing.btg.block.Blocks;
import turing.btg.item.Items;
import turing.btg.material.Material;
import turing.btg.material.MaterialItemType;
import turing.btg.material.Materials;
import turing.btg.material.OreStoneType;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ItemGroups {
	private static final String namespace = "common_";

	public static void init() {
		Registries.ITEM_GROUPS.getItem("minecraft:logs").add(Blocks.rubberLog.getDefaultStack());
		Registries.ITEM_GROUPS.getItem("minecraft:leaves").add(Blocks.rubberLeaves.getDefaultStack());

		List<ItemStack> blueDyes = new ArrayList<>();
		blueDyes.add(Material.getItemForMaterial(Materials.LAPIS.id, MaterialItemType.GEM));
		blueDyes.add(Material.getItemForMaterial(Materials.SODALITE.id, MaterialItemType.GEM));
		blueDyes.add(Material.getItemForMaterial(Materials.LAZURITE.id, MaterialItemType.GEM));
		Registries.ITEM_GROUPS.register("btg:dyes_blue", blueDyes);

		for (Material material : Material.MATERIALS.values()) {
			for (MaterialItemType type : MaterialItemType.ITEM_TYPES) {
				if (material.hasItemType(type)) {
					addToGroup(Material.getItemForMaterial(material.id, type), getGroupName(type.getGroupName(), material));
				}
			}
			if (material.hasOre()) {
				for (IOreStoneType type : OreStoneType.TYPES) {
					addToGroup(Material.getOreForMaterial(material.id, type), namespace + type.getName() + "_ores:" + material.name);
					addToGroup(Material.getOreForMaterial(material.id, type), namespace + "ores:" + material.name);
				}
			}
			if (material.hasBlock()) {
				addToGroup(Material.getBlockForMaterial(material.id), getGroupName("blocks", material));
			}
			if (material.hasFlag("tools")) {
				for (IToolType toolType : ToolType.TYPES) {
					if (material.hasToolType(toolType)) {
						ItemStack stack = Items.TOOLS.get(toolType).get(material.id).getDefaultStack();
						addToGroup(stack, "btg:craftingTools_" + toolType.getName());
					}
				}
			}
		}
	}

	public static String getGroup(MaterialItemType type, Material material) {
		return namespace + type.getGroupName() + ":" + material.name;
	}

	public static String getGroupName(String type, Material material, String namespace) {
		return namespace + type + ":" + material.name;
	}

	public static String getGroupName(String type, Material material) {
		return getGroupName(type, material, namespace);
	}

	public static void addToGroup(ItemStack stack, String group) {
		List<ItemStack> list = Registries.ITEM_GROUPS.getItem(group);
		if (list == null) {
			list = new ArrayList<>();
			Registries.ITEM_GROUPS.register(group, list);
		}
		list.add(stack);
	}
}
