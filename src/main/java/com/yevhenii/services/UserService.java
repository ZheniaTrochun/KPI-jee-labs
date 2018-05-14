package com.yevhenii.services;

import com.yevhenii.model.User;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserService {

    @PersistenceContext(unitName = "Movie")
    private EntityManager manager;


    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void register(String username, String password) {

        User user = new User(username, password);

        manager.persist(user);
    }

    public Optional<User> getByUsername(String username) {

        try {
            return Optional.of(
                    manager.createQuery("select a from User a where a.username = :username", User.class)
//                    manager.createNamedQuery("select a from User a where a.username = :username", User.class)
                            .setParameter("username", username)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public User getByUsernameAndPassword(String username, String password) {

        return manager.createQuery(
                    "select a from User a where a.username = :username and a.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
    }
}
