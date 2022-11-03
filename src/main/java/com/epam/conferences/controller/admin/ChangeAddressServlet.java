package com.epam.conferences.controller.admin;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Address;
import com.epam.conferences.service.EventService;
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

@WebServlet(name = "ChangeAddressServlet", value = "/admin/changeAddress")
public class ChangeAddressServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ChangeAddressServlet.class);
    private EventService eventService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        eventService = (EventService) config.getServletContext().getAttribute("eventService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("ChangeAddressServlet: doPost method.");
        int addressId = Integer.parseInt(request.getParameter("addressId"));
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        int house = Integer.parseInt(request.getParameter("house"));
        int apartment = Integer.parseInt(request.getParameter("apartment"));

        Address addressEvent = new Address();
        addressEvent.setApartment(apartment);
        addressEvent.setHouse(house);
        addressEvent.setCity(city);
        addressEvent.setStreet(street);
        addressEvent.setCountry(country);

        String address;

        try {
            eventService.changeAddressEvent(addressId, addressEvent);
            address = URLUtil.ADMIN_CARD_EVENT + "?id=" + eventId;
        } catch (ServiceException e) {
            logger.error("ChangeAddressServlet: exception ({}) during changing address with id={}", e.getMessage(), addressId);
            request.getSession().setAttribute("address", "admin/card?id=" + eventId);
            request.getSession().setAttribute("ex", "Sorry, we have some troubles. Our specialists have already tried to copy with this.");
            address = PathUtil.ADMIN_ERROR_PAGE;
        }
        response.sendRedirect(address);

    }
}
