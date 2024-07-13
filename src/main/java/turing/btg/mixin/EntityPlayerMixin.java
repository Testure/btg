package turing.btg.mixin;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityPlayer.class, remap = false)
public class EntityPlayerMixin {
	@Inject(method = "getDeathMessage", at = @At("HEAD"), cancellable = true)
	public void addNewMessages(Entity entityKilledBy, CallbackInfoReturnable<String> cir) {

	}
}
