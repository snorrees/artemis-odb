package com.artemis.io;

import com.artemis.ArchetypeBuilder;
import com.artemis.World;
import com.artemis.component.ComponentX;
import com.artemis.component.ComponentY;
import com.artemis.component.Packed;
import com.artemis.component.ReusedComponent;
import com.artemis.io.section.NullComponentTypeSection;
import com.artemis.utils.reflect.ClassReflection;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ComponentTypeSectionTest {
	@Test
	public void read_write_world_component_types_test() {
		World world = new World();
		world.initialize();

		new ArchetypeBuilder()
			.add(ComponentX.class)
			.add(ComponentY.class)
			.add(Packed.class)
			.add(ReusedComponent.class)
			.build(world);

		WorldModel wm = createWorldModel(world);
		ComponentTypeSection cts = wm.getSection(ComponentTypeSection.class);
		assertNotNull(cts);

		assertEquals(4, cts.componentTypes.size());

		World world2 = new World();
		world2.initialize();
		wm.modelToWorld(world2);

		WorldModel wm2 = createWorldModel(world2);
		ComponentTypeSection cts2 = wm2.getSection(ComponentTypeSection.class);
		assertNotNull(cts2);

		assertEquals(cts.componentTypes, cts2.componentTypes);
	}

	private WorldModel createWorldModel(World world) {
		WorldModel wm = WorldModel.create()
				.add(NullComponentTypeSection.class)
				.build();

		wm.worldToModel(world);
		return wm;
	}
}