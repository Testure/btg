package turing.btg.material;

import turing.btg.util.SmallDigits;

public class MaterialStack {
	protected Material material;
	protected int count;

	public MaterialStack(Material material, int count) {
		this.material = material;
		this.count = count;
	}

	public MaterialStack(Material material) {
		this(material, 1);
	}

	public String format() {
		String formula = material.getChemicalFormula();

		StringBuilder builder = new StringBuilder(formula.length());
		if (formula.isEmpty()) {
			builder.append("?");
		} else if (material.componentList.size() > 1) {
			builder.append("(");
			builder.append(formula);
			builder.append(")");
		} else {
			builder.append(formula);
		}
		if (count > 1) {
			builder.append(SmallDigits.toSmallDownNumber(String.valueOf(count)));
		}
		return builder.toString();
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isEmpty() {
		return count <= 0 || material == null;
	}
}
