package com.yevhenii.services;

import com.yevhenii.exceptions.InvalidUsernameException;
import com.yevhenii.model.Token;
import com.yevhenii.model.User;
import com.yevhenii.security.Algorithm;
import com.yevhenii.security.HashAlgorithm;
import com.yevhenii.security.HashGenerator;
import jdk.nashorn.internal.runtime.options.Option;

import javax.ejb.*;
import javax.inject.Inject;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TokenService {

//    @PersistenceContext
//    private EntityManager manager;

    @PersistenceUnit
    private EntityManagerFactory factory;

    @Inject
//    @HashAlgorithm(algorithm = Algorithm.SHA256)
    private HashGenerator tokenHasher;

    @Inject
    private UserService userService;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private void save(String rawToken, String username, String ipAddress,
                      String description, Instant expiration) {

        User user = userService.getByUsername(username)
                .orElseThrow(InvalidUsernameException::new);

        Token token = new Token();

        token.setCreated(Instant.now());
        token.setDescription(description);
        token.setExpiration(expiration);
        token.setIpAddress(ipAddress);
        token.setTokenHash(tokenHasher.getHashedText(rawToken));

        token.setUser(user);

        factory.createEntityManager().persist(token);
    }

    public String generate(String username, String ipAddress, String description) {

        String raw = UUID.randomUUID().toString();
        Instant expiration = Instant.now();

        save(raw, username, ipAddress, description, expiration);

        return raw;
    }

    public Optional<Token> getByRawToken(String token) {

        try {

            return Optional.of(factory.createEntityManager()
                    .createNamedQuery("select t from Token t where t.tokenHash = :token", Token.class)
                    .setParameter("tokenHash", token)
                    .getSingleResult());
        } catch (NoResultException e) {
            e.printStackTrace();

            return Optional.empty();
        }
    }

    public void remove(String token) {
        factory.createEntityManager()
                .createNamedQuery("delete from Token t where t.tokenHash = :tokenHash", Token.class)
                .setParameter("tokenHash", token)
                .executeUpdate();
    }
}
