package turing.btg.modularui.impl;

import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.player.inventory.InventoryPlayer;
import turing.btg.modularui.ModularUI;
import turing.btg.modularui.api.IModularUITile;
import turing.btg.modularui.api.RenderContext;

public class ModularUIScreen extends GuiContainer {
	protected final InventoryPlayer inventory;
	protected final ModularUI ui;
	protected final RenderContext context;

	public ModularUIScreen(ModularUIContainer container, ModularUI ui, IModularUITile tile, InventoryPlayer inventory) {
		super(container);
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
