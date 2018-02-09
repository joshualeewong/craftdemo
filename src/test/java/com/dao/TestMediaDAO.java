package com.dao;

import com.data.Media;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class TestMediaDAO {
	private final String id = "Westworld";
	private final String description = "When robots go rogue.";
	private final String[] category = null; //{"Scifi", "Horror"};
	private final String type = "tv";
	private final Date dateCreated = new Date();

	private MediaDAO dao = new MediaDAO();

	@Before
	public void startUp() {
		DataManager.setSAConnection();
	}

	@Test
	public void TestMediaInsert() {
		Media media = new Media(id, description, category, Media.MEDIA_TYPE.TV.name(), dateCreated);
		try {
			dao.save(media);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestGetMediaIdById() {
		try {
			int result = dao.getMediaIdById("Stranger Things");
			assertEquals(1, result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestGetOneResult() {
		try {
			Media result = (Media) dao.search();
			assertNotNull(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {
		try {
			boolean success = false;
			Connection conn = DataManager.getConnection();
			StringBuilder sql = new StringBuilder(" DELETE FROM MEDIA WHERE ID=? " );
			PreparedStatement pst = conn.prepareStatement(sql.toString());
			pst.setString(1, id);
			success = pst.execute();
			dao.closeAll(conn, pst, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
