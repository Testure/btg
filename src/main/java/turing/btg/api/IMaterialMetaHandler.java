package turing.btg.api;

import turing.btg.material.Materials;

public interface IMaterialMetaHandler {
	int getHandlerID();

	default int getMaterialIDForMeta(int meta) {
		return meta + (Materials.iMETA_LIMIT * getHandlerID());
	}

	default int getMetaForMaterialID(int material) {
		return material - (Materials.iMETA_LIMIT * getHandlerID());
	}
}
