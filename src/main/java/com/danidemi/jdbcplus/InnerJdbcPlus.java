package com.danidemi.jdbcplus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import static java.lang.String.format;

public class InnerJdbcPlus {

	String url;
	String driverClass;
	private QueryReader queryReader;

	public void setQueryReader(QueryReader queryReader) {
		this.queryReader = queryReader;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public void run() {
		try {


			String sql = queryReader.readSql();
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


}
