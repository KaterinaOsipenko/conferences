package com.epam.conferences.dao;

import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Category;

import java.sql.Connection;
import java.util.List;

public interface CategoryDAO {

    void deleteCategoryFromEvent(Connection connection, int id_category, int id_event) throws DBException;

    List<Category> findAll(Connection connection) throws DBException;

    void addCategoryToEvent(Connection connection, int id_category, int id_event) throws DBException;

    List<Category> getAllCategoriesByEvent(Connection connection, int id_event) throws DBException;

}
