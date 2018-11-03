package com.webdrone.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.webdrone.model.BaseModel;

public class BaseService<T extends BaseModel> {

	protected Class<T> entityClass;

	@PersistenceContext(unitName = "webdroneAssembla")
	private EntityManager entityManager;

	public BaseService() {
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object findByExternalRefId(EntityManager em, Class clazz, String externalRefId) {

		List<Object> resultList = em.createQuery("SELECT u FROM " + clazz.getSimpleName() + " u WHERE u.externalRefId = '" + externalRefId + "'", clazz).getResultList();
		Object result = resultList != null && resultList.size() > 0 ? resultList.get(0) : null;
		return result;
	}

	@SuppressWarnings({ "rawtypes" })
	public Object findByExternalRefId(Class clazz, String externalRefId) {
		return findByExternalRefId(getEntityManager(), clazz, externalRefId);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> listAll(Class clazz) {
		TypedQuery<T> query = getEntityManager().createQuery("SELECT s FROM " + clazz.getSimpleName() + " s ORDER BY s.id", clazz);

		return query.getResultList();
	}

	public Object create(EntityManager em, Object object) {
		em.persist(object);
		em.flush();
		em.clear();
		return object;
	}

	public Object create(Object object) {
		return create(getEntityManager(), object);
	}

	public Object update(EntityManager em, Object object) {

		em.merge(object);
		em.flush();
		em.clear();
		return object;
	}

	public Object threadCreate(UserTransaction utx, EntityManager em, Object object)
			throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		utx.begin();
		em.joinTransaction();
		em.persist(object);
		em.flush();
		em.clear();
		utx.commit();
		return object;
	}

	public Object threadUpdate(UserTransaction utx, EntityManager em, Object object)
			throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		utx.begin();
		em.joinTransaction();
		em.merge(object);
		em.flush();
		em.clear();
		utx.commit();
		return object;
	}
	
	public Object update(Object object) {
		return update(getEntityManager(), object);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object find(EntityManager em, Class clazz, long id) {
		Object result = em.find(clazz, id);
		return result;
	}

	@SuppressWarnings({ "rawtypes" })
	public Object find(Class clazz, long id) {
		return find(getEntityManager(), clazz, id);
	}
}
