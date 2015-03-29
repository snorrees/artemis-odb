package com.artemis.io;

import com.artemis.ArchetypeBuilder;
import com.artemis.Component;
import com.artemis.ComponentType;
import com.artemis.World;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;

public abstract class ComponentDataSection extends Section {
//	protected Bag<ComponentModel> componentTypes = new Bag<ComponentModel>();

	protected ComponentTypeSection componentTypeSection;

	@Override
	protected final void clear() {

	}

	@Override
	protected final void worldToSection(World world) {
	}

	@Override
	protected final void sectionToWorld(World world) {
	}
}
