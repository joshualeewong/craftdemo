package com.dao;

import com.util.MyLogger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataManager {
	private static final String HOSTNAME = "mydbcraftdemo.cqodofserr5r.us-west-2.rds.amazonaws.com";
	private static final String PORT = "3306";
	private static final String USERNAME = "production";
	private static final String PASS = "password";
	private static final String DB_NAME = "craftdemo";
	private static final String context = "jdbc/craftdemo";

	/*
  	* Set this to true when deploying to local tomcat and want to use Tomcat connection.
  	* Set this to false in dev and deploying to AWS Elastic Beanstalk.
  	*/
	private static boolean isWebServer = false;

	private static Connection connMySQL;

	public static Connection getConnection() {

		if (isWebConnection()) {
			return getWebConnection(context);
		} else {
			return getSAConnection(context);
		}
	}

	private static Connection getWebConnection(String context) {
		if (context == null || context.length() == 0) {
			throw new IllegalArgumentException("Context is empty");
		}

		try {
			MyLogger.getLogger().info("Getting new JDBC MySQL tomcat connection.");
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + context);
			if (ds == null) {
				throw new RuntimeException("No datasource configured in the context " + context);
			}

			Connection conn = ds.getConnection();
			if (conn == null) {
				throw new RuntimeException("Could not establish connection to database");
			}
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Connection getSAConnection(String context) {
		if (context == null || context.length() == 0) {
			throw new IllegalArgumentException("Context is empty");
		}
		String jdbcUrl = null;
		if ("jdbc/craftdemo".equals(context)) {
			jdbcUrl = "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DB_NAME + "?autoReconnect=true&user=" + USERNAME + "&password=" + PASS;
		}

		try {
			if (connMySQL != null && !connMySQL.isClosed()) {
				return connMySQL;
			}
			Class.forName("com.mysql.jdbc.Driver");
			MyLogger.getLogger().info("Getting new JDBC MySQL SA connection.");
			connMySQL = DriverManager.getConnection(jdbcUrl);
			return connMySQL;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connMySQL;
	}

	private static boolean isWebConnection() {
		return isWebServer;
	}

	public static void setSAConnection() {
		isWebServer = false;
	}
}
