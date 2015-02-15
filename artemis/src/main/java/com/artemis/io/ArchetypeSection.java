package com.artemis.io;

import com.artemis.utils.Bag;

public abstract class ArchetypeSection extends Section {
	protected Bag<EntityModel> entities = new Bag<EntityModel>();

	Bag<EntityModel> getEntities() {
		return entities;
	}

	private static class EntityModel {
		public int id;
		public int archetypeId;
	}
}
