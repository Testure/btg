package turing.btg.modularui.impl;

import net.minecraft.client.gui.container.ScreenContainerAbstract;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import turing.btg.modularui.ModularUI;
import turing.btg.modularui.api.IModularUITile;
import turing.btg.modularui.api.RenderContext;

public class ModularUIScreen extends ScreenContainerAbstract {
	protected final Container inventory;
	protected final ModularUI ui;
	protected final RenderContext context;

	public ModularUIScreen(ModularUIContainer menu, ModularUI ui, IModularUITile tile, ContainerInventory inventory) {
		super(menu);
		this.inventory = inventory;
		this.ui = ui;
		this.context = new RenderContext(tile);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		ui.draw((this.width - this.xSize) / 2, (this.height - this.ySize) / 2);
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		ui.draw(context);
	}
}
