package turing.btg.api;


import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import turing.btg.BTG;

import java.util.ArrayList;
import java.util.List;

public class MachineTexture {
	public static final List<MachineTexture> TEXTURES = new ArrayList<>();

	protected String tex;
	private IconCoordinate iconCoordinate;
	public final MachineTexture active;
	public final MachineTexture paused;
	public MachineTexture overbright;

	public MachineTexture(String tex, String modid, MachineTexture active, MachineTexture paused) {
		this.tex = modid + ":block/" + tex;
		this.active = active;
		this.paused = paused;
		TEXTURES.add(this);
	}

	public MachineTexture(String tex, String modid) {
		this(tex, modid, null, null);
	}

	public MachineTexture(String tex) {
		this(tex, BTG.MOD_ID);
	}

	public MachineTexture(String tex, MachineTexture active, MachineTexture paused) {
		this(tex, BTG.MOD_ID, active, paused);
	}

	public MachineTexture(String tex, MachineTexture active) {
		this(tex, BTG.MOD_ID, active, null);
	}

	public MachineTexture setOverbright(MachineTexture overbright) {
		this.overbright = overbright;
		return this;
	}

	public IconCoordinate getIconCoordinate() {
		if (iconCoordinate == null) {
			iconCoordinate = TextureRegistry.getTexture(tex);
		}
		return iconCoordinate;
	}

	public String getPath() {
		return tex;
	}
}
