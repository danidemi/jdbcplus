package com.danidemi.jdbcplus;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.hsqldb.Server;

public class TestServer {

	Server server;
	private final File tmp;
	private final String name;

	public TestServer(File tmp2, String name2) {
		this.tmp = tmp2;
		this.name = name2;
	}

	public void start() throws IOException {
		String absolutePath = tmp.getAbsolutePath();
		System.out.println("=========================");
		System.out.println("Starting " + absolutePath);
		System.out.println("=========================");
		server = new Server();
		server.setDaemon(true);
		server.setDatabaseName(0, name);
		server.setSilent(false);
		server.setTrace(true);
		server.setDatabasePath(0, absolutePath);
		server.start();
		System.out.println("=========================");
		System.out.println("Started");
		System.out.println("=========================");
	}

	public void stop() throws InterruptedException {
		server.shutdown();

		int state = server.getState();
		System.err.println("ssttaattuuss ====== " + state);
		while (state != 16) {
			Thread.yield();
			Thread.sleep(100);
			state = server.getState();
			System.err.println("ssttaattuuss ====== " + state);
		}

	}

	Connection newConnection() throws ClassNotFoundException, SQLException {


		Class.forName("org.hsqldb.jdbcDriver");
		Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/" + name,
				"sa",
				"");
		return conn;
	}

	void executeStm(String statement) {

		try (Connection conn = newConnection(); Statement stm = conn.createStatement()) {

			stm.execute(statement);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	String getJdbcUrl() {
		return "jdbc:hsqldb:hsql://localhost/" + name + "?user=sa&password=";
	}
}
