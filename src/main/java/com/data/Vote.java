package com.data;

import java.util.Date;

public class Vote {
	private int voteId;
	private int mediaId;
	private int voteRating;
	private Date dateCreated;

	public Vote() {
	}

	public Vote(int _mediaId, int _voteRating, Date _dateCreated) {
		mediaId = _mediaId;
		voteRating = _voteRating;
		dateCreated = _dateCreated;
	}

	public int getVoteId() {
		return voteId;
	}

	public void setVoteId(int _voteId) {
		voteId = _voteId;
	}

	public int getMediaId() {
		return mediaId;
	}

	public void setMediaId(int _mediaId) {
		mediaId = _mediaId;
	}

	public int getVoteRating() {
		return voteRating;
	}

	public void setVoteRating(int _voteRating) {
		voteRating = _voteRating;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date _dateCreated) {
		dateCreated = _dateCreated;
	}
}
