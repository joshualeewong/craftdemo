package com.dao;

import com.data.Media;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaDAO extends MySQLAbstractDAO {
	public MediaDAO() {
		super();
	}

	public enum COLUMNS {
		ID, DESCRIPTION, CATEGORY, TYPE, DATE_CREATED
	}

	protected String getTableName() {
		return "MEDIA";
	}

	protected String getPrimaryKey() {
		return "MEDIA_ID";
	}

	protected Object loadColumns(Map _columns) {
		Media media = new Media();
		media.setId((String) _columns.get(MediaDAO.COLUMNS.ID.name()));
		media.setDescription((String) _columns.get(MediaDAO.COLUMNS.DESCRIPTION.name()));
		String category = (String) _columns.get(MediaDAO.COLUMNS.CATEGORY.name());
		media.setCategory(category.split(","));
		media.setDateCreated((Date) _columns.get(MediaDAO.COLUMNS.DATE_CREATED.name()));
		return media;
	}

	public Map<String, Object> buildColumns(Object object, boolean isInsert) {
		Media media = (Media) object;
		Map<String, Object> map = new HashMap<>();
		map.put(MediaDAO.COLUMNS.ID.name(), media.getId());
		map.put(MediaDAO.COLUMNS.DESCRIPTION.name(), media.getDescription());
		map.put(MediaDAO.COLUMNS.CATEGORY.name(), media.getCategory());
		map.put(MediaDAO.COLUMNS.DATE_CREATED.name(), media.getDateCreated());
		return map;
	}

	/*
	 * Save Object into database
	 */
	@Override
	public void save(Object _objectSave) throws SQLException {
		super.save(_objectSave);
	}

	/*
	 * Delete by _id, String title of show
	 * DAO JUnit helper
	 */
	//TODO: To finish, not within scope of project
	public void deleteById(String _id) throws  SQLException {
		List<Object> params = new ArrayList<>();
		params.add(_id);
		StringBuilder sql = new StringBuilder(" DELETE FROM ").append(getTableName()).append(" WHERE ID= ");
	}

	/*
	 * Method to get primary key MEDIA_ID by ID (title)
	 *
	 * @param _id String title of show
	 * @return primary key value MEDIA.MEDIA_ID, foreign key for VOTE table
	 */
	public int getMediaIdById(String _id) throws SQLException {
		List<Object> params = new ArrayList<>();
		params.add(_id);
		StringBuilder sql = new StringBuilder(" SELECT ").append(getPrimaryKey()).append(" FROM ").append(getTableName()).append(" WHERE ID= ? ");
		return (int) getCount(sql.toString(), params);
	}

	/*
	 * Method to return one record from MEDIA table, for DAO unit testing
	 */
	public Object search() throws SQLException {
		List<Object> params = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" SELECT * FROM ").append(getTableName()).append(" ORDER BY 1 DESC LIMIT 1 ");
		return search(sql.toString(), params);
	}

}
