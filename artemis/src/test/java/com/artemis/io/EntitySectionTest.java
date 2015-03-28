package com.artemis.io;

import com.artemis.World;
import com.artemis.component.ComponentX;
import com.artemis.component.ComponentY;
import com.artemis.io.section.NullArchetypeSection;
import com.artemis.io.section.NullComponentTypeSection;
import com.artemis.io.section.NullEntitySection;
import org.junit.Test;

import static com.artemis.ArtemisTestUtils.compositionId;
import static org.junit.Assert.*;

public class EntitySectionTest {
	@Test
	public void read_entities_from_world_and_back_test() {
		World world = new World();
		world.initialize();

		world.createEntity().edit()
			.add(new ComponentX("hello 1"))
			.add(new ComponentY("bye 1"));

		world.createEntity().edit()
			.add(new ComponentX("hello 2"));

		world.createEntity().edit()
			.add(new ComponentX("hello 3"))
			.add(new ComponentY("bye 3"));

		world.createEntity().edit()
			.add(new ComponentY("bye 4"));

		world.process();

		World world2 = new World();
		world2.initialize();

		worldModel(world).modelToWorld(world2);

		world2.process();

		// sanity check
		assertNotEquals(
			compositionId(world2, ComponentX.class, ComponentY.class),
			compositionId(world2, ComponentY.class));

		assertEquals(
			compositionId(world2, ComponentX.class, ComponentY.class),
			world2.getEntity(0).getCompositionId());

		assertEquals(
			compositionId(world2, ComponentX.class),
			world2.getEntity(1).getCompositionId());

		assertEquals(
			compositionId(world2, ComponentX.class, ComponentY.class),
			world2.getEntity(2).getCompositionId());

		assertEquals(
			compositionId(world2, ComponentY.class),
			world2.getEntity(3).getCompositionId());

		fail("not checking component values");
	}

	private static WorldModel worldModel(World world) {
		WorldModel wm = WorldModel.create()
				.add(NullComponentTypeSection.class)
				.add(NullArchetypeSection.class)
				.add(NullEntitySection.class)
				.build();

		wm.worldToModel(world);
		return wm;
	}

}