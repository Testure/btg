package turing.btg.material;

import turing.btg.api.IMaterialProperty;

import java.util.HashSet;
import java.util.Set;

public class MaterialFlag<T> {
	public static final MaterialFlag<ToolStats> TOOLS = new MaterialFlag<>("tools");
	public static final MaterialFlag<OreProperties> ORE = new MaterialFlag<>("ore");
	public static final MaterialFlag<Void> PLATE = new MaterialFlag<>("plate");
	public static final MaterialFlag<Void> ROD = new MaterialFlag<>("rod");
	public static final MaterialFlag<Void> GEAR = new MaterialFlag<>("gear");
	public static final MaterialFlag<Void> SCREW = new MaterialFlag<>("screw");
	public static final MaterialFlag<Void> FOIL = new MaterialFlag<>("foil");
	public static final MaterialFlag<Void> RING = new MaterialFlag<>("ring");
	public static final MaterialFlag<Void> ROTOR = new MaterialFlag<>("rotor");
	public static final MaterialFlag<Void> WIRE_FINE = new MaterialFlag<>("wire_fine");
	public static final MaterialFlag<Void> SMALL_GEAR = new MaterialFlag<>("gearSmall");
	public static final MaterialFlag<Void> NO_COMPOSITION = new MaterialFlag<>("no_composition");
	public static final MaterialFlag<Void> NO_SMELTING = new MaterialFlag<>("no_smelting");
	public static final MaterialFlag<Long> FLUID = new MaterialFlag<>("fluid");
	public static final MaterialFlag<Long> GAS = new MaterialFlag<>("gas");
	public static final MaterialFlag<Material> MAGNETIC = new MaterialFlag<>("magnetic");
	public static final MaterialFlag<Void> GENERATE_MORTAR = new MaterialFlag<>("generate_mortar");
	public static final MaterialFlag<Void> GENERATE_SCREWDRIVER = new MaterialFlag<>("generate_screwdriver");
	public static final MaterialFlag<Void> GENERATE_CROWBAR = new MaterialFlag<>("generate_crowbar");
	public static final MaterialFlag<Void> GENERATE_WIRE_CUTTERS = new MaterialFlag<>("generate_wire_cutters");
	public static final MaterialFlag<Void> BASIC_TOOLS_ONLY = new MaterialFlag<>("basic_tools_only");
	public static final MaterialFlag<Void> MANUAL_CRUSHING = new MaterialFlag<>("manual_crushing");
	public static final MaterialFlag<Void> NO_HANDWORKING = new MaterialFlag<>("no_handworking");
	public static final MaterialFlag<Void> NO_BLOCK_COMPACTING = new MaterialFlag<>("no_block_compacting");

	public final String name;
	private final T value;
	private final Set<MaterialFlag<?>> requiredFlags;
	private final Set<IMaterialProperty> requiredProperties;

	public MaterialFlag(String name, T value, Set<MaterialFlag<?>> requiredFlags, Set<IMaterialProperty> requiredProperties) {
		this.name = name;
		this.value = value;
		this.requiredFlags = requiredFlags;
		this.requiredProperties = requiredProperties;
	}

	public MaterialFlag(String name, T value) {
		this(name, value, new HashSet<>(), new HashSet<>());
	}

	public MaterialFlag(String name) {
		this(name, null);
	}

	public T getValue() {
		return value;
	}

	public boolean validate(Material material) {
		for (MaterialFlag<?> flag : requiredFlags) {
			if (!material.flags.contains(flag)) return false;
		}
		for (IMaterialProperty property : requiredProperties) {
			if (!material.properties.contains(property)) return false;
		}
		return true;
	}
}
