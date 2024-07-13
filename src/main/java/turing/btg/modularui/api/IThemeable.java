package turing.btg.modularui.api;

public interface IThemeable {
	ThemeLayer getThemeLayer();

	void applyTheme(ITheme theme);
}
