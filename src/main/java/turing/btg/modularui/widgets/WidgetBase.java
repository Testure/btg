package turing.btg.modularui.widgets;

import org.jetbrains.annotations.Nullable;
import turing.btg.gui.GuiTextures;
import turing.btg.modularui.ModularUI;
import turing.btg.modularui.UIUtil;
import turing.btg.modularui.api.IWidget;
import turing.btg.modularui.api.RenderContext;
import turing.btg.util.RenderUtil;

import java.util.ArrayList;
import java.util.List;

public class WidgetBase implements IWidget {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected ModularUI ui;
	protected IWidget parent;
	protected List<IWidget> children = new ArrayList<>();

	public WidgetBase setPos(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public WidgetBase setSize(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}

	@Override
	public ModularUI getUI() {
		return ui;
	}

	@Override
	public List<IWidget> getChildren() {
		return children;
	}

	@Nullable
	@Override
	public IWidget getParent() {
		return parent;
	}

	@Override
	public IWidget addChild(IWidget child) {
		child.setParent(this);
		children.add(child);
		return this;
	}

	@Override
	public void setParent(IWidget parent) {
		this.parent = parent;
	}

	@Override
	public boolean shouldDraw() {
		return true;
	}

	@Override
	public void setUI(ModularUI ui) {
		this.ui = ui;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void draw(RenderContext context) {
		GuiTextures.BACKGROUND.bindTexture();
		RenderUtil.drawTexture(UIUtil.getXForRender(this), UIUtil.getYForRender(this), UIUtil.getWidthForRender(this), UIUtil.getHeightForRender(this), 1F, 176);
	}
}
