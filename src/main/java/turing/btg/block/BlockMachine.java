package turing.btg.block;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.Pair;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.BlockSection;
import turing.btg.BTG;
import turing.btg.api.Machine;
import turing.btg.api.MachineBehavior;
import turing.btg.api.MachineProperties;
import turing.btg.entity.tile.TileEntityMachine;
import turing.btg.item.ItemToolCrowbar;
import turing.btg.item.ItemToolScrewdriver;
import turing.btg.item.ItemToolWrench;
import turing.btg.util.Function2;

public class BlockMachine extends BlockTileEntityRotatable {
	public final Function2<MachineProperties, BlockMachine, MachineBehavior> behaviorSupplier;
	protected final MachineProperties properties;
	public final Machine machine;

	public BlockMachine(String key, int id, Function2<MachineProperties, BlockMachine, MachineBehavior> behaviorSupplier, MachineProperties properties, Machine machine) {
		super(key, id, Material.metal);
		this.behaviorSupplier = behaviorSupplier;
		this.properties = properties;
		this.machine = machine;
		withHardness(properties.getHardness());
		withBlastResistance(properties.getResistance());
		withTags(properties.getEffectiveTag());
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileEntityMachine) {
			((TileEntityMachine) tile).onNeighborChanged();
		}
	}

	@Override
	public void setDefaultDirection(World world, int x, int y, int z) {
		super.setDefaultDirection(world, x, y, z);
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileEntityMachine) {
			((TileEntityMachine) tile).getBehavior().setFrontFacing(Direction.NORTH);
		}
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public boolean isPoweringTo(WorldSource blockAccess, int x, int y, int z, int side) {
		TileEntity tile = blockAccess.getBlockTileEntity(x, y, z);
		if (tile instanceof TileEntityMachine) {
			return ((TileEntityMachine) tile).getBehavior().getOutputRedstoneSignal(Side.getSideById(side)) > 0;
		}
		return false;
	}

	@Override
	public void onBlockPlaced(World world, int x, int y, int z, Side side, EntityLiving entity, double sideHeight) {
		super.onBlockPlaced(world, x, y, z, side, entity, sideHeight);
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileEntityMachine) {
			((TileEntityMachine) tile).getBehavior().setFrontFacing(entity.getHorizontalPlacementDirection(side).getOpposite());
			((TileEntityMachine) tile).getBehavior().setOutputSide(entity.getHorizontalPlacementDirection(side).getSide());
			ItemStack stack = entity.getHeldItem();
			CompoundTag tag = stack.getData();
			if (!tag.getValues().isEmpty()) {
				((TileEntityMachine) tile).getBehavior().loadFromStackNBT(tag);
			}
		}
	}

	@Override
	public void onBlockRemoved(World world, int x, int y, int z, int data) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileEntityMachine) {
			if (!((TileEntityMachine) tile).getBehavior().keepsInventory()) {
				((TileEntityMachine) tile).getBehavior().spillInventory(world, x, y, z);
			}
		}
		super.onBlockRemoved(world, x, y, z, data);
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		if (dropCause != EnumDropCause.IMPROPER_TOOL) {
			ItemStack stack = new ItemStack(this, 1);
			if (dropCause != EnumDropCause.EXPLOSION && dropCause != EnumDropCause.WORLD && tileEntity instanceof TileEntityMachine) {
				CompoundTag tag = new CompoundTag();
				((TileEntityMachine) tileEntity).getBehavior().writeNBTForStack(tag);
				if (!tag.getValues().isEmpty()) {
					stack.setData(tag);
				}
			}
			return new ItemStack[]{stack};
		}
		return null;
	}

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, Side side, double xHit, double yHit) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityMachine && !world.isClientSide) {
			TileEntityMachine tile = (TileEntityMachine) te;
			MachineBehavior behavior = tile.getBehavior();
			if (behavior != null) {
				ItemStack stack = player.getHeldItem();
				if (stack != null) {
					Pair<sunsetsatellite.catalyst.core.util.Direction, BlockSection> pair = Catalyst.getBlockSurfaceClickPosition(world, player);
					if (stack.getItem() instanceof ItemToolWrench && behavior.onWrenchClick(player, side, xHit, yHit, pair)) {
						return true;
					}
					if (stack.getItem() instanceof ItemToolScrewdriver && behavior.onScrewdriverClick(player, side, xHit, yHit, pair)) {
						return true;
					}
					if (stack.getItem() instanceof ItemToolCrowbar && behavior.onCrowbarClick(player, side, xHit, yHit, pair)) {
						return true;
					}
				}
				if (behavior.onRightClick(player, side, xHit, yHit)) {
					return true;
				}
				if (!player.isSneaking()) {
					if (behavior.openUIOnRightClick()) {
						BTG.displayModularUI(player, tile.getUI(player), tile, x, y, z);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public int getPistonPushReaction() {
		return 2;
	}

	@Override
	public void onBlockLeftClicked(World world, int x, int y, int z, EntityPlayer player, Side side, double xHit, double yHit) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityMachine) {
			TileEntityMachine tile = (TileEntityMachine) te;
			MachineBehavior behavior = tile.getBehavior();
			if (behavior != null) {
				behavior.onLeftClick(player, side, xHit, yHit, Catalyst.getBlockSurfaceClickPosition(world, player));
			}
		}
	}

	@Override
	protected TileEntity getNewBlockEntity() {
		return new TileEntityMachine(behaviorSupplier.apply(properties, this));
	}

	public MachineProperties getProperties() {
		return properties;
	}
}
