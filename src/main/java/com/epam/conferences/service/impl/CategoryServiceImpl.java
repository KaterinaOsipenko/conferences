package com.epam.conferences.service.impl;

import com.epam.conferences.dao.CategoryDAO;
import com.epam.conferences.dao.impl.DAOFactoryImpl;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Category;
import com.epam.conferences.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LogManager.getLogger(CategoryServiceImpl.class);

    private final CategoryDAO categoryDAO;

    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        if (categoryDAO == null) {
            logger.error("CategoryServiceImpl: categoryDAO reference in constructor is NULL.");
            throw new IllegalArgumentException("CategoryServiceImpl: categoryDAO reference in constructor is NULL.");
        }
        this.categoryDAO = categoryDAO;
    }

    @Override
    public void deleteCategoryFromEvent(int id_category, int id_event) throws ServiceException {
        logger.info("CategoryServiceImpl: deleting category with id {} from event with id {}", id_category, id_event);
        try {
            categoryDAO.deleteCategoryFromEvent(DAOFactoryImpl.getConnection(), id_category, id_event);
        } catch (DBException | NamingException | SQLException e) {
            logger.error("CategoryServiceImpl: exception ({}) during removing category from event.", e.getMessage());
            throw new ServiceException(e);
        }
        logger.info("CategoryServiceImpl:category from event was deleted successfully.");
    }

    @Override
    public List<Category> findAll() throws ServiceException {
        logger.info("CategoryServiceImpl: find all categories");
        List<Category> categories;
        try {
            categories = categoryDAO.findAll(DAOFactoryImpl.getConnection());
            if (categories.isEmpty()) {
                throw new ServiceException("There are not any categories.");
            }
        } catch (DBException | NamingException | SQLException e) {
            logger.error("CategoryServiceImpl: exception ({}) during obtaining all categories.", e.getMessage());
            throw new ServiceException(e);
        }
        logger.info("CategoryServiceImpl: all categories was found successfully.");
        return categories;
    }

    @Override
    public void addCategoryToEvent(int id_category, int id_event) throws ServiceException {
        logger.info("CategoryServiceImpl: add category with id={} to the event with id={}.", id_category, id_event);
        try {
            categoryDAO.addCategoryToEvent(DAOFactoryImpl.getConnection(), id_category, id_event);
        } catch (DBException | NamingException | SQLException e) {
            logger.error("CategoryServiceImpl: exception ({}) during adding category to the event.", e.getMessage());
            throw new ServiceException(e);
        }
        logger.info("CategoryServiceImpl: category was added successfully.");
    }

    @Override
    public List<Category> findCategoriesToEvent(int id_event) throws ServiceException {
        logger.info("CategoryServiceImpl: find all categories to the event with id={}.", id_event);
        List<Category> categories;
        try {
            categories = categoryDAO.getAllCategoriesByEvent(DAOFactoryImpl.getConnection(), id_event);
        } catch (DBException | NamingException | SQLException e) {
            logger.error("CategoryServiceImpl: exception ({}) during obtaining categories to the event.", e.getMessage());
            throw new ServiceException(e);
        }
        logger.info("CategoryServiceImpl: all categories was found successfully.");
        return categories;
    }
}
