package turing.btg.mixin;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.player.inventory.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import turing.btg.item.ICustomDescription;

@Mixin(targets = "net.minecraft.client.gui.GuiTooltip", remap = false)
public class GuiTooltipMixin {
	@Inject(method = "getTooltipText(Lnet/minecraft/core/item/ItemStack;ZLnet/minecraft/core/player/inventory/slot/Slot;)Ljava/lang/String;", at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;append(Ljava/lang/String;)Ljava/lang/StringBuilder;", ordinal = 14, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	public void customDesc(ItemStack itemStack, boolean showDescription, Slot slot, CallbackInfoReturnable<String> cir, I18n trans, StringBuilder text, boolean shiftPressed, boolean ctrlPressed) {
		ItemStack stack = slot.getStack();
		if (stack != null && stack.getItem() instanceof ICustomDescription) {
			ICustomDescription item = (ICustomDescription) stack.getItem();
			String ogDesc = formatDescription(trans.translateKey(itemStack.getItemName() + ".desc"), 16);
			text.delete(text.indexOf(ogDesc), text.length());
			text.append(TextFormatting.formatted(item.getTranslatedDescription(stack), TextFormatting.LIGHT_GRAY)).append("\n");
			String[] tooltips = item.getTooltips(stack, shiftPressed, ctrlPressed);
			for (String tooltip : tooltips) {
				text.append(tooltip);
			}
		}
	}

    @Shadow
    private static String formatDescription(String description, int preferredLineLength) {
		throw new AssertionError();
    }
}
