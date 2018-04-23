package com.yevhenii.jsf;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import static org.omnifaces.util.Faces.*;
import static org.omnifaces.util.Messages.addGlobalError;

@Model
public class Login {

    private String username;
    private String password;

    @Inject
    private SecurityContext securityContext;

    public void submit() throws IOException {

        Credential credential = new UsernamePasswordCredential(username, new Password(password));

        AuthenticationStatus status = securityContext.authenticate(
                getRequest(),
                getResponse(),
                withParams()
                        .credential(credential)
                        .newAuthentication(false)
                        .rememberMe(true)
        );

        if (status.equals(AuthenticationStatus.SUCCESS)) {
            redirect("index.xhtml");
        } else if (status.equals(AuthenticationStatus.SEND_FAILURE)) {
            addGlobalError("auth.message.error.failure");
            validationFailed();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
