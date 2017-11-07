package com.webdrone.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.webdrone.model.BaseModel;

public class BaseService<T extends BaseModel> {

	protected Class<T> entityClass;

	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> listAll(Class clazz) {
		TypedQuery<T> query = getEntityManager()
				.createQuery("SELECT s FROM " + clazz.getSimpleName() + " s ORDER BY s.id", clazz);

		return query.getResultList();
	}

	public void create(Object object) {
		getEntityManager().persist(object);
	}
}
