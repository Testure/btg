package turing.btg.modularui.widgets;

import turing.btg.modularui.GuiTexture;
import turing.btg.modularui.api.IModularUITile;
import turing.btg.modularui.api.RenderContext;
import turing.btg.util.RenderUtil;

public abstract class WidgetSlot<T> extends WidgetTexture {
	protected int id;

	public WidgetSlot(int id, GuiTexture texture) {
		super(texture);
		this.id = id;
	}

	public int getSlotId() {
		return id;
	}

	public abstract Object createContainerSlot(IModularUITile tile);

	public boolean canInsertIntoSlot(T item) {
		return true;
	}
}
