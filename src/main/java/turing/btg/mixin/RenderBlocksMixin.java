package turing.btg.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turing.btg.client.BlockModelLayered;
import turing.btg.client.BlockModelMaterial;

@Mixin(targets = "net.minecraft.client.render.RenderBlocks", remap = false)
public abstract class RenderBlocksMixin {
    /*@Inject(method = "renderBlockOnInventory(Lnet/minecraft/core/block/Block;IFF)V", at = @At("HEAD"))
	public void beforeRenderBlockOnInventory(Block block, int metadata, float brightness, float alpha, CallbackInfo ci) {
		BlockModel<?> model = BlockModelDispatcher.getInstance().getDispatch(block);
		if (model instanceof BlockModelLayered) {
			BlockModelLayered layered = (BlockModelLayered) model;
			renderBlockOnInventory(layered.getBase(), layered.getBaseMeta(), brightness, alpha);
		}
	}

	@Inject(method = "renderBlockOnInventory(Lnet/minecraft/core/block/Block;IFF)V", at = @At("TAIL"))
	public void afterRenderBlockOnInventory(Block block, int metadata, float brightness, float alpha, CallbackInfo ci, @Local Tessellator tessellator) {
		BlockModel<?> model = BlockModelDispatcher.getInstance().getDispatch(block);
		if (model instanceof BlockModelMaterial) {
			BlockModelMaterial modelMaterial = (BlockModelMaterial) model;
			int i = modelMaterial.getOverlayIndex(metadata);

			if (i != -1) {
				GL11.glColor4f(brightness, brightness, brightness, 0.99F);
				block.setBlockBoundsForItemRender();
				GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				this.renderBottomFace(block, 0.0D, 0.0D, 0.0D, i);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				this.renderTopFace(block, 0.0D, 0.0D, 0.0D, i);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				this.renderNorthFace(block, 0.0D, 0.0D, 0.0D, i);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				this.renderSouthFace(block, 0.0D, 0.0D, 0.0D, i);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				this.renderWestFace(block, 0.0D, 0.0D, 0.0D, i);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				this.renderEastFace(block, 0.0D, 0.0D, 0.0D, i);
				tessellator.draw();
				GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			}
		}
	}*/
}
