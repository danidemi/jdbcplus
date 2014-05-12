package com.danidemi.jdbcplus;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class InputStreamQueryReader implements QueryReader {

	private final InputStream in;

	public InputStreamQueryReader(InputStream in) {
		this.in = in;
	}

	@Override
	public String readSql() throws IOException {
		return StringUtils.join(IOUtils.readLines(in), null);
	}

}
