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
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.equalToIgnoringCase;

//@RunWith(PowerMockRunner.class)
// @PrepareForTest(Class.class)
public class JdbcPlusTestWithHsql {

	@Rule public TemporaryFolder tmp = new TemporaryFolder();
	@Rule public TestName currentTest = new TestName();

	HsqlTestServer tServer;

	@Before
	public void setUp() throws IOException, ClassNotFoundException, SQLException {

		tServer = new HsqlTestServer(tmp.newFolder(), currentTest.getMethodName());
		tServer.start();

	}


	@After
	public void tearDown() throws SQLException, ClassNotFoundException, InterruptedException {

		tServer.stop();

	}


	@Test
	public void shouldExtractTwoRows() throws ClassNotFoundException, SQLException {

		// given
		PowerMockito.spy(Class.class);

		tServer.executeStm("CREATE TABLE PALL(Field1 INTEGER);");
		tServer.executeStm("INSERT INTO PALL(Field1) VALUES (13);");
		tServer.executeStm("INSERT INTO PALL(Field1) VALUES (16);");

		String input = "SELECT * FROM PALL";
		String expectedOutput =
				"Field1\n"
						+ "13\n" + "16\n";

		// when
		ByteArrayOutputStream outStream = runWithInput(input);

		// then
		assertThat(new String(outStream.toByteArray()), equalToIgnoringCase(expectedOutput));

	}


	@Test
	public void shouldExtractOneRow() throws ClassNotFoundException, SQLException {

		// given
		tServer.executeStm("CREATE TABLE PALL(Field1 VARCHAR(64), Field2 VARCHAR(64));");
		tServer.executeStm("INSERT INTO PALL(Field1,Field2) VALUES ('Hello','World');");

		String input = "SELECT * FROM PALL";
		String expectedOutput =
				"FIELD1;FIELD2\n"
						+ "\"Hello\";\"World\"\n";

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
