package com.artemis.io;

import com.artemis.*;
import com.artemis.utils.Bag;
import com.artemis.utils.reflect.ReflectionException;

import java.io.*;

public abstract class ComponentDataSection extends Section {
	private final Bag<ComponentData> modelData;

	protected ComponentTypeSection componentTypeSection;
	protected EntitySection entitySection;

	public ComponentDataSection() {
		modelData = new Bag<ComponentData>();
	}

	@Override
	protected final void clear() {

	}

	@Override
	protected final void worldToSection(World world) {
		Bag<ComponentTypeSection.ComponentModel> types = componentTypeSection.componentTypes;
		Bag<EntitySection.EntityModel> entities = entitySection.getEntities();
		for (ComponentTypeSection.ComponentModel model : types) {
			modelData.add(new ComponentData(world, entities, model));
		}
	}

	@Override
	protected final void sectionToWorld(World world) {
		for (ComponentData componentData : modelData) {
		}
	}

	static class ComponentData {
		private final ComponentOutputStream data;
		private final ByteArrayOutputStream baos;

		public ComponentData(World world,
							 Bag<EntitySection.EntityModel> entities,
							 ComponentTypeSection.ComponentModel model) {

			baos = new ByteArrayOutputStream();
			this.data = new ComponentOutputStream(baos);
			ComponentMapper<? extends Component> mapper = world.getMapper(model.type);
			for (EntitySection.EntityModel entity : entities) {
				if (mapper.has(entity.id)) {
					try {
						data.writeEntityId(entity);
						data.writeComponent(mapper.get(entity.id), model);
					} catch (IOException e) {
						throw new RuntimeException(e);
					} catch (ReflectionException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}

		ComponentInputStream getInputStream() {
			return new ComponentInputStream(new ByteArrayInputStream(baos.toByteArray());
		}
	}
}
