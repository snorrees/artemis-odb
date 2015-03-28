package com.artemis.io;

import com.artemis.World;
import com.artemis.component.ComponentX;
import com.artemis.component.ComponentY;
import com.artemis.io.section.NullArchetypeSection;
import com.artemis.io.section.NullComponentTypeSection;
import com.artemis.ArtemisTestUtils;
import com.artemis.utils.ImmutableBag;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ArchetypeSectionTest {

	@Test
	public void archetypes_world_roundtrip() {
		World world = new World();
		world.initialize();

		int[] archetypeIds = new int[5];
		archetypeIds[1] = ArtemisTestUtils.createEntity(world, ComponentX.class);
		archetypeIds[2] = ArtemisTestUtils.createEntity(world, ComponentY.class);
		archetypeIds[3] = ArtemisTestUtils.createEntity(world, ComponentX.class, ComponentY.class);
		archetypeIds[4] = ArtemisTestUtils.createEntity(world, ComponentX.class);

		world.process(); // FIXME: hmm...should saving mid-processing be allowed?

		WorldModel wm = createWorldModel(world);
		ArchetypeSection as = wm.getSection(ArchetypeSection.class);

		// 3 compositions + empty and null.
		assertEquals(5, as.getArchetypeModels().size());

		World world2 = new World();
		world2.initialize();
		wm.modelToWorld(world2);

		WorldModel wm2 = createWorldModel(world2);
		ArchetypeSection as2 = wm2.getSection(ArchetypeSection.class);

		assertEquals(typeHash(as.getArchetypeModels()), typeHash(as2.getArchetypeModels()));
	}

	private Set<ArchetypeSection.ArchetypeModel> typeHash(ImmutableBag<ArchetypeSection.ArchetypeModel> archetypeModels) {
		Set<ArchetypeSection.ArchetypeModel> set = new HashSet<ArchetypeSection.ArchetypeModel>();
		for (ArchetypeSection.ArchetypeModel am : archetypeModels) {
			set.add(new ArchetypeSection.ArchetypeModel(0, am.componentBits));
		}
		return set;
	}

	private WorldModel createWorldModel(World world) {
		WorldModel wm = WorldModel.create()
				.add(NullComponentTypeSection.class)
				.add(NullArchetypeSection.class)
				.build();

		wm.worldToModel(world);
		return wm;
	}
}