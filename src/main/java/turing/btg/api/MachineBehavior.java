package turing.btg.api;

import com.mojang.nbt.CompoundTag;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.Pair;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.BlockSection;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import turing.btg.BTGConfig;
import turing.btg.BTGTextures;
import turing.btg.entity.tile.TileEntityMachine;
import turing.btg.modularui.ModularUI;
import turing.btg.modularui.api.ITheme;
import turing.btg.modularui.widgets.*;
import turing.btg.util.MachineUtils;
import turing.btg.util.RenderUtil;

public class MachineBehavior {
	public TileEntityMachine holder;
	protected MachineProperties properties;
	public ItemStack[] inventory;
	public FluidStack[] tank;
	public int itemInputs;
	public int itemOutputs;
	public int fluidInputs;
	public int fluidOutputs;
	protected Direction frontFacing = Direction.NORTH;
	protected Side outputSide = Side.SOUTH;
	protected int paintingColor = -1;

	private final int[] sidedRedstoneOutput = new int[6];
	private final int[] sidedRedstoneInput = new int[6];

	public MachineBehavior(MachineProperties properties) {
		this.properties = properties;
	}

	public MachineBehavior(MachineProperties properties, int inputs, int outputs, int fluidInputs, int fluidOutputs) {
		this(properties);
		if (inputs + outputs > 0) initializeInventory(inputs, outputs);
		if (fluidInputs + fluidOutputs > 0) initializeTank(fluidInputs, fluidOutputs);
	}

	public void initializeInventory(int inputs, int outputs) {
		if (inventory != null) return;
		this.itemInputs = inputs;
		this.itemOutputs = outputs;
		this.inventory = new ItemStack[inputs + outputs];
	}

	public void initializeTank(int inputs, int outputs) {
		if (tank != null) return;
		this.fluidInputs = inputs;
		this.fluidOutputs = outputs;
		this.tank = new FluidStack[inputs + outputs];
	}

	public World getWorld() {
		return holder != null ? holder.worldObj : null;
	}

	public int getX() {
		return holder != null ? holder.x : 0;
	}

	public int getY() {
		return holder != null ? holder.y : 0;
	}

	public int getZ() {
		return holder != null ? holder.z : 0;
	}

	public int getPaintingColor() {
		return paintingColor;
	}

	public int getPaintingColorForRendering() {
		if (!properties.canPaint()) return -1;
		if (getPaintingColor() == -1) return BTGConfig.config.getInt("DefaultPaintingColor");
		return getPaintingColor();
	}

	public boolean openUIOnRightClick() {
		return true;
	}

	public void onNeighborChanged() {

	}

	public int getInputRedstoneSignal(Side side, boolean ignoreCover) {
		/*if (!ignoreCover && false) {
			return 0;
		}*/
		return sidedRedstoneInput[side.ordinal()];
	}

	public boolean isBlockRedstonePowered() {
		for (Side side : Side.values()) {
			if (getInputRedstoneSignal(side, false) > 0) {
				return true;
			}
		}
		return false;
	}

	public int getOutputRedstoneSignal(Side side) {
		int sidedOutput = sidedRedstoneOutput[side.ordinal()];
		return sidedOutput;
	}

	public void setOutputRedstoneSignal(Side side, int strength) {
		sidedRedstoneOutput[side.ordinal()] = strength;
		if (getWorld() != null && !getWorld().isClientSide) {
			notifyBlockUpdate();
		}
	}

	public void onTick() {

	}

	protected void notifyBlockUpdate() {
		if (holder != null) {
			holder.notifyBlockUpdate();
		}
	}

	protected void markDirty() {
		if (holder != null) {
			holder.markDirty();
		}
	}

	public void setFrontFacing(Direction direction) {
		this.frontFacing = direction;
		if (getWorld() != null && !getWorld().isClientSide) {
			notifyBlockUpdate();
			markDirty();

		}
	}

	public void setOutputSide(Side outputSide) {
		if (outputSide != frontFacing.getSide()) {
			this.outputSide = outputSide;
			if (getWorld() != null && !getWorld().isClientSide) {
				notifyBlockUpdate();
				markDirty();

			}
		}
	}

