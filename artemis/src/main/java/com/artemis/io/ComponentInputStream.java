package com.artemis.io;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.World;

import java.io.DataInputStream;
import java.io.InputStream;

public class ComponentInputStream extends DataInputStream {
	private final Class<? extends Component> type;

	public ComponentInputStream(ComponentTypeSection.ComponentModel model,
								ComponentDataSection.ComponentData componentData) {

		super(componentData.getInputStream());
		type = model.type;
	}

	public void readIntoWorld(World world) {
		ComponentMapper<? extends Component> mapper = world.getMapper(type);

		throw new RuntimeException("need to write data to mapper");
	}
}
