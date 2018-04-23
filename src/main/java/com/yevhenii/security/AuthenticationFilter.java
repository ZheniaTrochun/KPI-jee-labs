package com.yevhenii.security;

import edu.emory.mathcs.backport.java.util.Collections;
import org.omnifaces.filter.HttpFilter;
import org.omnifaces.util.Servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@WebFilter(urlPatterns = {"/*"})
public class AuthenticationFilter extends HttpFilter {

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/login.xhtml", "/register.xhtml")
    ));

    @Override
    public void doFilter(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         HttpSession httpSession, FilterChain filterChain)
            throws ServletException, IOException {

        String loginUrl = httpServletRequest.getContextPath() + "/login.xhtml";
        String path = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length())
                .replaceAll("[/]+$", "");

        boolean loggedIn = Objects.isNull(httpServletRequest.getRemoteUser());
        boolean loginRequest = httpServletRequest.getRequestURI().equals(loginUrl);
        boolean resourceRequest = Servlets.isFacesResourceRequest(httpServletRequest);

        boolean isAllowed = ALLOWED_PATHS.contains(path);

        if (loggedIn || loginRequest || resourceRequest || isAllowed) {
            if (!resourceRequest) {
                Servlets.setNoCacheHeaders(httpServletResponse);
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            Servlets.facesRedirect(httpServletRequest, httpServletResponse, loginUrl);
        }

    }
}
