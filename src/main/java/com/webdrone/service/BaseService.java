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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object findByExternalRefId(Class clazz, String externalRefId) {

		List<Object> resultList = getEntityManager().createQuery(
				"SELECT u FROM " + clazz.getSimpleName() + " u WHERE u.externalRefId = '" + externalRefId + "'", clazz)
				.getResultList();
		Object result = resultList != null && resultList.size() > 0 ? resultList.get(0) : null;
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> listAll(Class clazz) {
		TypedQuery<T> query = getEntityManager()
				.createQuery("SELECT s FROM " + clazz.getSimpleName() + " s ORDER BY s.id", clazz);

		return query.getResultList();
	}

	public Object create(Object object) {
		getEntityManager().persist(object);
		getEntityManager().flush();
		getEntityManager().clear();
		return object;
	}

	public Object update(Object object) {
		getEntityManager().merge(object);
		getEntityManager().flush();
		getEntityManager().clear();
		return object;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object find(Class clazz, long id) {
		Object result = getEntityManager().find(clazz, id);
		return result;
	}
}
