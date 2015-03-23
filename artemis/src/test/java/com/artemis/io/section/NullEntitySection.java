package com.artemis.io.section;

import com.artemis.io.EntitySection;

import java.io.InputStream;
import java.io.OutputStream;

public class NullEntitySection extends EntitySection {
	@Override protected void read(InputStream is) {}
	@Override protected void write(OutputStream is) {}
	@Override protected void clear() {}
}