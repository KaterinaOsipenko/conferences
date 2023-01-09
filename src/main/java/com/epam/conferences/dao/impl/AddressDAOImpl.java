package com.epam.conferences.dao.impl;

import com.epam.conferences.dao.AddressDAO;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddressDAOImpl implements AddressDAO {

    private static final Logger logger = LogManager.getLogger(AddressDAOImpl.class);
    private static final String CREATE_VIEW_ALL_ADDRESSES = "CREATE OR REPLACE VIEW addressesView AS SELECT * FROM addresses;";
    private static final String GET_ALL_ADDRESSES = "SELECT * FROM addressesView";

    static {
        logger.info("AddressDAOImpl: create view.");
        try (Statement statement = DAOFactoryImpl.getConnection().createStatement()) {
            statement.execute(CREATE_VIEW_ALL_ADDRESSES);
        } catch (NamingException | SQLException e) {
            logger.error("AddressDAOImpl: exception {} during creation view.", e.getMessage());
        }
    }

    @Override
    public List<Address> findAllAddresses(Connection connection) throws DBException {
        logger.info("AddressDAOImpl: searching all addresses...");
        List<Address> addressList;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_ADDRESSES);
            addressList = extractAddressesList(resultSet);
        } catch (SQLException e) {
            logger.error("AddressDAOImpl: exception during getting all events by page.");
            throw new DBException(e);
        }
        logger.info("AddressDAOImpl: all events were found.");
        return addressList;
    }

    private List<Address> extractAddressesList(ResultSet resultSet) throws SQLException, DBException {
        List<Address> addressesList = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Address> address = Optional.of(extractAddress(resultSet));
            address.ifPresent(addressesList::add);
        }
        return addressesList;
    }

    private Address extractAddress(ResultSet resultSet) throws DBException {
        try {
            Address address = new Address();
            address.setId(resultSet.getInt("id"));
            address.setCountry(resultSet.getString("country"));
            address.setCity(resultSet.getString("city"));
            address.setStreet(resultSet.getString("street"));
            address.setHouse(resultSet.getInt("numberBuilding"));
            return address;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
