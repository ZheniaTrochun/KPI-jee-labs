package com.yevhenii.jsf;

import com.yevhenii.services.UserService;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import static org.omnifaces.util.Messages.addGlobalInfo;

@Model
public class Register {

    private String username;
    private String password;

    @Inject
    private UserService userService;

//    @Inject
//    private HttpServletResponse response;

    public void submit() {
        userService.register(username, password);
        System.out.println("REGISTERED but I don't know what to do next :(");
        addGlobalInfo("register.message.success");
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

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
