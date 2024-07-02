package turing.btg.material;

public class MaterialProperties {
	public static final MaterialProperty FLUID = new MaterialProperty.MaterialPropertyBuilder("fluid").build();
	public static final MaterialProperty INGOT = new MaterialProperty.MaterialPropertyBuilder("ingot").addItem(MaterialItemType.INGOT).build();
	public static final MaterialProperty GEM = new MaterialProperty.MaterialPropertyBuilder("gem").addItem(MaterialItemType.GEM).incompatibleWith(INGOT).build();
	public static final MaterialProperty DUST = new MaterialProperty.MaterialPropertyBuilder("dust").addItem(MaterialItemType.DUST, MaterialItemType.DUST_SMALL, MaterialItemType.DUST_TINY).build();
	public static final MaterialProperty ORE = new MaterialProperty.MaterialPropertyBuilder("ore").addItem(MaterialItemType.CRUSHED_ORE).requires(DUST).setOre(true).build();
	public static final MaterialProperty PLATE = new MaterialProperty.MaterialPropertyBuilder("plate").addItem(MaterialItemType.PLATE).requiresOr(INGOT, GEM, DUST).build();
	public static final MaterialProperty ROD = new MaterialProperty.MaterialPropertyBuilder("rod").addItem(MaterialItemType.STICK).requiresOr(INGOT, GEM, PLATE).build();
	public static final MaterialProperty BOLT = new MaterialProperty.MaterialPropertyBuilder("bolt").addItem(MaterialItemType.BOLT).requires(ROD).build();
	public static final MaterialProperty SCREW = new MaterialProperty.MaterialPropertyBuilder("screw").addItem(MaterialItemType.SCREW).requires(BOLT).build();
	public static final MaterialProperty FOIL = new MaterialProperty.MaterialPropertyBuilder("foil").addItem(MaterialItemType.FOIL).requires(PLATE).build();
	public static final MaterialProperty GEAR = new MaterialProperty.MaterialPropertyBuilder("gear").addItem(MaterialItemType.GEAR).requires(PLATE).requires(ROD).build();
	public static final MaterialProperty GEAR_SMALL = new MaterialProperty.MaterialPropertyBuilder("gearSmall").addItem(MaterialItemType.GEAR_SMALL).requires(GEAR).build();
	public static final MaterialProperty RING = new MaterialProperty.MaterialPropertyBuilder("ring").addItem(MaterialItemType.RING).requires(INGOT).build();
	public static final MaterialProperty ROTOR = new MaterialProperty.MaterialPropertyBuilder("rotor").addItem(MaterialItemType.ROTOR).requires(PLATE).requires(SCREW).requires(RING).build();
	public static final MaterialProperty WIRE_FINE = new MaterialProperty.MaterialPropertyBuilder("wire_fine").addItem(MaterialItemType.WIRE_FINE).requires(INGOT).build();
}
