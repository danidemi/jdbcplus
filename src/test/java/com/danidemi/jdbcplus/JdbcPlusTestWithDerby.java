package com.danidemi.jdbcplus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.equalToIgnoringCase;

public class JdbcPlusTestWithDerby {

	@Rule public TestName currentTest = new TestName();
	@Rule public TemporaryFolder tmp = new TemporaryFolder();

	DerbyServer tServer;

	@Before
	public void setUp() throws IOException, ClassNotFoundException, SQLException {

		tServer = new DerbyServer(currentTest.getMethodName(), tmp.getRoot());
		tServer.start();

	}


	@After
	public void tearDown() throws SQLException, ClassNotFoundException, InterruptedException {

		tServer.stop();

	}

	// @Before
	// public void deregisterAllJdbcDrivers() throws SQLException {
	// Enumeration<Driver> drivers = DriverManager.getDrivers();
	// while (drivers.hasMoreElements()) {
	// DriverManager.deregisterDriver(drivers.nextElement());
	// }
	// }

	@Test
	public void shouldExtractTwoRows() throws ClassNotFoundException, SQLException {

		// given
		tServer.executeStm("CREATE TABLE GROK(Field1 INTEGER)");
		tServer.executeStm("INSERT INTO GROK(Field1) VALUES (113)");
		tServer.executeStm("INSERT INTO GROK(Field1) VALUES (116)");

		String input = "SELECT * FROM GROK";
		String expectedOutput =
				"Field1\n"
						+ "113\n" + "116\n";

		// when
		ByteArrayOutputStream outStream = runWithInput(input);

		// then
		assertThat(new String(outStream.toByteArray()), equalToIgnoringCase(expectedOutput));

	}


	@Test
	public void shouldExtractOneRow() throws ClassNotFoundException, SQLException {

		// given
		tServer.executeStm("CREATE TABLE TRAL(FieldOne VARCHAR(64), FieldTwo VARCHAR(64))");
		tServer.executeStm("INSERT INTO TRAL(FieldOne,FieldTwo) VALUES ('Gin','Tonic')");

		String input = "SELECT * FROM TRAL";
		String expectedOutput =
				"FIELDONE;FIELDTWO\n"
						+ "\"Gin\";\"Tonic\"\n";

		// when
		ByteArrayOutputStream outStream = runWithInput(input);

		// then
		assertThat(new String(outStream.toByteArray()), equalToIgnoringCase(expectedOutput));

	}

	private ByteArrayOutputStream runWithInput(String input) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outStream));
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		JdbcPlus.main(new String[] { tServer.getJdbcUrl(), tServer.getDriverName() });
		return outStream;
	}
}
