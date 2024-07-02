package turing.btg.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import turing.btg.BTG;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigHandler {
	public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
	public static final File CONFIG_DIR = new File("config/btg");

	static {
		initFolder(CONFIG_DIR);
	}

	public static boolean initFolder(File dir) {
		if (!dir.exists() && !dir.mkdir()) {
			BTG.LOGGER.error("Could not make folder {}!", dir.getAbsolutePath());
			return false;
		}
		return true;
	}

	public static int initDefaults(String sub) {
		String defaultsPath = "configs/" + sub;
		String actualPath = CONFIG_DIR.getPath() + "/" + sub;
		File defaultsFolder = BTG.getAsset(defaultsPath);
		if (defaultsFolder != null) {
			try {
				File actualFolder = new File(actualPath);
				File[] files = defaultsFolder.listFiles();
				File[] existingFiles = actualFolder.listFiles();
				if (files != null && existingFiles != null && existingFiles.length == 0) {
					for (File file : files) {
						Files.copy(file.toPath(), Paths.get(actualPath + "/" + file.getName()));
					}
					return 2;
				}
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public static void writeConfig(JsonObject json, File folder, String name) {
		if (!initFolder(folder)) throw new NullPointerException("Directory " + folder.getAbsolutePath() + " does not exist!");

		String fileName = folder.getPath() + "/" + (name.contains(".json") ? name : name + ".json");
		Path path = Paths.get(fileName);

		if (path.toFile().exists()) throw new IllegalStateException("Config file " + path + " already exists!");
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write(GSON.toJson(json));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static JsonObject readConfig(File folder, String name) {
		if (!folder.exists()) throw new NullPointerException("Folder " + folder.getAbsolutePath() + " does not exist!");

		String fileName = folder.getPath() + "/" + (name.contains(".json") ? name : name + ".json");
		Path path = Paths.get(fileName);

		if (!path.toFile().exists()) throw new IllegalStateException("Config " + path + " does not exist!");
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			return GSON.fromJson(reader, JsonObject.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean configExists(File folder, String name) {
		if (!folder.exists()) throw new NullPointerException("Folder " + folder.getAbsolutePath() + " does not exist!");

		String fileName = folder.getPath() + "/" + (name.contains(".json") ? name : name + ".json");
		Path path = Paths.get(fileName);

		return path.toFile().exists();
	}
}
