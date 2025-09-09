package turing.btg.api;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Mob;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import turing.btg.BTG;
import turing.btg.recipe.RecipeMap;
import turing.btg.recipe.entries.SimpleRecipeEntry;

public class SteamMachineBehavior<T extends SimpleRecipeEntry> extends RecipeMapMachineBehavior<T> {
	protected final boolean isHighPressure;
	protected boolean needsRelease;

	public SteamMachineBehavior(MachineProperties properties, RecipeMap<T> map, boolean isHighPressure) {
		super(properties, map);
		this.isHighPressure = isHighPressure;
		initializeTank(map.getMaxFluidInputs() + 1, map.getMaxFluidOutputs());
	}

	public SteamMachineBehavior(MachineProperties properties, RecipeMap<T> map) {
		this(properties, map, false);
	}

	@Override
	protected int getFluidInputOffset() {
		return 1;
	}

	@Override
	protected boolean canStartRecipe(T recipe) {
		return tank[0] != null && tank[0].amount > 0;
	}

	@Override
	protected boolean shouldShowFluidSlot(int i, boolean isOutput) {
		return i != 0;
	}

	@Override
	public void onTick() {
		super.onTick();
		if (getWorld() != null && !getWorld().isClientSide) {
			if (needsRelease && canReleaseSteam()) {
				releaseSteam();
			}
			if (currentRecipe == null && !isWorkingDisabled()) {
				T recipe = findRecipe();
				if (recipe != null) {
					startRecipe(recipe);
				}
			}
			if (currentRecipe != null && !isWorkingDisabled()) {
				if (progress < goal) {
					FluidStack steam = tank[0];
					int toTake = isHighPressure ? 4 : 2;
					if (steam != null && steam.amount > toTake) {
						setActive(true);
						steam.amount -= toTake;
						if (steam.amount <= 0) {
							tank[0] = null;
						}
						progress += toTake / 2;
						if (progress >= goal) {
							needsRelease = true;
						}
						markDirty();
					} else if (progress > -2) {
						progress--;
						setActive(false);
						markDirty();
					}
				} else if (!needsRelease) {
					finishRecipe();
					if (currentRecipe != null) {
						consumeInputs();
					}
				}
			}
			if (currentRecipe == null || isWorkingDisabled()) {
				setActive(false);
			}
		}
	}

	protected void releaseSteam() {
		int drainBase = currentRecipe != null ? (currentRecipe.getData().EUt * 100) / 2 : 1000;
		int amountToDrain = isHighPressure ? (int) (drainBase * 1.85) : drainBase;
		if (tank[0].amount <= amountToDrain) {
			tank[0] = null;
		} else {
			tank[0].amount -= amountToDrain;
		}
		needsRelease = false;

		int x = getX() + outputSide.getOffsetX();
		int y = getY() + outputSide.getOffsetY();
		int z = getZ() + outputSide.getOffsetZ();
		//getWorld().playSoundEffect(1004, x, y, z, 0);
		if (getWorld().getBlock(getX(), getY(), getZ()) != null) {
			getWorld().getEntitiesWithinAABB(Mob.class, getWorld().getBlock(getX(), getY(), getZ()).getSelectedBoundingBoxFromPool(getWorld(), x, y, z))
				.forEach(entity -> entity.hurt(null, 6, BTG.HEAT));
		}

		double posX = getX() + 0.5D + outputSide.getOffsetX() * 0.6D;
		double posY = getY() + 0.5D + outputSide.getOffsetY() * 0.6D;
		double posZ = getZ() + 0.5D + outputSide.getOffsetZ() * 0.6D;

		for (int i = 0; i < 7 + getWorld().rand.nextInt(3); i++) {
			getWorld().spawnParticle("smoke", posX, posY, posZ, outputSide.getOffsetX() / 20D, outputSide.getOffsetY() / 20D, outputSide.getOffsetZ() / 20D, 0);
		}

		markDirty();
	}

	protected boolean canReleaseSteam() {
		if (getWorld() != null) {
			int x = getX() + outputSide.getOffsetX();
			int y = getY() + outputSide.getOffsetY();
			int z = getZ() + outputSide.getOffsetZ();
			Block block = getWorld().getBlock(x, y, z);
			boolean blocked = block != null && block.getCollisionBoundingBoxFromPool(getWorld(), x, y, z) != null;
			FluidStack steam = tank[0];
			int drainBase = currentRecipe != null ? (currentRecipe.getData().EUt * 100) / 2 : 1000;
			int amountToDrain = isHighPressure ? (int) (drainBase * 1.85) : drainBase;
			return steam != null && steam.amount >= amountToDrain && !blocked;
		}
		return false;
	}

	@Override
	public CompoundTag writeToNBT(CompoundTag tag) {
		tag = super.writeToNBT(tag);
		tag.putBoolean("needsRelease", needsRelease);
		return tag;
	}

	@Override
	public void readFromNBT(CompoundTag tag) {
		super.readFromNBT(tag);
		needsRelease = tag.getBoolean("needsRelease");
	}
}
