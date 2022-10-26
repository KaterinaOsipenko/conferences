package com.epam.conferences.filter;

import com.epam.conferences.util.PathUtil;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@WebFilter(filterName = "EmailValidatorFilter", urlPatterns = {"/registration", "/chooseEvent"})
public class EmailValidatorFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(EmailValidatorFilter.class);

    public void init(FilterConfig config) throws ServletException {
        logger.info("EmailValidatorFilter: validation filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("EmailValidatorFilter: validation filter doFilter.");
        HttpServletRequest req = (HttpServletRequest) request;
        if (Objects.equals(req.getMethod(), "GET")) {
            chain.doFilter(request, response);
        } else {
            String email = request.getParameter("email");
            boolean valid = EmailValidator.getInstance().isValid(email);
            if (!valid) {
                logger.error("EmailValidatorFilter: exception during validation user input. Email is not valid");
                request.setAttribute("ex", "Email is not valid!");
                request.getRequestDispatcher(PathUtil.REGISTRATION_PAGE).include(request, response);
            } else {
                logger.info("EmailValidatorFilter: data validated successfully.");
                chain.doFilter(request, response);
            }
        }
    }
}
