package com.yevhenii.dao.abstraction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class PaginatedDao <E, K> extends AbstractDao<E, K> {

    private int pageSize = 10;

    public PaginatedDao(Class<E> type, String tableName, int pageSize) {
        super(type, tableName);
        this.pageSize = pageSize;
    }

    public PaginatedDao(Class<E> type, String tableName) {
        super(type, tableName);
    }

    public PaginatedDao(Class<E> type, EntityManagerFactory entityManagerFactory) {
        super(type, entityManagerFactory);
    }

    public List<E> findAllByPage(int page) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();

        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(type);
        Root<E> root = cq.from(type);
        CriteriaQuery<E> all = cq.select(root);
        TypedQuery<E> allQuery = manager.createQuery(all);
        allQuery.setFirstResult(page * (pageSize - 1));
        allQuery.setMaxResults(pageSize);

        List<E> res = allQuery.getResultList();
        manager.close();

        return res;
    }

//    TODO finish
    public List<E> findByQueryAndPage(Predicate query, int page) {
//        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
//
//        CriteriaBuilder cb = manager.getCriteriaBuilder();
//        CriteriaQuery<E> cq = cb.createQuery(type);
//        Root<E> root = cq.from(type);
//        CriteriaQuery<E> all = cq.select(root).where();
//        TypedQuery<E> allQuery = manager.createQuery(all);
//        allQuery.setFirstResult(page * (pageSize - 1));
//        allQuery.setMaxResults(pageSize);
//
//        List<E> res = allQuery.getResultList();
//        manager.close();
//
//        return res;

//        return connectionManager.withConnection(connection ->
//                extractAllEntities(
//                        connection
//                                .prepareStatement(String.format("%s LIMIT %d, %d", query, (page - 1) * pageSize, pageSize))
//                                .executeQuery()
//                )
//        ).getOrElse(new ArrayList<>());

        return null;
    }
}
