package com.data;

import com.service.MediaJSONReader;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class TestMediaJSONReader {

	@Test
	public void TestParseGson() throws Exception {
		MediaJSONReader reader = new MediaJSONReader();
		Response resp = reader.parseGson();
		assertEquals("Stranger Things", resp.movies.get(0).getId());
		assertEquals("Doctor Strange", resp.movies.get(1).getId());
		assertEquals("Planet Earth", resp.movies.get(2).getId());

		assertEquals("Flash", resp.tv.get(0).getId());
		assertEquals("Friends", resp.tv.get(1).getId());
		assertEquals("Blue Bloods", resp.tv.get(2).getId());
	}
}
