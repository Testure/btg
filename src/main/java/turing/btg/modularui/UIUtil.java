package turing.btg.modularui;

import turing.btg.modularui.api.IConstraint;
import turing.btg.modularui.api.IWidget;

public class UIUtil {
	public static int getX(IWidget widget) {
		int x = widget.getX();
		IWidget parent = widget.getParent();
		if (parent != null) {
			x += getX(parent);
		}
		return x;
	}

	public static int getY(IWidget widget) {
		int y = widget.getY();
		IWidget parent = widget.getParent();
		if (parent != null) {
			y += getY(parent);
		}
		return y;
	}

	public static int getBaseX(IWidget widget, int x) {
		IWidget parent = widget.getParent();
		if (parent != null) {
			x -= getX(parent);
		}
		return x;
	}

	public static int getBaseY(IWidget widget, int y) {
		IWidget parent = widget.getParent();
		if (parent != null) {
			y -= getY(parent);
		}
		return y;
	}

	public static int getWidth(IWidget widget) {
		int width = widget.getWidth();
		IWidget parent = widget.getParent();
		if (parent != null) {
			width += getWidth(parent);
		}
		return width;
	}

	public static int getHeight(IWidget widget) {
		int height = widget.getHeight();
		IWidget parent = widget.getParent();
		if (parent != null) {
			height += getHeight(parent);
		}
		return height;
	}

	public static int getXForRender(IWidget widget) {
		int x = getX(widget);
		IWidget parent = widget.getParent();
		ModularUI ui = widget.getUI();
		if (ui != null) {
			for (IWidget child : ui.getWidgets()) {
				if (child instanceof IConstraint) {
					x = ((IConstraint) child).applyX(widget, x);
				}
			}
		}
		if (parent != null) {
			x += parent.getX();
			for (IWidget child : parent.getChildren()) {
				if (child instanceof IConstraint) {
					x = ((IConstraint) child).applyX(widget, x);
				}
			}
		}
		return x;
	}

	public static int getYForRender(IWidget widget) {
		int y = getY(widget);
		IWidget parent = widget.getParent();
		ModularUI ui = widget.getUI();
		if (ui != null) {
			for (IWidget child : ui.getWidgets()) {
				if (child instanceof IConstraint) {
					y = ((IConstraint) child).applyY(widget, y);
				}
			}
		}
		if (parent != null) {
			for (IWidget child : parent.getChildren()) {
				if (child instanceof IConstraint) {
					y = ((IConstraint) child).applyY(widget, y);
				}
			}
		}
		return y;
	}

	public static int getWidthForRender(IWidget widget) {
		int width = widget.getWidth();
		IWidget parent = widget.getParent();
		ModularUI ui = widget.getUI();
		if (ui != null) {
			for (IWidget child : ui.getWidgets()) {
				if (child instanceof IConstraint) {
					width = ((IConstraint) child).applyWidth(widget, width);
				}
			}
		}
		if (parent != null) {
			for (IWidget child : parent.getChildren()) {
				if (child instanceof IConstraint) {
					width = ((IConstraint) child).applyWidth(widget, width);
				}
			}
		}
		return width;
	}

	public static int getHeightForRender(IWidget widget) {
		int height = widget.getHeight();
		IWidget parent = widget.getParent();
		ModularUI ui = widget.getUI();
		if (ui != null) {
			for (IWidget child : ui.getWidgets()) {
				if (child instanceof IConstraint) {
					height = ((IConstraint) child).applyHeight(widget, height);
				}
			}
		}
		if (parent != null) {
			for (IWidget child : parent.getChildren()) {
				if (child instanceof IConstraint) {
					height = ((IConstraint) child).applyHeight(widget, height);
				}
			}
		}
		return height;
	}
}
