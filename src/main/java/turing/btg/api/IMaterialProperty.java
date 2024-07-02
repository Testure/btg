package turing.btg.api;

import turing.btg.material.Material;
import turing.btg.material.MaterialItemType;

import java.util.Collection;

public interface IMaterialProperty {
	boolean hasItemType(MaterialItemType type);

	default boolean hasOre() {
		return false;
	}

	default boolean validateMaterial(Material material) {
		return true;
	}
}
