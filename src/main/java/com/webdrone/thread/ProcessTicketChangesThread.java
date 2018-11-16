package com.webdrone.thread;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

public class ProcessTicketChangesThread implements Runnable {

	private String username;

	private EntityManager entityManager;

	private UserTransaction utx;

	public ProcessTicketChangesThread() {

	}

	public ProcessTicketChangesThread(UserTransaction utx, EntityManager em, String username) {
		this.username = username;
		this.entityManager = em;
		this.utx = utx;
	}

	public void run() {
		ProcessTicketChanges.processTicketChanges(utx, entityManager, username);
	}
}
