package com.epam.conferences.service;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Address;

import java.util.List;

public interface AddressService {

    List<Address> findAllAddress() throws ServiceException;
}
