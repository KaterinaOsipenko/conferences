package com.epam.conferences.model;

import java.io.Serializable;

public class Address implements Serializable {

  private long id;

  private String country;

  private String city;

  private String street;

  private int house;

  private int apartment;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public int getHouse() {
    return house;
  }

  public void setHouse(int house) {
    this.house = house;
  }

  public int getApartment() {
    return apartment;
  }

  public void setApartment(int apartment) {
    this.apartment = apartment;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Address)) {
      return false;
    }

    Address address = (Address) o;

    if (getId() != address.getId()) {
      return false;
    }
    if (getHouse() != address.getHouse()) {
      return false;
    }
    if (getApartment() != address.getApartment()) {
      return false;
    }
    if (getCountry() != null ? !getCountry().equals(address.getCountry())
        : address.getCountry() != null) {
      return false;
    }
    if (getCity() != null ? !getCity().equals(address.getCity()) : address.getCity() != null) {
      return false;
    }
    return getStreet() != null ? getStreet().equals(address.getStreet())
        : address.getStreet() == null;
  }

  @Override
  public int hashCode() {
    int result = (int) (getId() ^ (getId() >>> 32));
    result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
    result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
    result = 31 * result + (getStreet() != null ? getStreet().hashCode() : 0);
    result = 31 * result + getHouse();
    result = 31 * result + getApartment();
    return result;
  }

  @Override
  public String toString() {
    return "Address{" + "id=" + id + ", country='" + country + '\'' + ", city='" + city + '\''
        + ", street='" + street + '\'' + ", house=" + house + ", apartment=" + apartment + '}';
  }
}
