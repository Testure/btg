package turing.btg.api;

import net.minecraft.core.item.Item;
import turing.btg.BTG;
import turing.btg.item.*;
import turing.btg.util.Function3;

import java.util.ArrayList;
import java.util.List;

public class ToolType implements IToolType {
	public static final List<IToolType> TYPES = new ArrayList<>();
	public static final IToolType SWORD = new ToolType("sword", true, true, ItemToolSwordMaterial::new) {
		@Override
		public boolean isVanillaType() {
			return true;
		}
	};
	public static final IToolType PICKAXE = new ToolType("pickaxe", true, true, ItemToolPickaxeMaterial::new) {
		@Override
		public boolean isVanillaType() {
			return true;
		}
		@Override
		public String getTextureIndex() {
			return BTG.MOD_ID + ":item/tools/handle";
		}
	};
	public static final IToolType AXE = new ToolType("axe", true, true, ItemToolAxeMaterial::new) {
		@Override
		public boolean isVanillaType() {
			return true;
		}
		@Override
		public String getTextureIndex() {
			return BTG.MOD_ID + ":item/tools/handle";
		}
	};
	public static final IToolType SHOVEL = new ToolType("shovel", true, true, ItemToolShovelMaterial::new) {
		@Override
		public boolean isVanillaType() {
			return true;
		}
	};
	public static final IToolType HOE = new ToolType("hoe", true, true, ItemToolHoeMaterial::new) {
		@Override
		public boolean isVanillaType() {
			return true;
		}
		@Override
		public String getTextureIndex() {
			return BTG.MOD_ID + ":item/tools/handle";
		}
	};
	public static final IToolType WRENCH = new ToolType("wrench", false, ItemToolWrench::new) {
		@Override
		public String getTextureIndex() {
			return BTG.MOD_ID + ":item/tools/wrench";
		}
	};
	public static final IToolType MORTAR = new ToolType("mortar", ItemToolMortar::new).setRequiresTag();
	public static final IToolType HAMMER = new ToolType("hammer", true, true, ItemToolHammer::new);
	public static final IToolType FILE = new ToolType("file", ItemToolFile::new);
	public static final IToolType SCREWDRIVER = new ToolType("screwdriver", ItemToolScrewdriver::new).setRequiresTag();
	public static final IToolType CROWBAR = new ToolType("crowbar", ItemToolCrowbar::new).setRequiresTag();
	public static final IToolType WIRE_CUTTERS = new ToolType("wire_cutters", ItemToolWireCutters::new).setRequiresTag();
	public static final IToolType SAW = new ToolType("saw", ItemToolSaw::new);

	private final String name;
	private final boolean hasOverlay;
	private final boolean is3D;
	private boolean requiresTag;
	private final Function3<String, Integer, Integer, Item> constructor;

	public ToolType(String name, boolean hasOverlay, boolean is3D, Function3<String, Integer, Integer, Item> constructor) {
		this.name = name;
		this.hasOverlay = hasOverlay;
		this.is3D = is3D;
		this.constructor = constructor;
		TYPES.add(this);
	}

	public ToolType(String name, boolean hasOverlay, Function3<String, Integer, Integer, Item> constructor) {
		this(name, hasOverlay, false, constructor);
	}

	public ToolType(String name, Function3<String, Integer, Integer, Item> constructor) {
		this(name, true, constructor);
	}

	public ToolType setRequiresTag() {
		this.requiresTag = true;
		return this;
	}

	@Override
	public boolean requiresTag() {
		return requiresTag;
	}

	@Override
	public Function3<String, Integer, Integer, Item> getConstructor() {
		return constructor;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isFull3D() {
		return is3D;
	}

	@Override
	public String getTextureIndex() {
		return hasOverlay ? IToolType.super.getTextureIndex() : "";
	}
}
