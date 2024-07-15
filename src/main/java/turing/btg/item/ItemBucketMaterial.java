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
import turing.btg.BTG;
import turing.btg.api.ColoredTexture;
import turing.btg.api.IColored;
import turing.btg.api.ICustomDescription;
import turing.btg.block.BlockFluidMaterial;
import turing.btg.material.Material;

public class ItemBucketMaterial extends ItemBucket implements ICustomDescription, IColored {
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
}
