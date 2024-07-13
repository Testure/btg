package turing.btg.modularui.widgets;

import turing.btg.modularui.GuiTexture;
import turing.btg.modularui.UIUtil;
import turing.btg.modularui.api.ProgressMoveType;
import turing.btg.modularui.api.RenderContext;
import turing.btg.modularui.api.ThemeLayer;
import turing.btg.util.RenderUtil;

import java.util.function.IntSupplier;

public class WidgetProgress extends WidgetTexture {
	protected final ProgressMoveType moveType;
	protected final IntSupplier progressSupplier;
	protected final IntSupplier goalSupplier;

	public WidgetProgress(GuiTexture texture, ProgressMoveType moveType, IntSupplier progressSupplier, IntSupplier goalSupplier) {
		super(texture, ThemeLayer.FOREGROUND);
		this.moveType = moveType;
		this.progressSupplier = progressSupplier;
		this.goalSupplier = goalSupplier;
	}

	public ProgressMoveType getMoveType() {
		return moveType;
	}

	protected void drawBase(int x, int y, int width, int height) {
		RenderUtil.drawTexture(x, y, 0, 0, width, height / 2, 1F, texture.getWidth(), texture.getHeight());
	}

	protected void drawProgress(int progress, int goal, int x, int y, int width, int height) {
		float scale = (float) progress / goal;
		switch (getMoveType()) {
			//TODO: Circular progress bar rendering
			case CIRCULAR:
			case HORIZONTAL:
				RenderUtil.drawTexture(x, y, 0, texture.getHeight() / 2, (int) (width * scale), height / 2, 1F, texture.getWidth(), texture.getHeight());
				break;
			case VERTICAL:
				RenderUtil.drawTexture(x, y, 0, texture.getHeight() / 2, width, (int) ((height / 2) * scale), 1F, texture.getWidth(), texture.getHeight());
				break;
		}
	}

	@Override
	public void draw(RenderContext context) {
		texture.bindTexture();
		int x = UIUtil.getXForRender(this);
		int y = UIUtil.getYForRender(this);
		int width = UIUtil.getWidthForRender(this);
		int height = UIUtil.getHeightForRender(this);

		drawBase(x, y, width, height);
		RenderUtil.resetGLColor();

		int progress = progressSupplier.getAsInt();
		int goal = goalSupplier.getAsInt();
		if (goal > 0) {
			if (progress > goal) {
				progress = goal;
			}
			drawProgress(progress, goal, x, y, width, height);
		}
	}
}
