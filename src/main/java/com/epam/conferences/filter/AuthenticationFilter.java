package com.epam.conferences.filter;

import com.epam.conferences.model.Role;
import com.epam.conferences.model.User;
import com.epam.conferences.util.PathUtil;
import com.epam.conferences.util.URLUtil;
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
            resp.sendRedirect(URLUtil.LOGIN_URL);
        } else {
            User user = (User) req.getSession().getAttribute("user");
            String url = String.valueOf(req.getRequestURL());
            if (url.contains("admin") && user.getRoleId() == Role.MODERATOR.id) {
                chain.doFilter(request, response);
            } else if (url.contains("speaker") && user.getRoleId() == Role.SPEAKER.id) {
                chain.doFilter(request, response);
            } else {
                req.getSession().setAttribute("ex", "Error 403: forbidden access.");
                req.getSession().setAttribute("address", PathUtil.INDEX_PAGE);
                req.getRequestDispatcher(PathUtil.ERROR_PAGE).forward(req, resp);
            }
        }
    }
}
