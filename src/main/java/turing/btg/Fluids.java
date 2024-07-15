package turing.btg;

import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.item.Item;
import sunsetsatellite.catalyst.CatalystFluids;
import sunsetsatellite.catalyst.fluids.registry.FluidContainerRegistryEntry;
import sunsetsatellite.catalyst.fluids.util.FluidType;
import turing.btg.block.BlockFluidMaterial;
import turing.btg.block.Blocks;
import turing.btg.item.Items;
import turing.btg.material.Material;

import java.util.Collections;
import java.util.List;

public class Fluids {
	public static void init() {
		for (Material material : Material.MATERIALS.values()) {
			if (material.hasFlag("fluid") || material.hasFlag("gas")) {
				BlockFluidMaterial fluid = Blocks.fluidBlocks.get(material.id);
				List<BlockFluid> list = Collections.singletonList(fluid);
				CatalystFluids.TYPES.register(new FluidType("common:" + material.name, list));
				CatalystFluids.CONTAINERS.register(BTG.MOD_ID + ":" + material.name + "_bucket", new FluidContainerRegistryEntry(BTG.MOD_ID, Items.BUCKETS.get(material.id), Item.bucket, list));
			}
		}
		CatalystFluids.TYPES.register(new FluidType("common:steam", Collections.singletonList(Blocks.steam)));
		CatalystFluids.CONTAINERS.register(BTG.MOD_ID + ":steam_bucket", new FluidContainerRegistryEntry(BTG.MOD_ID, Items.steamBucket, Item.bucket, Collections.singletonList(Blocks.steam)));
	}
}
