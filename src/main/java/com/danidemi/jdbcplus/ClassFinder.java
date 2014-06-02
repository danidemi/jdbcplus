package com.danidemi.jdbcplus;

import java.io.IOException;
import java.util.Set;

public interface ClassFinder {

	Set<Class> findClasses() throws IOException;
	
}
