package com.artemis.io;

import com.artemis.Archetype;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;

public abstract class EntitySection extends Section {
	protected Bag<EntityModel> entities = new Bag<EntityModel>();
	private ArchetypeSection archetypeSection;

	public Bag<EntityModel> getEntities() {
		return entities;
	}

	@Override
	protected final void worldToSection(World world) {
		world.getEntityManager().read(new EntitySection.Callback() {
			@Override
			public void read(ImmutableBag<Entity> entities) {
				Bag<EntityModel> entityModels = EntitySection.this.entities;
				for (int i = 0; i < entities.size(); i++) {
					entityModels.add(new EntityModel(entities.get(i)));
				}
			}
		});
	}

	@Override
	protected final void sectionToWorld(World world) {
		ImmutableBag<Archetype> archetypes = archetypeSection.getArchetypes(world);
		for (EntityModel entityModel : entities) {
			Archetype archetype = archetypes.get(entityModel.compositionId);
			world.createEntity(archetype);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		EntitySection that = (EntitySection) o;
		return entities.equals(that.entities);
	}

	@Override
	public int hashCode() {
		return entities.hashCode();
	}

	public static class EntityModel {
		public final int id;
		public final int compositionId;

		private EntityModel(int id, int compositionId) {
			this.id = id;
			this.compositionId = compositionId;
		}

		private EntityModel(Entity e) {
			this.id = e.getId();
			this.compositionId = e.getCompositionId();
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			EntityModel that = (EntityModel) o;

			if (compositionId != that.compositionId) return false;
			if (id != that.id) return false;

			return true;
		}

		@Override
		public int hashCode() {
			int result = id;
			result = 31 * result + compositionId;
			return result;
		}

		@Override
		public String toString() {
			return "EntityModel{" +
					"id=" + id +
					", compositionId=" + compositionId +
					'}';
		}
	}

	public static interface Callback {
		void read(ImmutableBag<Entity> entities);
	}
}
