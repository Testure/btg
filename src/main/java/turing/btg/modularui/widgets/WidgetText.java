package turing.btg.modularui.widgets;

import net.minecraft.client.Minecraft;
import turing.btg.modularui.UIUtil;
import turing.btg.modularui.api.ITheme;
import turing.btg.modularui.api.IThemeable;
import turing.btg.modularui.api.RenderContext;
import turing.btg.modularui.api.ThemeLayer;

public class WidgetText extends WidgetBase implements IThemeable {
	protected final String text;
	protected final boolean shadow;
	protected int color = -1;

	public WidgetText(String text, boolean shadow) {
		this.text = text;
		this.shadow = shadow;
	}

	public WidgetText(String text) {
		this(text, true);
	}

	@Override
	public ThemeLayer getThemeLayer() {
		return ThemeLayer.TEXT;
	}

	@Override
	public void applyTheme(ITheme theme) {
		color = theme.getColorForLayer(getThemeLayer());
	}

	@Override
	public void draw(RenderContext context) {
		Minecraft.getMinecraft(this).fontRenderer.drawString(text, UIUtil.getXForRender(this), UIUtil.getYForRender(this), color, shadow);
	}
}
