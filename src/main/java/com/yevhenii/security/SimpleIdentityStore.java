package com.yevhenii.security;

import com.yevhenii.services.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@ApplicationScoped
public class SimpleIdentityStore implements IdentityStore {

    @Inject
    private UserService userService;

    @Override
    public CredentialValidationResult validate(Credential credential) {

        UsernamePasswordCredential user = (UsernamePasswordCredential) credential;

        String username = user.getCaller();
        String password = user.getPasswordAsString();

        return userService.getByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .map(u -> new CredentialValidationResult(username, new HashSet<>(Collections.singletonList("user"))))
                .orElse(INVALID_RESULT);
    }
}
