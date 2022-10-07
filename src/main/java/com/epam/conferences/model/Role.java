package com.epam.conferences.model;

import java.io.Serializable;

public enum Role implements Serializable {

    MODERATOR(1), SPEAKER(2), REG_USER(3);

    public final int id;

    Role(int id) {
        this.id = id;
    }


}
