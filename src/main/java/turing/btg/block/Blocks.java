package turing.btg.block;

import net.minecraft.client.render.block.color.BlockColor;
import net.minecraft.client.render.block.color.BlockColorLeavesOak;
import net.minecraft.client.render.block.model.BlockModelCrossedSquares;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelFluid;
import net.minecraft.client.render.colorizer.Colorizers;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.block.BlockFluidStill;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.sound.BlockSounds;
import net.minecraft.core.util.helper.Side;
import org.useless.dragonfly.helper.ModelHelper;
import org.useless.dragonfly.model.block.BlockModelDragonFly;
import turing.btg.BTG;
import turing.btg.BTGConfig;
import turing.btg.api.IOreStoneType;
import turing.btg.client.*;
import turing.btg.item.ItemBlockMaterial;
import turing.btg.material.Material;
import turing.btg.material.MaterialFlag;
import turing.btg.material.Materials;
import turing.btg.material.OreStoneType;
import turniplabs.halplibe.helper.BlockBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Blocks {
	protected static int NextID;
	protected static int NextMaterialID;
	protected static int NextFluidID;

	public static Block rubberLog;
	public static Block rubberLeaves;
	public static Block rubberSapling;
	public static BlockFluid steam;

	public static final Map<IOreStoneType, List<BlockOreMaterial>> ores = new HashMap<>();
	public static final List<BlockMaterial> materialBlock = new ArrayList<>();
	public static final List<BlockSurfaceRock> surfaceRock = new ArrayList<>();
	public static final Map<Integer, BlockFluidMaterial> fluidBlocks = new HashMap<>();

	public static void init() {
		NextID = BTGConfig.config.getInt("StartingBlockID");
		NextMaterialID = BTGConfig.config.getInt("StartingMaterialBlockID");
		NextFluidID = BTGConfig.config.getInt("StartingMaterialFluidID");

		rubberLog = new BlockBuilder(BTG.MOD_ID)
			.setBlockSound(BlockSounds.WOOD)
			.setSideTextures(BTG.MOD_ID + ":block/log_rubber_side")
			.setTopBottomTextures(BTG.MOD_ID + ":block/log_rubber_top")
			.setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.FENCES_CONNECT)
			.setHardness(2.0F)
			.build(new BlockLogRubber("rubberLog", NextID++));

		rubberLeaves = new BlockBuilder(BTG.MOD_ID)
			.setHardness(0.2F)
			.setLightOpacity(1)
			.setBlockSound(BlockSounds.GRASS)
			.setTextures(BTG.MOD_ID + ":block/leaves_rubber")
			.setBlockColor((block) -> new BlockColorLeavesOak(Colorizers.oak))
			.setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH)
			.build(new BlockLeavesRubber("rubberLeaves", NextID++));

		rubberSapling = new BlockBuilder(BTG.MOD_ID)
			.setTextures(BTG.MOD_ID + ":block/sapling_rubber")
			.setHardness(0F)
			.setBlockSound(BlockSounds.GRASS)
			.setBlockModel(BlockModelCrossedSquares::new)
			.addTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR)
			.build(new BlockSaplingRubber("rubberSapling", NextID++));

		steam = new BlockBuilder(BTG.MOD_ID)
			.setTextures(BTG.MOD_ID + ":block/fluids/fluid.steam")
			.setBlockModel(BlockModelFluid::new)
			.addTags(BlockTags.IS_LAVA, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.PLACE_OVERWRITES)
			.build(new BlockFluidStill("steam", NextID++, net.minecraft.core.block.material.Material.water));

		BlockBuilder material = new BlockBuilder(BTG.MOD_ID).addTags(BlockTags.NOT_IN_CREATIVE_MENU).setItemBlock(ItemBlockMaterial::new);

		BlockBuilder block = material
			.setHardness(5.0F)
			.setResistance(7.0F)
			.setBlockSound(BlockSounds.METAL)
			.addTags(BlockTags.MINEABLE_BY_PICKAXE)
			.setBlockModel((b) -> new BlockModelMaterial<>((BlockMaterial) b));

		BlockBuilder ore = material
			.setHardness(3.0F)
			.setResistance(5.0F);

		for (int i = 0; i <= Materials.HANDLERS_NEEDED; i++) {
			BlockColor color = new BlockColorMaterial(i);
			for (IOreStoneType stoneType : OreStoneType.TYPES) {
				ores.computeIfAbsent(stoneType, k -> new ArrayList<>());
				ores.get(stoneType).add(i, ore
						.setBlockModel((b) -> new BlockModelLayered<>(((BlockMaterial) b), stoneType.getBaseBlock(), BlockModelDispatcher.getInstance().getDispatch(stoneType.getBaseBlock()).getParticleTexture(Side.BOTTOM, 0)))
						.setBlockColor((b) -> color)
						.setBlockSound(stoneType.getBlockSound())
						.addTags(stoneType.getNeededTool())
						.build(new BlockOreMaterial("ore" + stoneType.getName(), NextMaterialID++, i, stoneType))
				);
				ItemToolPickaxe.miningLevels.put(ores.get(stoneType).get(i), 1);
			}
			materialBlock.add(i, block
					.setBlockColor((b) -> color)
					.build(new BlockMaterial("blockMaterial", NextMaterialID++, i))
			);
			surfaceRock.add(i, material
					.setBlockSound(BlockSounds.GRAVEL)
					.setResistance(0)
					.setHardness(0)
					.setBlockModel((b) -> new BlockModelDragonFly(b, ModelHelper.getOrCreateBlockModel(BTG.MOD_ID, "block/surface_rock.json"), null, null, true, 0.25F))
					.setBlockColor((b) -> color)
					.addTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PREVENT_MOB_SPAWNS)
					.build(new BlockSurfaceRock("surfaceRock", NextMaterialID++, i))
			);
		}
		for (Material material1 : Material.MATERIALS.values()) {
			if (material1.hasFlag("gas") || material1.hasFlag("fluid")) {
				long temp = material1.hasFlag("gas") ? material1.getFlagValue(MaterialFlag.GAS) : material1.hasFlag("fluid") ? material1.getFlagValue(MaterialFlag.FLUID) : 1;
				fluidBlocks.put(material1.id, new BlockBuilder(BTG.MOD_ID)
					.setTextures(BTG.MOD_ID + ":block/liquid")
					.setHardness(100F)
					.setLightOpacity(3)
					.setBlockModel(BlockModelFluid::new)
					.addTags(BlockTags.PLACE_OVERWRITES, BlockTags.NOT_IN_CREATIVE_MENU, temp > 200 ? BlockTags.IS_LAVA : BlockTags.IS_WATER)
					.setBlockColor((b) -> new BlockColorStatic(material1.getColor()))
					.build(new BlockFluidMaterial(material1.name, NextFluidID++, material1))
				);
			} else NextFluidID++;
		}
	}
}
