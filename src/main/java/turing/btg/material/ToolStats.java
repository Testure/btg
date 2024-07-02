package turing.btg.material;

import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.MathHelper;
import turing.enchantmentlib.api.EnchantmentData;
import turing.enchantmentlib.api.IEnchant;

import java.util.ArrayList;
import java.util.List;

public class ToolStats {
	private final ToolMaterial toolMaterial;
	private final List<EnchantmentData> enchantments;

	public ToolStats(ToolMaterial toolMaterial, List<EnchantmentData> enchantments) {
		this.toolMaterial = toolMaterial;
		this.enchantments = enchantments != null ? enchantments : new ArrayList<>();
	}

	public ToolStats(ToolMaterial toolMaterial) {
		this(toolMaterial, null);
	}

	public ToolMaterial getToolMaterial() {
		return toolMaterial;
	}

	public List<EnchantmentData> getEnchantments() {
		return enchantments;
	}

	public static class Builder {
		private final int durability;
		private int miningLevel = 1;
		private int damage = 0;
		private final float efficiency;
		private boolean silkTouch;
		private int hitDelay = 5;
		private final List<EnchantmentData> enchantments = new ArrayList<>();

		public Builder(int durability, float efficiency) {
			this.durability = durability;
			this.efficiency = efficiency;
		}

		public Builder setHitDelay(int delay) {
			this.hitDelay = delay;
			return this;
		}

		public Builder setMiningLevel(int level) {
			this.miningLevel = level;
			if (this.damage <= 0) this.damage = level;
			return this;
		}

		public Builder setDamage(int damage) {
			this.damage = damage;
			return this;
		}

		public Builder setSilkTouch() {
			this.silkTouch = true;
			return this;
		}

		public Builder withEnchant(IEnchant enchant, int level) {
			this.enchantments.add(new EnchantmentData(enchant, level));
			return this;
		}

		public ToolStats build() {
			ToolMaterial toolMaterial = new ToolMaterial()
				.setMiningLevel(miningLevel)
				.setDurability(durability)
				.setDamage(damage)
				.setBlockHitDelay(hitDelay)
				.setSilkTouch(silkTouch)
				.setEfficiency(efficiency, MathHelper.floor_float((float) Math.pow(efficiency, 1.35F)));
			return new ToolStats(toolMaterial, enchantments);
		}
	}
}
