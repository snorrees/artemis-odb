package com.artemis.io.section;

import com.artemis.io.ArchetypeSection;
import com.artemis.io.ComponentTypeSection;

import java.io.InputStream;
import java.io.OutputStream;

public class NullComponentTypeSection extends ComponentTypeSection {
	@Override protected void read(InputStream is) {}
	@Override protected void write(OutputStream is) {}
}