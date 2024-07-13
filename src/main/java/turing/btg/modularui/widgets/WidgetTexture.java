package turing.btg.modularui.widgets;

import org.lwjgl.opengl.GL11;
import turing.btg.modularui.GuiTexture;
import turing.btg.modularui.UIUtil;
import turing.btg.modularui.api.ITheme;
import turing.btg.modularui.api.IThemeable;
import turing.btg.modularui.api.RenderContext;
import turing.btg.modularui.api.ThemeLayer;
import turing.btg.util.RenderUtil;

public class WidgetTexture extends WidgetBase implements IThemeable {
	protected final GuiTexture texture;
	protected final ThemeLayer layer;

	public WidgetTexture(GuiTexture texture, ThemeLayer layer) {
		this.texture = texture;
		this.layer = layer;
		this.setSize(texture.getWidth(), texture.getHeight());
	}

	public WidgetTexture(GuiTexture texture) {
		this(texture, ThemeLayer.FOREGROUND);
	}

	@Override
	public ThemeLayer getThemeLayer() {
		return layer;
	}

	@Override
	public void applyTheme(ITheme theme) {
		int color = theme.getColorForLayer(getThemeLayer());
		float r = ((color >> 16) & 255) / 255F;
		float g = ((color >> 8) & 255) / 255F;
		float b = (color & 255) / 255F;
		GL11.glColor4f(r, g, b, 1.0F);
	}

	@Override
	public void draw(RenderContext context) {
		texture.bindTexture();
		RenderUtil.drawTexture(UIUtil.getXForRender(this), UIUtil.getYForRender(this), UIUtil.getWidthForRender(this), UIUtil.getHeightForRender(this), 1F, texture.getWidth());
	}
}
