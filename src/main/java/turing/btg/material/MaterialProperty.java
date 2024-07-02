package turing.btg.material;

import turing.btg.api.IMaterialProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class MaterialProperty implements IMaterialProperty {
	public final String name;
	private final Collection<MaterialItemType> items;
	private final boolean hasOre;
	private final Function<Material, Boolean> validate;

	public MaterialProperty(String name, Function<Material, Boolean> validate, Collection<MaterialItemType> items, boolean hasOre) {
		this.name = name;
		this.validate = validate;
		this.items = items;
		this.hasOre = hasOre;
	}

	@Override
	public boolean hasItemType(MaterialItemType type) {
		return items.contains(type);
	}

	@Override
	public boolean hasOre() {
		return hasOre;
	}

	@Override
	public boolean validateMaterial(Material material) {
		return validate.apply(material);
	}

	public static class MaterialPropertyBuilder {
		private final String name;
		private boolean hasOre = false;
		private final List<MaterialItemType> items = new ArrayList<>();
		private final List<IMaterialProperty> incompatibleWith = new ArrayList<>();
		private final List<IMaterialProperty[]> requires = new ArrayList<>();

		public MaterialPropertyBuilder(String name) {
			this.name = name;
		}

		public MaterialPropertyBuilder setOre(boolean has) {
			this.hasOre = has;
			return this;
		}

		public MaterialPropertyBuilder addItem(MaterialItemType... items) {
			this.items.addAll(Arrays.asList(items));
			return this;
		}

		public MaterialPropertyBuilder incompatibleWith(IMaterialProperty... props) {
			this.incompatibleWith.addAll(Arrays.asList(props));
			return this;
		}

		public MaterialPropertyBuilder requires(IMaterialProperty property) {
			this.requires.add(new IMaterialProperty[]{property});
			return this;
		}

		public MaterialPropertyBuilder requiresOr(IMaterialProperty... options) {
			this.requires.add(options);
			return this;
		}

		public MaterialProperty build() {
			return new MaterialProperty(this.name, material -> {
				if (material.properties.stream().anyMatch(incompatibleWith::contains)) return false;
				for (IMaterialProperty[] properties : requires) {
					boolean bool = false;
					for (IMaterialProperty prop : properties) {
						if (material.properties.contains(prop)) {
							bool = true;
							break;
						}
					}
					if (!bool) return false;
				}
				return true;
			}, items, hasOre);
		}
	}
}
