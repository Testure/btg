package turing.btg.mixin;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.ContainerPlayerCreative;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = ContainerPlayerCreative.class, remap = false)
public abstract class ContainerPlayerCreativeMixin {
	@Shadow
	public static int creativeItemsCount;

	@Shadow
	protected List<ItemStack> searchedItems;

	@Shadow
	public static List<ItemStack> creativeItems;

	@Inject(method = "searchPage", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/ContainerPlayerCreative;updatePage()V", shift = At.Shift.BEFORE))
	public void injectProperSearch(String search, CallbackInfo ci) {
		for (int i = 0; i < creativeItemsCount; ++i) {
			ItemStack stack = creativeItems.get(i);
			if (stack.getItem().getTranslatedName(stack).toLowerCase().contains(search.toLowerCase())) {
				if (!this.searchedItems.contains(stack)) this.searchedItems.add(stack);
			}
		}
	}
}
