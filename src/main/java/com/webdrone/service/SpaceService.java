package com.webdrone.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.webdrone.model.Space;

@Stateless
public class SpaceService {

	@PersistenceContext
	private EntityManager em;

	public List<Space> listAll() {
		TypedQuery<Space> query = em.createQuery("SELECT s FROM Space s ORDER BY s.id", Space.class);

		return query.getResultList();
	}
}
