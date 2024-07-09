package turing.btg.item;

import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.CatalystFluids;
import sunsetsatellite.catalyst.fluids.api.IFluidInventory;
import sunsetsatellite.catalyst.fluids.api.IItemFluidContainer;
import sunsetsatellite.catalyst.fluids.impl.ItemInventoryFluid;
import sunsetsatellite.catalyst.fluids.impl.tiles.TileEntityFluidContainer;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import sunsetsatellite.catalyst.fluids.util.SlotFluid;
import turing.btg.BTG;
import turing.btg.BTGConfig;
import turing.btg.api.ColoredTexture;
import turing.btg.api.IColored;
import turing.btg.api.ICustomDescription;
import turing.btg.block.BlockFluidMaterial;
import turing.btg.material.Material;

import java.awt.*;

public class ItemBucketMaterial extends ItemBucket implements IItemFluidContainer, ICustomDescription, IColored {
	private final Material material;
	protected final int fluidId;

	public ItemBucketMaterial(String name, int id, int fluidId, Material material) {
		super(name, id, null);
		this.material = material;
		this.fluidId = fluidId;
		setContainerItem(Item.bucket);
		if (!material.hasFlag("fluid") && !material.hasFlag("gas")) {
			withTags(ItemTags.NOT_IN_CREATIVE_MENU);
		}
	}

	@Override
	public boolean hasContainerItem() {
		return true;
	}

	protected int getFluidId() {
		return this.fluidId;
	}

	protected BlockFluidMaterial getFluid() {
		return (BlockFluidMaterial) Block.getBlock(getFluidId());
	}

	public Material getMaterial() {
		return material;
	}

	@Override
	public String getTranslatedName(ItemStack itemstack) {
		return I18n.getInstance().translateKeyAndFormat("item.btg.bucket.name", I18n.getInstance().translateNameKey("material." + material.name));
	}

	@Override
	public String getTranslatedDescription(ItemStack stack) {
		String materialName = I18n.getInstance().translateNameKey("material." + material.name);
		return I18n.getInstance().translateKeyAndFormat("item.btg.bucket.desc", materialName.substring(0, 1).toUpperCase() + materialName.substring(1));
	}

	@Override
	public ColoredTexture[] getTextures(ItemStack itemStack) {
		int color = material.getColor();
		IconCoordinate index = BTG.getItemTexture("bucket_overlay");
		return new ColoredTexture[]{new ColoredTexture("minecraft:item/bucket", -1), new ColoredTexture(index, color)};
	}

	@Override
	public ItemStack onUseItem(ItemStack stack, World world, EntityPlayer player) {
		return Item.ingotGold.onUseItem(stack, world, player);
	}

	@Override
	public int getCapacity(ItemStack itemStack) {
		return 1000;
	}

	@Override
	public int getRemainingCapacity(ItemStack itemStack) {
		return 0;
	}

	@Override
	public boolean canFill(ItemStack itemStack) {
		return false;
	}

	@Override
	public boolean canDrain(ItemStack itemStack) {
		return true;
	}

	@Override
	public FluidStack getCurrentFluid(ItemStack itemStack) {
		return getFluid() != null ? new FluidStack(getFluid(), 1000) : null;
	}

	@Override
	public ItemStack fill(FluidStack fluidStack, ItemStack itemStack) {
		return null;
	}

	@Override
	public ItemStack fill(FluidStack fluidStack, ItemStack itemStack, TileEntityFluidContainer tileEntityFluidContainer) {
		return null;
	}

	@Override
	public ItemStack fill(FluidStack fluidStack, ItemStack itemStack, IFluidInventory iFluidInventory) {
		return null;
	}

	@Override
	public ItemStack fill(FluidStack fluidStack, ItemStack itemStack, TileEntityFluidContainer tileEntityFluidContainer, int i) {
		return null;
	}

	@Override
	public ItemStack fill(FluidStack fluidStack, ItemStack itemStack, ItemInventoryFluid itemInventoryFluid) {
		return null;
	}

	@Override
	public void drain(ItemStack stack, SlotFluid slot, TileEntityFluidContainer te) {
		int capacity = te.getRemainingCapacity(slot.slotNumber);
		if (capacity >= getCapacity(stack)) {
			if (slot.getFluidStack() == null) {
				slot.putStack(new FluidStack(getFluid(), 1000));
				stack.itemID = Item.bucket.id;
				stack.setMetadata(0);
			} else {
				FluidStack fluid = new FluidStack(getFluid(), 1000);
				if (slot.getFluidStack().isFluidEqual(fluid)) {
					slot.putStack(fluid);
					stack.itemID = Item.bucket.id;
					stack.setMetadata(0);
				}
			}
		}
	}

	@Override
	public void drain(ItemStack itemStack, SlotFluid slotFluid, IFluidInventory iFluidInventory) {

	}

	@Override
	public void drain(ItemStack stack, SlotFluid slot, ItemInventoryFluid inv) {
		int capacity = inv.getRemainingCapacity(slot.slotNumber);
		if (capacity >= getCapacity(stack)) {
			if (slot.getFluidStack() == null) {
				slot.putStack(new FluidStack(getFluid(), 1000));
				stack.itemID = Item.bucket.id;
				stack.setMetadata(0);
			} else {
				FluidStack fluid = new FluidStack(getFluid(), 1000);
				if (slot.getFluidStack().isFluidEqual(fluid)) {
					slot.putStack(fluid);
					stack.itemID = Item.bucket.id;
					stack.setMetadata(0);
				}
			}
		}
	}
}
