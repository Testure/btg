package turing.btg.machines;

import turing.btg.BTG;
import turing.btg.BTGTextures;
import turing.btg.api.Machine;
import turing.btg.api.MachineProperties;
import turing.btg.api.RecipeMapMachineBehavior;
import turing.btg.machines.behaviors.SteamBoilerMachineBehavior;
import turing.btg.modularui.Themes;
import turing.btg.recipe.RecipeMaps;
import turing.btg.recipe.entries.SimpleRecipeEntry;

import java.util.HashMap;
import java.util.Map;

public class Machines {
	public static final Map<String, Machine> MACHINES = new HashMap<>();

	public static final Machine STEAM_ALLOY_SMELTER = new SteamMachine<>("steam_alloy_smelter", new MachineProperties(BTGTextures.createFrontOnly(BTGTextures.ALLOY_SMELTER_FRONT), BTGTextures.CASING_STEAM).setNoPaint(true), RecipeMaps.ALLOY_SMELTER);
	public static final Machine STEAM_BOILER = new Machine("steam_boiler", new MachineProperties(BTGTextures.createFrontOnly(BTGTextures.COAL_BOILER_FRONT), BTGTextures.CASING_STEAM).setNoPaint(true), (p, b) -> new SteamBoilerMachineBehavior(p, false, false), Themes.BRONZE);
	public static final Machine ALLOY_SMELTER = new TieredMachine<>("alloy_smelter", BTG.VOLTAGE_TIERS, new MachineProperties(BTGTextures.createFrontOnly(BTGTextures.ALLOY_SMELTER_FRONT)), RecipeMaps.ALLOY_SMELTER);
}
