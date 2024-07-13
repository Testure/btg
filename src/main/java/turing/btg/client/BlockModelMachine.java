package turing.btg.client;

import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import org.jetbrains.annotations.Nullable;
import turing.btg.api.MachineProperties;
import turing.btg.api.MachineTexture;
import turing.btg.block.BlockMachine;
import turing.btg.entity.tile.TileEntityMachine;

public class BlockModelMachine<T extends BlockMachine> extends BlockModelStandard<T> {
	protected final MachineProperties properties;

	static {
		for (MachineTexture texture : MachineTexture.TEXTURES) {
			texture.getIconCoordinate();
		}
	}

	public BlockModelMachine(Block block) {
		super(block);
		assert block instanceof BlockMachine;
		properties = ((BlockMachine) block).getProperties();
	}

	@Override
	public boolean shouldSideBeColored(WorldSource blockAccess, int x, int y, int z, int side, int meta) {
		return true;
	}

	@Override
	public boolean shouldItemRender3d() {
		return true;
	}

	@Override
	public boolean render(Tessellator tessellator, int x, int y, int z) {
		TileEntity tile = renderBlocks.blockAccess.getBlockTileEntity(x, y, z);
		if (tile instanceof TileEntityMachine && ((TileEntityMachine) tile).getBehavior() != null) {
			return ((TileEntityMachine) tile).getBehavior().renderBlock(tessellator, x, y, z);
		}
		return false;
	}

	@Override
	public void renderBlockOnInventory(Tessellator tessellator, int metadata, float brightness, float alpha, @Nullable Integer lightmapCoordinate) {
		properties.renderBlockInInventory(tessellator, brightness, alpha, lightmapCoordinate);
	}

	@Override
	public IconCoordinate getParticleTexture(Side side, int meta) {
		return properties.getParticleTexture();
	}

	@Override
	public IconCoordinate getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
		TileEntity tile = blockAccess.getBlockTileEntity(x, y, z);
		if (tile instanceof TileEntityMachine) {
			return ((TileEntityMachine) tile).getBehavior().getParticleTexture();
		}
		return getParticleTexture(side, blockAccess.getBlockMetadata(x, y, z));
	}

	@Override
	public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
		return getParticleTexture(side, data);
	}

	@Override
	public IconCoordinate getBlockOverbrightTextureFromSideAndMeta(Side side, int data) {
		return null;
	}

	@Override
	public IconCoordinate getBlockOverbrightTexture(WorldSource blockAccess, int x, int y, int z, int side) {
		TileEntity tile = blockAccess.getBlockTileEntity(x, y, z);
		if (tile instanceof TileEntityMachine) {
			return ((TileEntityMachine) tile).getBehavior().getOverbrightTexture(Side.getSideById(side));
		}
		return null;
	}
}
