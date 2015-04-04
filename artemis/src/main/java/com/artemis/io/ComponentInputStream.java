package com.artemis.io;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.reflect.ReflectionException;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ComponentInputStream extends DataInputStream {
	private final ComponentTypeSection.ComponentModel model;
	private ComponentDataSection.ComponentData componentData;

	public ComponentInputStream(InputStream inputStream,
								ComponentTypeSection.ComponentModel model,
								ComponentDataSection.ComponentData componentData) {

		super(inputStream);
		this.model = model;
		this.componentData = componentData;
	}

	public void readIntoWorld(World world) throws IOException {
	}

	public void readIntoComponent(Component c) throws IOException, ReflectionException {
		for (ComponentTypeSection.FieldReflector field : model.fields) {
			field.read(this, c);
		}
	}

	public Entity readEntity(World world) throws IOException {
		return world.getEntity(readInt());
	}

}
