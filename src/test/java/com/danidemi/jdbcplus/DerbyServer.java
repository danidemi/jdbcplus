package com.danidemi.jdbcplus;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DerbyServer implements EmbeddableServer {

	String dbName;
	private final File folder;

	public DerbyServer(String dbName, File folder) {
		super();
		this.dbName = dbName;
		this.folder = folder;
	}

	@Override
	public void start() throws IOException {

	}

	@Override
	public void stop() throws InterruptedException {
		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException e) {
			System.err.println(e.getErrorCode());
			System.err.println(e.getSQLState());
			if (e.getErrorCode() == 50000 && "XJ015".equals(e.getSQLState())) {
				// normal shutdown
				System.err.println("Properly shutted down");
			} else {
				e.printStackTrace();
			}
		}
	}

	Connection newConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String driver = getDriverName();
		Class.forName(driver).newInstance();
		Properties props = null;
		String jdbcUrl = getJdbcUrl();
		Connection conn = DriverManager.getConnection(jdbcUrl, props);
		return conn;
	}

	@Override
	public String getDriverName() {
		return "org.apache.derby.jdbc.EmbeddedDriver";
	}

	public String getJdbcUrl() {
		String protocol = "jdbc:derby:";
		File file = new File(folder, dbName);
		return protocol + file.getAbsolutePath() + ";create=true";
	}

	void executeStm(String statement) {

		try (Connection conn = newConnection(); Statement stm = conn.createStatement()) {

			stm.execute(statement);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
