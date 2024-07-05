package turing.btg.recipe;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DyeColor;
import turing.btg.BTG;
import turing.btg.api.ToolType;
import turing.btg.block.Blocks;
import turing.btg.item.Items;
import turing.btg.material.*;
import turing.btg.recipe.builders.RecipeBuilderShaped;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderBlastFurnace;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderFurnace;

public class CraftingRecipes {
	public static void init() {
		initMaterials();
		RecipeBuilder.Shapeless(BTG.MOD_ID)
			.addInput(Blocks.rubberLog.getDefaultStack())
			.create("rubberLogToPlanks", new ItemStack(Block.planksOakPainted, 4, DyeColor.DYE_YELLOW.blockMeta));
		RecipeBuilder.Shaped(BTG.MOD_ID, "FF", "FF")
			.addInput('F', "common_gems:flint")
			.create("flint_to_block", Material.getBlockForMaterial(Materials.FLINT.id));
		RecipeBuilder.Shaped(BTG.MOD_ID, "TC", "CC")
			.addInput('T', "common_dusts:tin")
			.addInput('C', "common_dusts:copper")
			.create("bronzeDustMixing", Material.getItemForMaterial(Materials.BRONZE.id, MaterialItemType.DUST, 2));
		RecipeBuilder.Shaped(BTG.MOD_ID, "TC", "CC")
			.addInput('T', "common_dusts:zinc")
			.addInput('C', "common_dusts:copper")
			.create("brassDustMixing", Material.getItemForMaterial(Materials.BRASS.id, MaterialItemType.DUST, 2));
		RecipeBuilder.Shaped(BTG.MOD_ID, "III", "III", "CMN")
			.addInput('I', "common_dusts:iron")
			.addInput('C', "common_dusts:chrome")
			.addInput('M', "common_dusts:manganese")
			.addInput('N', "common_dusts:nickel")
			.create("stainlessSteelDustMixing", Material.getItemForMaterial(Materials.STAINLESS_STEEL.id, MaterialItemType.DUST, 9));
	}

