package com.epam.conferences.model;

import java.io.Serializable;

public class Report implements Serializable {

    private long id;

    private Topic topic;

    private User speaker;

    private Event event;

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getSpeaker() {
        return speaker;
    }

    public void setSpeaker(User speaker) {
        this.speaker = speaker;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;

        Report report = (Report) o;

        if (getId() != report.getId()) return false;
        if (getTopic() != null ? !getTopic().equals(report.getTopic()) : report.getTopic() != null) return false;
        if (getSpeaker() != null ? !getSpeaker().equals(report.getSpeaker()) : report.getSpeaker() != null)
            return false;
        return getEvent() != null ? getEvent().equals(report.getEvent()) : report.getEvent() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getTopic() != null ? getTopic().hashCode() : 0);
        result = 31 * result + (getSpeaker() != null ? getSpeaker().hashCode() : 0);
        result = 31 * result + (getEvent() != null ? getEvent().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", topic=" + topic +
                ", speaker=" + speaker +
                ", event=" + event +
                '}';
    }
}
