package ru.dinis.library.beans;

import java.io.Serializable;
/**
 * class Book.
 * User: Dinis Saetgareev (dinis0086@gmail.com)
 * Date: 26.11.17
 */
public class Genre implements Serializable {
    /**
     * id.
      */
    private int id;
    /**
     * name.
     */
    private String name;

    /**
     * constructor.
     */
    public Genre() {

    }

    /**
     * gets id.
     * @return - id
     */
    public int getId() {
        return id;
    }

    /**
     * sets id.
     * @param id - id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * sets name.
     * @param name - name
     */
    public void setName(String name) {
        this.name = name;
    }
}
