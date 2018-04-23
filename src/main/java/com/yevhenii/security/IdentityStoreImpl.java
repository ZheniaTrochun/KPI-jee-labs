package com.yevhenii.security;

import com.yevhenii.exceptions.InvalidUsernameException;
import com.yevhenii.model.User;
import com.yevhenii.services.UserService;
import org.apache.http.auth.InvalidCredentialsException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

@ApplicationScoped
public class IdentityStoreImpl implements IdentityStore {

    @Inject
    private UserService userService;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        try {
            if (credential instanceof UsernamePasswordCredential) {
                UsernamePasswordCredential creds = (UsernamePasswordCredential) credential;

                String username = creds.getCaller();
                String pass = creds.getPasswordAsString();

                User user = userService.getByUsernameAndPassword(username, pass);

                if (user != null)
                    return new CredentialValidationResult(user.getUsername());
            }


        } catch (NoResultException e) {
            e.printStackTrace();

            return CredentialValidationResult.INVALID_RESULT;
        }

        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
}
