package com.danidemi.jdbcplus;

import java.io.File;


public class VisitableFile {

	private File root;

	void accept(VisitableFileVisitor visitor) {
		File root2 = root;
		visit(visitor, root2);
	}

	private void visit(VisitableFileVisitor visitor, File root2) {
		visitor.visit(root2);
		if (root.isDirectory()) {
			File[] listFiles = root.listFiles();
			for (File file : listFiles) {
				visitor.visit(file);
				visit(visitor, file);
			}
		}
	}

}
