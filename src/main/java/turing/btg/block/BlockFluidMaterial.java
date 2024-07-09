package turing.btg.block;

import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;

public class BlockFluidMaterial extends BlockFluid {
	private final turing.btg.material.Material material;

	public BlockFluidMaterial(String key, int id, turing.btg.material.Material material) {
		super(key, id, Material.water);
		this.material = material;
	}

	public int getColor() {
		return material.getColor();
	}

	public int getMaterialId() {
		return material.id;
	}

	@Override
	public String getLanguageKey(int meta) {
		return "material." + material.name;
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return false;
	}
}
