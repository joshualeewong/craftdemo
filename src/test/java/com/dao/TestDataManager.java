package com.dao;

import com.util.MyLogger;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.assertNotNull;

public class TestDataManager {
	@Before
	public void startUp() {
		DataManager.setSAConnection();
	}

	@Test
	public void testMediaDAO() throws Exception {
		Connection conn = DataManager.getConnection();
		Object output;
		assertNotNull(conn);
		String sql = "SELECT COUNT(*) FROM MEDIA";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			output = rs.getObject(1);
			assertNotNull(output);
			MyLogger.getLogger().severe("Count of media rows : " + output.toString());
		}
	}

	@Test
	public void testDataManager() throws Exception {
		Connection conn = DataManager.getConnection();
		assertNotNull(conn);
	}
}
