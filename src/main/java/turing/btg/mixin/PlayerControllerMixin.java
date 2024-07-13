package turing.btg.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.player.controller.PlayerController;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import turing.btg.block.BlockMachine;

@Mixin(value = PlayerController.class, remap = false)
public class PlayerControllerMixin {
	@Inject(method = "useItemOn", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/core/world/World;getBlockId(III)I", shift = At.Shift.AFTER), cancellable = true)
	public void allowSneakingInteraction(EntityPlayer entityplayer, World world, ItemStack itemstack, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> cir, @Local(name = "i1") int i1) {
		if (i1 > 0) {
			Block block = Block.blocksList[i1];
			if (block instanceof BlockMachine) {
				if (block.onBlockRightClicked(world, blockX, blockY, blockZ, entityplayer, side, xPlaced, yPlaced)) {
					cir.setReturnValue(true);
					cir.cancel();
				}
			}
		}
	}
}
