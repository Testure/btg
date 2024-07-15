package turing.btg.entity.tile;

import com.mojang.nbt.CompoundTag;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.Packet140TileEntityData;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import turing.btg.api.MachineBehavior;
import turing.btg.block.BlockMachine;
import turing.btg.modularui.ModularUI;
import turing.btg.modularui.api.IModularUITile;

import java.util.ArrayList;

public class TileEntityMachine extends TileEntity implements IModularUITile {
	protected MachineBehavior behavior;

	public TileEntityMachine() {}

	public TileEntityMachine(MachineBehavior behavior) {
		this.behavior = behavior;
		this.behavior.holder = this;
	}

	public MachineBehavior getBehavior() {
		return behavior;
	}

	@Override
	public void tick() {
		if (behavior != null && worldObj != null) {
			behavior.onTick();
		}
	}

	public void markDirty() {
		if (worldObj != null) {
			worldObj.markBlockDirty(x, y, z);
		}
	}

	public void onNeighborChanged() {
		behavior.onNeighborChanged();
	}

	public void notifyBlockUpdate() {
		if (worldObj != null) {
			worldObj.notifyBlocksOfNeighborChange(x, y, z, worldObj.getBlockId(x, y, z));
		}
	}

	public void markNeedsUpdate() {
		if (worldObj != null) {
			worldObj.markBlockNeedsUpdate(x, y, z);
		}
	}

	@Override
	public int getSizeInventory() {
		return behavior != null ? behavior.getSizeInventory() : 0;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return behavior != null ? behavior.getStackInSlot(i) : null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		if (behavior != null) {
			behavior.setStackInSlot(i, itemStack);
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		return behavior != null ? behavior.decreaseStackSize(slot, amount) : null;
	}

	@Override
	public void sortInventory() {
		if (behavior != null) {
			behavior.sortInventory();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return behavior != null ? behavior.getInventoryStackLimit() : 64;
	}

	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		if (behavior != null) {
			behavior.onInventoryChanged();
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		return new Packet140TileEntityData(this);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return worldObj.getBlockTileEntity(x, y, z) == this && entityPlayer.distanceToSqr((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D) <= 64.0;
	}

	@Override
	public void readFromNBT(CompoundTag tag) {
		super.readFromNBT(tag);
		int id = tag.getInteger("blockId");
		if (id != 0) {
			Block block = Block.getBlock(id);
			if (block instanceof BlockMachine) {
				behavior = ((BlockMachine) block).behaviorSupplier.apply(((BlockMachine) block).getProperties(), (BlockMachine) block);
				behavior.holder = this;
				behavior.readFromNBT(tag.getCompound("behavior"));
			} else throw new IllegalStateException("Non-machine block attempted to use a machine tile entity");
		}
	}

	@Override
	public void writeToNBT(CompoundTag tag) {
		super.writeToNBT(tag);
		tag.putInt("blockId", worldObj.getBlockId(x, y, z));
		if (behavior != null) {
			tag.put("behavior", behavior.writeToNBT(new CompoundTag()));
		}
	}

	@Override
	public String getInvName() {
		return "Machine";
	}

	@Override
	public void invalidate() {
		super.invalidate();
		ModularUI.UI_CACHE.remove((long)x + y + z);
	}

	public ModularUI getUI(EntityPlayer player) {
		long pos = (long)x + y + z;
		ModularUI ui = ModularUI.UI_CACHE.get(pos);
		if (ui == null) {
			ui = createUI();
			if (player instanceof EntityPlayerSP) {
				ModularUI.UI_CACHE.put(pos, ui);
			}
		}
		return ui;
	}

	@Override
	public ModularUI createUI() {
		Block block = worldObj.getBlock(x, y, z);
		return behavior.createUI(((BlockMachine) block).machine.theme);
	}

	@Override
	public boolean canInsertFluid(int i, FluidStack fluidStack) {
		return behavior != null && behavior.canInsertFluid(i, fluidStack);
	}

	@Override
	public FluidStack getFluidInSlot(int i) {
		return behavior != null ? behavior.getFluidInSlot(i) : null;
	}

	@Override
	public int getFluidCapacityForSlot(int i) {
		return behavior != null ? behavior.getFluidCapacityForSlot(i) : 0;
	}

	@Override
	public ArrayList<BlockFluid> getAllowedFluidsForSlot(int i) {
		if (i >= 0 && getSizeInventory() > 0) {
			if (i > behavior.fluidInputs) {
				return new ArrayList<>();
			} else {
				FluidStack stack = getFluidInSlot(i);
				if (stack != null) {
					ArrayList<BlockFluid> list = new ArrayList<>();
					list.add(stack.liquid);
					return list;
				}
			}
		}
		return null;
	}

	@Override
	public void setFluidInSlot(int i, FluidStack fluidStack) {
		if (behavior != null) {
			behavior.setFluidInSlot(i, fluidStack);
		}
	}

	@Override
	public FluidStack insertFluid(int i, FluidStack fluidStack) {
		return behavior != null ? behavior.insertFluid(i, fluidStack) : null;
	}

	@Override
	public int getRemainingCapacity(int i) {
		return behavior != null ? behavior.getRemainingFluidCapacity(i) : 0;
	}

	@Override
	public int getFluidInventorySize() {
		return behavior != null ? behavior.getSizeTank() : 0;
	}

	@Override
	public void onFluidInventoryChanged() {
		if (behavior != null) behavior.onFluidInventoryChanged();
		markDirty();
	}

	@Override
	public int getTransferSpeed() {
		return behavior != null ? behavior.getFluidTransferSpeed() : 1000;
	}
}
