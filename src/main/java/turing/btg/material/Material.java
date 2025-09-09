package turing.btg.material;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import turing.btg.api.IMaterialProperty;
import turing.btg.api.IOreStoneType;
import turing.btg.api.IToolType;
import turing.btg.block.BlockMaterial;
import turing.btg.block.BlockOreMaterial;
import turing.btg.block.Blocks;
import turing.btg.item.ItemMaterial;
import turing.btg.item.Items;

import java.util.*;

public class Material {
	public static final Map<Integer, Material> MATERIALS = new HashMap<>();

	public final int id;
	public final String name;
	protected Block existingBlock;
	protected MaterialIconSet iconSet;
	protected String chemicalFormula;
	protected Element element;
	protected int color = -1;
	protected List<MaterialStack> componentList;
	protected List<IMaterialProperty> properties;
	protected List<MaterialFlag<?>> flags;
	protected Map<MaterialItemType, ItemStack> manualItemProvider;

	public Material(int id, String name) {
		this.id = id;
		this.name = name;
		this.iconSet = IconSets.METAL;
		MATERIALS.put(this.id, this);
	}

	public static ItemStack getItemForMaterial(int materialID, MaterialItemType type, int amount) {
		Material material = MATERIALS.get(materialID);
		if (material.manualItemProvider != null && material.manualItemProvider.get(type) != null) {
			ItemStack stack = material.manualItemProvider.get(type).copy();
			stack.stackSize = amount;
			return stack;
		}
		ItemMaterial item = Items.MATERIAL_ITEMS.get(type).get(MathHelper.floor_float(materialID / Materials.fMETA_LIMIT));
		return new ItemStack(item, amount, item.getMetaForMaterialID(materialID));
	}

	public static ItemStack getItemForMaterial(int materialID, MaterialItemType type) {
		return getItemForMaterial(materialID, type, 1);
	}

	public static ItemStack getBlockForMaterial(int materialID, int amount) {
		Material material = MATERIALS.get(materialID);
		if (material.existingBlock != null) return new ItemStack(material.existingBlock, amount);
		BlockMaterial block = Blocks.materialBlock.get(MathHelper.floor_float(materialID / Materials.fMETA_LIMIT));
		return new ItemStack(block, amount, block.getMetaForMaterialID(materialID));
	}

	public static ItemStack getBlockForMaterial(int materialID) {
		return getBlockForMaterial(materialID, 1);
	}

	public static ItemStack getOreForMaterial(int materialID, IOreStoneType type, int amount) {
		Material material = MATERIALS.get(materialID);
		if (!material.hasOre()) throw new NullPointerException("Material does not have an ore block!");
		List<BlockOreMaterial> list = Blocks.ores.get(type);
		if (list == null) throw new NullPointerException("Could not find ores for stone type " + type);
		BlockOreMaterial block = list.get(MathHelper.floor_float(materialID / Materials.fMETA_LIMIT));
		return new ItemStack(block, amount, block.getMetaForMaterialID(materialID));
	}

	public static ItemStack getOreForMaterial(int materialID, IOreStoneType type) {
		return getOreForMaterial(materialID, type, 1);
	}

	public static Material getMaterialForItem(ItemStack item) {
		if (item.getItem() instanceof ItemMaterial) {
			return MATERIALS.get(((ItemMaterial) item.getItem()).getMaterialIDForMeta(item.getMetadata()));
		}
		for (Material material : MATERIALS.values()) {
			if (material.existingBlock != null && material.existingBlock.id() == item.itemID) {
				return material;
			}
			if (material.manualItemProvider != null) {
				for (ItemStack stack : material.manualItemProvider.values()) {
					if (stack.itemID == item.itemID && stack.getMetadata() == item.getMetadata()) {
						return material;
					}
				}
			}
		}
		return null;
	}

	public static int getIdFromName(String name) {
		for (Material material : MATERIALS.values()) {
			if (material.name.equalsIgnoreCase(name)) {
				return material.id;
			}
		}
		return -1;
	}

	public boolean isBlockExisting() {
		return existingBlock != null;
	}

	public boolean isItemExisting(MaterialItemType type) {
		return manualItemProvider != null && manualItemProvider.get(type) != null;
	}

	public boolean hasBlock() {
		return properties.stream().anyMatch(property -> property == MaterialProperties.INGOT || property == MaterialProperties.GEM || property == MaterialProperties.DUST);
	}

	public boolean hasOre() {
		return properties.stream().anyMatch(IMaterialProperty::hasOre);
	}

	public boolean hasItemType(MaterialItemType type) {
		return properties.stream().anyMatch(prop -> prop.hasItemType(type));
	}

	protected String calculateChemicalFormula() {
		if (chemicalFormula != null && !chemicalFormula.isEmpty()) return chemicalFormula;
		if (element != null) return element.symbol;
		if (!componentList.isEmpty()) {
			if (componentList.size() == 1) {
				MaterialStack stack = componentList.get(0);
				if (stack.count > 0) {
					return stack.material.getChemicalFormula();
				}
			}
			StringBuilder builder = new StringBuilder();
			for (MaterialStack stack : componentList)
				builder.append(stack.format());
			return builder.toString();
		}
		return "";
	}

	public boolean validate() {
		for (IMaterialProperty property : properties) {
			if (!property.validateMaterial(this)) return false;
		}
		for (MaterialFlag<?> flag : flags) {
			if (!flag.validate(this)) return false;
		}
		return true;
	}

	public boolean hasFlag(String name) {
		return flags.stream().anyMatch(flag -> flag.name.equalsIgnoreCase(name));
	}

	public boolean hasToolType(IToolType toolType) {
		if (hasFlag("basic_tools_only")) return toolType.isVanillaType();
		return !toolType.requiresTag() || hasFlag("generate_" + toolType.getName());
	}

	@SuppressWarnings("unchecked")
	public <T> T getFlagValue(MaterialFlag<T> flag) {
		Optional<MaterialFlag<?>> value = flags.stream().filter(f -> f.name.equals(flag.name)).findFirst();
		return value.map(materialFlag -> (T) materialFlag.getValue()).orElse(null);
	}

	public ToolStats getToolStats() {
		if (!hasFlag("tools")) throw new NullPointerException("Material does not have tools!");
		return getFlagValue(MaterialFlag.TOOLS);
	}

	public long getProtons() {
		if (element != null) return element.protons;
		if (componentList.isEmpty()) return 45L;
		long totalProtons = 0, totalAmount = 0;
		for (MaterialStack stack : componentList) {
			totalAmount += stack.count;
			totalProtons += stack.count * stack.material.getProtons();
		}
		return totalProtons / totalAmount;
	}

	public long getNeutrons() {
		if (element != null) return element.neutrons;
		if (componentList.isEmpty()) return 0L;
		long totalNeutrons = 0, totalAmount = 0;
		for (MaterialStack stack : componentList) {
			totalAmount += stack.count;
			totalNeutrons += stack.count * stack.material.getNeutrons();
		}
		return totalNeutrons / totalAmount;
	}

	public long getMass() {
		return getProtons() + getNeutrons();
	}

	public int getColor() {
		return color;
	}

	public MaterialIconSet getIconSet() {
		return iconSet;
	}

	public String getChemicalFormula() {
		return chemicalFormula;
	}
}
