package com.artemis.io;


import com.artemis.World;
import com.artemis.io.section.NullArchetypeSection;
import com.artemis.io.section.NullComponentDataSection;
import com.artemis.io.section.NullComponentTypeSection;
import com.artemis.io.section.NullEntitySection;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class ComponentDataSectionTest {

    @Test
    public void dependent_sections_imported() {
        World world = new World();
        world.initialize();

        ComponentDataSection section = worldModel(world).getSection(ComponentDataSection.class);
        assertNotNull(section.componentTypeSection);
        assertEquals(NullComponentTypeSection.class, section.componentTypeSection.getClass());

        assertNotNull(section.entitySection);
        assertEquals(NullEntitySection.class, section.entitySection.getClass());
    }

    @Test
    public void reflector_read_test() {

    }

    @Test
    public void write_components() {
        fail();
    }

    @Test
    public void read_components() {
        fail();
    }

    @Test
    public void read_write_values() {
        fail();
    }

    private static WorldModel worldModel(World world) {
        WorldModel wm = WorldModel.create()
                .add(NullComponentTypeSection.class)
                .add(NullArchetypeSection.class)
                .add(NullEntitySection.class)
                .add(NullComponentDataSection.class)
                .build();

        wm.worldToModel(world);
        return wm;
    }
}