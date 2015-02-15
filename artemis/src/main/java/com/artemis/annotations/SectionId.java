package com.artemis.annotations;

import com.artemis.io.Section;

public @interface SectionId {
	Class<Section> value();
}
