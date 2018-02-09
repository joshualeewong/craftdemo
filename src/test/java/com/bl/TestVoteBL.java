package com.bl;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestVoteBL {
	private static int mediaId;
	private static final String id = "Doctor Strange";
	private static final String voteDown = "-1";
	private static final String voteUp = "1";

	private VoteBL bl = new VoteBL();

	@Before
	public void setUp() {
		mediaId = new MediaBL().getMediaIdByTitleId(id);
	}

	@Test
	public void TestCastVoteUp() {
		BigDecimal preVoteCast = bl.getVoteRatingSumById(mediaId, "1");
		preVoteCast = preVoteCast.add(BigDecimal.valueOf(1L));

		bl.castVote(mediaId, id, voteUp);

		BigDecimal postVoteCast = bl.getVoteRatingSumById(mediaId, "1");
		assertEquals(preVoteCast, postVoteCast);
	}

	@Test
	public void TestCastVoteDown() {
		BigDecimal preVoteCast = bl.getVoteRatingSumById(mediaId, "-1");
		preVoteCast = preVoteCast.add(BigDecimal.valueOf(1L));

		bl.castVote(mediaId, id, voteDown);

		BigDecimal postVoteCast = bl.getVoteRatingSumById(mediaId, "-1");
		assertEquals(preVoteCast, postVoteCast);
	}

	@Test
	public void TestGetTotalVotesById() {
		BigDecimal expectedThumbsUp = bl.getVoteRatingSumById(mediaId, "1");
		BigDecimal expectedThumbsDown = bl.getVoteRatingSumById(mediaId, "-1");

		Map<String, Object> result = bl.getTotalVotesById(mediaId, id);

		assertEquals(id, result.get(VoteBL.TOTAL_VOTES_MAP.ID.name()));
		assertEquals(expectedThumbsUp, result.get(VoteBL.TOTAL_VOTES_MAP.THUMBS_UP.name()));
		assertEquals(expectedThumbsDown, result.get(VoteBL.TOTAL_VOTES_MAP.THUMBS_DOWN.name()));
	}
}
