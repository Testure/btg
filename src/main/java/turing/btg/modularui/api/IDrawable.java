package turing.btg.modularui.api;

public interface IDrawable {
	int getX();

	int getY();

	int getWidth();

	int getHeight();

	void draw(RenderContext context);
}
