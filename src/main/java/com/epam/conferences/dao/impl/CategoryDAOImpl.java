package com.epam.conferences.dao.impl;

import com.epam.conferences.dao.CategoryDAO;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDAOImpl implements CategoryDAO {

    private static final Logger logger = LogManager.getLogger(CategoryDAOImpl.class);

    private static final String FIND_ALL_CATEGORIES = "SELECT * FROM category";

    private static final String FIND_CATEGORIES_BY_EVENT = "SELECT event_category.id_category as id, category.name FROM event_category JOIN category " +
            "ON event_category.id_category = category.id WHERE id_event = ?";

    private static final String INSERT_CATEGORY_TO_EVENT = "INSERT INTO event_category VALUES (?, ?);";
    private static final String DELETE_CATEGORY_FROM_EVENT = "DELETE FROM event_category WHERE id_category = ? AND id_event = ?";

    @Override
    public void deleteCategoryFromEvent(Connection connection, int id_category, int id_event) throws DBException {
        logger.info("CategoryDAOImpl: deleting category with id {} from event with id {}", id_category, id_event);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CATEGORY_FROM_EVENT)) {
            preparedStatement.setInt(1, id_category);
            preparedStatement.setInt(2, id_event);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("CategoryDAOImpl: exception ({}) during removing category from event.", e.getMessage());
            throw new DBException(e);
        }
        logger.info("CategoryDAOImpl:category from event was deleted successfully.");
    }

    @Override
    public List<Category> findAll(Connection connection) throws DBException {
        logger.info("CategoryDAOImpl: find all categories");
        List<Category> categories;
        try (Statement statement = connection.createStatement()) {
            categories = extractCategoriesList(statement.executeQuery(FIND_ALL_CATEGORIES));
        } catch (SQLException e) {
            logger.error("CategoryDAOImpl: exception ({}) during obtainig all categories.", e.getMessage());
            throw new DBException(e);
        }
        logger.info("CategoryDAOImpl: all categories was found successfully.");
        return categories;
    }

    @Override
    public void addCategoryToEvent(Connection connection, int id_category, int id_event) throws DBException {
        logger.info("CategoryDAOImpl: add category with id={} to the event with id={}.", id_category, id_event);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATEGORY_TO_EVENT)) {
            preparedStatement.setInt(1, id_category);
            preparedStatement.setInt(2, id_event);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("CategoryDAOImpl: exception ({}) during adding category to the event.", e.getMessage());
            throw new DBException(e);
        }
        logger.info("CategoryDAOImpl: category was added successfully.");
    }

    @Override
    public List<Category> getAllCategoriesByEvent(Connection connection, int id_event) throws DBException {
        logger.info("CategoryDAOImpl: get categories to the event with id={}.", id_event);
        List<Category> categories;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_CATEGORIES_BY_EVENT)) {
            preparedStatement.setInt(1, id_event);
            categories = extractCategoriesList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            logger.error("CategoryDAOImpl: exception ({}) during obtaining category to the event.", e.getMessage());
            throw new DBException(e);
        }
        logger.info("CategoryDAOImpl: category was obtained successfully.");
        return categories;
    }

    private List<Category> extractCategoriesList(ResultSet resultSet) throws SQLException {
        List<Category> categories = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Category> category = Optional.of(extractCategory(resultSet));
            category.ifPresent(categories::add);
        }
        return categories;
    }

    private Category extractCategory(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        return new Category(id, name);
    }
}
