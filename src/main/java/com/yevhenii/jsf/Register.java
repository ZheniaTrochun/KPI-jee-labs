package com.yevhenii.jsf;

import com.yevhenii.services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import static org.omnifaces.util.Messages.addGlobalInfo;

@Named
@RequestScoped
public class Register {

    private String username;
    private String password;

    @Inject
    private UserService userService;

    public void submit() {
        System.out.println("register");
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
