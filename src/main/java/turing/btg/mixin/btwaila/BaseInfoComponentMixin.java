package turing.btg.mixin.btwaila;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turing.btg.api.ICustomDescription;

@Mixin(targets = "toufoumaster.btwaila.gui.components.BaseInfoComponent", remap = false)
public class BaseInfoComponentMixin {
	@Inject(method = "baseBlockInfo", at = @At(value = "CONSTANT", args = "stringValue=Minecraft", shift = At.Shift.AFTER))
	public void changeNameAndDesc(Block<?> block, int blockMetadata, ItemStack[] blockDrops, CallbackInfo ci, @Local(name = "blockName") LocalRef<String> blockName, @Local(name = "blockDesc") LocalRef<String> blockDesc) {
		Item item = block.asItem();
		ItemStack stack = item.getDefaultStack();
		stack.setMetadata(blockMetadata);
		if (item instanceof ICustomDescription) {
			blockDesc.set(((ICustomDescription) item).getTranslatedDescription(stack));
		}
		blockName.set(item.getTranslatedName(stack));
	}
}
