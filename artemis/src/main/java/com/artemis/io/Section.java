package com.artemis.io;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class Section {
	protected WorldModel wm;


	protected void initialize() {}
	abstract protected void read(InputStream is);
	abstract protected void write(OutputStream is);



}