	public static void initMaterials() {
		for (Material material : Material.MATERIALS.values()) {
			String uppercaseName = material.name.substring(0, 1).toUpperCase() + material.name.substring(1);
			if (!material.hasFlag("no_compacting")) {
				compactingRecipes(material, uppercaseName);
			}
			if (material.hasItemType(MaterialItemType.DUST)) {
				smeltingRecipes(material, uppercaseName);
			}
			if (material.hasFlag("tools")) {
				toolRecipes(material, uppercaseName);
			}
			if (material.hasItemType(MaterialItemType.DUST) && material.hasItemType(MaterialItemType.CRUSHED_ORE)) {
				new RecipeBuilderShaped(BTG.MOD_ID, "H ", " O")
					.addInput('O', "common_crushed_ores:" + material.name)
					.addInput('H', "btg:craftingTools_hammer")
					.create("manualOreCrushing" + uppercaseName, Material.getItemForMaterial(material.id, MaterialItemType.DUST));
			}
			if (material.hasFlag("manual_crushing")) {
				if (material.hasItemType(MaterialItemType.DUST) && (material.hasItemType(MaterialItemType.INGOT) || material.hasItemType(MaterialItemType.GEM))) {
					String input = material.hasItemType(MaterialItemType.INGOT) ? "ingots" : "gems";
					new RecipeBuilderShaped(BTG.MOD_ID, "I", "M")
						.addInput('I', "common_" + input + ":" + material.name)
						.addInput('M', "btg:craftingTools_mortar")
						.create("manualCrushing" + uppercaseName, Material.getItemForMaterial(material.id, MaterialItemType.DUST));
				}
			}
			if (!material.hasFlag("no_handworking")) {
				MaterialItemType baseItemType = getBaseItemType(material);
				if (material.hasItemType(MaterialItemType.STICK) && baseItemType != MaterialItemType.DUST) {
					new RecipeBuilderShaped(BTG.MOD_ID, "F ", " I")
						.addInput('I', "common_" + baseItemType.getGroupName() + ":" + material.name)
						.addInput('F', "btg:craftingTools_file")
						.create("manualFiling" + uppercaseName, Material.getItemForMaterial(material.id, MaterialItemType.STICK));
				}
				if (material.hasItemType(MaterialItemType.PLATE) && baseItemType == MaterialItemType.INGOT) {
					new RecipeBuilderShaped(BTG.MOD_ID, " H ", " I ", " I ")
						.addInput('I', "common_ingots:" + material.name)
						.addInput('H', "btg:craftingTools_hammer")
						.create("manualHammering" + uppercaseName, Material.getItemForMaterial(material.id, MaterialItemType.PLATE));
				}
				if (material.hasItemType(MaterialItemType.GEAR_SMALL)) {
					new RecipeBuilderShaped(BTG.MOD_ID, "H ", " P")
						.addInput('P', "common_plates:" + material.name)
						.addInput('H', "btg:craftingTools_hammer")
						.create("manualSmallGear" + uppercaseName, Material.getItemForMaterial(material.id, MaterialItemType.GEAR_SMALL));
				}
				if (material.hasItemType(MaterialItemType.GEAR)) {
					new RecipeBuilderShaped(BTG.MOD_ID, "RPR", "PSP", "RPR")
						.addInput('R', "common_sticks:" + material.name)
						.addInput('P', "common_plates:" + material.name)
						.addInput('S', "btg:craftingTools_screwdriver")
						.create("manualGear" + uppercaseName, Material.getItemForMaterial(material.id, MaterialItemType.GEAR));
				}
				if (material.hasItemType(MaterialItemType.RING) && material.hasItemType(MaterialItemType.STICK)) {
					new RecipeBuilderShaped(BTG.MOD_ID, "H ", " R")
						.addInput('H', "btg:craftingTools_hammer")
						.addInput('R', "common_sticks:" + material.name)
						.create("manualRing" + uppercaseName, Material.getItemForMaterial(material.id, MaterialItemType.RING));
				}
				if (material.hasItemType(MaterialItemType.SCREW)) {
					new RecipeBuilderShaped(BTG.MOD_ID, "S ", " R")
						.addInput('S', "btg:craftingTools_saw")
						.addInput('R', "common_sticks:" + material.name)
						.create("manualBolts" + uppercaseName, Material.getItemForMaterial(material.id, MaterialItemType.BOLT, 2));
					new RecipeBuilderShaped(BTG.MOD_ID, "FB", "B ")
						.addInput('F', "btg:craftingTools_file")
						.addInput('B', "common_bolts:" + material.name)
						.create("manualScrew" + uppercaseName, Material.getItemForMaterial(material.id, MaterialItemType.SCREW));
				}
				if (material.hasItemType(MaterialItemType.ROTOR)) {
					new RecipeBuilderShaped(BTG.MOD_ID, "PHP", "SRF", "PsP")
						.addInput('P', "common_plates:" + material.name)
						.addInput('R', "common_rings:" + material.name)
						.addInput('S', "common_screws:" + material.name)
						.addInput('s', "btg:craftingTools_screwdriver")
						.addInput('H', "btg:craftingTools_hammer")
						.addInput('F', "btg:craftingTools_file")
						.create("manualRotor" + uppercaseName, Material.getItemForMaterial(material.id, MaterialItemType.ROTOR));
				}
			}
		}
	}

