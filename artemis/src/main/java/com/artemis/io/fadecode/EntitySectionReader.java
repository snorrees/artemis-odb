package com.artemis.io.fadecode;

import com.artemis.io.EntitySection;
import com.artemis.io.SectionReader;

import java.io.InputStream;

public class EntitySectionReader implements SectionReader<EntitySection> {
	private EntitySection section;

	@Override
	public void initialize(EntitySection section) {
		this.section = section;
	}

	@Override
	public void read(InputStream is) {

	}
}
