package com.epam.conferences.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;
import java.util.Optional;

@WebFilter(urlPatterns = "/*",
        initParams = @WebInitParam(name = "encoding", value = "UTF-8"))
public class JSPEncodingFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(JSPEncodingFilter.class);

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("JSPEncodingFilter: encoding Filter init.");
        this.encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("JSPEncodingFilter: encoding filter start working.");
        String encoding = Optional
                .ofNullable(servletRequest.getParameter("encoding"))
                .orElse(this.encoding);
        logger.info("JSPEncodingFilter: set encoding {} for request.", encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
