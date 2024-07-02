package turing.btg.mixin;

import net.minecraft.core.data.registry.recipe.RecipeRegistry;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import turing.btg.api.IItemToolMaterial;

@Mixin(value = RecipeRegistry.class, remap = false)
public class RecipeRegistryMixin {
	@Inject(method = "onCraftResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/data/registry/recipe/entry/RecipeEntryCrafting;onCraftResult(Lnet/minecraft/core/player/inventory/InventoryCrafting;)[Lnet/minecraft/core/item/ItemStack;", shift = At.Shift.BEFORE))
	public void handleToolsInCrafting(InventoryCrafting inventorycrafting, CallbackInfoReturnable<ItemStack[]> cir) {
		for (int i = 0; i < inventorycrafting.getSizeInventory(); i++) {
			ItemStack stack = inventorycrafting.getStackInSlot(i);
			if (stack != null && stack.getItem() instanceof IItemToolMaterial) {
				ItemStack copy = stack.copy();
				copy.damageItem(1, null);
				if (copy.getMetadata() < copy.getMaxDamage()) {
					inventorycrafting.setInventorySlotContents(i, copy);
				}
			}
		}
	}
}
