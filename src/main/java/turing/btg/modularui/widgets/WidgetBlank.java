package turing.btg.modularui.widgets;

import turing.btg.modularui.api.RenderContext;

public class WidgetBlank extends WidgetBase {
	@Override
	public boolean shouldDraw() {
		return false;
	}

	@Override
	public void draw(RenderContext context) {}
}
