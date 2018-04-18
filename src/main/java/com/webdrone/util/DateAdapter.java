package com.webdrone.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateAdapter extends XmlAdapter<String, DateTime> {

	@Override
	public String marshal(DateTime v) throws Exception {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
		return formatter.print(v);
	}

	@Override
	public DateTime unmarshal(String v) throws Exception {
		if (v.isEmpty()) {
			return null;
		} else if (v.length() == 10 && v.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d")) {
			DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
			return formatter.parseDateTime(v);
		} else {
			DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
			return formatter.parseDateTime(v);
		}
	}
}
