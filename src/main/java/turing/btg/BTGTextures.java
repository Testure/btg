package turing.btg;

import turing.btg.api.MachineTexture;
import turing.btg.modularui.GuiTexture;

public class BTGTextures {
	public static final MachineTexture CASING_STEAM = new MachineTexture("machines/casings/steam");
	public static final MachineTexture CASING_ULV = new MachineTexture("machines/casings/ulv");
	public static final MachineTexture CASING_LV = new MachineTexture("machines/casings/lv");
	public static final MachineTexture CASING_MV = new MachineTexture("machines/casings/mv");
	public static final MachineTexture CASING_HV = new MachineTexture("machines/casings/hv");
	public static final MachineTexture CASING_EV = new MachineTexture("machines/casings/ev");

	public static final MachineTexture MACHINE_OUTPUT = new MachineTexture("machines/overlays/output");
	public static final MachineTexture ITEM_OUTPUT = new MachineTexture("machines/overlays/item_output");
	public static final MachineTexture FLUID_OUTPUT = new MachineTexture("machines/overlays/fluid_output");

	public static final MachineTexture COAL_BOILER_FRONT_ACTIVE_OVERBRIGHT = new MachineTexture("machines/generators/boiler/coal/overlay_front_active_overbright");
	public static final MachineTexture COAL_BOILER_FRONT_ACTIVE = new MachineTexture("machines/generators/boiler/coal/overlay_front").setOverbright(COAL_BOILER_FRONT_ACTIVE_OVERBRIGHT);
	public static final MachineTexture COAL_BOILER_FRONT = new MachineTexture("machines/generators/boiler/coal/overlay_front", COAL_BOILER_FRONT_ACTIVE);

	public static final MachineTexture ASSEMBLER_FRONT_OVERBRIGHT = new MachineTexture("machines/assembler/overlay_front_overbright");
	public static final MachineTexture ASSEMBLER_FRONT_ACTIVE_OVERBRIGHT = new MachineTexture("machines/assembler/overlay_front_active_overbright");
	public static final MachineTexture ASSEMBLER_FRONT_PAUSED_OVERBRIGHT = new MachineTexture("machines/assembler/overlay_front_paused_overbright");
	public static final MachineTexture ASSEMBLER_TOP_OVERBRIGHT = new MachineTexture("machines/assembler/overlay_top_overbright");
	public static final MachineTexture ASSEMBLER_FRONT_ACTIVE = new MachineTexture("machines/assembler/overlay_front_active").setOverbright(ASSEMBLER_FRONT_ACTIVE_OVERBRIGHT);
	public static final MachineTexture ASSEMBLER_FRONT_PAUSED = new MachineTexture("machines/assembler/overlay_paused").setOverbright(ASSEMBLER_FRONT_PAUSED_OVERBRIGHT);
	public static final MachineTexture ASSEMBLER_FRONT = new MachineTexture("machines/assembler/overlay_front", ASSEMBLER_FRONT_ACTIVE, ASSEMBLER_FRONT_PAUSED).setOverbright(ASSEMBLER_FRONT_OVERBRIGHT);
	public static final MachineTexture ASSEMBLER_TOP = new MachineTexture("machines/assembler/overlay_top").setOverbright(ASSEMBLER_TOP_OVERBRIGHT);

	public static final MachineTexture ALLOY_SMELTER_FRONT_ACTIVE_OVERBRIGHT = new MachineTexture("machines/alloy_smelter/overlay_front_active_overbright");
	public static final MachineTexture ALLOY_SMELTER_FRONT_ACTIVE = new MachineTexture("machines/alloy_smelter/overlay_front_active").setOverbright(ALLOY_SMELTER_FRONT_ACTIVE_OVERBRIGHT);
	public static final MachineTexture ALLOY_SMELTER_FRONT = new MachineTexture("machines/alloy_smelter/overlay_front", ALLOY_SMELTER_FRONT_ACTIVE);

	public static final MachineTexture[] CASINGS = new MachineTexture[]{
		CASING_ULV,
		CASING_LV,
		CASING_MV,
		CASING_HV,
		CASING_EV
	};

	public static MachineTexture[] createFrontOnly(MachineTexture front) {
		return new MachineTexture[]{null, null, front, null, null, null};
	}

	public static MachineTexture[] createFrontAndSide(MachineTexture front, MachineTexture side) {
		return new MachineTexture[]{null, null, front, null, side, side};
	}

	public static MachineTexture[] createFrontAndTop(MachineTexture front, MachineTexture top) {
		return new MachineTexture[]{null, top, front, null, null, null};
	}

	public static MachineTexture[] createFrontTopAndSide(MachineTexture front, MachineTexture top, MachineTexture side) {
		return new MachineTexture[]{null, top, front, null, side, side};
	}
}
