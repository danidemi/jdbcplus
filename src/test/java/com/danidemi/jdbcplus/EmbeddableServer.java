package com.danidemi.jdbcplus;

import java.io.IOException;

public interface EmbeddableServer {

	void start() throws IOException;

	void stop() throws InterruptedException;

	String getDriverName();

}
