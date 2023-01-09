package com.epam.conferences.controller.admin;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Address;
import com.epam.conferences.service.AddressService;
import com.epam.conferences.util.PathUtil;
import com.epam.conferences.util.URLUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetAlladdressesServlet", value = "/admin/addresses")
public class GetAllAddressesServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(GetAllAddressesServlet.class);

    private AddressService addressService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        addressService = (AddressService) config.getServletContext().getAttribute("addressService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GetAllAddressesServlet: doGet method.");
        String address;
        try {
            List<Address> addressList = addressService.findAllAddress();
            request.getSession().setAttribute("addresses", addressList);
            address = PathUtil.ADMIN_VIEW_ADDRESS_PAGE;
        } catch (ServiceException e) {
            logger.error("GetAllAddressesServlet: exception ({}) during creating event", e.getMessage());
            request.getSession().setAttribute("address", URLUtil.ADMIN_HOME);
            request.getSession().setAttribute("ex", "Sorry, we have some troubles. Our specialists have already tried to copy with this.");
            address = PathUtil.ADMIN_ERROR_PAGE;
        }
        request.getRequestDispatcher(address).forward(request, response);
    }


}
