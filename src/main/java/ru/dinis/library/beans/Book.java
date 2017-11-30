package ru.dinis.library.beans;

import java.io.Serializable;
/**
 * class Book.
 * User: Dinis Saetgareev (dinis0086@gmail.com)
 * Date: 25.11.17
 */
public class Book implements Serializable {
    /**
     * book's id.
     */
    private long id;
    /**
     * book's name.
     */
    private String name;
    /**
     * book's content.
     */
    private byte[] content;
    /**
     * page count.
     */
    private int pageCount;
    /**
     * book's isbn.
     */
    private String isbn;
    /**
     * book's author.
     */
    private String author;
    /**
     * book's genre.
     */
    private String genre;
    /**
     * book's publish date.
     */
    private int publishDate;
    /**
     * publisher.
     */
    private String publisher;
    /**
     * image.
     */
    private byte[] image;
    /**
     * book's description.
     */
    private String descr;
    /**
     * edit.
     */
    private boolean edit;

    /**
     * constructor.
     */
    public Book() {

    }

    /**
     * gets id.
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * sets id.
     * @param id - id
     */
    public void setId(long id) {
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
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets content.
     * @return contetn
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * sets content.
     * @param content content
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * gets page count.
     * @return page count
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * sets page count.
     * @param pageCount - page count
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * gets isbn.
     * @return isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * sets isbn.
     * @param isbn isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * gets author.
     * @return - author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * sets author.
     * @param author author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * gets genre.
     * @return - genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * sets genre.
     * @param genre - genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * gets publisher date.
     * @return - publisher date
     */
    public int getPublishDate() {
        return publishDate;
    }

    /**
     * sets publisher date.
     * @param publishDate - publisher date
     */
    public void setPublishDate(int publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * gets publisher.
     * @return - publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * sets publisher.
     * @param publisher - publisher
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * get image.
     * @return - image
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * sets image.
     * @param image - image
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * gets description
     * @return description
     */
    public String getDescr() {
        return descr;
    }

    /**
     * sets description.
     * @param descr - description
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }
}
