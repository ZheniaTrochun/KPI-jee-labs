package com.yevhenii.security;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.*;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


@AutoApplySession
@RememberMe(
        cookieMaxAgeSeconds = 60 * 60 * 24 * 14,
        cookieSecureOnly = false,
        isRememberMeExpression = "#{self.isRememberMe(httpMessageContext)}"
)
@LoginToContinue(
        loginPage = "/login.xhtml?continue=true",
        errorPage = "",
        useForwardToLogin = false // think about this
)
@ApplicationScoped
public class FormAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Inject
    private IdentityStore identityStore;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                HttpMessageContext httpMessageContext)
            throws AuthenticationException {

        System.out.println("validate request");

        Credential credential = httpMessageContext.getAuthParameters().getCredential();

        System.out.println("credential is null = " + Objects.isNull(credential));

        if (Objects.isNull(credential)) {
            return httpMessageContext.doNothing();
        } else {
            return httpMessageContext.notifyContainerAboutLogin(identityStore.validate(credential));
        }
    }

    public Boolean isRememberMe(HttpMessageContext httpMessageContext) {

        System.out.println("is remember me");
        return httpMessageContext.getAuthParameters().isRememberMe();
//        return false;
    }

    @Override
    public void cleanSubject(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
        HttpAuthenticationMechanism.super.cleanSubject(request, response, httpMessageContext);
    }
}
