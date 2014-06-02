package com.danidemi.jdbcplus;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.jar.JarFile;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

public class AllClassesBySystemClasspath implements ClassFinder {

	@Override
	public Set<Class> findClasses() throws IOException {
		
		LinkedHashSet<Class> found = new LinkedHashSet<>(0);
		
		String cp = System.getProperty("java.class.path");
		String[] split = StringUtils.split(cp, File.pathSeparator);
		for (String string : split) {
			File file = new File(string);
			if(file.isDirectory()){
				System.out.println("dir " + file);
				found.addAll( new AllClassesByFolder(file).findClasses() );
			}else if(file.isFile() && "jar".equals( FilenameUtils.getExtension(file.getAbsolutePath()) )){
				System.out.println("jar " + file);
				
				AllClassesByJar allClassesByJar = new AllClassesByJar( file );
				found.addAll( allClassesByJar.findClasses() );
			}else{
				System.out.println("boh " + file);
			}
		}
		System.out.println( cp );
		
		return found;
	}

}
