package com.danidemi.jdbcplus;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;


public class AllClassesTest {
	
	@Test
	public void findAllBySystemClasspath() throws IOException {

		System.out.println( new AllClassesBySystemClasspath().findClasses() );
		
	}

	@Test
	public void findClassesFromSystemClassLoader() throws IOException {

		ClassLoader thecl = ClassLoader.getSystemClassLoader();
		

		Set<Class> findByClassLoader = new AllClassesByClassloader(thecl).findClasses();
		
		System.out.println(findByClassLoader);

	}
	
	@Test
	public void findClassesFromClassLoader() throws IOException {


		
		ClassLoader classLoader = ArrayUtils.class.getClassLoader();
		Set<Class> findByClassLoader = new AllClassesByClassloader(classLoader).findClasses();
		
		System.out.println(findByClassLoader);

	}	

}
