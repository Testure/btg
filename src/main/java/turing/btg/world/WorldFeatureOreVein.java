package turing.btg.world;

import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.generate.feature.WorldFeature;
import turing.btg.api.IOreStoneType;
import turing.btg.block.BlockOreMaterial;
import turing.btg.block.Blocks;
import turing.btg.material.Materials;
import turing.btg.material.OreStoneType;

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
			Object surface = vein.getProperties().get("surface_material");
			if (surface instanceof Integer && WorldGen.GENERATED_SURFACE_ROCKS.get(x / 16 + "," + z / 16) == null) {
				int surfaceID = (int) surface;
				int handler = MathHelper.floor_float(surfaceID / Materials.fMETA_LIMIT);
				Object surfaceType = vein.getProperties().get("surface_generator");
				Object surfacePattern = vein.getProperties().get("surface_generator_pattern");
				String generator = "ROCK";
				int pattern = 0;
				if (surfaceType instanceof String) {
					generator = (String) surfaceType;
				}
				if (surfacePattern instanceof Integer) {
					pattern = (int) surfacePattern;
				}
				switch (generator) {
					case "PATTERN_ROCKS":
						new WorldFeatureSurfaceRockPattern(handler, pattern).generate(world, random, x, world.getHeightValue(x, z), z, surfaceID);
						break;
					case "PATTERN_BLOCKS":
						new WorldFeatureSurfacePattern(surfaceID, 0, pattern).generate(world, random, x, world.getHeightValue(x, z), z);
						break;
					case "ROCK":
					default:
						new WorldFeatureSurfaceRock(handler).generate(world, random, x, y, z, surfaceID);
						break;
				}
				WorldGen.GENERATED_SURFACE_ROCKS.put(x / 16 + "," + z / 16, surfaceID);
			}
			int[] oreIds = vein.getOreIds();
			int radiusX = (int)(vein.getRadiusX() * ((float)(random.nextInt((100 - 82) + 1) + 82) / 100.0F));
			int radiusZ = vein.getRadiusZ() == vein.getRadiusX() ? radiusX : (int)(vein.getRadiusZ() * ((float)(random.nextInt((100 - 82) + 1) + 82) / 100.0F));
			for (int layer = 0; layer < 8; ++layer) {
				for (int x1 = x - (radiusX / 2); x1 < (x + (radiusX / 2)); ++x1) {
					for (int z1 = z - (radiusZ / 2); z1 < (z + (radiusZ / 2)); ++z1) {
						int currentBlockId = world.getBlockId(x1, (y - 4) - layer, z1);
						if (canBlockBeReplaced(currentBlockId)) {
							List<BlockOreMaterial> list = getBlockListFromReplacement(currentBlockId);
							if (list != null) {
								if (vein.getDensity() > random.nextFloat()) {
									int oreBlock = random.nextInt(3);
									int oreId = currentBlockId;
									switch (oreBlock) {
										case 0:
											oreId = layer < 4 ? oreIds[0] : oreIds[1];
											break;
										case 1:
											oreId = layer > 2 && layer < 6 ? oreIds[2] : (layer < 4 ? oreIds[0] : oreIds[1]);
											break;
										case 2:
											oreId = oreIds[3];
											break;
									}
									int handlerID = MathHelper.floor_float(oreId / Materials.fMETA_LIMIT);
									BlockOreMaterial block = list.get(handlerID);
									if (block != null) {
										world.setBlockAndMetadata(x1, (y - 4) - layer, z1, block.id, oreId - (Materials.iMETA_LIMIT * handlerID));
									}
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
						if (Math.max(prevY - 4, y - 4) - Math.min(prevY - 4, y - 4) < 64) {
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

	private List<BlockOreMaterial> getBlockListFromReplacement(int replacedId) {
		for (IOreStoneType stoneType : OreStoneType.TYPES) {
			if (stoneType.getBaseBlock().id == replacedId) return Blocks.ores.get(stoneType);
		}
		return null;
	}

	private boolean canBlockBeReplaced(int blockId) {
		for (IOreStoneType stoneType : OreStoneType.TYPES) {
			if (stoneType.getBaseBlock().id == blockId) return true;
		}
		return false;
	}
}
