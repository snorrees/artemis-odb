package com.artemis.io;

import com.artemis.ArchetypeBuilder;
import com.artemis.Component;
import com.artemis.ComponentType;
import com.artemis.World;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;

public abstract class ComponentTypeSection extends Section {
	protected Bag<ComponentModel> componentTypes = new Bag<ComponentModel>();

	ComponentModel getType(int index) {
		return componentTypes.get(index);
	}

	@Override
	protected final void clear() {

	}

	@Override
	protected final void worldToSection(World world) {
		world.getComponentManager().read(new Callback() {
			@Override
			public void read(ImmutableBag<ComponentType> types) {
				for (int i = 0, s = types.size(); s > i; i++) {
					componentTypes.add(new ComponentModel(types.get(i)));
				}
			}
		});
	}

	@Override
	protected final void sectionToWorld(World world) {
		ArchetypeBuilder ab = new ArchetypeBuilder();
		for (ComponentModel component : componentTypes)
			ab.add(component.type);

		ab.build(world);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ComponentTypeSection that = (ComponentTypeSection) o;
		return componentTypes.equals(that.componentTypes);
	}

	@Override
	public int hashCode() {
		return componentTypes.hashCode();
	}

	public static class ComponentModel {
		public final Class<? extends Component> type;
		public final int componentIndex;

		public ComponentModel(ComponentType componentType) {
			type = componentType.getType();
			componentIndex = componentType.getIndex();
		}

		public ComponentModel(Class<?> type, int componentIndex) {
			this.type = (Class<? extends  Component>) type;
			this.componentIndex = componentIndex;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			ComponentModel that = (ComponentModel) o;
			return componentIndex == that.componentIndex && type == that.type;
		}

		@Override
		public int hashCode() {
			int result = type.hashCode();
			result = 31 * result + componentIndex;
			return result;
		}

		@Override
		public String toString() {
			return "ComponentModel{" +
					"componentIndex=" + componentIndex +
					", type=" + type +
					'}';
		}
	}

	public interface Callback {
		void read(ImmutableBag<ComponentType> componentTypes);
	}
}
