package com.dao;

import com.bl.VoteBL;
import com.data.Vote;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class TestVoteDAO {
	private final int mediaId = 1;
	private final String id = "Stranger Things";
	private final Date dateCreated = new Date();

	private VoteDAO dao = new VoteDAO();

	@Test
	public void TestVoteInsert() {
		Vote vote = new Vote(2, VoteBL.THUMBS_UP, dateCreated);
		try {
			dao.save(vote);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestGetOneResult() {
		try {
			Vote result = (Vote) dao.search();
			assertNotNull(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestGetRatingCountById() {
		BigDecimal expected = BigDecimal.valueOf(0);
		try {
			Object count = dao.getRatingCountById(mediaId, VoteBL.THUMBS_DOWN);
			assertNotEquals(expected, count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
