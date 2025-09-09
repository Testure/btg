package turing.btg.entity;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.BlockLogicSand;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import turing.btg.api.IOreStoneType;

public class EntityFallingOre extends Entity {
	public int blockId;
	public int blockMeta;
	public int fallTime;
	public boolean hasRemovedBlock = false;
	public final IOreStoneType stoneType;

	public EntityFallingOre(World world, int blockId, int meta, IOreStoneType stoneType) {
		super(world);
		this.blockId = blockId;
		this.blockMeta = meta;
		this.fallTime = 0;
		this.stoneType = stoneType;
	}

	public EntityFallingOre(World world, double x, double y, double z, int blockId, int meta, IOreStoneType stoneType) {
		this(world, blockId, meta, stoneType);
		this.blocksBuilding = true;
		this.setSize(0.98F, 0.98F);
		this.heightOffset = this.bbHeight / 2.0F;
		this.setPos(x, y, z);
		this.xd = 0D;
		this.yd = 0D;
		this.zd = 0D;
		this.xo = x;
		this.yo = y;
		this.zo = z;
	}

	@Override
	protected boolean makeStepSound() {
		return false;
	}

	@Override
	public boolean isPickable() {
		return !this.removed;
	}

	@Override
	public void tick() {
		if (this.blockId == 0) {
			this.remove();
			return;
		}
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		++this.fallTime;
		this.yd -= 0.04F;
		this.move(this.xd, this.yd, this.zd);
		this.xd *= 0.98D;
		this.yd *= 0.98D;
		this.zd *= 0.98D;
		int fx = MathHelper.floor(this.x);
		int fy = MathHelper.floor(this.y);
		int fz = MathHelper.floor(this.z);
		if (this.world.getBlockId(fx, fy, fz) == this.blockId && this.world.getBlockMetadata(fx, fy, fz) == this.blockMeta) {
			this.world.setBlockWithNotify(fx, fy, fz, 0);
			this.hasRemovedBlock = true;
		}
		if (this.onGround) {
			this.xd *= 0.7D;
			this.zd *= 0.7D;
			this.yd *= -0.5D;
			this.remove();
			if (!(this.world.canBlockBePlacedAt(this.blockId, fx, fy, fz, true, Side.TOP) && !BlockLogicSand.canFallBelow(this.world, fx, fy - 1, fz) && this.world.setBlockAndMetadataWithNotify(fx, fy, fz, this.blockId, this.blockMeta) || this.world.isClientSide || !this.hasRemovedBlock)) {
				this.dropItem(new ItemStack(this.blockId, 1, this.blockMeta), 0F);
			}
		} else if (this.fallTime > 100 && !this.world.isClientSide) {
			if (this.hasRemovedBlock) {
				this.dropItem(new ItemStack(this.blockId, 1, this.blockMeta), 0F);
			}
			this.remove();
		}
	}

	@Override
	public float getShadowHeightOffs() {
		return 0F;
	}

	@Override
	protected void defineSynchedData() {

	}

	@Override
	public boolean showBoundingBoxOnHover() {
		return true;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		this.blockId = tag.getShort("id") & 0x3FFF;
		this.blockMeta = tag.getShort("meta") & 0x3FFF;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		tag.putShort("id", (short) this.blockId);
		tag.putShort("meta", (short) this.blockMeta);
	}
}
