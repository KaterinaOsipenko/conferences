package com.epam.conferences.dao.impl;

import com.epam.conferences.dao.ReportDAO;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Report;
import com.epam.conferences.model.Topic;
import com.epam.conferences.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReportDAOImpl implements ReportDAO {

    private static final Logger logger = LogManager.getLogger(ReportDAOImpl.class);
    private final static String FIND_ALL_REPORTS_FOR_EVENT_ID = "SELECT reports.id, users.id AS id_user, users.firstName, " +
            "users.lastName, topics.id AS id_topic, topics.name FROM reports JOIN users " +
            "ON reports.id_speaker = users.id JOIN topics ON reports.id_topic = topics.id WHERE id_event = ?";

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
        logger.info("EventDAOImpl: all reports were found.");
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
        Topic topic = new Topic();
        topic.setId(resultSet.getInt("id_topic"));
        topic.setName(resultSet.getString("name"));
        User user = new User();
        user.setId(resultSet.getInt("id_user"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        Report report = new Report();
        report.setId(resultSet.getInt("id"));
        report.setTopic(topic);
        report.setSpeaker(user);
        return report;
    }
}
