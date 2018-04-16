package com.yevhenii.dao.abstraction;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.annotation.Annotation;
import java.util.*;

public abstract class AbstractDao<E, T> implements Dao<E, T> {

    protected Class<E> type;
    protected final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    public AbstractDao(Class<E> type, String tableName) {
        this.type = type;

        ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(tableName);
    }

    public AbstractDao(Class<E> type, EntityManagerFactory entityManagerFactory) {
        this.type = type;

        ENTITY_MANAGER_FACTORY = entityManagerFactory;
    }

    @Override
    public Optional<E> findOne(T id){

        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();

        E res = manager.find(type, id);

        return Optional.ofNullable(res);
    }

    @Override
    public List<E> findAll() {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();

        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(type);
        Root<E> root = cq.from(type);
        CriteriaQuery<E> all = cq.select(root);
        TypedQuery<E> allQuery = manager.createQuery(all);

        return allQuery.getResultList();
    }

    @Override
    public boolean delete(T id) {

        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        manager.getTransaction().begin();

        E entity = manager.find(type, id);
        manager.remove(entity);

        manager.getTransaction().commit();
        manager.close();

        return true;
    }

    protected abstract T extractId(E entity);

    @Override
    public T save(E entity) {

        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        manager.getTransaction().begin();

        manager.persist(entity);

        manager.getTransaction().commit();
        manager.close();

        return extractId(entity);
    }

    @Override
    public boolean update(E entity) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        manager.getTransaction().begin();

        manager.merge(entity);

        manager.getTransaction().commit();
        manager.close();

        return true;
    }

    @Override
    public void createSchema() {
        withTransaction((manager) ->
                manager.createNativeQuery(getCreateSchemaQuery()).executeUpdate());
    }

    @Override
    public void dropSchema() {
        withTransaction((manager) ->
                manager.createNativeQuery(getDropSchemaQuery()).executeUpdate());
    }

//    TODO think about it
    protected <G> G withTransaction(WithTransaction<G> trx) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        manager.getTransaction().begin();

        G res = trx.transactional(manager);

        manager.getTransaction().commit();
        manager.close();

        return res;
    }

    protected abstract String getCreateSchemaQuery();
    protected abstract String getDropSchemaQuery();


    @FunctionalInterface
    interface WithTransaction<G> {
        G transactional(EntityManager manager);
    }
}
