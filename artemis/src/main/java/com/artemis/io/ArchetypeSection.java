package com.artemis.io;

import com.artemis.*;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;

import java.util.BitSet;

public abstract class ArchetypeSection extends Section {
	private Bag<ArchetypeModel> archetypes = new Bag<ArchetypeModel>();
	private ComponentTypeSection cts;

	protected ImmutableBag<ArchetypeModel> getArchetypeModels() {
		return archetypes;
	}

	@Override
	protected final void worldToSection(World world) {
		world.getEntityManager().readSection(new Callback() {
			@Override
			public void read(ImmutableBag<BitSet> compositions) {
				for (int i = 0, s = compositions.size(); s > i; i++) {
					archetypes.add(new ArchetypeModel(i, compositions.get(i)));
				}
			}
		});
	}

	@Override
	protected final void sectionToWorld(World world) {
		// TODO: need to sync bitsets with world
		EntityManager em = world.getEntityManager();
		for (int i = 2; i < archetypes.size(); i++) {
			ArchetypeModel model = archetypes.get(i);
			toArchetype(world, model);
		}
	}

	@Override
	protected void clear() {
		archetypes.clear();
	}

	public ImmutableBag<Archetype> getArchetypes(World world) {
		Bag<Archetype> resolvedArchetypes = new Bag<Archetype>();
		for (ArchetypeModel model : archetypes)
			resolvedArchetypes.add(toArchetype(world, model));

		return resolvedArchetypes;
	}

	private Archetype toArchetype(World world, ArchetypeModel model) {
		BitSet bits = model.componentBits;
		ArchetypeBuilder builder = new ArchetypeBuilder();
		if (bits != null) {
			for (int i = bits.nextSetBit(0); i >= 0; i = bits.nextSetBit(i + 1)) {
				builder.add((Class<? extends Component>) cts.getType(i).type);
			}
		}

		return builder.build(world);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ArchetypeSection that = (ArchetypeSection) o;
		return archetypes.equals(that.archetypes);
	}

	@Override
	public int hashCode() {
		return archetypes.hashCode();
	}

	public static class ArchetypeModel {
		public final int archetypeId;
		public final BitSet componentBits;

		public ArchetypeModel(int archetypeId, BitSet componentBits) {
			this.archetypeId = archetypeId;
			this.componentBits = componentBits;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			ArchetypeModel that = (ArchetypeModel) o;

			if (archetypeId != that.archetypeId) return false;
			if (componentBits != null ? !componentBits.equals(that.componentBits) : that.componentBits != null)
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			int result = archetypeId;
			result = 31 * result + (componentBits != null ? componentBits.hashCode() : 0);
			return result;
		}

		@Override
		public String toString() {
			return "\nArchetypeModel{" +
					"archetypeId=" + archetypeId +
					", componentBits=" + componentBits +
					"} (" + hashCode() + ")";
		}
	}

	public interface Callback {
		void read(ImmutableBag<BitSet> compositions);
	}
}
