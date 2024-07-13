package turing.btg.modularui.widgets;

import turing.btg.modularui.api.IConstraint;
import turing.btg.modularui.api.IWidget;

public class WidgetConstraintBase extends WidgetBlank implements IConstraint {
	@Override
	public int applyX(IWidget widget, int x) {
		return x;
	}

	@Override
	public int applyY(IWidget widget, int y) {
		return y;
	}

	@Override
	public int applyWidth(IWidget widget, int width) {
		return width;
	}

	@Override
	public int applyHeight(IWidget widget, int height) {
		return height;
	}
}
