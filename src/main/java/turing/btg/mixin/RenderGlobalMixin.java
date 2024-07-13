package turing.btg.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turing.btg.api.IOverlayShower;
import turing.btg.block.BlockMachine;

@Mixin(value = RenderGlobal.class, remap = false)
public class RenderGlobalMixin {
	@Shadow
	private Minecraft mc;

	@Shadow
	private World worldObj;

	@Inject(method = "drawSelectionBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderGlobal;drawOutlinedBoundingBox(Lnet/minecraft/core/util/phys/AABB;)V", shift = At.Shift.BEFORE), cancellable = true)
	public void a(ICamera camera, HitResult hitResult, float partialTick, CallbackInfo ci, @Local int j, @Local(name = "expand") float expand, @Local(name = "offsetX") double offsetX, @Local(name = "offsetY") double offsetY, @Local(name = "offsetZ") double offsetZ) {
		Block block = Block.blocksList[j];
		ItemStack stack = mc.thePlayer.getHeldItem();

		if (block instanceof BlockMachine && stack != null && stack.getItem() instanceof IOverlayShower && ((IOverlayShower) stack.getItem()).shouldShowOverlay(stack, block)) {
			AABB aabb = block.getSelectedBoundingBoxFromPool(worldObj, hitResult.x, hitResult.y, hitResult.z).expand(expand, expand, expand).getOffsetBoundingBox(-offsetX, -offsetY, -offsetZ);
			double minX = aabb.minX;
			double minY = aabb.minY;
			double minZ = aabb.minZ;
			double maxX = aabb.maxX;
			double maxY = aabb.maxY;
			double maxZ = aabb.maxZ;
			double magicNumber = 0.30000001192092896D;
			Tessellator tessellator = Tessellator.instance;

			tessellator.startDrawing(3);
			tessellator.addVertex(minX, minY, minZ);
			tessellator.addVertex(maxX, minY, minZ);
			tessellator.addVertex(maxX, minY, maxZ);
			tessellator.addVertex(minX, minY, maxZ);
			tessellator.addVertex(minX, minY, minZ);
			tessellator.draw();
			tessellator.startDrawing(3);
			tessellator.addVertex(minX, maxY, minZ);
			tessellator.addVertex(maxX, maxY, minZ);
			tessellator.addVertex(maxX, maxY, maxZ);
			tessellator.addVertex(minX, maxY, maxZ);
			tessellator.addVertex(minX, maxY, minZ);
			tessellator.draw();
			tessellator.startDrawing(1);
			tessellator.addVertex(minX, minY, minZ);
			tessellator.addVertex(minX, maxY, minZ);
			tessellator.addVertex(maxX, minY, minZ);
			tessellator.addVertex(maxX, maxY, minZ);
			tessellator.addVertex(maxX, minY, maxZ);
			tessellator.addVertex(maxX, maxY, maxZ);
			tessellator.addVertex(minX, minY, maxZ);
			tessellator.addVertex(minX, maxY, maxZ);
			tessellator.draw();

			switch (hitResult.side) {
				case NORTH:
					tessellator.startDrawing(3);
					tessellator.addVertex(minX, minY + magicNumber, minZ);
					tessellator.addVertex(maxX, minY + magicNumber, minZ);
					tessellator.draw();
					tessellator.startDrawing(3);
					tessellator.addVertex(minX, maxY - magicNumber, minZ);
					tessellator.addVertex(maxX, maxY - magicNumber, minZ);
					tessellator.draw();
					break;
				case SOUTH:
					tessellator.startDrawing(3);
					tessellator.addVertex(minX, minY + magicNumber, maxZ);
					tessellator.addVertex(maxX, minY + magicNumber, maxZ);
					tessellator.draw();
					tessellator.startDrawing(3);
					tessellator.addVertex(minX, maxY - magicNumber, maxZ);
					tessellator.addVertex(maxX, maxY - magicNumber, maxZ);
					tessellator.draw();
					break;
				case EAST:
					tessellator.startDrawing(3);
					tessellator.addVertex(maxX, minY + magicNumber, minZ);
					tessellator.addVertex(maxX, minY + magicNumber, maxZ);
					tessellator.draw();
					tessellator.startDrawing(3);
					tessellator.addVertex(maxX, maxY - magicNumber, minZ);
					tessellator.addVertex(maxX, maxY - magicNumber, maxZ);
					tessellator.draw();
					break;
				case WEST:
					tessellator.startDrawing(3);
					tessellator.addVertex(minX, minY + magicNumber, minZ);
					tessellator.addVertex(minX, minY + magicNumber, maxZ);
					tessellator.draw();
					tessellator.startDrawing(3);
					tessellator.addVertex(minX, maxY - magicNumber, minZ);
					tessellator.addVertex(minX, maxY - magicNumber, maxZ);
					tessellator.draw();
					break;
			}

			switch (hitResult.side) {
				case TOP:
					tessellator.startDrawing(3);
					tessellator.addVertex(minX + magicNumber, maxY, minZ);
					tessellator.addVertex(maxX - magicNumber, maxY, minZ);
					tessellator.addVertex(maxX - magicNumber, maxY, maxZ);
					tessellator.addVertex(minX + magicNumber, maxY, maxZ);
					tessellator.addVertex(minX + magicNumber, maxY, minZ);
					tessellator.draw();
					tessellator.startDrawing(3);
					tessellator.addVertex(minX, maxY, minZ + magicNumber);
					tessellator.addVertex(maxX, maxY, minZ + magicNumber);
					tessellator.addVertex(maxX, maxY, maxZ - magicNumber);
					tessellator.addVertex(minX, maxY, maxZ - magicNumber);
					tessellator.addVertex(minX, maxY, minZ + magicNumber);
					tessellator.draw();
					break;
				case BOTTOM:
					tessellator.startDrawing(3);
					tessellator.addVertex(minX + magicNumber, minY, minZ);
					tessellator.addVertex(maxX - magicNumber, minY, minZ);
					tessellator.addVertex(maxX - magicNumber, minY, maxZ);
					tessellator.addVertex(minX + magicNumber, minY, maxZ);
					tessellator.addVertex(minX + magicNumber, minY, minZ);
					tessellator.draw();
					tessellator.startDrawing(3);
					tessellator.addVertex(minX, minY, minZ + magicNumber);
					tessellator.addVertex(maxX, minY, minZ + magicNumber);
					tessellator.addVertex(maxX, minY, maxZ - magicNumber);
					tessellator.addVertex(minX, minY, maxZ - magicNumber);
					tessellator.addVertex(minX, minY, minZ + magicNumber);
					tessellator.draw();
					break;
				case NORTH:
				case SOUTH:
					tessellator.startDrawing(1);
					tessellator.addVertex(minX + magicNumber, minY, minZ);
					tessellator.addVertex(minX + magicNumber, maxY, minZ);
					tessellator.addVertex(maxX - magicNumber, minY, minZ);
					tessellator.addVertex(maxX - magicNumber, maxY, minZ);
					tessellator.addVertex(minX + magicNumber, minY, maxZ);
					tessellator.addVertex(minX + magicNumber, maxY, maxZ);
					tessellator.addVertex(maxX - magicNumber, minY, maxZ);
					tessellator.addVertex(maxX - magicNumber, maxY, maxZ);
					tessellator.draw();
					break;
				case WEST:
				case EAST:
					tessellator.startDrawing(1);
					tessellator.addVertex(minX, minY, minZ + magicNumber);
					tessellator.addVertex(minX, maxY, minZ + magicNumber);
					tessellator.addVertex(maxX, minY, minZ + magicNumber);
					tessellator.addVertex(maxX, maxY, minZ + magicNumber);
					tessellator.addVertex(minX, minY, maxZ - magicNumber);
					tessellator.addVertex(minX, maxY, maxZ - magicNumber);
					tessellator.addVertex(maxX, minY, maxZ - magicNumber);
					tessellator.addVertex(maxX, maxY, maxZ - magicNumber);
					tessellator.draw();
					break;
			}

			GL11.glDepthMask(true);
			GL11.glEnable(3553);
			GL11.glDisable(3042);
			ci.cancel();
		}
	}
}
