package com.yevhenii.security;

import com.yevhenii.model.Token;
import com.yevhenii.model.User;
import com.yevhenii.services.TokenService;
import com.yevhenii.services.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class RememberMeIdentityStoreImpl implements RememberMeIdentityStore {

    @Inject
    private HttpServletRequest request;

    @Inject
    private UserService userService;

    @Inject
    private TokenService tokenService;


    @Override
    public CredentialValidationResult validate(RememberMeCredential rememberMeCredential) {

        return tokenService.getByRawToken(rememberMeCredential.getToken())
                .map(token1 -> new CredentialValidationResult(new CallerPrincipal(token1.getUser().getUsername())))
                .orElse(CredentialValidationResult.INVALID_RESULT);
    }

    @Override
    public String generateLoginToken(CallerPrincipal callerPrincipal, Set<String> set) {

        return tokenService.generate(callerPrincipal.getName(),
                request.getRemoteAddr(),
                "");
    }

    @Override
    public void removeLoginToken(String s) {
        tokenService.remove(s);
    }
}
