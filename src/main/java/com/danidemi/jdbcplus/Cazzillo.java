package com.danidemi.jdbcplus;

import java.io.PrintStream;

public class Cazzillo {

	private PrintStream out;

	private StringBuilder line;

	public void onStartColumns() {
		line = new StringBuilder();
	}

	public void onColumn(int i, String columnName) {
		if (line.length() > 0) {
			line.append(";");
		}
		line.append(columnName);
	}

	public void onEndColumns() {
		out.println(line);
	}

	public void onStartRows() {}

	public void onStartRow() {
		line = new StringBuilder();
	}

	public void onValue(int i, Object object) {
		if (line.length() > 0) {
			line.append(";");
		}
		if (object instanceof String) {
			line.append("\"" + String.valueOf(object) + "\"");
		} else {
			line.append(String.valueOf(object));
		}
	}

	public void onEndRow() {
		out.println(line);
	}

	public void onEndRows() {

	}

	public void setOut(PrintStream out) {
		this.out = out;
	}

}
