package com.epam.conferences.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

public class Event implements Serializable {

  private long id;

  private String name;

  private Timestamp date;

  private int addressId;

  private Set<Integer> reportsId;

  private Set<User> registerUsers;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Timestamp getDate() {
    return date;
  }

  public void setDate(Timestamp date) {
    this.date = date;
  }

  public int getAddressId() {
    return addressId;
  }

  public void setAddress(int addressId) {
    this.addressId = addressId;
  }

  public Set<Integer> getReports() {
    return reportsId;
  }

  public void setReports(Set<Integer> reportsId) {
    this.reportsId = reportsId;
  }

  public Set<User> getRegisterUsers() {
    return registerUsers;
  }

  public void setRegisterUsers(Set<User> registerUsers) {
    this.registerUsers = registerUsers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Event)) {
      return false;
    }

    Event event = (Event) o;

    if (getId() != event.getId()) {
      return false;
    }
    if (getAddressId() != event.getAddressId()) {
      return false;
    }
    if (getName() != null ? !getName().equals(event.getName()) : event.getName() != null) {
      return false;
    }
    if (getDate() != null ? !getDate().equals(event.getDate()) : event.getDate() != null) {
      return false;
    }
    if (reportsId != null ? !reportsId.equals(event.reportsId) : event.reportsId != null) {
      return false;
    }
    return getRegisterUsers() != null ? getRegisterUsers().equals(event.getRegisterUsers())
        : event.getRegisterUsers() == null;
  }

  @Override
  public int hashCode() {
    int result = (int) (getId() ^ (getId() >>> 32));
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
    result = 31 * result + getAddressId();
    result = 31 * result + (reportsId != null ? reportsId.hashCode() : 0);
    result = 31 * result + (getRegisterUsers() != null ? getRegisterUsers().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Event{" + "id=" + id + ", name='" + name + '\'' + ", date=" + date + ", address="
        + addressId + ", reports=" + reportsId + ", registerUsers=" + registerUsers + '}';
  }
}
