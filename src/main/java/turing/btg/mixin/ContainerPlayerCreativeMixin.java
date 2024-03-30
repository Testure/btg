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
	protected abstract void updatePage();

	@Shadow
	protected List<ItemStack> searchedItems;

	@Shadow
	public static List<ItemStack> creativeItems;

	@Inject(method = "searchPage", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/lang/I18n;getInstance()Lnet/minecraft/core/lang/I18n;", shift = At.Shift.BEFORE), cancellable = true)
	public void injectProperSearch(String search, CallbackInfo ci) {
		for (int i = 0; i < creativeItemsCount; ++i) {
			ItemStack stack = creativeItems.get(i);
			if (stack.getItem().getTranslatedName(stack).toLowerCase().contains(search.toLowerCase())) {
				this.searchedItems.add(stack);
			}
		}
		this.updatePage();
		ci.cancel();
	}
}
