package com.dao;

import com.util.MyLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class MySQLAbstractDAO extends AbstractDAO{

	public MySQLAbstractDAO() {
	}

	protected abstract String getTableName();

	protected abstract String getPrimaryKey();

	protected abstract Object loadColumns(Map<String, Object> columns);

	protected abstract Map<String, Object> buildColumns(Object obj, boolean isInsert);

	@Override
	protected Connection getConnection() {
		return conn == null ? DataManager.getConnection() : conn;
	}

	public void save(Object object) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder(" INSERT INTO ").append(getTableName()).append(" (");
			StringBuilder placeholders = new StringBuilder();

			Map<String, Object> columns = buildColumns(object, true);
			Iterator<String> iter = columns.keySet().iterator();
			while (iter.hasNext()) {
				sql.append(iter.next());
				placeholders.append("?");

				if (iter.hasNext()) {
					sql.append(", ");
					placeholders.append(", ");
				}
			}
			sql.append(") VALUES (").append(placeholders).append(")");
			pst = conn.prepareStatement(sql.toString());

			int index = 1;
			for (Object value : columns.values()) {
				pst.setObject(index, value);
				index++;
			}
			MyLogger.getLogger().info(pst.toString());
			pst.execute();
		} finally {
			closeAll(conn, pst, rs);
		}
	}

	public Object getCount(String sql, List<Object> params) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			if (params != null) {
				for (int index = 1; index <= params.size(); index++) {
					pst.setObject(index, params.get(index-1));
				}
			}
			MyLogger.getLogger().info(pst.toString());
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getObject(1);
			}
			return null;
		} finally {
			closeAll(conn, pst, rs);
		}
	}

	public Object search(String sql, List<Object> params) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			if (params != null) {
				for (int index = 1; index <= params.size(); index++) {
					pst.setObject(index, params.get(index-1));
				}
			}
			MyLogger.getLogger().info(pst.toString());
			rs = pst.executeQuery();

			while (rs.next()) {
				return getObject(rs);
			}
			return null;
		} finally {
			closeAll(conn, pst, rs);
		}
	}

	//TODO: Not fully implemented, not needed for scope of project
	public Collection searchCollection(String sql, List<Object> params) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			if (params != null) {
				for (int index = 1; index <= params.size(); index++) {
					pst.setObject(index, params.get(index-1));
				}
			}
			MyLogger.getLogger().info(pst.toString());
			rs = pst.executeQuery();

			while (rs.next()) {

			}
		} finally {
			closeAll(conn, pst, rs);
		}
		return null;
	}

	public Object getObject(ResultSet rs) throws SQLException {
		Object result;
		ResultSetMetaData rsmd = rs.getMetaData();
		Map <String, Object> columns = new HashMap<>();
		int columnCount = rsmd.getColumnCount();

		if (rs != null) {
			for (int i = 1; i <= columnCount; i++) {
				columns.put(rsmd.getColumnName(i), rs.getObject(i));
			}
			result = loadColumns(columns);
			return result;
		}
		return null;
	}
}
