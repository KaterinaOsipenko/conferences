package com.epam.conferences.dao;

import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Address;

import java.sql.Connection;
import java.util.List;

public interface AddressDAO {

    List<Address> findAllAddresses(Connection connection) throws DBException;
}
