package ru.dinis.library.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dinis.library.beans.Book;

import java.io.Serializable;
import java.util.ArrayList;

public class BooksController implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BooksController.class);

    private ArrayList<Book> currentBookList;
}
