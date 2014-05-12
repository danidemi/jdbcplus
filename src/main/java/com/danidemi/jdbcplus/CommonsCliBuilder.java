package com.danidemi.jdbcplus;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommonsCliBuilder {

	public InnerJdbcPlus build(String[] args) {

		Options addOption = new Options()
			.addOption(new Option("u", "jdbcurl", true, "Mandatory. The JDBC URL of the database you want to connect to."))
			.addOption(new Option("d", "driver", true, "Mandatory. The JDBC Driver's classname you want to connect to."))
			.addOption(
					new Option("q", "query", true,
							"The query or queries to be executed. If not provided, jdbcplus expects to read them from stdin."));

		CommandLineParser parser = new BasicParser();
		CommandLine cLine = null;
		try {
			cLine = parser.parse(addOption, args);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InnerJdbcPlus ijp = new InnerJdbcPlus();
		if (cLine.hasOption("q")) {
			ijp.setQueryReader(new StringQueryReader(cLine.getOptionValue("q")));
		} else {
			ijp.setQueryReader(new InputStreamQueryReader(System.in));
		}
		return ijp;

	}


}
