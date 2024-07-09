package turing.btg.material;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.MathHelper;
import turing.btg.BTG;
import turing.enchantmentlib.EnchantmentLib;
import turing.enchantmentlib.enchants.EnchantmentFlame;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Materials {
	public static final int HANDLERS_NEEDED = 1;
	public static final int iMETA_LIMIT = 256;
	public static final float fMETA_LIMIT = 256F;

	private static final Map<MaterialItemType, ItemStack> IRON_ITEMS = new HashMap<>();
	private static final Map<MaterialItemType, ItemStack> DIAMOND_ITEMS = new HashMap<>();
	private static final Map<MaterialItemType, ItemStack> COAL_ITEMS = new HashMap<>();
	private static final Map<MaterialItemType, ItemStack> GOLD_ITEMS = new HashMap<>();
	private static final Map<MaterialItemType, ItemStack> LAPIS_ITEMS = new HashMap<>();
	private static final Map<MaterialItemType, ItemStack> REDSTONE_ITEMS = new HashMap<>();
	private static final Map<MaterialItemType, ItemStack> STEEL_ITEMS = new HashMap<>();
	private static final Map<MaterialItemType, ItemStack> QUARTZ_ITEMS = new HashMap<>();
	private static final Map<MaterialItemType, ItemStack> WOOD_ITEMS = new HashMap<>();
	private static final Map<MaterialItemType, ItemStack> FLINT_ITEMS = new HashMap<>();
	private static final Map<MaterialItemType, ItemStack> OLIVINE_ITEMS = new HashMap<>();
	private static final Map<MaterialItemType, ItemStack> GLOWSTONE_ITEMS = new HashMap<>();

	public static final Material OXYGEN = new MaterialBuilder(14, "oxygen")
		.gas()
		.color(0x6688DD)
		.element(Elements.O)
		.build();
	public static final Material CARBON = new MaterialBuilder(16, "carbon")
		.color(0x323232)
		.dust()
		.iconSet(IconSets.DULL)
		.element(Elements.C)
		.build();
	public static final Material CHLORINE = new MaterialBuilder(22, "chlorine")
		.color(0x2D8C8C)
		.gas()
		.element(Elements.Cl)
		.build();
	public static final Material FLUORINE = new MaterialBuilder(31, "fluorine")
		.gas()
		.element(Elements.F)
		.color(0x6EA7DC)
		.build();
	public static final Material NITROGEN = new MaterialBuilder(7, "nitrogen")
		.gas()
		.element(Elements.N)
		.color(0x743373)
		.build();
	public static final Material MERCURY = new MaterialBuilder(25, "mercury")
		.fluid()
		.color(0xE6DCDC)
		.element(Elements.Hg)
		.build();
	public static final Material CALCIUM = new MaterialBuilder(15, "calcium")
		.element(Elements.Ca)
		.color(0xFFF5DE)
		.dust()
		.iconSet(IconSets.DULL)
		.build();
	public static final Material SODIUM = new MaterialBuilder(19, "sodium")
		.color(0x000096)
		.dust()
		.iconSet(IconSets.DULL)
		.element(Elements.Na)
		.build();
	public static final Material CALCITE = new MaterialBuilder(17, "calcite")
		.color(0xFAE6DC)
		.dust()
		.ore()
		.iconSet(IconSets.DULL)
		.components(new MaterialStack(CALCIUM, 1), new MaterialStack(CARBON, 1), new MaterialStack(OXYGEN, 2))
		.build();
	public static final Material SULFUR = new MaterialBuilder(23, "sulfur")
		.color(0xC8C800)
		.dust().ore(2)
		.iconSet(IconSets.DULL)
		.element(Elements.S)
		.build();
	public static final Material LITHIUM = new MaterialBuilder(8, "lithium")
		.ingot().ore()
		.iconSet(IconSets.DULL)
		.element(Elements.Li)
		.color(0xAAAAAA)
		.build();
	public static final Material IRON = new MaterialBuilder(0, "iron")
		.ingot()
		.ore()
		.fluid(300)
		.color(0xC8C8C8)
		.element(Elements.Fe)
		.toolStats(256, 6.0F, 2)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.RING, MaterialFlag.GEAR, MaterialFlag.SMALL_GEAR, MaterialFlag.FOIL, MaterialFlag.SCREW, MaterialFlag.GENERATE_MORTAR, MaterialFlag.GENERATE_SCREWDRIVER, MaterialFlag.GENERATE_WIRE_CUTTERS, MaterialFlag.GENERATE_CROWBAR, MaterialFlag.MANUAL_CRUSHING)
		.withExistingItems(IRON_ITEMS)
		.withExistingBlock(Block.blockIron)
		.build();
	public static final Material ALUMINUM = new MaterialBuilder(18, "aluminum")
		.ingot().ore()
		.fluid(933)
		.color(0x80C8F0)
		.element(Elements.Al)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.FOIL, MaterialFlag.GEAR, MaterialFlag.SMALL_GEAR, MaterialFlag.NO_SMELTING)
		.build();
	public static final Material CHROME = new MaterialBuilder(26, "chrome")
		.ingot()
		.fluid(2180)
		.color(0xEAC4D8)
		.iconSet(IconSets.SHINY)
		.element(Elements.Cr)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.SCREW, MaterialFlag.GEAR, MaterialFlag.NO_SMELTING)
		.build();
	public static final Material DIAMOND = new MaterialBuilder(1, "diamond")
		.gem()
		.ore(4)
		.color(0xC8FFFF)
		.iconSet(IconSets.DIAMOND)
		.components(CARBON)
		.toolStats(new ToolStats.Builder(1536, 14.0F).setDamage(4).setMiningLevel(3).setHitDelay(4).build())
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.GEAR)
		.withExistingItems(DIAMOND_ITEMS)
		.withExistingBlock(Block.blockDiamond)
		.build();
	public static final Material RUBY = new MaterialBuilder(27, "ruby")
		.gem().ore(6)
		.color(0xFF6464)
		.iconSet(IconSets.RUBY)
		.withFlag(MaterialFlag.PLATE)
		.components(new MaterialStack(CHROME, 1), new MaterialStack(ALUMINUM, 2), new MaterialStack(OXYGEN, 3))
		.build();
	public static final Material COAL = new MaterialBuilder(2, "coal")
		.gem()
		.ore(8)
		.color(0x333333)
		.iconSet(IconSets.COAL)
		.components(CARBON)
		.withFlag(MaterialFlag.MANUAL_CRUSHING)
		.withExistingItems(COAL_ITEMS)
		.withExistingBlock(Block.blockCoal)
		.build();
	public static final Material PYRITE = new MaterialBuilder(24, "pyrite")
		.dust().ore(2)
		.color(0x967828)
		.iconSet(IconSets.ROUGH)
		.components(new MaterialStack(IRON, 1), new MaterialStack(SULFUR, 2))
		.build();
	public static final Material GOLD = new MaterialBuilder(3, "gold")
		.ingot()
		.ore()
		.color(0xFFE650)
		.fluid(250)
		.iconSet(IconSets.SHINY)
		.element(Elements.Au)
		.toolStats(new ToolStats.Builder(64, 5.5F).setMiningLevel(2).setDamage(3).setSilkTouch().build())
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.RING, MaterialFlag.FOIL, MaterialFlag.SCREW, MaterialFlag.GEAR, MaterialFlag.MANUAL_CRUSHING, MaterialFlag.GENERATE_SCREWDRIVER)
		.withExistingItems(GOLD_ITEMS)
		.withExistingBlock(Block.blockGold)
		.build();
	public static final Material SILICON = new MaterialBuilder(13, "silicon")
		.ingot()
		.fluid(800)
		.color(0x3C3C50)
		.element(Elements.Si)
		.build();
	public static final Material LAZURITE = new MaterialBuilder(20, "lazurite")
		.gem().ore(6)
		.color(0x6478FF)
		.iconSet(IconSets.LAPIS)
		.withFlag(MaterialFlag.PLATE)
		.components(new MaterialStack(ALUMINUM, 6), new MaterialStack(SILICON, 6), new MaterialStack(CALCIUM, 8), new MaterialStack(SODIUM, 8))
		.build();
	public static final Material SODALITE = new MaterialBuilder(21, "sodalite")
		.gem().ore(6)
		.color(0x1414FF)
		.iconSet(IconSets.LAPIS)
		.withFlag(MaterialFlag.PLATE)
		.components(new MaterialStack(ALUMINUM, 3), new MaterialStack(SILICON, 3), new MaterialStack(SODIUM, 4), new MaterialStack(CHLORINE, 1))
		.build();
	public static final Material LAPIS = new MaterialBuilder(4, "lapis")
		.gem()
		.ore(6)
		.color(0x4646DC)
		.iconSet(IconSets.LAPIS)
		.withExistingItems(LAPIS_ITEMS)
		.withExistingBlock(Block.blockLapis)
		.components(new MaterialStack(LAZURITE, 12), new MaterialStack(SODALITE, 2), new MaterialStack(PYRITE, 1), new MaterialStack(CALCITE, 1))
		.build();
	public static final Material REDSTONE = new MaterialBuilder(5, "redstone")
		.dust()
		.ore(6)
		.color(0xC80000)
		.fluid(270)
		.iconSet(IconSets.ROUGH)
		.withFlag(MaterialFlag.PLATE)
		.withExistingItems(REDSTONE_ITEMS)
		.withExistingBlock(Block.blockRedstone)
		.components(new MaterialStack(SILICON, 1), new MaterialStack(PYRITE, 5), new MaterialStack(RUBY, 1), new MaterialStack(MERCURY, 3))
		.build();
	public static final Material STEEL = new MaterialBuilder(6, "steel")
		.ingot()
		.dust()
		.fluid(600)
		.color(0x808080)
		.iconSet(IconSets.METAL)
		.components(IRON, COAL)
		.toolStats(new ToolStats.Builder(4608, 7.0F).setMiningLevel(3).withEnchant(BTG.TREE_CAPITATOR, 1).build())
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.RING, MaterialFlag.ROTOR, MaterialFlag.FOIL, MaterialFlag.SCREW, MaterialFlag.GEAR, MaterialFlag.GENERATE_MORTAR, MaterialFlag.GENERATE_SCREWDRIVER, MaterialFlag.GENERATE_WIRE_CUTTERS, MaterialFlag.GENERATE_CROWBAR)
		.withExistingItems(STEEL_ITEMS)
		.withExistingBlock(Block.blockSteel)
		.build();
	public static final Material COPPER = new MaterialBuilder(9, "copper")
		.ingot()
		.ore()
		.fluid(300)
		.color(0xFF6400)
		.iconSet(IconSets.SHINY)
		.element(Elements.Cu)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.FOIL, MaterialFlag.GEAR, MaterialFlag.MANUAL_CRUSHING)
		.build();
	public static final Material TIN = new MaterialBuilder(10, "tin")
		.ingot()
		.ore()
		.color(0xDCDCDC)
		.fluid(295)
		.iconSet(IconSets.DULL)
		.element(Elements.Sn)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.RING, MaterialFlag.ROTOR, MaterialFlag.FOIL, MaterialFlag.SCREW, MaterialFlag.GEAR, MaterialFlag.MANUAL_CRUSHING)
		.build();
	public static final Material LEAD = new MaterialBuilder(34, "lead")
		.ingot().ore()
		.fluid(600)
		.color(0x8C648C)
		.element(Elements.Pb)
		.toolStats(545, 5.5F, 2, 3)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.SCREW, MaterialFlag.RING, MaterialFlag.ROTOR, MaterialFlag.MANUAL_CRUSHING, MaterialFlag.GENERATE_CROWBAR, MaterialFlag.GENERATE_WIRE_CUTTERS)
		.build();
	public static final Material SILVER = new MaterialBuilder(35, "silver")
		.ingot().ore()
		.fluid(1235)
		.color(0xDCDCFF)
		.iconSet(IconSets.SHINY)
		.element(Elements.Ag)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.RING, MaterialFlag.FOIL, MaterialFlag.GEAR, MaterialFlag.MANUAL_CRUSHING)
		.build();
	public static final Material TITANIUM = new MaterialBuilder(36, "titanium")
		.ingot()
		.fluid()
		.color(0xDCA0F0)
		.element(Elements.Ti)
		.toolStats(1536, 8.0F, 4, 3)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.RING, MaterialFlag.ROTOR, MaterialFlag.SCREW, MaterialFlag.GEAR, MaterialFlag.SMALL_GEAR, MaterialFlag.GENERATE_CROWBAR, MaterialFlag.GENERATE_WIRE_CUTTERS, MaterialFlag.NO_SMELTING)
		.build();
	public static final Material BRONZE = new MaterialBuilder(11, "bronze")
		.ingot()
		.dust()
		.fluid(340)
		.color(0xFF8000)
		.components(new MaterialStack(COPPER, 3), new MaterialStack(TIN))
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.RING, MaterialFlag.ROTOR, MaterialFlag.FOIL, MaterialFlag.SCREW, MaterialFlag.GEAR, MaterialFlag.GENERATE_MORTAR, MaterialFlag.MANUAL_CRUSHING, MaterialFlag.GENERATE_CROWBAR, MaterialFlag.GENERATE_WIRE_CUTTERS)
		.build();
	public static final Material QUARTZ = new MaterialBuilder(12, "quartz")
		.gem()
		.ore()
		.color(0xF9DDDC)
		.iconSet(IconSets.QUARTZ)
		.components(new MaterialStack(SILICON), new MaterialStack(OXYGEN, 2))
		.withFlag(MaterialFlag.PLATE)
		.withExistingItems(QUARTZ_ITEMS)
		.withExistingBlock(Block.blockQuartz)
		.build();
	public static final Material GRAPHITE = new MaterialBuilder(29, "graphite")
		.ore()
		.color(0x808080)
		.iconSet(IconSets.DULL)
		.components(CARBON)
		.build();
	public static final Material CINNABAR = new MaterialBuilder(28, "cinnabar")
		.dust().ore()
		.iconSet(IconSets.EMERALD)
		.color(0x960000)
		.components(MERCURY, SULFUR)
		.build();
	public static final Material ARSENIC = new MaterialBuilder(33, "arsenic")
		.dust()
		.gas(887)
		.color(0x676756)
		.iconSet(IconSets.DULL)
		.element(Elements.As)
		.build();
	public static final Material COBALT = new MaterialBuilder(30, "cobalt")
		.ingot()
		.fluid(1768)
		.color(0x5050FA)
		.element(Elements.Co)
		.toolStats(300, 15.5F, 4, 2)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.GENERATE_MORTAR)
		.build();
	public static final Material COBALTITE = new MaterialBuilder(32, "cobaltite")
		.ore().dust()
		.color(0x5050FA)
		.iconSet(IconSets.DULL)
		.components(COBALT, ARSENIC, SULFUR)
		.withFlag(MaterialFlag.NO_SMELTING)
		.build();
	public static final Material GALENA = new MaterialBuilder(37, "galena")
		.dust().ore()
		.color(0x643C64)
		.iconSet(IconSets.DULL)
		.components(LEAD, SULFUR)
		.withFlag(MaterialFlag.NO_SMELTING)
		.build();
	public static final Material ANTIMONY = new MaterialBuilder(38, "antimony")
		.ingot()
		.color(0xDCDCC8)
		.fluid()
		.iconSet(IconSets.SHINY)
		.element(Elements.Sb)
		.withFlag(MaterialFlag.MANUAL_CRUSHING)
		.build();
	public static final Material BISMUTH = new MaterialBuilder(39, "bismuth")
		.ingot().ore()
		.color(0x64A0A0)
		.fluid()
		.iconSet(IconSets.METAL)
		.element(Elements.Bi)
		.build();
	public static final Material BORON = new MaterialBuilder(40, "boron")
		.dust()
		.color(0xD2FDD2)
		.iconSet(IconSets.DULL)
		.element(Elements.B)
		.build();
	public static final Material BARIUM = new MaterialBuilder(41, "barium")
		.ingot()
		.color(0xFFFFFF)
		.iconSet(IconSets.SHINY)
		.element(Elements.Ba)
		.build();
	public static final Material CADMIUM = new MaterialBuilder(42, "cadmium")
		.ingot()
		.color(0x505060)
		.iconSet(IconSets.SHINY)
		.element(Elements.Cd)
		.build();
	public static final Material ARGON = new MaterialBuilder(43, "argon")
		.gas()
		.color(0xBBBB00)
		.element(Elements.Ar)
		.build();
	public static final Material GALLIUM = new MaterialBuilder(44, "gallium")
		.ingot()
		.color(0xEEEEFF)
		.fluid()
		.iconSet(IconSets.SHINY)
		.element(Elements.Ga)
		.withFlag(MaterialFlag.PLATE)
		.build();
	public static final Material HELIUM = new MaterialBuilder(45, "helium")
		.gas()
		.color(0xDDDD00)
		.element(Elements.He)
		.build();
	public static final Material Indium = new MaterialBuilder(46, "indium")
		.ingot()
		.color(0x6600BB)
		.fluid()
		.iconSet(IconSets.METAL)
		.element(Elements.In)
		.build();
	public static final Material Iridium = new MaterialBuilder(47, "iridium")
		.ingot().ore()
		.color(0xFFFFFF)
		.fluid()
		.iconSet(IconSets.DULL)
		.element(Elements.Ir)
		.build();
	public static final Material MANGANESE = new MaterialBuilder(48, "manganese")
		.ingot()
		.color(0xCDE1B9)
		.fluid()
		.iconSet(IconSets.DULL)
		.element(Elements.Mn)
		.build();
	public static final Material MAGNESIUM = new MaterialBuilder(49, "magnesium")
		.ingot()
		.color(0xFFBBBB)
		.iconSet(IconSets.METAL)
		.element(Elements.Mg)
		.build();
	public static final Material NICKEL = new MaterialBuilder(50, "nickel")
		.ingot().ore()
		.color(0xAAAAFF)
		.fluid()
		.iconSet(IconSets.METAL)
		.element(Elements.Ni)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.MANUAL_CRUSHING)
		.build();
	public static final Material PLATINUM = new MaterialBuilder(51, "platinum")
		.ingot().ore()
		.color(0xFFFF99)
		.fluid()
		.iconSet(IconSets.SHINY)
		.element(Elements.Pt)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.GEAR, MaterialFlag.FOIL)
		.build();
	public static final Material MOLYBDENUM = new MaterialBuilder(52, "molybdenum")
		.ingot().ore()
		.color(0xAAAAADD)
		.fluid()
		.iconSet(IconSets.DULL)
		.element(Elements.Mo)
		.build();
	public static final Material NEODYMIUM = new MaterialBuilder(53, "neodymium")
		.ingot()
		.color(0x777777)
		.fluid()
		.iconSet(IconSets.METAL)
		.element(Elements.Nd)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.SCREW, MaterialFlag.NO_SMELTING)
		.build();
	public static final Material OSMIUM = new MaterialBuilder(54, "osmium")
		.ingot().ore()
		.color(0x5050FF)
		.fluid(900)
		.iconSet(IconSets.METAL)
		.element(Elements.Os)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.GEAR, MaterialFlag.GENERATE_SCREWDRIVER, MaterialFlag.GENERATE_CROWBAR, MaterialFlag.NO_SMELTING)
		.toolStats(760, 5.0F, 3)
		.build();
	public static final Material ZINC = new MaterialBuilder(55, "zinc")
		.ingot().ore()
		.color(0xFAF0F0)
		.fluid(300)
		.iconSet(IconSets.METAL)
		.element(Elements.Zn)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.FOIL, MaterialFlag.MANUAL_CRUSHING)
		.build();
	public static final Material VANADIUM = new MaterialBuilder(56, "vanadium")
		.ingot().ore()
		.color(0x323232)
		.fluid()
		.iconSet(IconSets.METAL)
		.element(Elements.V)
		.withFlag(MaterialFlag.NO_SMELTING)
		.build();
	public static final Material TUNGSTEN = new MaterialBuilder(57, "tungsten")
		.ingot()
		.color(0x323232)
		.fluid()
		.iconSet(IconSets.METAL)
		.element(Elements.W)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.GEAR, MaterialFlag.SCREW, MaterialFlag.NO_SMELTING)
		.build();
	public static final Material BRASS = new MaterialBuilder(58, "brass")
		.ingot()
		.color(0xFFB400)
		.iconSet(IconSets.METAL)
		.fluid(420)
		.components(new MaterialStack(COPPER, 3), new MaterialStack(ZINC))
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.GEAR, MaterialFlag.MANUAL_CRUSHING, MaterialFlag.SCREW, MaterialFlag.GENERATE_SCREWDRIVER, MaterialFlag.GENERATE_WIRE_CUTTERS, MaterialFlag.GENERATE_MORTAR, MaterialFlag.GENERATE_CROWBAR)
		.toolStats(350, 4.0F, 2)
		.build();
	public static final Material BATTERY_ALLOY = new MaterialBuilder(59, "battery_alloy")
		.ingot()
		.color(0x9C7CA0)
		.fluid()
		.iconSet(IconSets.DULL)
		.components(new MaterialStack(LEAD, 4), new MaterialStack(ANTIMONY))
		.withFlag(MaterialFlag.PLATE)
		.build();
	public static final Material SOLDERING_ALLOY = new MaterialBuilder(60, "soldering_alloy")
		.ingot().fluid(860)
		.color(0xDCDCE6)
		.iconSet(IconSets.DULL)
		.components(new MaterialStack(TIN, 9), new MaterialStack(ANTIMONY))
		.build();
	public static final Material STAINLESS_STEEL = new MaterialBuilder(61, "stainless_steel")
		.ingot()
		.color(0xC8C8DC)
		.fluid()
		.iconSet(IconSets.SHINY)
		.components(new MaterialStack(IRON, 6), new MaterialStack(CHROME), new MaterialStack(MANGANESE), new MaterialStack(NICKEL))
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.SCREW, MaterialFlag.RING, MaterialFlag.ROTOR, MaterialFlag.GEAR, MaterialFlag.SMALL_GEAR, MaterialFlag.NO_SMELTING, MaterialFlag.GENERATE_CROWBAR, MaterialFlag.GENERATE_WIRE_CUTTERS, MaterialFlag.GENERATE_SCREWDRIVER)
		.toolStats(800, 5.0F, 3)
		.build();
	public static final Material HYDROGEN = new MaterialBuilder(62, "hydrogen")
		.gas()
		.color(0x0032FF)
		.element(Elements.H)
		.build();
	public static final Material SULFURIC_ACID = new MaterialBuilder(63, "sulfuric_acid")
		.fluid()
		.color(0xFF8000)
		.components(new MaterialStack(HYDROGEN, 2), new MaterialStack(SULFUR), new MaterialStack(OXYGEN, 4))
		.build();
	public static final Material ANNEALED_COPPER = new MaterialBuilder(64, "annealed_copper")
		.ingot()
		.color(0xFF8D3B)
		.fluid()
		.iconSet(IconSets.SHINY)
		.components(COPPER)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.FOIL)
		.build();
	public static final Material TUNGSTEN_STEEL = new MaterialBuilder(65, "tungsten_steel")
		.ingot()
		.color(0x6464A4)
		.fluid()
		.iconSet(IconSets.METAL)
		.components(STEEL, TUNGSTEN)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.RING, MaterialFlag.ROTOR, MaterialFlag.SCREW, MaterialFlag.GEAR, MaterialFlag.SMALL_GEAR)
		.build();
	public static final Material STONE = new MaterialBuilder(66, "stone")
		.dust()
		.color(0xCDCDCD)
		.iconSet(IconSets.ROUGH)
		.withFlag(MaterialFlag.MANUAL_CRUSHING, MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.GEAR, MaterialFlag.NO_BLOCK_COMPACTING, MaterialFlag.NO_SMELTING)
		.withExistingBlock(Block.cobbleStone)
		.build();
	public static final Material WOOD = new MaterialBuilder(67, "wood")
		.dust()
		.color(0x896727)
		.iconSet(IconSets.WOOD)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.SCREW, MaterialFlag.GEAR, MaterialFlag.NO_BLOCK_COMPACTING, MaterialFlag.NO_SMELTING)
		.withExistingBlock(Block.planksOak)
		.withExistingItems(WOOD_ITEMS)
		.build();
	public static final Material GRANITE = new MaterialBuilder(68, "granite")
		.dust()
		.color(0xCFA18C)
		.iconSet(IconSets.ROUGH)
		.components(new MaterialStack(QUARTZ, 3), new MaterialStack(REDSTONE))
		.withFlag(MaterialFlag.NO_SMELTING, MaterialFlag.NO_BLOCK_COMPACTING)
		.withExistingBlock(Block.cobbleGranite)
		.build();
	public static final Material MAGNETIC_IRON = new MaterialBuilder(69, "magnetic_iron")
		.ingot()
		.magneticOf(IRON)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.GEAR, MaterialFlag.FOIL, MaterialFlag.SMALL_GEAR, MaterialFlag.SCREW)
		.build();
	public static final Material MAGNETIC_STEEL = new MaterialBuilder(70, "magnetic_steel")
		.ingot()
		.magneticOf(STEEL)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.GEAR, MaterialFlag.FOIL, MaterialFlag.SMALL_GEAR, MaterialFlag.SCREW)
		.build();
	public static final Material MAGNETIC_NEODYMIUM = new MaterialBuilder(71, "magnetic_neodymium")
		.ingot()
		.magneticOf(NEODYMIUM)
		.withFlag(MaterialFlag.PLATE, MaterialFlag.ROD, MaterialFlag.SCREW)
		.build();
	public static final Material SILICON_DIOXIDE = new MaterialBuilder(72, "silicon_dioxide")
		.dust()
		.color(0xC8C8C8)
		.iconSet(IconSets.ROUGH)
		.components(new MaterialStack(SILICON), new MaterialStack(OXYGEN, 2))
		.build();
	public static final Material FLINT = new MaterialBuilder(73, "flint")
		.gem()
		.color(0x002040)
		.iconSet(IconSets.ROUGH)
		.components(new MaterialStack(SILICON_DIOXIDE))
		.withFlag(MaterialFlag.NO_BLOCK_COMPACTING, MaterialFlag.MANUAL_CRUSHING, MaterialFlag.BASIC_TOOLS_ONLY)
		.toolStats(new ToolStats.Builder(80, 2.0F).setMiningLevel(1).setDamage(1).build())
		.withExistingItems(FLINT_ITEMS)
		.build();
	public static final Material OLIVINE = new MaterialBuilder(74, "olivine")
		.gem().ore()
		.color(0x66FF66)
		.iconSet(IconSets.RUBY)
		.components(new MaterialStack(MAGNESIUM, 2), new MaterialStack(IRON), new MaterialStack(SILICON_DIOXIDE, 2))
		.withFlag(MaterialFlag.PLATE)
		.withExistingItems(OLIVINE_ITEMS)
		.withExistingBlock(Block.blockOlivine)
		.build();
	public static final Material BASALT = new MaterialBuilder(75, "basalt")
		.dust()
		.color(0x1E1414)
		.iconSet(IconSets.ROUGH)
		.components(new MaterialStack(OLIVINE), new MaterialStack(CALCITE, 3), new MaterialStack(FLINT, 8))
		.withFlag(MaterialFlag.NO_BLOCK_COMPACTING)
		.withExistingBlock(Block.cobbleBasalt)
		.build();
	public static final Material NETHERRACK = new MaterialBuilder(76, "netherrack")
		.dust()
		.color(0xC80000)
		.iconSet(IconSets.ROUGH)
		.withFlag(MaterialFlag.NO_BLOCK_COMPACTING)
		.withExistingBlock(Block.netherrack)
		.build();
	public static final Material LIMESTONE = new MaterialBuilder(77, "limestone")
		.dust()
		.color(0xD1C7A3)
		.iconSet(IconSets.ROUGH)
		.components(new MaterialStack(CALCITE, 2), new MaterialStack(OXYGEN, 2))
		.withFlag(MaterialFlag.NO_BLOCK_COMPACTING)
		.withExistingBlock(Block.cobbleLimestone)
		.build();
	public static final Material GLOWSTONE = new MaterialBuilder(78, "glowstone")
		.dust().fluid(1200)
		.color(0xFFFF00)
		.iconSet(IconSets.SHINY)
		.withFlag(MaterialFlag.NO_BLOCK_COMPACTING)
		.withExistingItems(GLOWSTONE_ITEMS)
		.withExistingBlock(Block.glowstone)
		.build();
	public static final Material MAGNETITE = new MaterialBuilder(79, "magnetite")
		.dust().ore()
		.color(0x232323)
		.iconSet(IconSets.DULL)
		.components(new MaterialStack(IRON), new MaterialStack(OXYGEN, 2))
		.build();
	public static final Material VANADIUM_MAGNETITE = new MaterialBuilder(80, "vanadium_magnetite")
		.dust().ore()
		.color(0x322323)
		.iconSet(IconSets.DULL)
		.components(new MaterialStack(MAGNETITE), new MaterialStack(VANADIUM))
		.build();

	static {
		IRON_ITEMS.put(MaterialItemType.INGOT, Item.ingotIron.getDefaultStack());
		DIAMOND_ITEMS.put(MaterialItemType.GEM, Item.diamond.getDefaultStack());
		COAL_ITEMS.put(MaterialItemType.GEM, Item.coal.getDefaultStack());
		GOLD_ITEMS.put(MaterialItemType.INGOT, Item.ingotGold.getDefaultStack());
		LAPIS_ITEMS.put(MaterialItemType.GEM, new ItemStack(Item.dye, 1, DyeColor.DYE_BLUE.dyeMeta));
		REDSTONE_ITEMS.put(MaterialItemType.DUST, Item.dustRedstone.getDefaultStack());
		STEEL_ITEMS.put(MaterialItemType.INGOT, Item.ingotSteel.getDefaultStack());
		QUARTZ_ITEMS.put(MaterialItemType.GEM, Item.quartz.getDefaultStack());
		WOOD_ITEMS.put(MaterialItemType.STICK, Item.stick.getDefaultStack());
		FLINT_ITEMS.put(MaterialItemType.GEM, Item.flint.getDefaultStack());
		OLIVINE_ITEMS.put(MaterialItemType.GEM, Item.olivine.getDefaultStack());
		GLOWSTONE_ITEMS.put(MaterialItemType.DUST, Item.dustGlowstone.getDefaultStack());
		//HANDLERS_NEEDED = MathHelper.floor_float(Material.MATERIALS.size() / fMETA_LIMIT);
	}

	public static void init() {}
}
