package turing.btg.api;

import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import org.jetbrains.annotations.Nullable;
import turing.btg.BTG;
import turing.btg.BTGConfig;
import turing.btg.BTGTextures;
import turing.btg.util.RenderUtil;

import java.util.List;

public class MachineProperties {
	protected MachineTexture[] overlays;
	protected MachineTexture casing;
	protected boolean noPainting;

	public MachineProperties(MachineTexture[] overlays, MachineTexture casing) {
		this.overlays = overlays == null ? new MachineTexture[6] : overlays;
		this.casing = casing;
	}

	public MachineProperties(MachineTexture[] overlays) {
		this(overlays, null);
	}

	public MachineProperties() {
		this(null, null);
	}

	public IconCoordinate getParticleTexture() {
		return casing != null ? casing.getIconCoordinate() : BTGTextures.CASING_ULV.getIconCoordinate();
	}

	public MachineProperties setCasing(MachineTexture casing) {
		this.casing = casing;
		return this;
	}

	public MachineProperties setNoPaint(boolean noPaint) {
		this.noPainting = noPaint;
		return this;
	}

	public MachineProperties copy() {
		return new MachineProperties(overlays, casing);
	}

	public float getHardness() {
		return 6.0F;
	}

	public float getResistance() {
		return 6.0F;
	}

	public Tag<Block> getEffectiveTag() {
		return BlockTags.MINEABLE_BY_PICKAXE;
	}

	public boolean isFullCube() {
		return true;
	}

	public boolean keepsInventory() {
		return false;
	}

	public boolean canPaint() {
		return !noPainting;
	}

	public void addTooltips(ItemStack stack, List<String> tooltips, boolean shift, boolean control) {

	}

	public void renderBlockInInventory(Tessellator tessellator, float brightness, float alpha, @Nullable Integer lightmapCoordinate) {
		if (isFullCube()) {
			RenderUtil.renderFullCubeInventory(tessellator, brightness, alpha, lightmapCoordinate, getParticleTexture(), canPaint() ? BTGConfig.config.getInt("DefaultPaintingColor") : -1);
			for (Side side : Side.sides) {
				MachineTexture overlay = overlays[side != Side.BOTTOM && side != Side.TOP ? side.getOpposite().getId() : side.getId()];
				if (overlay != null) {
					RenderUtil.renderSideInventory(tessellator, side, overlay.getIconCoordinate(), lightmapCoordinate);
				}
			}
		}
	}
}
