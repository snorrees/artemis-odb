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
		Bag<ComponentTypeSection.ComponentModel> types = componentTypeSection.componentTypes;
		for (ComponentTypeSection.ComponentModel model : types) {
			for (ComponentData componentData : modelData) {
				try {
					componentData.toWorld(world, model);
				} catch (IOException e) {
					throw new RuntimeException(e);
				} catch (ReflectionException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	static class ComponentData {
		private final ComponentOutputStream data;
		private final ByteArrayOutputStream baos;
		private final Class<? extends Component> type;

		public ComponentData(World world,
							 Bag<EntitySection.EntityModel> entities,
							 ComponentTypeSection.ComponentModel model) {

			type = model.type;
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

//		private ComponentInputStream getInputStream() {
//			return new ComponentInputStream(new ByteArrayInputStream(baos.toByteArray()));
//		}

		public void toWorld(World target, ComponentTypeSection.ComponentModel model)
				throws IOException, ReflectionException {

			ComponentMapper<? extends Component> mapper = target.getMapper(type);
			ComponentInputStream in = new ComponentInputStream(new ByteArrayInputStream(baos.toByteArray()), model, this);
			while (in.available() > 0) {
				Entity e = in.readEntity(target);
				Component component = mapper.get(e);
				in.readIntoComponent(component);
			}
		}
	}
}
