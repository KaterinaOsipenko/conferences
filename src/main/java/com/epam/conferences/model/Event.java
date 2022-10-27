package com.epam.conferences.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Event implements Serializable {

    private final long id;

    private final String name;

    private final LocalDateTime date;

    private final String description;

    private final Address address;

    private final Set<Report> reports;

    private final Set<User> registerUsers;

    private Event(EventBuilder eventBuilder) {
        this.id = eventBuilder.id;
        this.address = eventBuilder.address;
        this.reports = eventBuilder.reports;
        this.name = eventBuilder.name;
        this.date = eventBuilder.date;
        this.registerUsers = eventBuilder.registerUsers;
        this.description = eventBuilder.description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Address getAddress() {
        return address;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public Set<User> getRegisterUsers() {
        return registerUsers;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        if (getId() != event.getId()) return false;
        if (getName() != null ? !getName().equals(event.getName()) : event.getName() != null) return false;
        if (getDate() != null ? !getDate().equals(event.getDate()) : event.getDate() != null) return false;
        if (getDescription() != null ? !getDescription().equals(event.getDescription()) : event.getDescription() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(event.getAddress()) : event.getAddress() != null) return false;
        if (getReports() != null ? !getReports().equals(event.getReports()) : event.getReports() != null) return false;
        return getRegisterUsers() != null ? getRegisterUsers().equals(event.getRegisterUsers()) : event.getRegisterUsers() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getReports() != null ? getReports().hashCode() : 0);
        result = 31 * result + (getRegisterUsers() != null ? getRegisterUsers().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", address=" + address +
                ", reports=" + reports +
                ", registerUsers=" + registerUsers +
                '}';
    }

    public static class EventBuilder {
        private long id;

        private String name;

        private LocalDateTime date;

        private String description;

        private Address address;

        private Set<Report> reports = new HashSet<>();

        private Set<User> registerUsers;

        public EventBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public EventBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public EventBuilder setDate(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public EventBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public EventBuilder setAddress(Address address) {
            this.address = address;
            return this;
        }

        public EventBuilder setReports(Set<Report> reports) {
            this.reports = reports;
            return this;
        }

        public EventBuilder setRegisterUsers(Set<User> registerUsers) {
            this.registerUsers = registerUsers;
            return this;
        }

        public Event build() {
            return new Event(this);
        }
    }
}
