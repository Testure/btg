package turing.btg.mixin;

import net.minecraft.client.render.RenderBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.client.render.block.model.BlockModelRenderBlocks")
public interface BlockModelRenderBlocksAccessor {
	@Accessor
	static RenderBlocks getRenderBlocks() {
		throw new AssertionError();
	}
}
