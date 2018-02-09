package com.data;

import java.util.Date;

public class Movies extends Media {
	public Movies() {
	}

	public Movies(String _id, String _description, String[] _category, String _type, Date _dateCreated) {
		super(_id, _description, _category, _type, _dateCreated);
	}
}
