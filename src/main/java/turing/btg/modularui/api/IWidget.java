package turing.btg.modularui.api;

import turing.btg.modularui.ModularUI;

import javax.annotation.Nullable;
import java.util.List;

public interface IWidget extends IDrawable {
	List<IWidget> getChildren();

	@Nullable
	IWidget getParent();

	IWidget addChild(IWidget child);

	void setParent(IWidget parent);

	boolean shouldDraw();

	void setUI(ModularUI ui);

	ModularUI getUI();
}
