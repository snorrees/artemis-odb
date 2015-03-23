package com.artemis.io.section;

import com.artemis.io.ArchetypeSection;

import java.io.InputStream;
import java.io.OutputStream;

public class NullArchetypeSection extends ArchetypeSection {
	@Override protected void read(InputStream is) {}
	@Override protected void write(OutputStream is) {}
	@Override protected void clear() {}
}