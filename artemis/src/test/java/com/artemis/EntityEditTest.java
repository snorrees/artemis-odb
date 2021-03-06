package com.artemis;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.artemis.component.ComponentX;
import com.artemis.component.ComponentY;

public class EntityEditTest {
	
	@SuppressWarnings("static-method")
	@Test
	public void basic_entity_edit_test() {
		LeManager lm = new LeManager();
		World world = new World(new WorldConfiguration()
				.setSystem(lm));

		Entity e = world.createEntity();
		world.process();
		
		assertEquals(1, lm.added);

		EntityEdit edit = e.edit();
		edit.create(ComponentX.class);
		edit.create(ComponentY.class);
		
		world.process();
		
		assertEquals(1, lm.added);
	}
	
	@Test
	public void test_composition_identity_simple_case() {
		World world = new World();

		Entity e = world.createEntity();
		world.process();
		assertEquals(1, e.getCompositionId());
	}
	
	@Test
	public void test_composition_identity() {
		World world = new World();

		Entity e = world.createEntity();
		assertEquals(1, e.getCompositionId());
	}
	
	private static class LeManager extends Manager {
		
		int added;

		@Override
		public void added(Entity e) {
			added++;
		}
	}
}
