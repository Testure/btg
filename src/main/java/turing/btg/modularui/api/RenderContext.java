package turing.btg.modularui.api;

public class RenderContext {
	protected final IModularUITile tile;

	public RenderContext(IModularUITile tile) {
		this.tile = tile;
	}

	public IModularUITile getTile() {
		return tile;
	}
}
