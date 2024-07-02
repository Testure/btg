package turing.btg.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.player.inventory.slot.Slot;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import turing.btg.BTG;
import turing.btg.api.ICustomDescription;
import turing.btg.material.Material;

@Mixin(targets = "net.minecraft.client.gui.GuiTooltip", remap = false)
public class GuiTooltipMixin {
	@Inject(method = "getTooltipText(Lnet/minecraft/core/item/ItemStack;ZLnet/minecraft/core/player/inventory/slot/Slot;)Ljava/lang/String;", at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;append(Ljava/lang/String;)Ljava/lang/StringBuilder;", ordinal = 14, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	public void customDesc(ItemStack itemStack, boolean showDescription, Slot slot, CallbackInfoReturnable<String> cir, I18n trans, StringBuilder text, boolean shiftPressed, boolean ctrlPressed) {
		ItemStack stack = slot.getStack();
		if (stack != null && stack.getItem() instanceof ICustomDescription) {
			ICustomDescription item = (ICustomDescription) stack.getItem();
			String ogDesc = formatDescription(trans.translateKey(itemStack.getItemKey() + ".desc"), 16);
			text.delete(text.indexOf(ogDesc), text.length());
			text.append(TextFormatting.formatted(item.getTranslatedDescription(stack), TextFormatting.LIGHT_GRAY)).append("\n");
		}
	}

	@Inject(method = "getTooltipText(Lnet/minecraft/core/item/ItemStack;ZLnet/minecraft/core/player/inventory/slot/Slot;)Ljava/lang/String;", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Ljava/lang/StringBuilder;append(Ljava/lang/String;)Ljava/lang/StringBuilder;", ordinal = 0))
	public void customTooltips(ItemStack itemStack, boolean showDescription, Slot slot, CallbackInfoReturnable<String> cir, @Local StringBuilder text) {
		ItemStack stack = slot.getStack();
		boolean ctrlPressed = Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
		boolean shiftPressed = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
		if (stack != null && stack.getItem() instanceof ICustomDescription) {
			ICustomDescription item = (ICustomDescription) stack.getItem();
			String[] tooltips = item.getTooltips(stack, shiftPressed, ctrlPressed);
			if (tooltips != null) {
				if (tooltips.length > 0 && tooltips[0] != null && !tooltips[0].isEmpty()) text.append("\n");
				for (int i = 0; i < tooltips.length; i++) {
					if (tooltips[i] != null && !tooltips[i].isEmpty()) {
						text.append(tooltips[i]);
						if (i < tooltips.length - 1) text.append("\n");
					}
				}
			}
		}
		if (stack != null && stack.getItem() != null && !stack.getItem().getKey().contains(BTG.MOD_ID)) {
			Material material = Material.getMaterialForItem(stack);
			if (material != null && !material.getChemicalFormula().isEmpty()) {
				text.append("\n").append(TextFormatting.formatted(material.getChemicalFormula(), TextFormatting.LIGHT_GRAY));
			}
		}
	}

    @Shadow
    private static String formatDescription(String description, int preferredLineLength) {
		throw new AssertionError();
    }
}
