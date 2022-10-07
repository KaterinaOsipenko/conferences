package com.epam.conferences.model;

import java.io.Serializable;

public class Topic implements Serializable {

  private long id;
  private String name;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Topic)) {
      return false;
    }

    Topic topics = (Topic) o;

    if (getId() != topics.getId()) {
      return false;
    }
    return getName() != null ? getName().equals(topics.getName()) : topics.getName() == null;
  }

  @Override
  public int hashCode() {
    int result = (int) (getId() ^ (getId() >>> 32));
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Topics{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
