package turing.btg.machines.behaviors;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.fluids.api.IFluidInventory;
import sunsetsatellite.catalyst.fluids.api.IItemFluidContainer;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import turing.btg.api.MachineProperties;
import turing.btg.api.RunnableMachineBehavior;
import turing.btg.block.Blocks;
import turing.btg.gui.GuiTextures;
import turing.btg.modularui.ModularUI;
import turing.btg.modularui.UIUtil;
import turing.btg.modularui.api.ITheme;
import turing.btg.modularui.api.RenderContext;
import turing.btg.modularui.widgets.WidgetTexture;
import turing.btg.util.RenderUtil;

public class SteamBoilerMachineBehavior extends RunnableMachineBehavior {
	protected final boolean isLava;
	protected final boolean isHighPressure;
	protected int burnTime = 0;
	protected int heat = 0;

	public SteamBoilerMachineBehavior(MachineProperties properties, boolean isLava, boolean isHighPressure) {
		super(properties);
		initializeInventory(2, 0);
		initializeTank(isLava ? 2 : 1, 1);
		this.isLava = isLava;
		this.isHighPressure = isHighPressure;
	}

	@Override
	public ModularUI createUI(ITheme theme) {
		ModularUI ui = super.createUI(theme);

		ui.addWidget(new WidgetTexture(GuiTextures.BOILER_BAR){
			@Override
			public void draw(RenderContext context) {
				super.draw(context);
				GuiTextures.BOILER_HEAT.bindTexture();
				RenderUtil.resetGLColor();
				if (heat > 0) {
					float progress = heat / 100F;
					RenderUtil.drawTexture(UIUtil.getXForRender(this), (int) ((UIUtil.getYForRender(this) + 54) - (54 * progress)), 0, 0, UIUtil.getWidthForRender(this), (int) (UIUtil.getHeightForRender(this) * progress), 1F, 10, 54);
				}
			}
		}.setPos(120, 18));

		return ui;
	}

	@Override
	protected boolean shouldShowFluidSlot(int i, boolean isOutput) {
		return false;
	}

	@Override
	public void onTick() {
		pushSteam();
		takeFluid();
		if (burnTime <= 0 && heat < 100) {
			int amount = burnFuel();
			if (amount > 0) {
				burnTime = amount;
				markDirty();
			}
		}
		if (heat < 100 && burnTime > 0 && tank[0] != null) {
			burnTime -= isHighPressure ? 3 : 1;
			heat += isHighPressure ? 2 : 1;
			markDirty();
		}
		setActive(burnTime > 0);
		if (heat > 0) {
			FluidStack waterStack = getFluidInSlot(0);
			if (waterStack == null) {
				//TODO: EXPLODE!!!!!!!!!!!
				return;
			}
			if (getRemainingFluidCapacity(getSteamSlot()) > 0) {
				if (waterStack.amount >= 10) {
					if (waterStack.amount - 10 <= 0) {
						tank[0] = null;
					} else {
						tank[0].amount -= 10;
					}
					FluidStack remaining = insertFluid(getSteamSlot(), new FluidStack(Blocks.steam, isHighPressure ? 64 : 32));
					if (remaining != null && remaining.amount > 0) {
						getWorld().playSoundEffect(1004, getX(), getY(), getZ(), 0);
						FluidStack steam = getFluidInSlot(getSteamSlot());
						if (steam != null) {
							if (steam.amount - 128 > 0) {
								tank[getSteamSlot()].amount -= 128;
							} else {
								tank[getSteamSlot()] = null;
							}
						}
					}
				}
			}
		}
	}

	protected void takeFluid() {
		ItemStack stack = getStackInSlot(0);
		ItemStack out = getStackInSlot(1);
		if (stack != null && (out == null ||  (out.getItem() == Item.bucket && out.stackSize + 1 < Math.min(getInventoryStackLimit(), out.getMaxStackSize())))) {
			if (stack.getItem() == Item.bucketWater) {
				if (getRemainingFluidCapacity(0) >= 1000) {
					if (tank[0] == null) {
						tank[0] = new FluidStack((BlockFluid) Block.fluidWaterStill, 1000);
					} else {
						tank[0].amount += 1000;
					}
					decreaseStackSize(0, 1);
					if (inventory[1] == null) {
						inventory[1] = new ItemStack(Item.bucket, 1);
					} else {
						inventory[1].stackSize += 1;
					}
					markDirty();
				}
			} else if (isLava && stack.getItem() == Item.bucketLava) {
				if (getRemainingFluidCapacity(1) >= 1000) {
					if (tank[1] == null) {
						tank[1] = new FluidStack((BlockFluid) Block.fluidLavaStill, 1000);
					} else {
						tank[1].amount += 1000;
					}
					decreaseStackSize(0, 1);
					if (inventory[1] == null) {
						inventory[1] = new ItemStack(Item.bucket, 1);
					} else {
						inventory[1].stackSize += 1;
					}
					markDirty();
				}
			}
		}
	}

