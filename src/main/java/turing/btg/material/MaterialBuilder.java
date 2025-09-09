package turing.btg.material;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import turing.btg.api.IMaterialProperty;

import java.util.*;

public class MaterialBuilder {
	private final int id;
	private final String name;
	private int color = -1;
	private MaterialIconSet iconSet = IconSets.METAL;
	private final List<MaterialFlag<?>> flags = new ArrayList<>();
	private final List<IMaterialProperty> properties = new ArrayList<>();
	private String formula = "";
	private final List<MaterialStack> components = new ArrayList<>();
	private Element element;
	private Map<MaterialItemType, ItemStack> manualItemProvider;
	private Block existingBlock;

	public MaterialBuilder(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public MaterialBuilder color(int color) {
		this.color = color;
		return this;
	}

	public MaterialBuilder iconSet(MaterialIconSet iconSet) {
		this.iconSet = iconSet;
		return this;
	}

	public MaterialBuilder formula(String formula) {
		this.formula = formula;
		return this;
	}

	public MaterialBuilder element(Element element) {
		this.element = element;
		return this;
	}

	public MaterialBuilder components(MaterialStack... stacks) {
		this.components.addAll(Arrays.asList(stacks));
		return this;
	}

	public MaterialBuilder components(Material... materials) {
		MaterialStack[] stacks = new MaterialStack[materials.length];
		for (int i = 0; i < materials.length; i++) {
			stacks[i] = new MaterialStack(materials[i]);
		}
		return components(stacks);
	}

	public MaterialBuilder withFlag(MaterialFlag<?>... flags) {
		this.flags.addAll(Arrays.asList(flags));
		return this;
	}

	public MaterialBuilder addProperty(IMaterialProperty... properties) {
		for (IMaterialProperty property : properties) {
			if (!this.properties.contains(property)) this.properties.add(property);
		}
		return this;
	}

	public MaterialBuilder withExistingItems(Map<MaterialItemType, ItemStack> manualItemProvider) {
		this.manualItemProvider = manualItemProvider;
		return this;
	}

	public MaterialBuilder withExistingBlock(Block block) {
		this.existingBlock = block;
		return this;
	}

	public MaterialBuilder ingot() {
		this.addProperty(MaterialProperties.INGOT);
		return dust();
	}

	public MaterialBuilder gem() {
		this.addProperty(MaterialProperties.GEM);
		return dust();
	}

	public MaterialBuilder dust() {
		this.addProperty(MaterialProperties.DUST);
		return this;
	}

	public MaterialBuilder ore() {
		return ore(1);
	}

	public MaterialBuilder ore(int smeltAmount) {
		this.flags.add(new MaterialFlag<>("ore", new OreProperties(smeltAmount)));
		this.addProperty(MaterialProperties.ORE);
		return dust();
	}

	public MaterialBuilder fluid(long temp) {
		this.addProperty(MaterialProperties.FLUID);
		this.flags.add(new MaterialFlag<>("fluid", temp));
		return this;
	}

	public MaterialBuilder gas(long temp) {
		this.addProperty(MaterialProperties.FLUID);
		this.flags.add(new MaterialFlag<>("gas", temp));
		return this;
	}

	public MaterialBuilder fluid() {
		return fluid(100L);
	}

	public MaterialBuilder gas() {
		return gas(100L);
	}

	public MaterialBuilder magneticOf(Material of) {
		this.flags.add(new MaterialFlag<>("magnetic", of));
		return this.withFlag(MaterialFlag.NO_SMELTING).components(of).color(of.getColor()).iconSet(IconSets.MAGNETIC);
	}

	public MaterialBuilder toolStats(ToolStats stats) {
		this.withFlag(new MaterialFlag<>("tools", stats));
		return this;
	}

	public MaterialBuilder toolStats(int durability, float efficiency, int miningLevel, int damage) {
		return this.toolStats(new ToolStats.Builder(durability, efficiency).setMiningLevel(miningLevel).setDamage(damage).build());
	}

	public MaterialBuilder toolStats(int durability, float efficiency, int miningLevel) {
		return this.toolStats(durability, efficiency, miningLevel, miningLevel);
	}

	public Material build() {
		Material material = new Material(id, name);
		material.color = color;
		material.iconSet = iconSet;
		material.chemicalFormula = formula;
		material.flags = flags;
		material.componentList = Collections.unmodifiableList(components);
		material.element = element;
		if (manualItemProvider != null) material.manualItemProvider = manualItemProvider;
		if (existingBlock != null) material.existingBlock = existingBlock;

		if (material.hasFlag("plate")) properties.add(MaterialProperties.PLATE);
		if (material.hasFlag("gear") || material.hasFlag("gearSmall")) properties.add(MaterialProperties.GEAR);
		if (material.hasFlag("gearSmall")) properties.add(MaterialProperties.GEAR_SMALL);
		if (material.hasFlag("rod")) properties.add(MaterialProperties.ROD);
		if (material.hasFlag("foil")) properties.add(MaterialProperties.FOIL);
		if (material.hasFlag("ring")) properties.add(MaterialProperties.RING);
		if (material.hasFlag("rotor")) properties.add(MaterialProperties.ROTOR);
		if (material.hasFlag("wire_fine")) properties.add(MaterialProperties.WIRE_FINE);
		if (material.hasFlag("screw")) {
			properties.add(MaterialProperties.BOLT);
			properties.add(MaterialProperties.SCREW);
		}

		material.properties = properties;
		if (!material.validate()) throw new IllegalStateException("Failed to validate material " + material.name);
		if (material.getChemicalFormula() == null || material.getChemicalFormula().isEmpty()) material.chemicalFormula = material.calculateChemicalFormula();
		return material;
	}
}
