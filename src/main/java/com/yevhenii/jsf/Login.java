package com.yevhenii.jsf;

import org.omnifaces.cdi.Param;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Named
@RequestScoped
public class Login implements Serializable {

    private String username;
    private String password;

    public void login() throws IOException {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("movies.html");
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
