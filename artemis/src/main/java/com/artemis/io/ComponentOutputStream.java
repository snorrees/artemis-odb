package com.artemis.io;

import com.artemis.Component;
import com.artemis.utils.reflect.ReflectionException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ComponentOutputStream extends DataOutputStream{
	public ComponentOutputStream(OutputStream out) {
		super(out);
	}

	public void writeEntityId(EntitySection.EntityModel entity) throws IOException {
		write(entity.id);
	}

	public void writeComponent(Component source, ComponentTypeSection.ComponentModel model)
			throws ReflectionException {

		model.serialize(source, this);
	}
}
