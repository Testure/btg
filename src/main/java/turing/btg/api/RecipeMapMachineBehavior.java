package turing.btg.api;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import turing.btg.gui.GuiTextures;
import turing.btg.modularui.ModularUI;
import turing.btg.modularui.api.ITheme;
import turing.btg.modularui.api.ProgressMoveType;
import turing.btg.modularui.widgets.WidgetProgress;
import turing.btg.modularui.widgets.WidgetTexture;
import turing.btg.recipe.RecipeMap;
import turing.btg.recipe.RecipeMapSymbol;
import turing.btg.recipe.SimpleRecipeOutput;
import turing.btg.recipe.entries.SimpleRecipeEntry;

import java.util.List;

public class RecipeMapMachineBehavior<T extends SimpleRecipeEntry> extends RunnableMachineBehavior {
	protected final RecipeMap<T> recipeMap;
	protected T currentRecipe;
	protected int goal;
	protected int progress;

	public RecipeMapMachineBehavior(MachineProperties properties, RecipeMap<T> map) {
		super(properties);
		this.recipeMap = map;
	}

	public RecipeMap<T> getRecipeMap() {
		return recipeMap;
	}

	protected int getFluidInputOffset() {
		return 0;
	}

	protected int getChanceBoost(SimpleRecipeOutput output) {
		return 0;
	}

	protected int getChanceTier() {
		return 1;
	}

	protected void finishRecipe() {
		if (currentRecipe != null && !areOutputsFull()) {
			addOutputs();
			progress = 0;
			if (!matchesRecipe(currentRecipe)) {
				goal = 0;
				currentRecipe = null;
			}
		}
	}

	protected T findRecipe() {
		for (T recipe : getRecipeMap().getAllRecipes()) {
			if (matchesRecipe(recipe)) {
				return recipe;
			}
		}
		return null;
	}

	protected void startRecipe(T recipe) {
		if (currentRecipe == null && recipe != null && canStartRecipe(recipe)) {
			progress = 0;
			currentRecipe = recipe;
			goal = recipe.getData().time;
			consumeInputs();
		}
	}

	protected boolean canStartRecipe(T recipe) {
		return true;
	}

