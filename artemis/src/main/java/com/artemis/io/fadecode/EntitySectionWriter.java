package com.artemis.io.fadecode;

import com.artemis.EntityManager;
import com.artemis.io.EntitySection;
import com.artemis.io.SectionWriter;

import java.io.OutputStream;

public class EntitySectionWriter extends SectionWriter<EntitySection> {
	@Override
	public void write(OutputStream bytes) {
		EntityManager em = world.getEntityManager();

	}
}
