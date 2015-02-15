package com.artemis.io;

import java.io.InputStream;

public interface SectionReader<S extends Section> {
	void initialize(S section);
	void read(InputStream is);
}