	public static void toolRecipes(Material material, String uppercaseName) {
		MaterialItemType baseItemType = getBaseItemType(material);

		if (!material.hasFlag("basic_tools_only")) {
			new RecipeBuilderShaped(BTG.MOD_ID, "II ", "IIS", "II ")
				.addInput('S', Item.stick.getDefaultStack())
				.addInput('I', "common_" + baseItemType.getGroupName() + ":" + material.name)
				.create("hammer" + uppercaseName, Items.TOOLS.get(ToolType.HAMMER).get(material.id).getDefaultStack());
			new RecipeBuilderShaped(BTG.MOD_ID, " P ", " P ", " S ")
				.addInput('S', Item.stick.getDefaultStack())
				.addInput('P', "common_plates:" + material.name)
				.create("file" + uppercaseName, Items.TOOLS.get(ToolType.FILE).get(material.id).getDefaultStack());
			new RecipeBuilderShaped(BTG.MOD_ID, "IHI", "III", " I ")
				.addInput('I', "common_" + baseItemType.getGroupName() + ":" + material.name)
				.addInput('H', "btg:craftingTools_hammer")
				.create("wrench" + uppercaseName, Items.TOOLS.get(ToolType.WRENCH).get(material.id).getDefaultStack());
			new RecipeBuilderShaped(BTG.MOD_ID, "HF ", "PPS")
				.addInput('H', "btg:craftingTools_hammer")
				.addInput('F', "btg:craftingTools_file")
				.addInput('S', Item.stick.getDefaultStack())
				.addInput('P', "common_plates:" + material.name)
				.create("saw" + uppercaseName, Items.TOOLS.get(ToolType.SAW).get(material.id).getDefaultStack());
			if (material.hasToolType(ToolType.SCREWDRIVER)) {
				new RecipeBuilderShaped(BTG.MOD_ID, " HR", " RF", "S  ")
					.addInput('S', Item.stick.getDefaultStack())
					.addInput('R', "common_sticks:" + material.name)
					.addInput('H', "btg:craftingTools_hammer")
					.addInput('F', "btg:craftingTools_file")
					.create("screwdriver" + uppercaseName, Items.TOOLS.get(ToolType.SCREWDRIVER).get(material.id).getDefaultStack());
			}
			if (material.hasToolType(ToolType.MORTAR) && baseItemType == MaterialItemType.INGOT) {
				new RecipeBuilderShaped(BTG.MOD_ID, " I ", "SIS", "SSS")
					.addInput('S', "minecraft:stones")
					.addInput('I', "common_ingots:" + material.name)
					.create("mortar" + uppercaseName, Items.TOOLS.get(ToolType.MORTAR).get(material.id).getDefaultStack());
			}
			if (material.hasToolType(ToolType.CROWBAR)) {
				new RecipeBuilderShaped(BTG.MOD_ID, "HLR", "LRL", "RLF")
					.addInput('R', "common_sticks:" + material.name)
					.addInput('H', "btg:craftingTools_hammer")
					.addInput('F', "btg:craftingTools_file")
					.addInput('L', "btg:dyes_blue")
					.create("crowbar" + uppercaseName, Items.TOOLS.get(ToolType.CROWBAR).get(material.id).getDefaultStack());
			}
			if (material.hasToolType(ToolType.WIRE_CUTTERS)) {
				new RecipeBuilderShaped(BTG.MOD_ID, "PFP", "HPs", "RSR")
					.addInput('H', "btg:craftingTools_hammer")
					.addInput('F', "btg:craftingTools_file")
					.addInput('S', "common_screws:" + material.name)
					.addInput('P', "common_plates:" + material.name)
					.addInput('s', "btg:craftingTools_screwdriver")
					.addInput('R', "common_sticks:" + material.name)
					.create("wireCutters" + uppercaseName, Items.TOOLS.get(ToolType.WIRE_CUTTERS).get(material.id).getDefaultStack());
			}
		}
		if (material.hasItemType(MaterialItemType.PLATE)) {
			new RecipeBuilderShaped(BTG.MOD_ID, " P ", "HPF", " S ")
				.addInput('H', "btg:craftingTools_hammer")
				.addInput('F', "btg:craftingTools_file")
				.addInput('P', "common_plates:" + material.name)
				.addInput('S', Item.stick.getDefaultStack())
				.create("sword" + uppercaseName, Items.TOOLS.get(ToolType.SWORD).get(material.id).getDefaultStack());
			new RecipeBuilderShaped(BTG.MOD_ID, "HPF", " S ", " S ")
				.addInput('H', "btg:craftingTools_hammer")
				.addInput('F', "btg:craftingTools_file")
				.addInput('P', "common_plates:" + material.name)
				.addInput('S', Item.stick.getDefaultStack())
				.create("shovel" + uppercaseName, Items.TOOLS.get(ToolType.SHOVEL).get(material.id).getDefaultStack());
			new RecipeBuilderShaped(BTG.MOD_ID, "PPH", " SF", " S ")
				.addInput('H', "btg:craftingTools_hammer")
				.addInput('F', "btg:craftingTools_file")
				.addInput('P', "common_plates:" + material.name)
				.addInput('S', Item.stick.getDefaultStack())
				.create("hoe" + uppercaseName, Items.TOOLS.get(ToolType.HOE).get(material.id).getDefaultStack());
			new RecipeBuilderShaped(BTG.MOD_ID, "IP ", "HPF", " S ")
				.addInput('H', "btg:craftingTools_hammer")
				.addInput('F', "btg:craftingTools_file")
				.addInput('P', "common_plates:" + material.name)
				.addInput('S', Item.stick.getDefaultStack())
				.addInput('I', "common_" + baseItemType.getGroupName() + ":" + material.name)
				.create("axe" + uppercaseName, Items.TOOLS.get(ToolType.AXE).get(material.id).getDefaultStack());
			new RecipeBuilderShaped(BTG.MOD_ID, "IPP", "HSF", " S ")
				.addInput('H', "btg:craftingTools_hammer")
				.addInput('F', "btg:craftingTools_file")
				.addInput('P', "common_plates:" + material.name)
				.addInput('S', Item.stick.getDefaultStack())
				.addInput('I', "common_" + baseItemType.getGroupName() + ":" + material.name)
				.create("pickaxe" + uppercaseName, Items.TOOLS.get(ToolType.PICKAXE).get(material.id).getDefaultStack());
		}
	}

