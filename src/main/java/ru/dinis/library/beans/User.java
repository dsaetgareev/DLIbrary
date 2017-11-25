package ru.dinis.library.beans;

import java.io.Serializable;

/**
 * class User.
 * User: Dinis Saetgareev (dinis0086@gmail.com)
 * Date: 25.11.17
 */
public class User implements Serializable{
    /**
     * name.
     */
    private String name;
    /**
     * password.
     */
    private String password;

    /**
     * constructor.
     */
    public User() {

    }

    /**
     * getter.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter.
     * @param name - user name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * password.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter.
     * @param password - user password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
