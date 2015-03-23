package com.artemis.io;

import com.artemis.World;
import com.artemis.utils.Bag;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Field;
import com.artemis.utils.reflect.ReflectionException;

import java.util.HashMap;
import java.util.Map;

public class WorldModel {
	Bag<Section> sections;

	private Map<Class<? extends Section>, Section> sectionMap =
			new HashMap<Class<? extends Section>, Section>();
	private EntitySection entitySection;
	private ArchetypeSection archetypeSection;
	private ComponentTypeSection componentSection;

	private WorldModel() {
		sections = new Bag<Section>();
	}

	public static WorldModel.Builder create() {
		return new Builder();
	}

	public void worldToModel(World world) {
		for (int i = 0, s = sections.size(); s > i; i++) {
			sections.get(i).worldToSection(world);
		}
	}

	public void modelToWorld(World world) {
		for (int i = 0, s = sections.size(); s > i; i++) {
			sections.get(i).sectionToWorld(world);
		}
	}

	public void clear() {
		for (int i = 0, s = sections.size(); s > i; i++) {
			sections.get(i).clear();
		}
	}

	public <T extends Section> T getSection(Class<T> section) {
		return (T) sectionMap.get(sectionId(section));
	}

	private static Class<? extends Section> sectionId(Class<? extends Section> section) {
		while (Section.class != section.getSuperclass()) {
			section = (Class<? extends Section>) section.getSuperclass();
		}

		return section;
	}

	public static class Builder {
		private Bag<Section> sections = new Bag<Section>();
		private Map<Class<? extends Section>, Section> sectionMap =
				new HashMap<Class<? extends Section>, Section>();

		Builder add(Class<? extends Section> section) {
			try {
				Section s = section.newInstance();
				sections.add(s);
				sectionMap.put(sectionId(section), s);
				return this;
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

		public WorldModel build() {
			WorldModel wm = new WorldModel();
			wm.sectionMap = sectionMap;
			for (int i = 0, s = sections.size(); s > i; i++) {
				Section section = sections.get(i);
				wm.sections.add(section);
				injectSection(section);
			}

			for (int i = 0, s = sections.size(); s > i; i++) {
				sections.get(i).initialize();
			}


			return wm;
		}

		private void injectSection(Section section) {
			Class<?> klazz = section.getClass();
			while (klazz != Section.class) {
				Field[] fields = ClassReflection.getDeclaredFields(klazz);
				for (int i = 0; fields.length > i; i++) {
					Field f = fields[i];
					if (ClassReflection.isAssignableFrom(Section.class, f.getType())) {
						inject(f, section);
					}
				}
				klazz = klazz.getSuperclass();
			}
		}

		private void inject(Field f, Section section) {
			try {
				Section sectionToInject = sectionMap.get(sectionId(f.getType()));
				f.set(section, sectionToInject);
			} catch (ReflectionException e) {
				e.printStackTrace();
			}
		}
	}
}
