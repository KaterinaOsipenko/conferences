package com.epam.conferences.dao.impl;

import com.epam.conferences.dao.ReportDAO;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.Report;
import com.epam.conferences.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReportDAOImpl implements ReportDAO {

    private static final Logger logger = LogManager.getLogger(ReportDAOImpl.class);
    private final static String FIND_ALL_REPORTS_FOR_EVENT_ID = "SELECT reports.id, users.id AS id_user, users.firstName, " +
            "users.lastName,  topic AS topic_name, events.id AS id_event,  events.name AS event_name, events.date" +
            "  FROM reports JOIN users ON reports.id_speaker = users.id " +
            "JOIN events ON reports.id_event = events.id WHERE id_event = ?";
    private final static String DELETE_REPORT = "DELETE FROM reports WHERE id = ?";
    private final static String UPDATE_TOPIC_NAME = "UPDATE reports SET topic = ? WHERE id = ?";
    private final static String FIND_REPORTS_BY_USER = "SELECT reports.id, users.id AS id_user, users.firstName, " +
            "users.lastName, topic AS topic_name, events.id AS id_event, " +
            "events.name AS event_name, events.date" +
            "  FROM reports JOIN users ON reports.id_speaker = users.id " +
            "JOIN events ON reports.id_event = events.id WHERE reports.id_speaker = ?";

    @Override
    public List<Report> findAllByEventId(Connection connection, long eventId) throws DBException {
        logger.info("ReportDAOImpl: find all reports for event with id = {}.", eventId);
        List<Report> reports;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_REPORTS_FOR_EVENT_ID)) {
            preparedStatement.setLong(1, eventId);
            ResultSet resultSet = preparedStatement.executeQuery();
            reports = extractReportList(resultSet);
        } catch (SQLException e) {
            logger.error("ReportDAOImpl: exception ({}) during obtaining all reports for event with id {}", e, eventId);
            throw new DBException(e);
        }
        logger.info("ReportDAOImpl: all reports were found.");
        return reports;
    }

    @Override
    public void deleteReport(Connection connection, int reportId) throws DBException {
        logger.info("ReportDAOImpl: delete report  with id = {}.", reportId);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REPORT)) {
            preparedStatement.setLong(1, reportId);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("ReportDAOImpl: exception ({}) during deleting report with id {}", e, reportId);
            throw new DBException(e);
        }
        logger.info("ReportDAOImpl: report was removed successfully.");
    }

    @Override
    public void changeReportTopic(Connection connection, int topicId, String name) throws DBException {
        logger.info("ReportDAOImpl: setting new name {} to the topic with id = {}.", name, topicId);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TOPIC_NAME)) {
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, topicId);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("ReportDAOImpl: exception ({}) during updating name of topic with id {}", e, topicId);
            throw new DBException(e);
        }
        logger.info("ReportDAOImpl: name of topic was updated successfully.");
    }

    @Override
    public List<Report> getReportsByUser(Connection connection, int userId) throws DBException {
        logger.info("ReportDAOImpl: get reports by user with id = {}.", userId);
        List<Report> reports;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_REPORTS_BY_USER)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            reports = extractReportList(resultSet);
        } catch (SQLException e) {
            logger.error("ReportDAOImpl: exception ({}) during obtaining all reports bu userId ={}", e.getMessage(), userId);
            throw new DBException(e);
        }
        logger.info("ReportDAOImpl: all reports were found.");
        return reports;
    }

    private List<Report> extractReportList(ResultSet resultSet) throws SQLException {
        List<Report> reportList = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Report> report = Optional.of(extractReport(resultSet));
            report.ifPresent(reportList::add);
        }
        return reportList;
    }

    private Report extractReport(ResultSet resultSet) throws SQLException {
        String nameEvent = resultSet.getString("event_name");
        Timestamp date = resultSet.getTimestamp("date");
        long eventId = resultSet.getLong("id_event");
        Event event = new Event.EventBuilder().setId(eventId)
                .setDate(date.toLocalDateTime())
                .setName(nameEvent)
                .build();
        User user = new User();
        user.setId(resultSet.getInt("id_user"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        Report report = new Report();
        report.setTopic(resultSet.getString("topic_name"));
        report.setId(resultSet.getInt("id"));
        report.setSpeaker(user);
        report.setEvent(event);
        return report;
    }
}
