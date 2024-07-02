package turing.btg.material;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MaterialItemType {
	public static final List<MaterialItemType> ITEM_TYPES = new ArrayList<>();
	public static final MaterialItemType INGOT = new MaterialItemType("ingot");
	public static final MaterialItemType GEM = new MaterialItemType("gem");
	public static final MaterialItemType DUST = new MaterialItemType("dust");
	public static final MaterialItemType DUST_SMALL = new MaterialItemType("smallDust", "dusts_small_");
	public static final MaterialItemType DUST_TINY = new MaterialItemType("tinyDust", "dusts_tiny_");
	public static final MaterialItemType CRUSHED_ORE = new MaterialItemType("crushedOre", "ores_crushed_");
	public static final MaterialItemType PLATE = new MaterialItemType("plate");
	public static final MaterialItemType STICK = new MaterialItemType("stick");
	public static final MaterialItemType GEAR = new MaterialItemType("gear");
	public static final MaterialItemType GEAR_SMALL = new MaterialItemType("gearSmall", "gears_small_");
	public static final MaterialItemType BOLT = new MaterialItemType("bolt");
	public static final MaterialItemType SCREW = new MaterialItemType("screw");
	public static final MaterialItemType FOIL = new MaterialItemType("foil");
	public static final MaterialItemType RING = new MaterialItemType("ring");
	public static final MaterialItemType ROTOR = new MaterialItemType("rotor");
	public static final MaterialItemType WIRE_FINE = new MaterialItemType("wire_fine");

	public final String name;
	public final String groupName;

	public MaterialItemType(String name, @Nullable String groupName) {
		this.name = name;
		this.groupName = groupName;
		ITEM_TYPES.add(this);
	}

	public MaterialItemType(String name) {
		this(name, null);
	}

	public String getGroupName() {
		return groupName != null ? groupName : name + "s_";
	}

	@Override
	public String toString() {
		return name;
	}
}