	public void setPaintingColor(int paintingColor) {
		if (!properties.canPaint()) return;
		this.paintingColor = paintingColor;
		if (getWorld() != null && !getWorld().isClientSide) {
			notifyBlockUpdate();
			markDirty();

		}
	}

	public boolean isValidFacing(Direction direction) {
		return direction != Direction.UP && direction != Direction.DOWN;
	}

	public boolean isValid() {
		return holder != null && !holder.isInvalid();
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public int getSizeInventory() {
		return inventory == null ? 0 : inventory.length;
	}

	public int getSizeTank() {
		return tank == null ? 0 : tank.length;
	}

	public int getFluidTransferSpeed() {
		return 100;
	}

	public int getFluidSlotForDirection(sunsetsatellite.catalyst.core.util.Direction direction) {
		return 0;
	}

	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	public boolean isFluidValidForSlot(int slot, FluidStack stack) {
		return true;
	}

	public ItemStack getStackInSlot(int slot) {
		if (slot >= 0 && getSizeInventory() > 0) {
			return inventory[slot];
		}
		return null;
	}

	public FluidStack getFluidInSlot(int slot) {
		if (slot >= 0 && getSizeTank() > 0) {
			return tank[slot];
		}
		return null;
	}

	public boolean canInsertFluid(int slot, FluidStack stack) {
		if (slot >= 0 && getSizeTank() > 0) {
			if (slot <= fluidInputs && isFluidValidForSlot(slot, stack)) {
				FluidStack stack1 = getFluidInSlot(slot);
				return stack1 == null || stack1.isFluidEqual(stack);
			}
		}
		return false;
	}

	public boolean canInsertStack(int slot, ItemStack stack) {
		if (slot >= 0 && getSizeInventory() > 0) {
			if (slot <= itemInputs && isItemValidForSlot(slot, stack)) {
				ItemStack stack1 = getStackInSlot(slot);
				return stack1 == null || stack1.canStackWith(stack);
			}
		}
		return false;
	}

	public void setStackInSlot(int slot, ItemStack stack) {
		if (slot >= 0 && getSizeInventory() > 0 && isItemValidForSlot(slot, stack)) {
			inventory[slot] = stack;
		}
	}

	public void setFluidInSlot(int slot, FluidStack stack) {
		if (slot >= 0 && getSizeTank() > 0 && isFluidValidForSlot(slot, stack)) {
			tank[slot] = stack;
		}
	}

	public int getFluidCapacityForSlot(int slot) {
		if (slot >= 0 && getSizeTank() > 0) {
			return 1000 * 64;
		}
		return 0;
	}

	public int getRemainingFluidCapacity(int slot) {
		if (slot >= 0 && getSizeTank() > 0) {
			FluidStack stack = getFluidInSlot(slot);
			if (stack != null) {
				return getFluidCapacityForSlot(slot) - stack.amount;
			} else return getFluidCapacityForSlot(slot);
		}
		return 0;
	}

	public FluidStack insertFluid(int slot, FluidStack stack) {
		if (slot >= 0 && getSizeTank() > 0) {
			int amount = stack.amount;
			FluidStack stack1 = getFluidInSlot(slot);
			if (stack1 == null) {
				int transferred = Math.min(amount, getFluidCapacityForSlot(slot));
				stack1 = stack.copy();
				stack1.amount = transferred;
				setFluidInSlot(slot, stack1);
				stack.amount -= transferred;
				return stack;
			} else if (stack.isFluidEqual(stack)) {
				int transferred = Math.min(amount, getRemainingFluidCapacity(slot));
				tank[slot].amount += transferred;
				stack.amount -= transferred;
				return stack;
			}
		}
		return null;
	}

	public ItemStack decreaseStackSize(int slot, int amount) {
		if (slot >= 0 && slot < getSizeInventory()) {
			ItemStack stack = getStackInSlot(slot);
			if (stack != null) {
				if (stack.stackSize <= amount) {
					inventory[slot] = null;
					holder.onInventoryChanged();
					return stack;
				} else {
					stack = stack.splitStack(amount);
					if (inventory[slot].stackSize <= 0) {
						inventory[slot] = null;
					}
				}
				holder.onInventoryChanged();
				return stack;
			}
		}
		return null;
	}

	public void sortInventory() {

	}

	public void onFluidInventoryChanged() {

	}

	public void onInventoryChanged() {

	}

	public void onLeftClick(EntityPlayer player, Side side, double xHit, double yHit, Pair<sunsetsatellite.catalyst.core.util.Direction, BlockSection> pair) {

	}

	public boolean onRightClick(EntityPlayer player, Side side, double xHit, double yHit) {
		return false;
	}

	public boolean onWrenchClick(EntityPlayer player, Side side, double xHit, double yHit, Pair<sunsetsatellite.catalyst.core.util.Direction, BlockSection> pair) {
		Side playerFacing = Catalyst.calculatePlayerFacing(player.yRot);
		Side effectiveSide = pair.getRight().toDirection(pair.getLeft(), playerFacing).getSide();
		if (!player.isSneaking()) {
			if (effectiveSide != getFrontFacing().getSide() && effectiveSide != outputSide) {
				setOutputSide(effectiveSide);
				return true;
			}
		} else {
			if (effectiveSide != getFrontFacing().getSide() && isValidFacing(effectiveSide.getDirection())) {
				setFrontFacing(effectiveSide.getDirection());
				return true;
			}
		}
		return false;
	}

	public boolean onScrewdriverClick(EntityPlayer player, Side side, double xHit, double yHit, Pair<sunsetsatellite.catalyst.core.util.Direction, BlockSection> pair) {
		return false;
	}

	public boolean onCrowbarClick(EntityPlayer player, Side side, double xHit, double yHit, Pair<sunsetsatellite.catalyst.core.util.Direction, BlockSection> pair) {
		return false;
	}

	public Direction getFrontFacing() {
		return frontFacing;
	}

	public Side getOutputSide() {
		return outputSide;
	}

	public boolean keepsInventory() {
		return properties.keepsInventory();
	}

	public boolean isFullCube() {
		return properties.isFullCube();
	}

	protected boolean shouldShowFluidSlot(int i, boolean isOutput) {
		return true;
	}

	public ModularUI createUI(ITheme theme) {
		ModularUI ui = new ModularUI().setTheme(theme).addPlayerInventory(8, 84);

		ui.addWidget(new WidgetPaddingConstraint(5, 5));
		ui.addWidget(new WidgetText("Machine"));

		for (int i = 0; i < itemInputs; i++) {
			ui.addWidget(new WidgetItemSlot(i).setPos(26 + i * 18, 32));
		}
		for (int i = 0; i < itemOutputs; i++) {
			ui.addWidget(new WidgetItemOutputSlot(itemInputs + i).setPos(100 + i * 18, 32));
		}
		for (int i = 0; i < fluidInputs; i++) {
			if (shouldShowFluidSlot(i, false)) {
				ui.addWidget(new WidgetFluidSlot(i).setPos(26 + i * 18, 50));
			}
		}
		for (int i = 0; i < fluidOutputs; i++) {
			if (shouldShowFluidSlot(fluidInputs + i, true)) {
				ui.addWidget(new WidgetFluidOutputSlot(fluidInputs + i).setPos(100 + i * 18, 50));
			}
		}

		return ui;
	}

	public IconCoordinate getParticleTexture() {
		return properties.getParticleTexture();
	}

	public IconCoordinate getOverbrightTexture(Side side) {
		MachineTexture overlay = properties.overlays[side.getId()];
		return overlay != null && overlay.overbright != null ? overlay.getIconCoordinate() : null;
	}

	public boolean renderBlock(Tessellator tessellator, int x, int y, int z) {
		if (isFullCube()) {
			RenderUtil.renderFullCube(tessellator, x, y, z, getParticleTexture(), getPaintingColorForRendering());
			MachineTexture top = properties.overlays[Side.TOP.getId()];
			MachineTexture bottom = properties.overlays[Side.BOTTOM.getId()];
			MachineTexture front = properties.overlays[Side.NORTH.getId()];
			MachineTexture back = properties.overlays[Side.SOUTH.getId()];
			MachineTexture east = properties.overlays[Side.EAST.getId()];
			MachineTexture west = properties.overlays[Side.WEST.getId()];
			if (top != null) {
				renderOverlay(tessellator, x, y, z, Side.TOP, top);
			}
			if (bottom != null) {
				renderOverlay(tessellator, x, y, z, Side.BOTTOM, bottom);
			}
			if (front != null) {
				renderOverlay(tessellator, x, y, z, frontFacing.getSide(), front);
			}
			if (back != null) {
				renderOverlay(tessellator, x, y, z, frontFacing.getOpposite().getSide(), back);
			}
			if (east != null) {
				renderOverlay(tessellator, x, y, z, frontFacing.rotate(1).getSide(), east);
			}
			if (west != null) {
				renderOverlay(tessellator, x, y, z, frontFacing.rotate(-1).getSide(), west);
			}
			RenderUtil.renderSide(tessellator, x, y, z, outputSide, BTGTextures.MACHINE_OUTPUT.getIconCoordinate());
		}
		return true;
	}

	protected void renderOverlay(Tessellator tessellator, int x, int y, int z, Side side, MachineTexture texture) {
		RenderUtil.renderSide(tessellator, x, y, z, side, texture.getIconCoordinate());
		if (texture.overbright != null) {
			RenderUtil.renderSide(tessellator, x, y, z, side, texture.overbright.getIconCoordinate(), true);
		}
	}

	public void spillInventory(World world, int x, int y, int z) {
		for (int i = 0; i < itemInputs + itemOutputs; i++) {
			ItemStack stack = inventory[i];
			if (stack != null) {
				EntityItem item = world.dropItem(x, y, z, stack);
				item.xd *= 0.5D;
				item.yd *= 0.5D;
				item.zd *= 0.5D;
				item.delayBeforeCanPickup = 0;
			}
		}
	}

	public CompoundTag writeNBTForStack(CompoundTag tag) {
		if (paintingColor != -1) tag.putInt("PaintingColor", paintingColor);

		if (keepsInventory()) {
			if (inventory != null) {
				tag.putList("Inventory", MachineUtils.serializeItemInventory(inventory));
			}
			if (tank != null) {
				tag.putList("Tank", MachineUtils.serializeFluidInventory(tank));
			}
		}

		return tag;
	}

	public void loadFromStackNBT(CompoundTag tag) {
		paintingColor = tag.getIntegerOrDefault("PaintingColor", -1);

		if (keepsInventory()) {
			if (tag.containsKey("Inventory")) {
				inventory = MachineUtils.deserializeItemInventory(tag.getList("Inventory"));
			}
			if (tag.containsKey("Tank")) {
				tank = MachineUtils.deserializeFluidInventory(tag.getList("Tank"));
			}
		}
	}

	public void readFromNBT(CompoundTag tag) {
		frontFacing = Direction.getDirectionById(tag.getInteger("FrontFacing"));
		paintingColor = tag.getInteger("PaintingColor");
		outputSide = Side.getSideById(tag.getInteger("OutputSide"));

		if (tag.containsKey("Inventory")) {
			inventory = MachineUtils.deserializeItemInventory(tag.getList("Inventory"));
		}
		if (tag.containsKey("Tank")) {
			tank = MachineUtils.deserializeFluidInventory(tag.getList("Tank"));
		}
	}

	public CompoundTag writeToNBT(CompoundTag tag) {
		tag.putInt("FrontFacing", frontFacing.getId());
		tag.putInt("PaintingColor", paintingColor);
		tag.putInt("OutputSide", outputSide.getId());

		if (inventory != null) {
			tag.putList("Inventory", MachineUtils.serializeItemInventory(inventory));
		}
		if (tank != null) {
			tag.putList("Tank", MachineUtils.serializeFluidInventory(tank));
		}

		return tag;
	}
}
