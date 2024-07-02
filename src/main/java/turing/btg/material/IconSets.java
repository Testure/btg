package turing.btg.material;

public class IconSets {
	public static final MaterialIconSet METAL = new MaterialIconSetMetal();
	public static final MaterialIconSet SHINY = new MaterialIconSetShiny();
	public static final MaterialIconSet DULL = new MaterialIconSet("dull");
	public static final MaterialIconSet ROUGH = new MaterialIconSet("rough", DULL);
	public static final MaterialIconSet DIAMOND = new MaterialIconSet("diamond", SHINY);
	public static final MaterialIconSet LAPIS = new MaterialIconSet("lapis", ROUGH);
	public static final MaterialIconSet COAL = new MaterialIconSetCoal();
	public static final MaterialIconSet QUARTZ = new MaterialIconSet("quartz", ROUGH);
	public static final MaterialIconSet EMERALD = new MaterialIconSet("emerald", DIAMOND);
	public static final MaterialIconSet RUBY = new MaterialIconSet("ruby", EMERALD);
	public static final MaterialIconSet WOOD = new MaterialIconSet("wood", DULL);
	public static final MaterialIconSet MAGNETIC = new MaterialIconSetMagnetic();
}
