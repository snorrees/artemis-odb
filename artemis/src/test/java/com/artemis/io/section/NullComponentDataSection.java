package com.artemis.io.section;

import com.artemis.io.ComponentDataSection;

import java.io.InputStream;
import java.io.OutputStream;

public class NullComponentDataSection extends ComponentDataSection {
	@Override protected void read(InputStream is) {}
	@Override protected void write(OutputStream is) {}
}