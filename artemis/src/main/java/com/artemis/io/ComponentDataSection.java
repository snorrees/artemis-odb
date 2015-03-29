package com.artemis.io;

import com.artemis.*;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Field;

public abstract class ComponentDataSection extends Section {
//	protected Bag<ComponentModel> componentTypes = new Bag<ComponentModel>();

	protected ComponentTypeSection componentTypeSection;
	protected EntitySection entitySection;

	@Override
	protected final void clear() {

	}

	@Override
	protected final void worldToSection(World world) {
		Bag<ComponentTypeSection.ComponentModel> types = componentTypeSection.componentTypes;
		for (ComponentTypeSection.ComponentModel model : types) {
			System.out.println(model);
		}
	}

	@Override
	protected final void sectionToWorld(World world) {
	}

	static class ComponentReflector {
		private Bag<FieldReflector> fields = new Bag<FieldReflector>();

		public ComponentReflector(Class<? extends  Component> component) {
			Field[] fields = ClassReflection.getDeclaredFields(component);
			for (Field field : fields) {
				field.get()
			}
		}

		static class FieldReflector {
			Field field;

		}
	}
}
