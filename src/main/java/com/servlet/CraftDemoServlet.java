package com.servlet;

import com.bl.MediaBL;
import com.bl.VoteBL;
import com.data.Response;
import com.google.gson.Gson;
import com.service.MediaJSONReader;
import com.util.MyLogger;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.*;

@javax.servlet.annotation.WebServlet(name = "CraftDemo")
public class CraftDemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * enum for allowable Actions by servlet
	 */
	private enum CRAFTDEMO_ACTION {
		LOAD_MEDIA_JSON, CAST_VOTE
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CRAFTDEMO_ACTION action = CRAFTDEMO_ACTION.valueOf(request.getParameter("ACTION_NAME"));
		response.setContentType("application/json");

		try {
			if (action == CRAFTDEMO_ACTION.LOAD_MEDIA_JSON) {
				Gson gson = new Gson();
				MediaJSONReader reader = new MediaJSONReader();
				Response data = reader.parseGson();
				response.getOutputStream().print(gson.toJson(data));
				response.getOutputStream().flush();
			} else if (action == CRAFTDEMO_ACTION.CAST_VOTE) {
				MyLogger.getLogger().info("CASTED VOTE PARAMS " + request.getParameter("PARAMS"));
				if (request.getParameter("PARAMS") != null) {
					JSONObject params = new JSONObject(request.getParameter("PARAMS"));
					String id = null;
					String vote = null;
					if (params.has("ID") && !params.getString("ID").isEmpty()) {
						id = params.getString("ID");
					}
					if (params.has("VOTE") && !params.getString("VOTE").isEmpty()) {
						vote = params.getString("VOTE");
					}
					MediaBL mediaBL = new MediaBL();
					int mediaId = mediaBL.getMediaIdByTitleId(id);

					VoteBL voteBL = new VoteBL();
					voteBL.castVote(mediaId, id, vote);
					Map<String,Object> totalVotes = voteBL.getTotalVotesById(mediaId, id);
					Gson gson = new Gson();
					String result = gson.toJson(totalVotes);
					MyLogger.getLogger().info("CAST VOTE response " + result);
					response.getOutputStream().print(result);
					response.getOutputStream().flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
