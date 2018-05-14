package com.yevhenii.security;

import com.yevhenii.exceptions.InvalidUsernameException;
import com.yevhenii.model.User;
import com.yevhenii.services.UserService;
import org.apache.http.auth.InvalidCredentialsException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;

@ApplicationScoped
@Default
public class MyIdentityStore implements IdentityStore {

    @Inject
    private UserService userService;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        System.out.println("Validating...");
        try {

            // check if the credential was UsernamePasswordCredential
            if (credential instanceof UsernamePasswordCredential) {
                String username = ((UsernamePasswordCredential) credential).getCaller();
                String password = ((UsernamePasswordCredential) credential).getPasswordAsString();

                System.out.println(userService.getByUsername(username).get().toString());

                return validate(this.userService.getByUsernameAndPassword(username, password));
            }

            // check if the credential was UsernamePasswordCredential
            if (credential instanceof CallerOnlyCredential) {
                String username = ((CallerOnlyCredential) credential).getCaller();

                return validate(
                        this.userService.getByUsername(username)
                                .orElseThrow(ValidationException::new)
                );
            }

        } catch (ValidationException e) {
            System.out.println("exception during validation");
            e.printStackTrace();

            return INVALID_RESULT;
        }
        return NOT_VALIDATED_RESULT;
    }

    // before return the CredentialValidationResult, check if the account is active or not
    private CredentialValidationResult validate(User account) {

        return new CredentialValidationResult(account.getUsername());
    }
}
