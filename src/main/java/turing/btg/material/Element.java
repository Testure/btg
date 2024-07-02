package turing.btg.material;

public class Element {
	public final String name;
	public final String symbol;
	public final long protons;
	public final long neutrons;

	public Element(String name, String symbol, long protons, long neutrons) {
		this.name = name;
		this.symbol = symbol;
		this.protons = protons;
		this.neutrons = neutrons;
	}
}
