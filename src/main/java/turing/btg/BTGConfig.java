package turing.btg;

import turing.btg.config.OreVeinConfig;
import turing.btg.config.SurfacePatternConfig;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

public class BTGConfig {
	private static final Toml properties = new Toml("BTG Config");
	public static final TomlConfigHandler config;
	public static final int BUCKET_FLUID_DIFF;

	static {
		properties.addEntry("StartingItemID", 20000);
		properties.addEntry("StartingMaterialItemID", 32700);
		properties.addEntry("StartingToolID", 30000);
		properties.addEntry("StartingMachineBlockID", 10000);
		properties.addEntry("StartingBlockID", 14000);
		properties.addEntry("StartingMaterialBlockID", 15700);
		properties.addEntry("StartingMaterialFluidID", 15400);
		properties.addEntry("StartingBucketID", 32400);
		properties.addEntry("DefaultPaintingColor", 0xD2DCFF);

		config = new TomlConfigHandler(BTG.MOD_ID, properties);
		SurfacePatternConfig.init();
		OreVeinConfig.init();

		BUCKET_FLUID_DIFF = config.getInt("StartingBucketID") - config.getInt("StartingMaterialFluidID");
	}
}
