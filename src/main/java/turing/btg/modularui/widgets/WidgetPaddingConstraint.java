package turing.btg.modularui.widgets;

import turing.btg.modularui.UIUtil;
import turing.btg.modularui.api.IWidget;

public class WidgetPaddingConstraint extends WidgetConstraintBase {
	protected final int xPadding;
	protected final int yPadding;

	public WidgetPaddingConstraint(int xPadding, int yPadding) {
		this.xPadding = xPadding;
		this.yPadding = yPadding;
	}

	protected int getEdgeX() {
		IWidget parent = getParent();
		if (parent != null)
			return UIUtil.getX(parent) + parent.getWidth();
		return ui.getWidth();
	}

	protected int getEdgeY() {
		IWidget parent = getParent();
		if (parent != null)
			return UIUtil.getY(parent) + parent.getHeight();
		return ui.getHeight();
	}

	@Override
	public int applyX(IWidget widget, int x) {
		int base = UIUtil.getBaseX(widget, x);
		if (base < xPadding) {
			x += Math.max(xPadding - base, 0);
		}
		if (base + getWidth() > getEdgeX() - xPadding) {
			x -= Math.max((getEdgeX() + xPadding) - (base + getWidth()), 0);
		}
		return x;
	}

	@Override
	public int applyY(IWidget widget, int y) {
		int base = UIUtil.getBaseY(widget, y);
		if (base < yPadding) {
			y += Math.max(yPadding - base, 0);
		}
		if (base + getHeight() > getEdgeY() - yPadding) {
			y -= Math.max((getEdgeY() + yPadding) - (base + getHeight()), 0);
		}
		return y;
	}
}
