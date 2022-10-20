package com.epam.conferences.model;

import java.io.Serializable;

public class Report implements Serializable {

    private long id;

    private int topicId;

    private int speakerId;

    private int eventId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(int speakerId) {
        this.speakerId = speakerId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof Report)) {
            return false;
        }

        Report report = (Report) o;

        if (getId() != report.getId()) {
            return false;
        }
        if (getTopicId() != report.getTopicId()) {
            return false;
        }
        if (getSpeakerId() != report.getSpeakerId()) {
            return false;
        }
        return getEventId() == report.getEventId();
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getTopicId();
        result = 31 * result + getSpeakerId();
        result = 31 * result + getEventId();
        return result;
    }

    @Override
    public String toString() {
        return "Report{" + "id=" + id + ", topicId=" + topicId + ", speakerId=" + speakerId
                + ", eventId=" + eventId + '}';
    }
}