	public static void smeltingRecipes(Material material, String uppercaseName) {
		MaterialItemType baseItemType = getBaseItemType(material);
		ItemStack baseItem = Material.getItemForMaterial(material.id, baseItemType);

		if (!material.hasFlag("no_smelting")) {
			if (baseItemType == MaterialItemType.INGOT) {
				new RecipeBuilderFurnace(BTG.MOD_ID)
					.setInput("common_dusts:" + material.name)
					.create("dustSmelting" + uppercaseName, baseItem);
				new RecipeBuilderBlastFurnace(BTG.MOD_ID)
					.setInput("common_dusts:" + material.name)
					.create("dustBlasting" + uppercaseName, baseItem);
			}
			if (material.hasFlag("ore")) {
				OreProperties properties = material.getFlagValue(MaterialFlag.ORE);
				baseItem.stackSize = properties.smeltAmount;
				if (!material.isItemExisting(baseItemType)) {
					new RecipeBuilderFurnace(BTG.MOD_ID)
						.setInput("common_ores:" + material.name)
						.create("oreSmelting" + uppercaseName, baseItem);
					new RecipeBuilderBlastFurnace(BTG.MOD_ID)
						.setInput("common_ores:" + material.name)
						.create("oreBlasting" + uppercaseName, baseItem);
				}
			}
		}
		if (material.hasFlag("magnetic")) {
			Material of = material.getFlagValue(MaterialFlag.MAGNETIC);
			for (MaterialItemType type : MaterialItemType.ITEM_TYPES) {
				if (material.hasItemType(type) && of.hasItemType(type)) {
					new RecipeBuilderFurnace(BTG.MOD_ID)
						.setInput("common_" + type.getGroupName() + ":" + material.name)
						.create("un-magnetize" + uppercaseName, Material.getItemForMaterial(of.id, type));
				}
			}
		}
	}

	public static void compactingRecipes(Material material, String uppercaseName) {
		if (material.hasBlock() && !material.isBlockExisting() && !material.hasFlag("no_block_compacting")) {
			String compactingType = material.hasItemType(MaterialItemType.INGOT) ? "ingots" : material.hasItemType(MaterialItemType.GEM) ? "gems" : material.hasItemType(MaterialItemType.DUST) ? "dusts" : null;
			if (compactingType != null) {
				ItemStack compactingItemStack = Registries.ITEM_GROUPS.getItem("common_" + compactingType + ":" + material.name).get(0);
				uncompacting(compactingItemStack, "common_blocks:" + material.name, 9);
				compacting3x3(Material.getBlockForMaterial(material.id), "common_" + compactingType + ":" + material.name);
			}
		}
		if (material.hasItemType(MaterialItemType.DUST)) {
			ItemStack dust = Material.getItemForMaterial(material.id, MaterialItemType.DUST);
			ItemStack small = Material.getItemForMaterial(material.id, MaterialItemType.DUST_SMALL).copy();
			ItemStack tiny = Material.getItemForMaterial(material.id, MaterialItemType.DUST_TINY).copy();
			small.stackSize = 4;
			tiny.stackSize = 9;
			RecipeBuilder.Shaped(BTG.MOD_ID, "   ", " I ", "   ")
				.addInput('I', ItemGroups.getGroup(MaterialItemType.DUST, material))
				.create("uncompactingSmallDust" + uppercaseName, small);
			RecipeBuilder.Shaped(BTG.MOD_ID, "I ")
				.addInput('I', ItemGroups.getGroup(MaterialItemType.DUST, material))
				.create("uncompactingTinyDust" + uppercaseName, tiny);
			compacting3x3(dust, ItemGroups.getGroup(MaterialItemType.DUST_TINY, material));
			compacting2x2(dust, ItemGroups.getGroup(MaterialItemType.DUST_SMALL, material));
		}
	}

	public static void uncompacting(ItemStack output, String input, int amount) {
		String name = "uncompacting" + output.getItem().getKey() + "X" + amount;
		output = output.copy();
		output.stackSize = amount;
		RecipeBuilder.Shapeless(BTG.MOD_ID)
			.addInput(input)
			.create(name, output);
	}

	public static void compacting3x3(ItemStack output, String input) {
		String name = "compacting3x3" + output.getItem().getKey();
		RecipeBuilder.Shaped(BTG.MOD_ID, "III", "III", "III")
			.addInput('I', input)
			.create(name, output);
	}

	public static void compacting2x2(ItemStack output, String input) {
		String name = "compacting2x2" + output.getItem().getKey();
		RecipeBuilder.Shaped(BTG.MOD_ID, "II", "II")
			.addInput('I', input)
			.create(name, output);
	}

	public static MaterialItemType getBaseItemType(Material material) {
		return material.hasItemType(MaterialItemType.GEM) ? MaterialItemType.GEM : material.hasItemType(MaterialItemType.INGOT) ? MaterialItemType.INGOT : MaterialItemType.DUST;
	}
}
