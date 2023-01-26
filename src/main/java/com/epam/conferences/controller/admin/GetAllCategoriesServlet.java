package com.epam.conferences.controller.admin;

import com.epam.conferences.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetAllCategoriesServlet", value = "/admin/getAllCategories")
public class GetAllCategoriesServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(GetEventsByCategoryServlet.class);

    private final static int PAGE_SIZE = 10;
    private CategoryService categoryService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        categoryService = (CategoryService) config.getServletContext().getAttribute("categoryService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
