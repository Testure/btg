package turing.btg.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.world.biome.Biome;
import turing.btg.BTG;
import turing.btg.material.Material;
import turing.btg.world.OreVein;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OreVeinConfig extends ConfigHandler {
	static {
		File folder = new File(CONFIG_DIR.getPath() + "/veins");
		initFolder(folder);

		int val = initDefaults("veins");
		if (val == 0) {
			BTG.LOGGER.error("Could not load default ore veins! These ore veins will not generate!");
		} else if (val == 2) BTG.LOGGER.info("Loaded default ore vein configs");

		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.getPath().endsWith(".json")) {
					JsonObject vein = readConfig(folder, file.getName());
					if (vein != null) {
						veinFromJson(vein);
					}
				}
			}
		}
	}

	public static void veinFromJson(JsonObject json) {
		String name = getValueOrError(json, "name", String.class);
		int weight = getValueOrError(json, "weight", Integer.class);
		float density = getValueOrError(json, "density", Float.class);
		int size = getValueOrError(json, "size", Integer.class);
		int minY, maxY;
		JsonElement y = json.get("Y");
		if (y != null) {
			minY = y.getAsInt();
			maxY = y.getAsInt();
		} else {
			minY = getValueOrError(json, "minY", Integer.class);
			maxY = getValueOrError(json, "maxY", Integer.class);
		}
		JsonObject materials = getValueOrError(json, "material", JsonObject.class);
		int primary = Material.getIdFromName(getValueOrError(materials, "primary", String.class));
		int secondary = Material.getIdFromName(getValueOrError(materials, "secondary", String.class));
		int between = Material.getIdFromName(getValueOrError(materials, "between", String.class));
		int sporadic = Material.getIdFromName(getValueOrError(materials, "sporadic", String.class));
		JsonObject generator = getValueOrError(json, "surface_generator", JsonObject.class);
		String type = getValueOrError(generator, "type", String.class).toUpperCase();
		JsonElement pattern = generator.get("pattern");
		int patternId = pattern != null && pattern.isJsonPrimitive() && ((JsonPrimitive) pattern).isNumber() ? pattern.getAsInt() : -1;
		JsonElement value = generator.get("value");

		Map<String, Object> props = new HashMap<>();
		props.put("surface_generator", type);
		if (patternId >= 0) props.put("surface_generator_pattern", patternId);
		if (value.isJsonPrimitive()) {
			if (((JsonPrimitive) value).isString()) {
				props.put("surface_material", Material.getIdFromName(value.getAsString()));
			} else if (((JsonPrimitive) value).isNumber()) {
				props.put("surface_material", value.getAsInt());
			} else {
				throw new NullPointerException("Could not find required property 'value' for " + generator);
			}
		} else {
			throw new NullPointerException("Could not find required property 'value' for " + generator);
		}

		JsonElement biomes = json.get("biomes");
		List<Biome> biomeList = new ArrayList<>();

		if (biomes != null) {
			biomes.getAsJsonArray().asList().forEach(jsonElement -> {
				if (jsonElement.isJsonPrimitive() && ((JsonPrimitive) jsonElement).isString()) {
					String id = jsonElement.getAsString();
					Biome biome = Registries.BIOMES.getItem(id);
					if (biome != null) biomeList.add(biome);
				}
			});
			props.put("biomes", biomeList);
		}

		new OreVein(new int[]{primary, secondary, between, sporadic}, name, minY, maxY, weight, density, size, props);
	}

	// :frown:
	@SuppressWarnings("unchecked")
	public static <T> T getValueOrError(JsonObject json, String property, Class<T> t) {
		JsonElement element = json.get(property);
		if (element != null) {
			if (t == Integer.class) {
				if (element.isJsonPrimitive() && ((JsonPrimitive) element).isNumber()) {
					return (T) (Object) element.getAsInt();
				}
			}
			if (t == Float.class) {
				if (element.isJsonPrimitive() && ((JsonPrimitive) element).isNumber()) {
					return (T) (Object) element.getAsFloat();
				}
			}
			if (t == String.class) {
				if (element.isJsonPrimitive() && ((JsonPrimitive) element).isString()) {
					return (T) element.getAsString();
				}
			}
			if (t == JsonObject.class) {
				if (element.isJsonObject()) {
					return (T) element.getAsJsonObject();
				}
			}
		}
		throw new NullPointerException("Could not find required property '" + property + "' for " + json);
	}

	public static void init() {}
}
