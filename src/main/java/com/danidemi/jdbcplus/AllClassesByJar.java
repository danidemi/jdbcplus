package com.danidemi.jdbcplus;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.management.RuntimeErrorException;

import org.apache.commons.lang.StringUtils;

public class AllClassesByJar implements ClassFinder {

	private JarFile jarFile;
	private File file;

	public AllClassesByJar(File jarFile) throws IOException {
		this.jarFile = new JarFile( jarFile );
		this.file = jarFile;
	}
	
	@Override
	public Set<Class> findClasses() throws IOException {
		
		LinkedHashSet<Class> classes = new LinkedHashSet<>(0);
		
		Enumeration<JarEntry> entries = jarFile.entries();
		
		while(entries.hasMoreElements()){
			JarEntry nextElement = entries.nextElement();
			
			if(nextElement.isDirectory()) continue;
			
			String name = nextElement.getName();

			System.out.println(name);
			name =  name.replace('/', '.');
			name = name.substring(0, name.length() - ".class".length());
			
			
			try {
				System.out.println(name);
				Class<?> forName = Class.forName(name);
				if(forName!=null){
				classes.add( forName );
				}
				//classes.add(e)
			} catch (ClassNotFoundException|NoClassDefFoundError e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		return classes;
	}

//	private void find(JarEntry nextElement) {
//		if(nextElement.isDirectory()){
//			nextElement.
//		}else{
//			
//		}
//	}
	
//	@Override
//	public Set<Class> findClasses() throws IOException {
//		//jar:file:/codeSamples/zipfs/zipfstest.zip
//		//jar:file:/codeSamples/zipfs/zipfstest.zip
//		//jar:file:/home/danidemi/.m2/repository/junit/junit/4.11/junit-4.11.jar
//		URI theUri;
//		String str = "jar:file:" + file.getAbsolutePath();
//		try {
//			theUri = new URI(str);
//		} catch (URISyntaxException e) {
//			throw new RuntimeException("URI " + str + "not valid", e);
//		}
//		
//		try{
//			FileSystem fs = FileSystems.getFileSystem(theUri);
//			
//		}catch(Exception e){
//			throw new RuntimeException("Problems with URI " + str, e);
//		}
//		
//		return null;
//	}

}
