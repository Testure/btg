package turing.btg.modularui.api;

public interface IConstraint {
	int applyX(IWidget widget, int x);

	int applyY(IWidget widget, int y);

	int applyWidth(IWidget widget, int width);

	int applyHeight(IWidget widget, int height);
}
