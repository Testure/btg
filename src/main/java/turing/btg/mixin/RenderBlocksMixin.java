package turing.btg.mixin;

import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.core.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turing.btg.client.BlockModelLayered;

@Mixin(targets = "net.minecraft.client.render.RenderBlocks", remap = false)
public abstract class RenderBlocksMixin {
	@Shadow
	public abstract void renderBlockOnInventory(Block block, int metadata, float brightness, float alpha);

	@Inject(method = "renderBlockOnInventory(Lnet/minecraft/core/block/Block;IFF)V", at = @At("HEAD"))
	public void beforeRenderBlockOnInventory(Block block, int metadata, float brightness, float alpha, CallbackInfo ci) {
		BlockModel model = BlockModelDispatcher.getInstance().getDispatch(block);
		if (model instanceof BlockModelLayered) {
			BlockModelLayered layered = (BlockModelLayered) model;
			renderBlockOnInventory(layered.getBase(), layered.getBaseMeta(), brightness, alpha);
		}
	}
}
