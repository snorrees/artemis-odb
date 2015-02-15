package com.artemis.io;

import com.artemis.World;

import java.io.OutputStream;

public abstract class SectionWriter<S extends Section> {
	protected S section;
	protected WorldModel worldModel;
	protected World world;

	protected final void initialize(S section, WorldModel worldModel, World world) {
		this.section = section;
		this.worldModel = worldModel;
		this.world = world;
	}

	protected abstract void write(OutputStream os);
}
