package turing.btg.modularui;

import org.lwjgl.opengl.GL11;
import turing.btg.gui.GuiTextures;
import turing.btg.modularui.api.*;
import turing.btg.modularui.widgets.WidgetTexture;
import turing.btg.util.RenderUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModularUI implements IDrawable, IThemeable {
	public static final Map<Long, ModularUI> UI_CACHE = new HashMap<>();

	protected int width = 176;
	protected int height = 166;
	protected ITheme theme = Themes.DEFAULT;
	protected GuiTexture backgroundTexture = GuiTextures.BACKGROUND;
	protected List<IWidget> widgets = new ArrayList<>();

	public int invX = 8;
	public int invY = 84;
	public boolean hasPlayerInventory;

	public ModularUI() {

	}

	public ModularUI setSize(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}

	public ModularUI setTheme(ITheme theme) {
		this.theme = theme;
		return this;
	}

	public ModularUI addPlayerInventory(int x, int y) {
		invX = x;
		invY = y;
		hasPlayerInventory = true;
		for (int k = 0; k < 3; k++) {
			for (int j = 0; j < 9; j++) {
				addWidget(new WidgetTexture(GuiTextures.SLOT)
					.setSize(18, 18)
					.setPos(x - 1 + j * 18, y - 1 + k * 18)
				);
			}
		}
		for (int i = 0; i < 9; i++) {
			addWidget(new WidgetTexture(GuiTextures.SLOT)
				.setSize(18, 18)
				.setPos(x - 1 + i * 18, y - 1 + 58)
			);
		}
		return this;
	}

	public ModularUI addWidget(IWidget widget) {
		widget.setUI(this);
		widgets.add(widget);
		return this;
	}

	public List<IWidget> getWidgets() {
		return widgets;
	}

	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
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
		drawWidgets(context);
	}

	public void draw(int x, int y) {
		applyTheme(theme);
		backgroundTexture.bindTexture();
		RenderUtil.drawTexture(x, y, getWidth(), getHeight(), 1F, backgroundTexture.width);
		RenderUtil.resetGLColor();
	}

	protected void drawWidgets(RenderContext context) {
		for (IWidget widget : widgets) {
			drawWidget(context, widget);
		}
	}

	protected void drawWidget(RenderContext context, IWidget widget) {
		if (widget.shouldDraw()) {
			if (widget instanceof IThemeable) {
				((IThemeable) widget).applyTheme(theme);
			}
			widget.draw(context);
			RenderUtil.resetGLColor();
		}
		for (IWidget child : widget.getChildren()) {
			drawWidget(context, child);
		}
	}

	@Override
	public ThemeLayer getThemeLayer() {
		return ThemeLayer.BACKGROUND;
	}

	@Override
	public void applyTheme(ITheme theme) {
		int color = theme.getColorForLayer(getThemeLayer());
		float r = ((color >> 16) & 255) / 255F;
		float g = ((color >> 8) & 255) / 255F;
		float b = (color & 255) / 255F;
		GL11.glColor4f(r, g, b, 1.0F);
	}
}
