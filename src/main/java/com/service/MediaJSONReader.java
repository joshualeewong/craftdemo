package com.service;

import com.google.gson.Gson;
import com.data.Movies;
import com.data.Response;
import com.data.Tv;
import com.util.MyLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MediaJSONReader {
	private static final String JSON_CONTENT="{\"movies\":[{\"id\":\"Stranger Things\",\"description\":\"When a young boy disappears, his mother, a police chief, and his friends must confront terrifying forces in order to get him back.\",\"category\":[\"Popular\",\"Horor\"]},{\"id\":\"Doctor Strange\",\"description\":\"After a neurosurgeon loses the use of this hangs he meets a mystical mentor who helps him harness magic to become the most powerful sorcerer on Earth.\",\"category\":[\"Action\"]},{\"id\":\"Planet Earth\",\"description\":\"This landmark series transports nature lovers all over the earth.\",\"category\":[\"Documentary\"]}],\"tv\":[{\"id\":\"Flash\",\"description\":\"Zoom challenges Barry to a race, but the team suspects a trap and tries to talk Barry out of it.\",\"category\":[\"Action\"]},{\"id\":\"Friends\",\"description\":\"This his sitcom follows the merry misadventures of six 20-somethings pals as they naviagte the pitfalls of work, life and love.\",\"category\":[\"Comedy\"]},{\"id\":\"Blue Bloods\",\"description\":\"New evidence surfaces against Danny in his shooting of a serial killer.\",\"category\":[\"Drama\"]}]}";

	/*
	 * Method to parse JSON file
	 *
	 * @return Response object of JSON sample file
	 */
	public Response parseGson() {
		try {
			Gson gson = new Gson();
			return gson.fromJson(JSON_CONTENT, Response.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Response parseGson(File file) {
		try {
			String content = fileToString(file);
			Gson g = new Gson();
			MyLogger.getLogger().info(content);
			return g.fromJson(content, Response.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void saveToDB(Response _response) {
		// Do nothing because this isn't needed for scope
	}

	/*
	 * Method to parse Response object into human readable format
	 */
	public void printResponse(Response _response) {
		if (_response != null) {
			if (!_response.movies.isEmpty()) {
				for (Movies mov : _response.movies) {
					MyLogger.getLogger().info("Title: " + mov.getId()
							+ " \n  Description: " + mov.getDescription()
							+ " \n  Category: " + mov.getCategoryToString());
				}
			}
			if (!_response.tv.isEmpty()) {
				for (Tv tv : _response.tv) {
					MyLogger.getLogger().info("Title: " + tv.getId()
							+ " \n  Description: " + tv.getDescription()
							+ " \n  Category: " + tv.getCategoryToString());
				}
			}
		} else {
			MyLogger.getLogger().severe("Response is empty.");
		}
	}

	private String fileToString(File _file) throws IOException {
		try (InputStream is = new FileInputStream(_file)){
			byte fileContent[] = new byte[(int) _file.length()];
			is.read(fileContent);
			String content = printByteStream(fileContent);
			is.close();
			return content;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String printByteStream(byte[] _stream) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < _stream.length; i++) {
			sb.append((char) _stream[i]);
		}
		return sb.toString();
	}
}
