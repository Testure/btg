package turing.btg;

import turniplabs.halplibe.util.ConfigUpdater;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

public class BTGConfig {
	private static final ConfigUpdater updater = ConfigUpdater.fromProperties();
	private static final Toml properties = new Toml("BTG Config");
	public static final TomlConfigHandler config;

	static {
		properties.addEntry("StartingItemID", 20000);
		properties.addEntry("StartingBlockID", 10000);

		config = new TomlConfigHandler(updater, BTG.MOD_ID, properties);
	}
}
