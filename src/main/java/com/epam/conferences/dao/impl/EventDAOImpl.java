package com.epam.conferences.dao.impl;

import com.epam.conferences.dao.DAOFactory;
import com.epam.conferences.dao.EventDAO;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Address;
import com.epam.conferences.model.Category;
import com.epam.conferences.model.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventDAOImpl implements EventDAO {

    private static final Logger logger = LogManager.getLogger(EventDAOImpl.class);

    private static final String FIND_ALL_EVENTS_BY_PAGE = "SELECT * FROM events JOIN addresses on addresses.id = events.id_address ORDER BY events.id LIMIT ? OFFSET ?";
    private static final String INSERT_EVENT = "INSERT INTO events (name, date, description, id_address) VALUES (?, ?, ?, ?)";
    
    private static final String FIND_EVENTS_BY_CATEGORY = "SELECT * FROM (SELECT * FROM event_category JOIN events ON event_category.id_event = events.id WHERE id_category = ?)" +
            " AS categoryEvent JOIN addresses ON categoryEvent.id_address = addresses.id ORDER BY categoryEvent.id_event LIMIT ? OFFSET ?;";
    private static final String COUNT_EVENTS_BY_CATEGORY = "SELECT COUNT(*) FROM event_category WHERE id_category = ?;";
    private static final String FIND_CATEGORY_BY_EVENT_ID = "SELECT * FROM event_category JOIN category ON event_category.id_category = category.id WHERE id_event = ?";

    private static final String INSERT_ADDRESS = "INSERT INTO addresses(country, city, street, numberBuilding) VALUES (?, ?, ?, ?)";
    private static final String COUNT_EVENTS = "SELECT COUNT(*) FROM events";
    private static final String FIND_EVENT_BY_ID = "SELECT * FROM events JOIN addresses a on a.id = events.id_address WHERE events.id = ?";
    private static final String SORT_EVENTS_BY_REGISTERED_USERS = "select * from (select events.id, events.id_address, " +
            "events.name, events.description, events.date, count(user_event_presence.id_user) as usersCount " +
            "FROM events left join user_event_presence on events.id = user_event_presence.id_event group by id_event) as tmp " +
            "join addresses on tmp.id_address = addresses.id order by usersCount desc limit ? offset ?";

    private static final String SORT_EVENTS_BY_REPORTS = "select * from (select events.id, events.id_address, events.name, events.description, events.date, count(reports.id) as reportsCount \n" +
            "FROM reports right join events on \n" +
            "reports.id_event = events.id group by id_event) as tmp\n" +
            "join addresses on tmp.id_address = addresses.id order by reportsCount desc limit ? offset ?";
    private static final String SORT_EVENTS_BY_DATE = "SELECT * FROM " +
            "(SELECT events.id, events.date, events.description, events.name, addresses.id as id_address, addresses.country, addresses.city,\n" +
            "addresses.street, addresses.numberBuilding FROM events JOIN addresses on addresses.id = events.id_address) " +
            "AS tmp ORDER BY date LIMIT ? OFFSET ?";
    private static final String FIND_ALL_FUTURE_EVENTS = "SELECT * FROM " +
            "(SELECT events.id, events.date, events.description, events.name, addresses.id as id_address, addresses.country, addresses.city,\n" +
            "addresses.street, addresses.numberBuilding FROM events JOIN addresses on addresses.id = events.id_address WHERE events.date > NOW()) " +
            "AS tmp ORDER BY id LIMIT ? OFFSET ?";
    private static final String FIND_ALL_PAST_EVENTS = "SELECT * FROM " +
            "(SELECT events.id, events.date, events.description, events.name, addresses.id as id_address, addresses.country, addresses.city,\n" +
            "addresses.street, addresses.numberBuilding FROM events JOIN addresses on addresses.id = events.id_address WHERE events.date < NOW()) " +
            "AS tmp ORDER BY id LIMIT ? OFFSET ?";

    private final static String COUNT_REPORTS_BY_EVENT = "SELECT COUNT(*) FROM reports WHERE id_event = ?";

    private final static String COUNT_USERS_BY_EVENT = "SELECT COUNT(*) FROM user_event_presence WHERE id_event = ?";

    private final static String UPDATE_EVENT = "UPDATE events SET name = ?, description = ?, date = ? WHERE id = ?";

    private final static String UPDATE_ADDRESS = "UPDATE addresses SET country = ?, city = ?, street = ?, numberBuilding = ? WHERE id = ?;";

    @Override
    public List<Event> sortByCountReports(Connection connection, int offset, int count) throws DBException {
        logger.info("EventDAOImpl: obtaining events sorted by reports with offset {}", offset);
        List<Event> eventList;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SORT_EVENTS_BY_REPORTS)) {
            preparedStatement.setInt(1, count);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            eventList = extractEventList(connection, resultSet);
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception during obtaining events sorted by count of reports with offset {}.", offset);
            throw new DBException(e);
        }
        logger.info("EventDAOImpl: all events were found.");
        return eventList;
    }

    @Override
    public List<Event> findAll(Connection connection, int offset, int count) throws DBException {
        logger.info("EventDAOImpl: find all events by page.");
        List<Event> eventList;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_EVENTS_BY_PAGE)) {
            preparedStatement.setInt(1, count);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            eventList = extractEventList(connection, resultSet);
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception during getting all events by page.");
            throw new DBException(e);
        }
        logger.info("EventDAOImpl: all events were found.");
        return eventList;
    }

    public Integer countEvents(Connection connection) throws DBException {
        logger.info("EventDAOImpl: count events.");
        int count;
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_EVENTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception {} during counting rows.", e.getMessage());
            throw new DBException(e);
        }
        logger.info("EventDAOImpl: all events were calculated.");
        return count;
    }

    @Override
    public Optional<Event> findById(Connection connection, int id) throws DBException {
        logger.info("EventDAOImpl: obtaining event by id={}", id);
        Optional<Event> event = Optional.empty();
        try (PreparedStatement preparedStatementFindEvent = connection.prepareStatement(FIND_EVENT_BY_ID);
             PreparedStatement preparedStatementCategories = connection.prepareStatement(FIND_CATEGORY_BY_EVENT_ID)) {
            connection.setAutoCommit(false);

            preparedStatementFindEvent.setInt(1, id);
            preparedStatementCategories.setInt(1, id);

            ResultSet resultSet = preparedStatementFindEvent.executeQuery();
            ResultSet resultSetCategory = preparedStatementCategories.executeQuery();
            while (resultSet.next()) {
                event = Optional.of(extractEvent(connection, resultSet, resultSetCategory));
            }

            connection.commit();
            event.ifPresent(e -> logger.info("EventDAOImpl: event with id={} was get successfully.", id));
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception during obtaining event with id={}.", id);
            DAOFactory.getInstance().rollback(connection);
            throw new DBException(e);
        }
        return event;
    }

    // TODO: check method for category
    @Override
    public int saveEvent(Connection connection, Event event) throws DBException {
        logger.info("EventDAOImpl: creating event.");
        int id;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVENT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedStatementAddress = connection.prepareStatement(INSERT_ADDRESS, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);

            insertParamAddress(preparedStatementAddress, event.getAddress());

            preparedStatementAddress.executeUpdate();
            long addressId = getKey(preparedStatementAddress.getGeneratedKeys());

            preparedStatement.setString(1, event.getName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(event.getDate()));
            preparedStatement.setString(3, event.getDescription());
            preparedStatement.setLong(4, addressId);
            preparedStatement.executeUpdate();
            id = (int) getKey(preparedStatement.getGeneratedKeys());

            connection.commit();

        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception during creating event.");
            DAOFactory.getInstance().rollback(connection);
            throw new DBException(e);
        }
        logger.info("EventDAOImpl: event with id={} was created successfully.", id);
        return id;
    }

    @Override
    public Integer countEventsByCategory(Connection connection, int id) throws DBException {
        logger.info("CategoryDAOImpl: count events by category.");
        int count;
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_EVENTS_BY_CATEGORY)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error("CategoryDAOImpl: exception {} during counting rows.", e.getMessage());
            throw new DBException(e);
        }
        logger.info("CategoryDAOImpl: all events were calculated.");
        return count;
    }

    @Override
    public List<Event> findAllEventByCategory(Connection connection, int offset, int count, int id) throws DBException {
        logger.info("CategoryDAOImpl: find all events by category with id {}.", id);
        List<Event> eventList;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_EVENTS_BY_CATEGORY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, count);
            preparedStatement.setInt(3, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            eventList = extractEventList(connection, resultSet);
        } catch (SQLException e) {
            logger.error("CategoryDAOImpl: exception during getting all events by category.");
            throw new DBException(e);
        }
        logger.info("CategoryDAOImpl: all events were found.");
        return eventList;
    }

    @Override
    public void updateEvent(Connection connection, int id, Event event) throws DBException {
        logger.info("EventDAOImpl: updating event with id={}", id);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EVENT)) {
            preparedStatement.setString(1, event.getName());
            preparedStatement.setString(2, event.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(event.getDate()));
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception {} during updating event with id={}.", e.getMessage(), id);
            throw new DBException(e);
        }
    }

    @Override
    public void updateAddress(Connection connection, int id, Address address) throws DBException {
        logger.info("EventDAOImpl: updating address with id={}", id);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADDRESS)) {
            insertParamAddress(preparedStatement, address);
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception {} during updating adress with id={}.", e.getMessage(), id);
            throw new DBException(e);
        }
        logger.info("EventDAOImpl: address with id={} was updated successfully.", id);
    }

    @Override
    public List<Event> sortByDate(Connection connection, int offset, int count) throws DBException {
        logger.info("EventDAOImpl: obtaining events sorted by date with offset {}", offset);
        List<Event> eventList;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SORT_EVENTS_BY_DATE)) {
            preparedStatement.setInt(1, count);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            eventList = extractEventList(connection, resultSet);
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception during obtaining events sorted by date with offset {}.", offset);
            throw new DBException(e);
        }
        logger.info("EventDAOImpl: all events were found.");
        return eventList;
    }

    @Override
    public List<Event> sortByCountUsers(Connection connection, int offset, int count) throws DBException {
        logger.info("EventDAOImpl: obtaining events sorted by registrated users with offset {}", offset);
        List<Event> eventList;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SORT_EVENTS_BY_REGISTERED_USERS)) {
            preparedStatement.setInt(1, count);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            eventList = extractEventList(connection, resultSet);
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception during obtaining events sorted by registrated user with offset {}.", offset);
            throw new DBException(e);
        }
        logger.info("EventDAOImpl: all events were found.");
        return eventList;
    }

    @Override
    public List<Event> findAllFutureEvents(Connection connection, int offset, int count) throws DBException {
        logger.info("EventDAOImpl: obtaining future events with offset {}", offset);
        List<Event> eventList;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_FUTURE_EVENTS)) {
            preparedStatement.setInt(1, count);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            eventList = extractEventList(connection, resultSet);
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception during obtaining future events with offset {}.", offset);
            throw new DBException(e);
        }
        logger.info("EventDAOImpl: all events were found.");
        return eventList;
    }

    @Override
    public List<Event> findAllPastEvents(Connection connection, int offset, int count) throws DBException {
        logger.info("EventDAOImpl: obtaining past events with offset {}", offset);
        List<Event> eventList;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PAST_EVENTS)) {
            preparedStatement.setInt(1, count);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            eventList = extractEventList(connection, resultSet);
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception during obtaining past events with offset {}.", offset);
            throw new DBException(e);
        }
        logger.info("EventDAOImpl: all events were found.");
        return eventList;
    }

    @Override
    public Integer countReportsByEventId(Connection connection, long eventId) throws DBException {
        logger.info("EventDAOImpl: count reports for event with id = {}.", eventId);
        int count;
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_REPORTS_BY_EVENT)) {
            preparedStatement.setLong(1, eventId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception ({}) during counting all reports for event with id {}", e, eventId);
            throw new DBException(e);
        }
        logger.info("EventDAOImpl: all reports were counted.");
        return count;
    }

    @Override
    public Integer countRegisteredUsersByEventId(Connection connection, long eventId) throws DBException {
        logger.info("EventDAOImpl: count users for event with id = {}.", eventId);
        int count;
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_USERS_BY_EVENT)) {
            preparedStatement.setLong(1, eventId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception ({}) during counting all users for event with id {}", e, eventId);
            throw new DBException(e);
        }
        logger.info("EventDAOImpl: all users were counted.");
        return count;
    }

    private List<Event> extractEventList(Connection connection, ResultSet resultSet) throws DBException, SQLException {
        List<Event> eventList = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Event> event = Optional.ofNullable(extractEvent(connection, resultSet, null));
            event.ifPresent(eventList::add);
        }
        return eventList;
    }

    private Event extractEvent(Connection connection, ResultSet resultSet, ResultSet resultSetCategories) throws DBException {
        try {
            Address address = new Address();
            address.setId(resultSet.getInt("id_address"));
            address.setCountry(resultSet.getString("country"));
            address.setCity(resultSet.getString("city"));
            address.setStreet(resultSet.getString("street"));
            address.setHouse(resultSet.getInt("numberBuilding"));

            return new Event.EventBuilder()
                    .setId(resultSet.getLong("id"))
                    .setDate(resultSet.getTimestamp("date").toLocalDateTime())
                    .setName(resultSet.getString("name"))
                    .setAddress(address).setDescription(resultSet.getString("description"))
                    .setReports(countReportsByEventId(connection, resultSet.getLong("id")))
                    .setCategories(addCategoryToEvent(resultSetCategories))
                    .setRegisterUsers(countRegisteredUsersByEventId(connection, resultSet.getLong("id")))
                    .build();
        } catch (DBException | SQLException e) {
            throw new DBException(e);
        }

    }

    private List<Category> addCategoryToEvent(ResultSet resultSetCategory) throws SQLException {
        List<Category> categories = new ArrayList<>();
        if (resultSetCategory == null) {
            return categories;
        }
        while (resultSetCategory.next()) {
            String name = resultSetCategory.getString("name");
            int id = resultSetCategory.getInt("id_category");
            categories.add(new Category(id, name));
        }
        return categories;
    }

    private void insertParamAddress(PreparedStatement preparedStatement, Address address) throws SQLException {
        preparedStatement.setString(1, address.getCountry());
        preparedStatement.setString(2, address.getCity());
        preparedStatement.setString(3, address.getStreet());
        preparedStatement.setInt(4, address.getHouse());
    }

    private long getKey(ResultSet resultSet) throws SQLException {
        long key = 0;
        if (resultSet.next()) {
            key = resultSet.getLong(1);
        }
        return key;
    }
}
