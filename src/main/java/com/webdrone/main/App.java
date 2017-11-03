package com.webdrone.main;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("")
public class App extends Application {

	private Set<Object> singletons = new HashSet<Object>();

	public App() {
		singletons.add(new RestServices());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