	protected void pushSteam() {
		if (tank[getSteamSlot()] != null) {
			for (Side side : Side.sides) {
				if (tank[getSteamSlot()] != null) {
					TileEntity te = getWorld().getBlockTileEntity(getX() + side.getOffsetX(), getY() + side.getOffsetY(), getZ() + side.getOffsetZ());
					if (te instanceof IFluidInventory) {
						IFluidInventory fluidInventory = (IFluidInventory) te;
						int slot = ((IFluidInventory) te).getActiveFluidSlot(Direction.getDirectionFromSide(side.getOpposite().getId()));
						FluidStack steam = new FluidStack(Blocks.steam, Math.min(getFluidTransferSpeed(), tank[getSteamSlot()].amount));
						int amount = steam.amount;
						if (fluidInventory.canInsertFluid(slot, steam)) {
							steam = fluidInventory.insertFluid(slot, steam);
							if (steam != null && steam.amount > 0) amount -= steam.amount;
							if (tank[getSteamSlot()].amount - amount > 0) {
								tank[getSteamSlot()].amount -= amount;
							} else {
								tank[getSteamSlot()] = null;
							}
							markDirty();
						}
					}
				} else break;
			}
		}
	}

	protected int burnFuel() {
		if (isLava) {
			FluidStack stack = getFluidInSlot(1);
			if (stack != null && stack.amount >= 100) {
				if (stack.amount - 100 <= 0) {
					tank[1] = null;
				} else {
					tank[1].amount -= 100;
				}
				return 1000;
			}
		} else {
			ItemStack stack = getStackInSlot(1);
			if (stack != null) {
				int fuel = LookupFuelFurnace.instance.getFuelYield(stack.itemID);
				if (fuel > 0) {
					decreaseStackSize(1, 1);
					return fuel;
				}
			}
		}
		return 0;
	}

	@Override
	public boolean isFluidValidForSlot(int slot, FluidStack stack) {
		if (slot == 0) return stack.getLiquid() == Block.fluidWaterStill;
		if (isLava) {
			if (slot == 1) return stack.getLiquid() == Block.fluidLavaStill;
			return slot == 2 && stack.getLiquid() == Blocks.steam;
		} else {
			return slot == 1 && stack.getLiquid() == Blocks.steam;
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 0) {
			if (stack.getItem() instanceof IItemFluidContainer) {
				IItemFluidContainer container = (IItemFluidContainer) stack.getItem();
				FluidStack fluid = container.getCurrentFluid(stack);
				if (fluid != null) {
					return (isLava && (fluid.getLiquid() == Block.fluidLavaStill || fluid.getLiquid() == Block.fluidLavaFlowing)) || (fluid.getLiquid() == Block.fluidWaterStill || fluid.getLiquid() == Block.fluidWaterFlowing);
				}
			} else {
				return stack.getItem() == Item.bucketWater || (isLava && stack.getItem() == Item.bucketLava);
			}
			return false;
		}
		if (slot == 1) {
			if (stack.getItem() instanceof IItemFluidContainer) {
				IItemFluidContainer container = (IItemFluidContainer) stack.getItem();
				FluidStack fluid = container.getCurrentFluid(stack);
				return fluid == null;
			} else if (stack.getItem() == Item.bucket) {
				return true;
			}
			if (!isLava) return LookupFuelFurnace.instance.getFuelYield(stack.itemID) > 0;
		}
		return false;
	}

	protected int getSteamSlot() {
		return isLava ? 2 : 1;
	}

	@Override
	public CompoundTag writeToNBT(CompoundTag tag) {
		tag = super.writeToNBT(tag);
		tag.putInt("burnTime", burnTime);
		return tag;
	}

	@Override
	public void readFromNBT(CompoundTag tag) {
		super.readFromNBT(tag);
		burnTime = tag.getInteger("burnTime");
	}
}
