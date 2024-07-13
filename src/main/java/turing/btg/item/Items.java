package turing.btg.item;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucket;
import turing.btg.BTG;
import turing.btg.BTGConfig;
import turing.btg.api.IItemToolMaterial;
import turing.btg.api.IToolType;
import turing.btg.api.ToolType;
import turing.btg.block.BlockFluidMaterial;
import turing.btg.block.Blocks;
import turing.btg.client.ItemModelBucket;
import turing.btg.client.ItemModelColored;
import turing.btg.client.ItemModelMaterial;
import turing.btg.material.Material;
import turing.btg.material.MaterialItemType;
import turing.btg.material.Materials;
import turniplabs.halplibe.helper.ItemBuilder;

import java.util.*;

public class Items {
	protected static int NextID;
	protected static int NextToolID;
	protected static int NextMaterialID;
	protected static int NextBucketID;

	public static final Map<MaterialItemType, List<ItemMaterial>> MATERIAL_ITEMS = new HashMap<>();
	public static final Map<IToolType, Map<Integer, IItemToolMaterial>> TOOLS = new HashMap<>();
	public static final Map<Integer, ItemBucketMaterial> BUCKETS = new HashMap<>();

	public static Item resin;
	public static Item creativeSword;
	public static Item steamBucket;

	public static void init() {
		NextID = BTGConfig.config.getInt("StartingItemID");
		NextToolID = BTGConfig.config.getInt("StartingToolID");
		NextMaterialID = BTGConfig.config.getInt("StartingMaterialItemID");
		NextBucketID = BTGConfig.config.getInt("StartingBucketID");

		resin = new ItemBuilder(BTG.MOD_ID)
			.setIcon(BTG.MOD_ID + ":item/sticky_resin")
			.build(new Item("stickyResin", NextID++));
		creativeSword = new ItemBuilder(BTG.MOD_ID)
			.setIcon(BTG.MOD_ID + ":item/nano_saber")
			.build(new ItemCreativeSword("creativeSword", NextID++));
		steamBucket = new ItemBuilder(BTG.MOD_ID)
			.setIcon(BTG.MOD_ID + ":item/steam_bucket")
			.setContainerItem(() -> Item.bucket)
			.build(new ItemBucketNoPlace("steamBucket", NextID++));

		MaterialItemType.ITEM_TYPES.forEach(type -> MATERIAL_ITEMS.put(type, new ArrayList<>()));
		for (int i = 0; i <= Materials.HANDLERS_NEEDED; i++) {
			for (MaterialItemType type : MaterialItemType.ITEM_TYPES) {
				List<ItemMaterial> list = MATERIAL_ITEMS.get(type);
				if (list != null) {
					list.add(i, new ItemBuilder(BTG.MOD_ID).setIcon("minecraft:item/ingot_iron").setItemModel((item) -> new ItemModelMaterial(item, BTG.MOD_ID, type.name)).build(new ItemMaterial(type, NextMaterialID++, i)));
				}
			}
		}
		for (int i = 0; i < Blocks.fluidBlocks.size(); i++) {
			BlockFluidMaterial fluid = Blocks.fluidBlocks.get(i);
			if (fluid != null) {
				BUCKETS.put(fluid.getMaterialId(), new ItemBuilder(BTG.MOD_ID).setIcon("minecraft:item/bucket").setItemModel((item) -> new ItemModelBucket(item, BTG.MOD_ID)).build(new ItemBucketMaterial("bucket" + fluid.getMaterialId(), NextBucketID++, fluid.id, Material.MATERIALS.get(fluid.getMaterialId()))));
			}
		}
		for (Material material : Material.MATERIALS.values()) {
			if (material.hasFlag("tools")) {
				for (IToolType toolType : ToolType.TYPES) {
					if (material.hasToolType(toolType)) {
						TOOLS.computeIfAbsent(toolType, i -> new HashMap<>()).put(material.id, (IItemToolMaterial) new ItemBuilder(BTG.MOD_ID).setIcon(toolType.getTextureIndex()).setItemModel((item) -> new ItemModelMaterial(item, BTG.MOD_ID, "")).build(toolType.getConstructor().apply(toolType.getName() + material.id, NextToolID++, material.id)));
					}
				}
			}
		}
	}
}
