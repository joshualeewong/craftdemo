package com.dao;

import com.data.Vote;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteDAO extends MySQLAbstractDAO {
	public enum COLUMNS {
		MEDIA_ID, VOTE_RATING, DATE_CREATED
	}

	public VoteDAO() {
		super();
	}

	@Override
	protected String getTableName() {
		return "VOTE";
	}

	@Override
	protected String getPrimaryKey() {
		return "VOTE_ID";
	}

	@Override
	protected Object loadColumns(Map<String, Object> _columns) {
		Vote vote = new Vote();
		vote.setVoteId((int) _columns.get(getPrimaryKey()));
		vote.setMediaId((int) _columns.get(COLUMNS.MEDIA_ID.name()));
		vote.setVoteRating((int) _columns.get(COLUMNS.VOTE_RATING.name()));
		vote.setDateCreated((Date) _columns.get(COLUMNS.DATE_CREATED.name()));
		return vote;
	}

	@Override
	protected Map<String, Object> buildColumns(Object object, boolean isInsert) {
		Vote vote = (Vote) object;
		Map<String, Object> map = new HashMap<>();
		map.put(COLUMNS.MEDIA_ID.name(), vote.getMediaId());
		map.put(COLUMNS.VOTE_RATING.name(), vote.getVoteRating());
		map.put(COLUMNS.DATE_CREATED.name(), vote.getDateCreated());
		return map;
	}

	@Override
	public void save(Object object) throws SQLException {
		super.save(object);
	}

	public Object search() throws SQLException {
		List<Object> params = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" SELECT * FROM ").append(getTableName()).append(" ORDER BY 1 DESC LIMIT 1 ");
		return search(sql.toString(), params);
	}

	/*
	 * Started to implement method to use single query to return total votes but required further implementation to parse resultSet
	 */
	public Collection getTotalVotesById(int _id) throws SQLException {
		List<Object> params = new ArrayList<>();
		params.add(_id);
		StringBuilder sql = new StringBuilder(" SELECT v.MEDIA_ID, m.ID, SUM(IF(v.VOTE_RATING = 1, 1, 0)) AS THUMBS_UP, SUM(IF(v.VOTE_RATING = -1, 1, 0)) AS THUMBS_DOWN ");
		sql.append(" FROM ").append(getTableName()).append(" v JOIN MEDIA m ON v.MEDIA_ID=m.MEDIA_ID ");
		sql.append(" WHERE v.MEDIA_ID=? ");
		return searchCollection(sql.toString(), params);
	}

	/*
	 * Method to get sum of votes for either thumbs up or thumbs down for a particular _id
	 *
	 * @param _id String title of show
	 * @param _voteRating vote to get sum of, either 1 or -1
	 *
	 * @return sum of votes as positive value
	 */
	public Object getRatingCountById(int _mediaId, int _voteRating) throws SQLException {
		List<Object> params = new ArrayList<>();
		params.add(_mediaId);
		params.add(_voteRating);
		StringBuilder sql = new StringBuilder(" SELECT ABS(SUM(v.VOTE_RATING)) FROM ").append(getTableName()).append(" v JOIN MEDIA m ON v.MEDIA_ID = m.MEDIA_ID WHERE v.MEDIA_ID= ? AND v.VOTE_RATING = ? ");
		return getCount(sql.toString(), params);
	}
}
