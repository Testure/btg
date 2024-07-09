package turing.btg.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.dynamictexture.DynamicTexture;
import net.minecraft.client.render.dynamictexture.DynamicTextureCustom;
import net.minecraft.client.render.stitcher.TextureRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turing.btg.BTG;

import java.util.List;

@Mixin(value = RenderEngine.class, remap = false)
public class RenderEngineMixin {
	@Shadow
	private List<DynamicTexture> dynamicTextures;

	@Shadow
	@Final
	public Minecraft mc;

	@Inject(method = "initDynamicTextures", at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V", shift = At.Shift.AFTER))
	public void afterInitDynamicTextures(CallbackInfo ci) {
		String steamName = BTG.MOD_ID + ":block/fluids/fluid.steam";
		String fluidName = BTG.MOD_ID + ":block/liquid";

		TextureRegistry.register("liquid", TextureRegistry.blockAtlas);
		TextureRegistry.register(steamName, TextureRegistry.blockAtlas);

		addNewAnimation(fluidName);
		addNewAnimation(steamName);
	}

	@Unique
	private void addNewAnimation(String tex) {
		String path = getAnimationPath(tex);
		dynamicTextures.add(new DynamicTextureCustom(mc, mc.texturePackList.getHighestPriorityTexturePackWithFile(path), path, tex, false));
	}

	@Unique
	private static String getAnimationPath(String tex) {
		String textureName = tex.replace(BTG.MOD_ID + ":", "");
		return "/assets/" + BTG.MOD_ID + "/textures/anims/" + textureName + ".png";
	}
}
