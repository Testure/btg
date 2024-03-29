package turing.btg.mixin;

import net.minecraft.client.entity.fx.EntityDiggingFX;
import net.minecraft.client.entity.fx.EntityFX;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import turing.btg.client.BlockModelLayered;

@Mixin(targets = "net.minecraft.client.entity.fx.EntityDiggingFX", remap = false)
public class EntityDiggingFXMixin extends EntityFX {
	@Shadow
	private Block block;

	public EntityDiggingFXMixin(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);
	}

	@Inject(method = "func_4041_a", at = @At("TAIL"))
	public void beforeFunc_4041_a(int x, int y, int z, CallbackInfoReturnable<EntityDiggingFX> ci) {
		BlockModel model = BlockModelDispatcher.getInstance().getDispatch(this.block);
		if (model instanceof BlockModelLayered) {
			BlockModelLayered layered = (BlockModelLayered) model;
			this.particleTextureIndex = layered.getParticleTextureIndex();
			this.particleRed = this.particleGreen = this.particleBlue = 0.6F;
		}
	}
}
