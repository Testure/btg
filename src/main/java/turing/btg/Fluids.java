package turing.btg;

import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.MathHelper;
import sunsetsatellite.catalyst.CatalystFluids;
import sunsetsatellite.catalyst.fluids.registry.FluidRegistryEntry;
import turing.btg.block.BlockFluidMaterial;
import turing.btg.block.Blocks;
import turing.btg.item.Items;
import turing.btg.material.Material;
import turing.btg.material.Materials;

import java.util.Collections;

public class Fluids {
	public static void init() {
		for (Material material : Material.MATERIALS.values()) {
			if (material.hasFlag("fluid") || material.hasFlag("gas")) {
				BlockFluidMaterial fluid = Blocks.fluidBlocks.get(material.id);
				CatalystFluids.FLUIDS.register(BTG.MOD_ID + ":" + material.name, new FluidRegistryEntry(BTG.MOD_ID, Items.BUCKETS.get(material.id), Item.bucket, Collections.singletonList(fluid)));
			}
		}
		CatalystFluids.FLUIDS.register(BTG.MOD_ID + ":steam", new FluidRegistryEntry(BTG.MOD_ID, Items.steamBucket, Item.bucket, Collections.singletonList(Blocks.steam)));
	}
}
