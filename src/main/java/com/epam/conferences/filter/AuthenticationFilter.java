package com.epam.conferences.filter;

import com.epam.conferences.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/admin/*", "/speaker/*"})
public class AuthenticationFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(AuthenticationFilter.class);

    public void init(FilterConfig config) throws ServletException {
        logger.info("AuthenticationFilter: init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("AuthenticationFilter: doFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("/login");
        } else {
            User user = (User) req.getSession().getAttribute("user");

            chain.doFilter(request, response);
        }
    }
}