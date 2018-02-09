package com.data;

import java.util.Date;

public class Tv extends Media {
	public Tv() {
	}

	public Tv(String _id, String _description, String[] _category, String _type, Date _dateCreated) {
		super(_id, _description, _category, _type, _dateCreated);
	}
}
