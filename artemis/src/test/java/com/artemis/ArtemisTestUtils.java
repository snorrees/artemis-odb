package com.artemis;

import com.artemis.utils.ImmutableBag;

import java.util.BitSet;
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

	public static int compositionId(World world, Class<? extends Component>... components) {
		BitSet bs = new BitSet();
		ComponentTypeFactory tf = world.getComponentManager().typeFactory;
		for (Class<? extends Component> component : components)
			bs.set(tf.getIndexFor(component));

		return world.getEntityManager().compositionIdentity(bs);
	}
}
