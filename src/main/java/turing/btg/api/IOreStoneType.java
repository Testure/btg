package turing.btg.api;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.sound.BlockSound;
import net.minecraft.core.sound.BlockSounds;
import turing.btg.material.Material;
import turing.btg.material.Materials;

public interface IOreStoneType {
	Block getBaseBlock();

	String getName();

	default BlockSound getBlockSound() {
		return BlockSounds.STONE;
	}

	default boolean hasGravity() {
		return false;
	}

	default Tag<Block<?>> getNeededTool() {
		return BlockTags.MINEABLE_BY_PICKAXE;
	}

	default Material getMaterial() {
		return null;
	}
}
