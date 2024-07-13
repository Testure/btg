package turing.btg;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turing.btg.api.IOreStoneType;
import turing.btg.api.IToolType;
import turing.btg.api.ToolType;
import turing.btg.block.BlockMaterial;
import turing.btg.block.BlockOreMaterial;
import turing.btg.block.Blocks;
import turing.btg.client.FallingOreRenderer;
import turing.btg.enchantments.EnchantmentTreeCapitator;
import turing.btg.entity.EntityFallingOre;
import turing.btg.entity.tile.TileEntityMachine;
import turing.btg.item.Items;
import turing.btg.material.Material;
import turing.btg.material.MaterialItemType;
import turing.btg.material.Materials;
import turing.btg.material.OreStoneType;
import turing.btg.interfaces.IEntityPlayerMP;
import turing.btg.modularui.ModularUI;
import turing.btg.modularui.api.IModularUITile;
import turing.btg.modularui.impl.ModularUIContainer;
import turing.btg.modularui.impl.ModularUIScreen;
import turing.btg.modularui.impl.PacketOpenModularUI;
import turing.btg.recipe.ItemGroups;
import turing.btg.recipe.Recipes;
import turing.enchantmentlib.api.EnchantmentBuilder;
import turniplabs.halplibe.helper.CreativeHelper;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.NetworkHelper;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.io.File;
import java.net.URL;

public class BTG implements ModInitializer, GameStartEntrypoint, ClientStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "btg";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static EnchantmentTreeCapitator TREE_CAPITATOR;
	public static final DamageType HEAT = new DamageType(BTG.MOD_ID + ".damagetype.heat", true, true, 4);
	public static final Tag<Block> MINEABLE_BY_WRENCH = Tag.of("mineable_by_wrench");
	public static final Tag<Block> MINEABLE_BY_HAMMER = Tag.of("mineable_by_hammer");

	public static final int VOLTAGE_TIERS = 3;

	static {
		NetworkHelper.register(PacketOpenModularUI.class, true, true);
	}

	@Override
    public void onInitialize() {
		TREE_CAPITATOR = new EnchantmentBuilder<>(MOD_ID, new EnchantmentTreeCapitator())
			.name("tree_capitator")
			.rarity(18)
			.maxLevel(1)
			.build();
		LOGGER.info(MOD_ID + " has initialized!");
    }

	@Override
	public void beforeGameStart() {
		Materials.init();
		Blocks.init();
		Items.init();
		Fluids.init();
		EntityHelper.createTileEntity(TileEntityMachine.class, "machine");
	}

	@Override
	public void afterGameStart() {
		for (Material material : Material.MATERIALS.values()) {
			for (MaterialItemType type : MaterialItemType.ITEM_TYPES) {
				if (material.hasItemType(type)) {
					ItemStack item = Material.getItemForMaterial(material.id, type);
					if (item.getItem().getKey().contains(MOD_ID))
						CreativeHelper.setPriority(item.getItem(), item.getMetadata(), item.getItem().id + material.id);
				}
			}
			int handlerID = MathHelper.floor_float(material.id / Materials.fMETA_LIMIT);
			BlockMaterial materialBlock = Blocks.materialBlock.get(handlerID);
			int meta = materialBlock.getMetaForMaterialID(material.id);
			if (material.hasOre()) {
				for (IOreStoneType stoneType : OreStoneType.TYPES) {
					BlockOreMaterial ore = Blocks.ores.get(stoneType).get(handlerID);
					if (ore != null)
						CreativeHelper.setPriority(ore, meta, ore.id + material.id);
				}
			}
			if (material.hasBlock() && Material.getBlockForMaterial(material.id).getItem().getKey().contains(MOD_ID)) {
				CreativeHelper.setPriority(materialBlock, meta, materialBlock.id + material.id);
			}
			if (material.hasFlag("tools")) {
				int base = BTGConfig.config.getInt("StartingToolID");
				for (IToolType toolType : ToolType.TYPES) {
					if (material.hasToolType(toolType)) {
						CreativeHelper.setPriority(Items.TOOLS.get(toolType).get(material.id).getDefaultStack(), base + material.id);
					}
				}
			}
		}
	}

	@Override
	public void beforeClientStart() {
		EntityHelper.Assignment.queueEntityRenderer(EntityFallingOre.class, FallingOreRenderer::new);
	}

	@Override
	public void afterClientStart() {

	}

	@Override
	public void initNamespaces() {
		Recipes.registerNamespace();
		RecipeBuilder.initNameSpace(MOD_ID);
	}

	@Override
	public void onRecipesReady() {
		Recipes.registerNamespace();
		ItemGroups.init();
		Recipes.init();
	}

	public static void displayModularUI(EntityPlayer player, ModularUI ui, IModularUITile tile, int x, int y, int z) {
		if (player instanceof EntityPlayerMP) {
			((IEntityPlayerMP) player).displayModularUI(new ModularUIContainer(player.inventory, tile, ui), tile, x, y, z);
		} else {
			Minecraft.getMinecraft(BTG.class).displayGuiScreen(new ModularUIScreen(new ModularUIContainer(player.inventory, tile, ui), ui, tile, player.inventory));
		}
	}

	public static File getAsset(String path) {
		String fullPath = "assets/" + MOD_ID + "/" + path;
		URL url = Thread.currentThread().getContextClassLoader().getResource(fullPath);
		if (url != null) {
			try {
				File file = new File(url.toURI());
				if (file.exists()) {
					return file;
				} else throw new NullPointerException("File does not exist!");
			} catch (Exception e) {
				LOGGER.error("Error getting jar asset '{}'!", fullPath);
				e.printStackTrace();
			}
		}
		return null;
	}

	public static IconCoordinate getBlockTexture(String block) {
		return TextureRegistry.getTexture(BTG.MOD_ID + ":block/" + block);
	}

	public static IconCoordinate getItemTexture(String item) {
		return TextureRegistry.getTexture(BTG.MOD_ID + ":item/" + item);
	}
}
