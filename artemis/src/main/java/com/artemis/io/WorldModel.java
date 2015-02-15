package com.artemis.io;

import com.artemis.utils.Bag;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Field;
import com.artemis.utils.reflect.ReflectionException;

import java.util.HashMap;
import java.util.Map;

public class WorldModel {
	Bag<Section> sections;

	private WorldModel() {
		sections = new Bag<Section>();
	}

	public static WorldModel.Builder create() {
		return new Builder();
	}

	public static class Builder {
		private Bag<Section> sections = new Bag<Section>();
		private Map<Class<? extends Section>, Section> sectionMap =
				new HashMap<Class<? extends Section>, Section>();

		Builder add(Class<? extends Section> section) {
			try {
				Section s = section.newInstance();
				sections.add(s);
				sectionMap.put(section, s);
				return this;
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

		public WorldModel build() {
			WorldModel wm = new WorldModel();
			for (int i = 0, s = sections.size(); s > i; i++) {
				Section section = sections.get(i);
				wm.sections.add(section);
				injectSections(section);
			}

			for (int i = 0, s = sections.size(); s > i; i++) {
				sections.get(i).initialize();
			}

			return wm;
		}

		private void injectSections(Section section) {
			Field[] fields = ClassReflection.getDeclaredFields(section.getClass());
			for (int i = 0; fields.length > i; i++) {
				Field f = fields[i];
				if (ClassReflection.isAssignableFrom(Section.class, f.getType())) {
					inject(f, section);
				}
			}
		}

		private void inject(Field f, Section section) {
			try {
				Section sectionToInject = sectionMap.get(f.getType());
				f.set(section, sectionToInject);
			} catch (ReflectionException e) {
				e.printStackTrace();
			}
		}
	}
}
