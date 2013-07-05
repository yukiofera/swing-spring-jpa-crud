package br.com.yaw.ssjpac.dao.generic.impl;

import br.com.yaw.ssjpac.dao.generic.AbstractRepository;
import br.com.yaw.ssjpac.util.BeanReflection;
import br.com.yaw.ssjpac.util.ReflectionUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marcelo Yukio Outa
 * @since 14/06/2013
 */
@Repository
@Transactional(readOnly = true)
public class AbstractRepositoryImpl<T, ID extends Serializable> implements AbstractRepository<T, ID> {

    @PersistenceContext
    protected EntityManager em;
    private Class<T> persistentClass;

    public AbstractRepositoryImpl() {
        this.persistentClass = (Class<T>) ReflectionUtil.getTypeArguments(AbstractRepositoryImpl.class, getClass()).get(0);
    }

    protected Class<T> getPersistentClass() {
        return persistentClass;
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return em.getCriteriaBuilder();
    }

    public CriteriaQuery<T> createCriteriaQuery() {
        return getCriteriaBuilder().createQuery(getPersistentClass());
    }

    public CriteriaQuery<T> createCriteriaQuery(CriteriaBuilder cb) {
        return cb.createQuery(getPersistentClass());
    }

//    public JPAQuery getJPAQuery() {
//        return new JPAQuery(em);
//    }

    @Transactional
    public void flush() {
        em.flush();
    }

    @Transactional
    public T save(T entity) {
        boolean aNew = ((Persistable) entity).isNew();
        if (isNew(entity)) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }

    @Transactional
    public List<T> save(Iterable<T> entities) {
        List<T> result = new ArrayList<T>();

        if (entities == null) {
            return result;
        }

        for (T entity : entities) {
            result.add(save(entity));
        }

        return result;
    }

    @Transactional
    public void delete(ID id) {
        delete(findById(id));
    }

    @Transactional
    public void delete(T entity) {
        em.remove(entity);
    }

    public T findById(ID id) {
        return em.find(persistentClass, id);
    }

    public List<T> findAll() {
//        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<T> cq = createCriteriaQuery();
        cq.select(cq.from(getPersistentClass()));

        return em.createQuery(cq).getResultList();
    }

    public Long count() {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(getPersistentClass());
        cq.select(cb.count(root));

        return em.createQuery(cq).getSingleResult();
    }

    private boolean isNew(T entity) {
        return getPrimaryKeyValue(entity) == null;
    }

    private Long getPrimaryKeyValue(T entity) {
        return BeanReflection.getPrimaryKeyValue(entity, entity.getClass().getSuperclass().getClass().getSuperclass());
    }
}
