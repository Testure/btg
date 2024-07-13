package turing.btg.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.net.handler.NetHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import turing.btg.interfaces.INetClientHandler;
import turing.btg.modularui.ModularUI;
import turing.btg.modularui.api.IModularUITile;
import turing.btg.modularui.impl.ModularUIContainer;
import turing.btg.modularui.impl.ModularUIScreen;
import turing.btg.modularui.impl.PacketOpenModularUI;

@Mixin(value = NetClientHandler.class, remap = false)
public class NetClientHandlerMixin extends NetHandler implements INetClientHandler {
	@Shadow
	private WorldClient worldClient;

	@Shadow
	@Final
	private Minecraft mc;

	@Override
	public boolean isServerHandler() {
		return false;
	}

	@Override
	@Unique
	public void handleOpenModularUI(PacketOpenModularUI packet) {
		TileEntity tile = worldClient.getBlockTileEntity(packet.blockX, packet.blockY, packet.blockZ);
		if (tile instanceof IModularUITile) {
			ModularUI ui = ModularUI.UI_CACHE.get((long) packet.blockX + packet.blockY + packet.blockZ);
			if (ui == null) {
				ui = ((IModularUITile) tile).createUI();
				ModularUI.UI_CACHE.put((long) packet.blockX + packet.blockY + packet.blockZ, ui);
			}
			mc.displayGuiScreen(new ModularUIScreen(new ModularUIContainer(mc.thePlayer.inventory, (IModularUITile) tile, ui), ui, (IModularUITile) tile, mc.thePlayer.inventory));
		}
		mc.thePlayer.craftingInventory.windowId = packet.windowId;
	}
}
