package turing.btg.api;

import com.mojang.nbt.CompoundTag;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.util.helper.Side;

public class RunnableMachineBehavior extends MachineBehavior {
	protected boolean active;
	protected boolean isWorkingDisabled;

	public RunnableMachineBehavior(MachineProperties properties) {
		super(properties);
	}

	public boolean isActive() {
		return active && !isWorkingDisabled();
	}

	public boolean isWorkingDisabled() {
		return isWorkingDisabled;
	}

	protected void setActive(boolean active) {
		this.active = active;
		if (getWorld() != null && !getWorld().isClientSide) {
			notifyBlockUpdate();
			markDirty();
		}
	}

	public void setWorkingDisabled(boolean workingDisabled) {
		this.isWorkingDisabled = workingDisabled;
		if (getWorld() != null && !getWorld().isClientSide) {
			notifyBlockUpdate();
			markDirty();
		}
	}

	@Override
	protected void renderOverlay(Tessellator tessellator, int x, int y, int z, Side side, MachineTexture texture) {
		texture = isWorkingDisabled() && texture.paused != null ? texture.paused : isActive() && texture.active != null ? texture.active : texture;
		super.renderOverlay(tessellator, x, y, z, side, texture);
	}

	@Override
	public void readFromNBT(CompoundTag tag) {
		super.readFromNBT(tag);
		active = tag.getBoolean("active");
		isWorkingDisabled = tag.getBoolean("workingDisabled");
	}

	@Override
	public CompoundTag writeToNBT(CompoundTag tag) {
		tag = super.writeToNBT(tag);
		tag.putBoolean("active", active);
		tag.putBoolean("workingDisabled", isWorkingDisabled);
		return tag;
	}
}
