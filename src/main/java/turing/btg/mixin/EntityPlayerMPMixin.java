package turing.btg.mixin;

import net.minecraft.core.crafting.ICrafting;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import turing.btg.interfaces.IEntityPlayerMP;
import turing.btg.modularui.api.IModularUITile;
import turing.btg.modularui.impl.PacketOpenModularUI;

@Mixin(value = EntityPlayerMP.class, remap = false)
public abstract class EntityPlayerMPMixin extends EntityPlayer implements IEntityPlayerMP, ICrafting {
	@Shadow
	protected abstract void getNextWindowId();

	@Shadow
	public NetServerHandler playerNetServerHandler;

	@Shadow
	private int currentWindowId;

	public EntityPlayerMPMixin(World world) {
		super(world);
	}

	@Override
	public void displayModularUI(Container container, IModularUITile tile, int x, int y, int z) {
		getNextWindowId();
		playerNetServerHandler.sendPacket(new PacketOpenModularUI(currentWindowId, tile.getInvName(), x, y, z));
		craftingInventory = container;
		craftingInventory.windowId = currentWindowId;
		craftingInventory.onContainerInit((EntityPlayerMP)((Object)this));
	}
}
