package turing.btg.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turing.btg.material.MaterialIconSet;

@Mixin(value = RenderEngine.class, remap = false)
public class RenderEngineMixin {
	@Inject(method = "initDynamicTextures", at = @At("HEAD"))
	public void afterInitDynamicTextures(CallbackInfo ci) {
		Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
		//MaterialIconSet.ICON_SETS.values().forEach(set -> set.checkOverlays(mc));
		//MaterialIconSet.init(mc);
	}
}
