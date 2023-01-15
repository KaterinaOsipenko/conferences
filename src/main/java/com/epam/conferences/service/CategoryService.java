package com.epam.conferences.service;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Category;

import java.util.List;

public interface CategoryService {

    void deleteCategoryFromEvent(int id_category, int id_event) throws ServiceException;

    List<Category> findAll() throws ServiceException;

    void addCategoryToEvent(int id_category, int id_event) throws ServiceException;

    List<Category> findCategoriesToEvent(int id_event) throws ServiceException;
}