	protected boolean matchesRecipe(T recipe) {
		if (recipe != null) {
			RecipeMapSymbol[] symbols = recipe.getInput();
			for (RecipeMapSymbol symbol : symbols) {
				if (recipeMap.getMaxFluidInputs() > 0) {
					List<FluidStack> fluids = symbol.resolveFluids();
					if (fluids != null) {
						boolean matches = false;
						for (int i = getFluidInputOffset(); i < fluidInputs + getFluidInputOffset(); i++) {
							FluidStack stack = tank[i];
							for (FluidStack resolved : fluids) {
								if (stack != null && stack.isFluidEqual(resolved) && stack.amount >= symbol.getAmount()) {
									matches = true;
									break;
								}
							}
						}
						if (!matches) {
							return false;
						}
					}
				}
				if (recipeMap.getMaxInputs() > 0) {
					List<ItemStack> items = symbol.resolve();
					if (items != null) {
						boolean matches = false;
						for (int i = 0; i < itemInputs; i++) {
							ItemStack stack = inventory[i];
							for (ItemStack resolved : items) {
								if (stack != null && stack.itemID == resolved.itemID && (resolved.getMetadata() == -1 || stack.getMetadata() == resolved.getMetadata()) && stack.stackSize >= symbol.getAmount()) {
									matches = true;
									break;
								}
							}
						}
						if (!matches) {
							return false;
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	protected boolean areOutputsFull() {
		if (currentRecipe != null) {
			SimpleRecipeOutput[] outputs = currentRecipe.getOutput();
			for (SimpleRecipeOutput output : outputs) {
				boolean fluidFree = false;
				boolean itemFree = false;
				FluidStack fluid = output.getFluid();
				ItemStack item = output.getItem();
				if (fluid != null) {
					for (int i = fluidInputs; i < fluidInputs + fluidOutputs; i++) {
						FluidStack existing = tank[i];
						if (existing != null) {
							if (existing.isFluidEqual(fluid) && (existing.amount + fluid.amount) <= (1000 * 64)) {
								fluidFree = true;
								break;
							}
						}
					}
					if (!fluidFree) {
						for (int i = fluidInputs; i < fluidInputs + fluidOutputs; i++) {
							if (tank[i] == null) {
								fluidFree = true;
								break;
							}
						}
					}
				} else fluidFree = true;
				if (item != null) {
					for (int i = itemInputs; i < itemInputs + itemOutputs; i++) {
						ItemStack existing = inventory[i];
						if (existing != null) {
							if (existing.itemID == item.itemID && existing.getMetadata() == item.getMetadata()) {
								if (existing.stackSize + item.stackSize <= item.getMaxStackSize()) {
									itemFree = true;
									break;
								}
							}
						}
					}
					if (!itemFree) {
						for (int i = itemInputs; i < itemInputs + itemOutputs; i++) {
							if (inventory[i] == null) {
								itemFree = true;
								break;
							}
						}
					}
				} else itemFree = true;
				if (!fluidFree || !itemFree) {
					return true;
				}
			}
		}
		return false;
	}

	protected void addOutputs() {
		if (currentRecipe != null) {
			SimpleRecipeOutput[] outputs = currentRecipe.getOutput();
			for (SimpleRecipeOutput output : outputs) {
				float chance = recipeMap.chanceFunction.apply(output.getChance(), getChanceBoost(output), getChanceTier());
				if (chance < 1.0F) {
					if (getWorld().rand.nextInt((int) (100 / chance)) == 0) {
						chance = 1.0F;
					} else {
						chance = 0.0F;
					}
				}
				if (chance >= 1.0F) {
					FluidStack fluid = output.getFluid();
					ItemStack item = output.getItem();
					if (fluid != null) {
						int slotFound = -1;
						for (int i = fluidInputs; i < fluidInputs + fluidOutputs; i++) {
							FluidStack existing = tank[i];
							if (existing != null) {
								if (existing.isFluidEqual(fluid) && (existing.amount + fluid.amount) <= (1000 * 64)) {
									slotFound = i;
									break;
								}
							}
						}
						if (slotFound < 0) {
							for (int i = fluidInputs; i < fluidInputs + fluidOutputs; i++) {
								if (tank[i] == null) {
									slotFound = i;
									break;
								}
							}
						}
						if (slotFound > -1) {
							if (tank[slotFound] == null) {
								tank[slotFound] = fluid.copy();
							} else {
								tank[slotFound].amount += fluid.amount;
							}
						}
					}
					if (item != null) {
						int slotFound = -1;
						for (int i = itemInputs; i < itemInputs + itemOutputs; i++) {
							ItemStack existing = inventory[i];
							if (existing != null) {
								if (existing.itemID == item.itemID && existing.getMetadata() == item.getMetadata()) {
									if (existing.stackSize + item.stackSize <= item.getMaxStackSize()) {
										slotFound = i;
										break;
									}
								}
							}
						}
						if (slotFound < 0) {
							for (int i = itemInputs; i < itemInputs + itemOutputs; i++) {
								if (inventory[i] == null) {
									slotFound = i;
									break;
								}
							}
						}
						if (slotFound >= 0) {
							if (inventory[slotFound] == null) {
								inventory[slotFound] = item.copy();
							} else {
								inventory[slotFound].stackSize += item.stackSize;
							}
						}
					}
				}
			}
			markDirty();
		}
	}

	protected void consumeInputs() {
		if (currentRecipe != null) {
			RecipeMapSymbol[] symbols = currentRecipe.getInput();
			for (RecipeMapSymbol symbol : symbols) {
				if (recipeMap.getMaxFluidInputs() > 0) {
					List<FluidStack> fluids = symbol.resolveFluids();
					if (fluids != null) {
						for (int i = getFluidInputOffset(); i < recipeMap.getMaxFluidInputs() + getFluidInputOffset(); i++) {
							FluidStack stack = tank[i];
							if (stack != null) {
								for (FluidStack resolved : fluids) {
									if (stack.isFluidEqual(resolved) && stack.amount >= symbol.getAmount()) {
										stack.amount -= symbol.getAmount();
										if (stack.amount <= 0) {
											tank[i] = null;
										}
										break;
									}
								}
							}
						}
					}
				}
				if (recipeMap.getMaxInputs() > 0) {
					List<ItemStack> items = symbol.resolve();
					if (items != null) {
						for (int i = 0; i < recipeMap.getMaxInputs(); i++) {
							ItemStack stack = inventory[i];
							if (stack != null) {
								for (ItemStack resolved : items) {
									if (stack.itemID == resolved.itemID && (resolved.getMetadata() == -1 || stack.getMetadata() == resolved.getMetadata()) && stack.stackSize >= symbol.getAmount()) {
										stack.stackSize -= symbol.getAmount();
										if (stack.stackSize <= 0) {
											inventory[i] = null;
										}
										break;
									}
								}
							}
						}
					}
				}
			}
			markDirty();
		}
	}

	protected int getProgress() {
		return progress;
	}

	protected int getGoal() {
		return goal;
	}

	@Override
	public ModularUI createUI(ITheme theme) {
		ModularUI ui = super.createUI(theme);

		ui.addWidget(new WidgetProgress(recipeMap.getProgressBarTexture(), recipeMap.getProgressBarMoveType(), this::getProgress, this::getGoal)
			.setPos(70, 32)
		);

		return ui;
	}

	@Override
	public CompoundTag writeToNBT(CompoundTag tag) {
		tag = super.writeToNBT(tag);
		tag.putInt("goal", goal);
		tag.putInt("progress", progress);
		if (currentRecipe != null) {
			tag.putString("currentRecipe", recipeMap.getKey(currentRecipe));
		}
		return tag;
	}

	@Override
	public void readFromNBT(CompoundTag tag) {
		super.readFromNBT(tag);
		goal = tag.getInteger("goal");
		progress = tag.getInteger("progress");
		if (tag.containsKey("currentRecipe")) {
			currentRecipe = recipeMap.getItem(tag.getString("currentRecipe"));
		}
	}
}
