package com.danidemi.jdbcplus;


public class JdbcPlus {

	public void run(String[] args) {

		InnerJdbcPlus inner = new CommonsCliBuilder().build(args);
		inner.run();

	}

	public static void main(String[] args) {

		new JdbcPlus().run(args);
	}

}
