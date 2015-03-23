package com.artemis.utils;

import com.artemis.Component;
import com.artemis.EntityEdit;
import com.artemis.World;

import java.util.HashSet;
import java.util.Set;

public final class ArtemisTestUtils {
	private ArtemisTestUtils() {}

	public static <T> Set<T> toSet(ImmutableBag<T> bag) {
		Set<T> set = new HashSet<T>();
		for (T t : bag)
			set.add(t);

		return set;
	}

	public static int createEntity(World world, Class<? extends Component>... components) {
		EntityEdit ee = world.createEntity().edit();
		for (Class<? extends Component> c : components)
			ee.create(c);

		return ee.getEntity().getCompositionId();
	}
}
