package turing.btg.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = RecipeSymbol.class, remap = false)
public class RecipeSymbolMixin {
	@Inject(method = "matches", at = @At(value = "RETURN", shift = At.Shift.BEFORE, ordinal = 1), cancellable = true)
	public void fixMatches(ItemStack stack, CallbackInfoReturnable<Boolean> cir, @Local(name = "stacks") List<ItemStack> stacks) {
		boolean found = false;
		for (ItemStack resolvedStack : stacks) {
			boolean foundId = stack.itemID == resolvedStack.itemID;
			boolean foundMeta = resolvedStack.getMetadata() == -1 || resolvedStack.getMetadata() == stack.getMetadata();
			if (foundId && foundMeta) {
				found = true;
				break;
			}
		}
		cir.setReturnValue(found);
	}
}
