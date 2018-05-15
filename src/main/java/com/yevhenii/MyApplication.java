package com.yevhenii;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class MyApplication extends Application {
}
