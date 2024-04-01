package turing.btg.world;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.generate.feature.WorldFeature;
import turing.btg.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WorldFeatureOreVein extends WorldFeature {

	public WorldFeatureOreVein() {

	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		OreVein vein = selectVein(world, random, x, y, z);
		if (vein != null) {
			WorldGen.onGenerateOreVein(x, y, z);
			/*int surfaceY = world.getHeightValue(x, z);
			world.setBlock(x, surfaceY + 1, z, Block.blockDiamond.id);*/
			int[] oreIds = vein.getOreIds();
			int radiusX = (int)(vein.getRadiusX() * ((float)(random.nextInt((100 - 82) + 1) + 82) / 100.0F));
			int radiusZ = vein.getRadiusZ() == vein.getRadiusX() ? radiusX : (int)(vein.getRadiusZ() * ((float)(random.nextInt((100 - 82) + 1) + 82) / 100.0F));
			for (int layer = 0; layer < 8; ++layer) {
				for (int x1 = x - (radiusX / 2); x1 < (x + (radiusX / 2)); ++x1) {
					for (int z1 = z - (radiusZ / 2); z1 < (z + (radiusZ / 2)); ++z1) {
						int currentBlockId = world.getBlockId(x1, (y - 4) - layer, z1);
						if (canBlockBeReplaced(currentBlockId)) {
							int blockId = getBlockIdFromReplacement(currentBlockId);
							if (blockId != currentBlockId) {
								if (vein.getDensity() > random.nextFloat()) {
									int oreBlock = random.nextInt(3);
									int oreBlockId = currentBlockId;
									switch (oreBlock) {
										case 0:
											oreBlockId = layer < 4 ? oreIds[0] : oreIds[1];
											break;
										case 1:
											oreBlockId = layer > 2 && layer < 6 ? oreIds[2] : (layer < 4 ? oreIds[0] : oreIds[1]);
											break;
										case 2:
											oreBlockId = oreIds[3];
											break;
									}
									world.setBlockAndMetadata(x1, (y - 4) - layer, z1, blockId, oreBlockId);
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	private OreVein selectVein(World world, Random rand, int x, int y, int z) {
		if ((x / 16) % 3 == 0 && (z / 16) % 3 == 0) {
			List<Integer> previousVeins = WorldGen.GENERATED_ORE_VEINS.get(x / 16 + "," + z / 16);
			Biome biome = world.getBlockBiome(x, y, z);
			List<OreVein> validVeins = OreVein.VEINS.stream().filter(vein -> {
				if (previousVeins != null) {
					for (int prevY : previousVeins) {
						if (Math.max(prevY - 4, y - 4) - Math.min(prevY - 4, y - 4) < 8) {
							return false;
						}
					}
				}
				if (y <= vein.getMaxY() && y >= vein.getMinY()) {
					return isBiomeValid(vein, biome);
				}
				return false;
			}).collect(Collectors.toList());
			List<Integer> ids = new ArrayList<>();

			for (int o = 0; o < validVeins.size(); ++o) {
				OreVein vein = validVeins.get(o);
				for (int i = ids.size(); i < vein.getChance(); ++i) {
					ids.add(i, o);
				}
			}

			return ids.isEmpty() ? null : validVeins.get(ids.get(rand.nextInt(ids.size())));
		}
		return null;
	}

	private boolean isBiomeValid(OreVein vein, Biome biome) {
		if (vein.getProperties().containsKey("biomes")) {
			Object list = vein.getProperties().get("biomes");
			if (list instanceof List) {
				return ((List) list).contains(biome);
			}
		}
		return true;
	}

	private int getBlockIdFromReplacement(int replacedId) {
		if (replacedId == Block.stone.id) return Blocks.oreStone.id;
		else if (replacedId == Block.basalt.id) return Blocks.oreBasalt.id;
		else if (replacedId == Block.granite.id) return Blocks.oreGranite.id;
		else if (replacedId == Block.limestone.id) return Blocks.oreLimestone.id;
		return replacedId;
	}

	private boolean canBlockBeReplaced(int blockId) {
		return blockId == Block.stone.id || blockId == Block.granite.id || blockId == Block.basalt.id || blockId == Block.limestone.id;
	}
}
