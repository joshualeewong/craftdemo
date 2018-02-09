package com.bl;

import com.dao.VoteDAO;
import com.data.Vote;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VoteBL {
	private Vote userVote = new Vote();
	public static final int THUMBS_UP = 1;
	public static final int THUMBS_DOWN = -1;

	private VoteDAO dao = new VoteDAO();

	/*
	 * enum to map total count of votes for UI
	 */
	public enum TOTAL_VOTES_MAP {
		MEDIA_ID, ID, THUMBS_UP, THUMBS_DOWN
	}

	/*
	 * Method to cast a vote inserting record into VOTE table
	 *
	 * @param _id String title of show
	 * @param _vote Positive for thumbs up, negative for thumbs down
	 * @return
	 */
	public void castVote(int _mediaId, String _id, String _vote) {
		try {
			if (_id == null) {
				throw new IllegalArgumentException("Must pass valid params for id");
			}
			if (_mediaId == 0) {
				_mediaId = new MediaBL().getMediaIdByTitleId(_id);
			}
			int voteRating = getVoteRatingValue(_vote);
			userVote.setMediaId(_mediaId);
			userVote.setVoteRating(voteRating);
			userVote.setDateCreated(new Date());

			dao.save(userVote);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Method to get total count of thumbs up and thumbs down votes, passed to UI
	 * Calls getVoteRatingSumById() to get sum for each
	 *
	 * @param _id String title of show
	 * @return map HashMap of TOTAL_VOTES_MAP and corresponding values, can be converted to JSON for UI via Gson
	 */
	public Map getTotalVotesById(int _mediaId, String _id) {
		if (_id == null) {
			throw new IllegalArgumentException("Must pass valid params for id");
		}
		if (_mediaId == 0) {
			_mediaId = new MediaBL().getMediaIdByTitleId(_id);
		}
		Map result = new HashMap<String, Object>();
		result.put(TOTAL_VOTES_MAP.MEDIA_ID.name(), _mediaId);
		result.put(TOTAL_VOTES_MAP.ID.name(), _id);
		result.put(TOTAL_VOTES_MAP.THUMBS_UP.name(), getVoteRatingSumById(_mediaId, String.valueOf(THUMBS_UP)));
		result.put(TOTAL_VOTES_MAP.THUMBS_DOWN.name(), getVoteRatingSumById(_mediaId, String.valueOf(THUMBS_DOWN)));
		return result;
	}

	/*
	 * Method to get total count of votes for either thumbs up OR thumbs down
	 *
	 * @param _id String title of show
	 * @param _vote Positive for thumbs up, negative for thumbs down
	 * @return sum of votes for either ThumbUp or ThumbDown
	 */
	public BigDecimal getVoteRatingSumById(int _mediaId, String _vote) {
		if (_mediaId == 0 || _vote == null) {
			throw new IllegalArgumentException("Must pass valid params for vote");
		}
		int voteRating = getVoteRatingValue(_vote);
		try {
			return (BigDecimal) dao.getRatingCountById(_mediaId, voteRating);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return BigDecimal.valueOf(0L);
	}

	/*
	 * Method to convert string representation of vote to integer
	 *
	 * @param _value String representation of vote
	 * @return integer value for SQL comparison, > or < 0
	 */
	private int getVoteRatingValue(String _value) {
		if (THUMBS_UP == Integer.parseInt(_value)) {
			return THUMBS_UP;
		} else if (THUMBS_DOWN == Integer.parseInt(_value)) {
			return THUMBS_DOWN;
		}
		return 0;
	}
}
