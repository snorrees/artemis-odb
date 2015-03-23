package com.artemis.io;

import com.artemis.World;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class Section {
	protected WorldModel wm;

	protected void initialize() {}
	abstract protected void worldToSection(World world);
	abstract protected void read(InputStream is);
	abstract protected void write(OutputStream is);
	abstract protected void sectionToWorld(World world);
	abstract protected void clear();
}
