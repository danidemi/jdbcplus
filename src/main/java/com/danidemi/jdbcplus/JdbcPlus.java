package com.danidemi.jdbcplus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import static java.lang.String.format;

public class JdbcPlus {

	public void run(String[] args) {
		try {

			String sql = StringUtils.join(IOUtils.readLines(System.in), null);

			// String driverClass = "org.hsqldb.jdbcDriver";
			// String driverClass = "org.apache.derby.jdbc.EmbeddedDriver";


			String url = args[0];
			String driverClass = args[1];

			System.err.println(format("Connecting to %s using %s to execute: %s", url, driverClass, sql));

			Class.forName(driverClass);

			Connection conn = DriverManager.getConnection(url);
			Statement createStatement = conn.createStatement();
			createStatement.setFetchSize(10);
			createStatement.setMaxFieldSize(512);
			createStatement.setMaxRows(100);
			createStatement.setQueryTimeout(10);
			boolean execute = createStatement.execute(sql);
			ResultSet resultSet = createStatement.getResultSet();

			ResultSetMetaData metaData = resultSet.getMetaData();

			int columnCount = metaData.getColumnCount();

			Cazzillo cazzillo = new Cazzillo();
			cazzillo.setOut(System.out);

			cazzillo.onStartColumns();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnName(i);
				cazzillo.onColumn(i, columnName);
			}
			cazzillo.onEndColumns();

			cazzillo.onStartRows();
			while (resultSet.next()) {
				cazzillo.onStartRow();
				for (int i = 1; i <= columnCount; i++) {
					cazzillo.onValue(i, resultSet.getObject(i));
				}
				cazzillo.onEndRow();
			}
			cazzillo.onEndRows();

			resultSet.close();
			createStatement.close();
			createStatement.close();


		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static void main(String[] args) {

		new JdbcPlus().run(args);
	}

}
