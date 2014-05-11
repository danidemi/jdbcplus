package com.danidemi.jdbcplus;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class AllClasses {

	public void dodo() throws IOException {

		Enumeration<URL> resources = getClass().getClassLoader().getResources("");

		while (resources.hasMoreElements()) {

			URL nextElement = resources.nextElement();
			String protocol = nextElement.getProtocol();

			System.out.println(nextElement);
			System.out.println(protocol);

			if ("file".equals(protocol)) {

				String root = nextElement.getFile();

				findClasses(new File(root));


			}

		}

	}

	private void findClasses(File root) {
		findClasses(root, root);
	}

	private void findClasses(File root, File current) {
		List<Class> classes = new ArrayList<>(0);
		if (current.isDirectory()) {
			File[] listFiles = current.listFiles();
		} else if (current.isFile()) {

		}
	}

}
