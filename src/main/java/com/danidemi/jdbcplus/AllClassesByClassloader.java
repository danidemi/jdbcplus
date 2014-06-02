package com.danidemi.jdbcplus;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang.ArrayUtils;

public class AllClassesByClassloader implements ClassFinder {
	
	private ClassLoader thecl;

	public AllClassesByClassloader(ClassLoader thecl) {
		this.thecl = thecl;
	}
	
	Set<Class> findByClassLoader(ClassLoader thecl) throws IOException {
		
		LinkedHashSet<Class> findClasses = new LinkedHashSet<>(0);
		
		Enumeration<URL> resources = thecl.getResources("");

		while (resources.hasMoreElements()) {

			URL nextElement = resources.nextElement();
			String protocol = nextElement.getProtocol();

			System.out.print(nextElement);
			System.out.print(" | ");
			System.out.println(protocol);

			if ("file".equals(protocol)) {

				String root = nextElement.getFile();

				findClasses.addAll( findClasses(new File(root),new File(root)) );
				

			}else{
				
				throw new UnsupportedOperationException( "unsupported " + nextElement );
				
			}

		}
		
		return findClasses;
	}

	private Set<Class> findClasses(File root, File current) {
		LinkedHashSet<Class> classes = new LinkedHashSet<>(0);
		if (current.isDirectory()) {
			
			System.out.println("Browsing " + current.getAbsolutePath());
			
//			FilenameFilter filter = new SuffixFileFilter("class");
//			File[] listFiles = current.listFiles( filter );

			File[] listFiles = current.listFiles( );			
			for (File file : listFiles) {
				classes.addAll( findClasses(root, file) );
			}
			return classes;
		} else if (current.isFile()) {
			
			
			
			String replace = current.getAbsolutePath().replace(root.getAbsolutePath(), "");
			
//			if(!replace.endsWith(".class")){
//				return classes;
//			}
			
			if(replace.startsWith(File.separator)){
				replace = replace.substring(File.separator.length());
			}
			if(replace.endsWith(".class")){
				replace = replace.substring(0, replace.length() - ".class".length());
			}
			replace = replace.replace(File.separatorChar, '.');
			
			System.out.println("Found " + current.getAbsolutePath() + " " + replace);
			
			Class<?> forName = null;
			try {
				forName = Class.forName(replace);
			} catch (ClassNotFoundException e) {
				System.out.println(replace + " not a class after all");
				//throw new RuntimeException(e);
			}
			classes.add(  forName );
		}
		return classes;		
	}

	@Override
	public Set<Class> findClasses() throws IOException {
		return findByClassLoader(thecl);
	}



}
