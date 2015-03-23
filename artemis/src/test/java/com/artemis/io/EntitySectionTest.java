package com.artemis.io;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.component.ComponentX;
import com.artemis.component.ComponentY;
import com.artemis.io.section.NullArchetypeSection;
import com.artemis.io.section.NullComponentTypeSection;
import com.artemis.io.section.NullEntitySection;
import junit.framework.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;

import static com.artemis.utils.ArtemisTestUtils.createEntity;
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

		WorldModel wm = createWorldModel(world);
		System.out.println(wm);

		World world2 = new World();
		world2.initialize();

		wm.modelToWorld(world2);


		world2.process();
		Entity e = world2.getEntity(0);

		fail("not tested for equality yet");
	}

	private WorldModel createWorldModel(World world) {
		WorldModel wm = WorldModel.create()
				.add(NullComponentTypeSection.class)
				.add(NullArchetypeSection.class)
				.add(NullEntitySection.class)
				.build();

		wm.worldToModel(world);
		return wm;
	}

}