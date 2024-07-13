package turing.btg.modularui.impl;

import turing.btg.modularui.api.ITheme;
import turing.btg.modularui.api.ThemeLayer;

public class Theme implements ITheme {
	protected String name;
	protected int backgroundColor;
	protected int foregroundColor;
	protected int accentColor1;
	protected int accentColor2;
	protected int textColor;

	public Theme(String name, int backgroundColor, int foregroundColor, int accentColor1, int accentColor2, int textColor) {
		this.name = name;
		this.backgroundColor = backgroundColor;
		this.foregroundColor = foregroundColor;
		this.accentColor1 = accentColor1;
		this.accentColor2 = accentColor2;
		this.textColor = textColor;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getColorForLayer(ThemeLayer layer) {
		switch (layer) {
			case BACKGROUND:
				return backgroundColor;
			case ACCENT1:
				return accentColor1;
			case ACCENT2:
				return accentColor2;
			case TEXT:
				return textColor;
			case FOREGROUND:
			default:
				return foregroundColor;
		}
	}
}
