package turing.btg.block;

import net.minecraft.client.render.block.color.BlockColorLeaves;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.core.block.*;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import net.minecraft.core.util.helper.Side;
import turing.btg.BTG;
import turing.btg.client.BlockColorMaterial;
import turing.btg.client.BlockModelLayered;
import turing.btg.item.ItemBlockOre;
import turniplabs.halplibe.helper.BlockBuilder;

public class Blocks {
	protected static int NextID;

	public static Block rubberLog;
	public static Block rubberLeaves;
	public static Block rubberSapling;
	public static Block oreStone;
	public static Block oreLimestone;
	public static Block oreGranite;
	public static Block oreBasalt;

	public static void init() {
		NextID = BTG.config.getInt("Starting_Block_ID");

		rubberLog = new BlockBuilder(BTG.MOD_ID)
			.setBlockSound(BlockSounds.WOOD)
			.setSideTextures("log_rubber_side.png")
			.setTopBottomTexture("log_rubber_top.png")
			.setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.FENCES_CONNECT)
			.setHardness(2.0F)
			.build(new BlockLog("rubberLog", NextID++));

		rubberLeaves = new BlockBuilder(BTG.MOD_ID)
			.setHardness(0.2F)
			.setLightOpacity(1)
			.setBlockSound(BlockSounds.GRASS)
			.setTextures("leaves_rubber.png")
			.setBlockColor(new BlockColorLeaves("oak"))
			.setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH)
			.build(new BlockLeavesRubber("rubberLeaves", NextID++));

		rubberSapling = new BlockBuilder(BTG.MOD_ID)
			.setTextures("sapling_rubber.png")
			.setHardness(0F)
			.setBlockSound(BlockSounds.GRASS)
			.setBlockModel(new BlockModelRenderBlocks(1))
			.build(new BlockSaplingRubber("rubberSapling", NextID++));

		oreStone = new BlockBuilder(BTG.MOD_ID)
			.setHardness(3.0F)
			.setResistance(5.0F)
			.addTags(BlockTags.MINEABLE_BY_PICKAXE)
			.setBlockColor(new BlockColorMaterial())
			.setBlockModel(new BlockModelLayered(Block.oreIronStone, Block.stone.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0)))
			.setBlockSound(BlockSounds.STONE)
			.setTextures("ore.png")
			.setItemBlock(ItemBlockOre::new)
			.build(new BlockOre("oreStone", NextID++));

		oreLimestone = new BlockBuilder(BTG.MOD_ID)
			.setHardness(3.0F)
			.setResistance(5.0F)
			.addTags(BlockTags.MINEABLE_BY_PICKAXE)
			.setBlockColor(new BlockColorMaterial())
			.setBlockModel(new BlockModelLayered(Block.oreIronLimestone, Block.limestone.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0)))
			.setBlockSound(BlockSounds.STONE)
			.setTextures("ore.png")
			.setItemBlock(ItemBlockOre::new)
			.build(new BlockOre("oreLimestone", NextID++));

		oreGranite = new BlockBuilder(BTG.MOD_ID)
			.setHardness(3.0F)
			.setResistance(5.0F)
			.addTags(BlockTags.MINEABLE_BY_PICKAXE)
			.setBlockColor(new BlockColorMaterial())
			.setBlockModel(new BlockModelLayered(Block.oreIronGranite, Block.granite.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0)))
			.setBlockSound(BlockSounds.STONE)
			.setTextures("ore.png")
			.setItemBlock(ItemBlockOre::new)
			.build(new BlockOre("oreGranite", NextID++));

		oreBasalt = new BlockBuilder(BTG.MOD_ID)
			.setHardness(3.0F)
			.setResistance(5.0F)
			.addTags(BlockTags.MINEABLE_BY_PICKAXE)
			.setBlockColor(new BlockColorMaterial())
			.setBlockModel(new BlockModelLayered(Block.oreIronBasalt, Block.basalt.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0)))
			.setBlockSound(BlockSounds.STONE)
			.setTextures("ore.png")
			.setItemBlock(ItemBlockOre::new)
			.build(new BlockOre("oreBasalt", NextID++));
	}
}
