package com.epam.conferences.model;

import java.io.Serializable;
import java.util.Arrays;

public class User implements Serializable {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private char[] password;
    private int roleId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
                + '\'' + ", email='" + email + '\'' + ", password=" + Arrays.toString(password)
                + ", roleId=" + roleId + '}';
    }
}
