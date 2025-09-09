package turing.btg.material;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.sound.BlockSound;
import net.minecraft.core.sound.BlockSounds;
import turing.btg.api.IOreStoneType;

import java.util.ArrayList;
import java.util.List;

public class OreStoneType implements IOreStoneType {
	public static final List<OreStoneType> TYPES = new ArrayList<>();

	public static final OreStoneType STONE = new OreStoneType("Stone", Blocks.STONE, Materials.STONE);
	public static final OreStoneType BASALT = new OreStoneType("Basalt", Blocks.BASALT, Materials.BASALT);
	public static final OreStoneType LIMESTONE = new OreStoneType("Limestone", Blocks.LIMESTONE, Materials.LIMESTONE);
	public static final OreStoneType GRANITE = new OreStoneType("Granite", Blocks.GRANITE, Materials.GRANITE);
	public static final OreStoneType GRAVEL = new OreStoneType("Gravel", Blocks.GRAVEL, BlockSounds.GRAVEL, true, Materials.FLINT);
	public static final OreStoneType SAND = new OreStoneType("Sand", Blocks.SAND, BlockSounds.SAND, true, Materials.SILICON_DIOXIDE);
	public static final OreStoneType NETHERRACK = new OreStoneType("Netherrack", Blocks.NETHERRACK, Materials.NETHERRACK);

	private final String name;
	private final Block<?> base;
	private final boolean gravity;
	private final BlockSound sound;
	private final Material material;

	public OreStoneType(String name, Block<?> base, BlockSound sound, boolean gravity, Material material) {
		this.name = name;
		this.base = base;
		this.sound = sound;
		this.gravity = gravity;
		this.material = material;
		TYPES.add(this);
	}

	public OreStoneType(String name, Block<?> base, BlockSound sound) {
		this(name, base, sound, false, null);
	}

	public OreStoneType(String name, Block<?> base, Material material) {
		this(name, base, BlockSounds.STONE, false, material);
	}

	public OreStoneType(String name, Block<?> base) {
		this(name, base, BlockSounds.STONE);
	}

	@Override
	public Block<?> getBaseBlock() {
		return base;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public BlockSound getBlockSound() {
		return sound;
	}

	@Override
	public boolean hasGravity() {
		return gravity;
	}

	@Override
	public Material getMaterial() {
		return material;
	}

	@Override
	public Tag<Block<?>> getNeededTool() {
		return hasGravity() ? BlockTags.MINEABLE_BY_SHOVEL : BlockTags.MINEABLE_BY_PICKAXE;
	}
}
