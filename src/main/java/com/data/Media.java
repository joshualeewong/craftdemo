package com.data;

import java.util.Date;

public class Media {
	private String id;
	private String description;
	private String[] category;
	private Date dateCreated;
	private static String DELIMITER = ", ";

	public Media() {
	}

	public Media(String _id, String _description, String[] _category, String _type, Date _dateCreated) {
		id = _id;
		description = _description;
		category = _category;
		dateCreated = _dateCreated;
	}

	public enum MEDIA_TYPE {
		MOVIES, TV
	}

	public String getId() {
		return id;
	}

	public void setId(String _id) {
		id = _id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String _description) {
		description = _description;
	}

	public String[] getCategory() {
		return category;
	}

	public void setCategory(String[] _category) {
		category = _category;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date _dateCreated) {
		dateCreated = _dateCreated;
	}

	public String getCategoryToString() {
		StringBuilder out = new StringBuilder();
		for (String item : category) {
			out.append(item);
			out.append(DELIMITER);
		}
		return out.toString().substring(0, out.length()-DELIMITER.length());
	}
}
