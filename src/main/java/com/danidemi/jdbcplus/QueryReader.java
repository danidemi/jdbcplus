package com.danidemi.jdbcplus;

import java.io.IOException;


public interface QueryReader {

	// StringUtils.join(IOUtils.readLines(System.in), null);
	String readSql() throws IOException;

}
