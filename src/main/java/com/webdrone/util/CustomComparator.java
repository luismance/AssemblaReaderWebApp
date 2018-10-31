package com.webdrone.util;

import java.util.Comparator;

import com.webdrone.model.Space;

public class CustomComparator implements Comparator<Space> {

	@Override
	public int compare(Space o1, Space o2) {
		return o1.getWikiname().compareTo(o2.getWikiname());
	}
}
