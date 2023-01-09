package com.epam.conferences.service.impl;

import com.epam.conferences.dao.AddressDAO;
import com.epam.conferences.dao.impl.DAOFactoryImpl;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Address;
import com.epam.conferences.service.AddressService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public class AddressServiceImpl implements AddressService {

    private static final Logger logger = LogManager.getLogger(AddressServiceImpl.class);

    private final AddressDAO addressDAO;

    public AddressServiceImpl(AddressDAO addressDAO) {
        if (addressDAO == null) {
            logger.error("AddressServiceImpl: addressDAO reference in constructor is NULL.");
            throw new IllegalArgumentException("AddressServiceImpl: eventDAO reference in constructor is NULL.");
        }
        this.addressDAO = addressDAO;
    }

    @Override
    public List<Address> findAllAddress() throws ServiceException {
        logger.info("AddressServiceImpl: searching for all addresses...");
        List<Address> addressList;
        try {
            addressList = addressDAO.findAllAddresses(DAOFactoryImpl.getConnection());
            if (addressList == null) {
                logger.error("AddressServiceImpl: address list is NULL.");
                throw new ServiceException("AddressServiceImpl: address list is NULL.");
            }
        } catch (SQLException | NamingException | DBException e) {
            logger.error("AddressServiceImpl: exception {} during obtaining all addresses.", e.getMessage());
            throw new ServiceException(e);
        }
        logger.info("AddressServiceImpl: all addresses were obtained successfully.");
        return addressList;
    }

}


