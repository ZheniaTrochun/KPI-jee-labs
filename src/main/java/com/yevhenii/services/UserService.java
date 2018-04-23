package com.yevhenii.services;

import com.yevhenii.model.User;
import com.yevhenii.security.Algorithm;
import com.yevhenii.security.HashAlgorithm;
import com.yevhenii.security.HashGenerator;

import javax.ejb.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.Optional;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserService {

    @PersistenceContext
    private EntityManager manager;

    @Inject
    @HashAlgorithm(algorithm = Algorithm.SHA256)
    private HashGenerator tokenHasher;

    @Inject
    @HashAlgorithm(algorithm = Algorithm.SHA256)
    private HashGenerator passHasher;


//    @Inject
//    private TokenService tokenService;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void register(String username, String password) {
        String hashedPass = passHasher.getHashedText(password);

        User user = new User(username, hashedPass);

        manager.persist(user);
    }

    public Optional<User> getByUsername(String username) {

        try {
            return Optional.of(
                    manager.createNamedQuery("select a from Account a where a.username = :username", User.class)
                            .setParameter("username", username)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public User getByUsernameAndPassword(String username, String password) {

        return manager.createNamedQuery(
                    "select a from Account a where a.username = :username and a.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", passHasher.getHashedText(password))
                .getSingleResult();
    }
}
