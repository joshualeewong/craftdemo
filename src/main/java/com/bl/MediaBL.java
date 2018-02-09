package com.bl;

import com.dao.MediaDAO;

import java.sql.SQLException;

public class MediaBL {
	/*
	 * Method to get primary key MEDIA_ID by ID (title)
	 *
	 * @param _id String title of show
	 * @return primary key value MEDIA.MEDIA_ID, foreign key for VOTE table
	 */
	public int getMediaIdByTitleId(String _id) {
		try {
			MediaDAO dao = new MediaDAO();
			return dao.getMediaIdById(_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
