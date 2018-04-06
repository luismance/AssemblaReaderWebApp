package com.webdrone.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DoubleAdapter extends XmlAdapter<String, Double> {

	@Override
	public Double unmarshal(String v) throws Exception {
		if (v.equalsIgnoreCase("null"))
			return null;

		if (v.isEmpty()) {
			return null;
		}
		return Double.valueOf(v);
	}

	@Override
	public String marshal(Double v) throws Exception {
		return v.toString();
	}

}
