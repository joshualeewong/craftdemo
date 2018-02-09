package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public abstract class AbstractDAO {
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	String context;
	private static String MYSQL_POOL = "jdbc/craftmysql";

	public AbstractDAO() {
		this(MYSQL_POOL);
	}

	public AbstractDAO(String _context) {
		context = _context;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	protected abstract String getTableName();

	protected abstract String getPrimaryKey();

	protected abstract Object loadColumns(Map<String, Object> columns);

	protected abstract Map<String, Object> buildColumns(Object obj, boolean isInsert);

	protected Connection getConnection() {
		if (conn == null) {
			return DataManager.getConnection();
		}
		return conn;
	}

	protected void closeAll(Connection _conn, PreparedStatement _stmt, ResultSet _rs) {
		try {
			if (_rs != null) {
				_rs.close();
			}
			if (_stmt != null) {
				_stmt.close();
			}
			if (_conn != null) {
				_conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
