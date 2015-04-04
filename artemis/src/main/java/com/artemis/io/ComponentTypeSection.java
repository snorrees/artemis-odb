package com.artemis.io;

import com.artemis.ArchetypeBuilder;
import com.artemis.Component;
import com.artemis.ComponentType;
import com.artemis.World;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Field;
import com.artemis.utils.reflect.Method;
import com.artemis.utils.reflect.ReflectionException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

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
		final Class<? extends Component> type;
		public final int componentIndex;
		public final Bag<FieldReflector> fields;

		public ComponentModel(ComponentType componentType) {
			type = componentType.getType();
			componentIndex = componentType.getIndex();
			fields = toReflectors(type);
		}

		public ComponentModel(Class<?> type, int componentIndex) {
			this.type = (Class<? extends  Component>) type;
			this.componentIndex = componentIndex;
			fields = toReflectors(type);
		}

		private static Bag<FieldReflector> toReflectors(Class<?> type) {
			Bag<FieldReflector> fields = new Bag<FieldReflector>();
			for (Field field : ClassReflection.getDeclaredFields(type)) {
				fields.add(new FieldReflector(field.getName(), field));
			}

			fields.sort(new FieldReflectorComparator());
			return fields;
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

		void serialize(Component source, DataOutputStream target) throws ReflectionException {
			for (FieldReflector field : fields) {
				field.write(source, target);
			}
		}

		void deserialize(Component source, DataOutputStream target) throws ReflectionException {
			sdfakgjdkls;fj dsfjklgjdsfkl;gjldksfjgldsfgjd;sf l
			for (FieldReflector field : fields) {
				field.write(source, target);
			}
		}

		private static class FieldReflectorComparator implements Comparator<FieldReflector> {
			@Override
			public int compare(FieldReflector o1, FieldReflector o2) {
				return o1.name.compareTo(o2.name);
			}
		}
	}

	static class FieldReflector {
		public final String name;
		public final Field field;
		public final Method method;

		private static final Map<Class<?>, Method> methodMappings = new HashMap<Class<?>, Method>();
		static {
			try {
				registerMethod(boolean.class);
				registerMethod(String.class, "UTF");
				registerMethod(float.class);
				registerMethod(int.class);
				registerMethod(double.class);
				registerMethod(long.class);

				methodMappings.put(byte.class,
					ClassReflection.getMethod(DataOutputStream.class, "writeByte", int.class));
				methodMappings.put(short.class,
					ClassReflection.getMethod(DataOutputStream.class, "writeShort", int.class));
			} catch (ReflectionException e) {
				e.printStackTrace();
			}
		}

		private static void registerMethod(Class<?> type) throws ReflectionException {
			String name = type.getName();
			registerMethod(type, (name.toUpperCase().charAt(0) + name.substring(1)));
		}

		private static void registerMethod(Class<?> type, String name) throws ReflectionException {
			methodMappings.put(type,
				ClassReflection.getMethod(DataOutputStream.class, "write" + name, type));
		}

		FieldReflector(String name, Field field) {
			this.name = name;
			this.field = field;
			method = methodMappings.get(field.getType());
		}

		void write(Object instance, Object value) throws ReflectionException {
			field.set(instance, value);
		}

		void write(DataInputStream out, Object source) throws ReflectionException {
			method.invoke(out, source);
		}
	}

	public interface Callback {
		void read(ImmutableBag<ComponentType> componentTypes);
	}
}
