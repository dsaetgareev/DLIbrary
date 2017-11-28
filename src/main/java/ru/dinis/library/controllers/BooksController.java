package ru.dinis.library.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dinis.library.beans.Book;
import ru.dinis.library.database.DBManager;
import ru.dinis.library.enums.SearchType;

import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

/**
 * class Books controller.
 * User: Dinis Saetgareev (dinis0086@gmail.com)
 * Date: 25.11.17
 */
public class BooksController implements Serializable {
    /**
     * logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BooksController.class);
    /**
     * current book list.
     */
    private ArrayList<Book> currentBookList;
    /**
     * count books on page.
     */
    private int booksOnPage = 2;
    /**
     * selected page number.
     */
    private int selectedPageNumber = 1;
    /**
     * total all books.
     */
    private int totalBooksCount;
    /**
     * list of page numbers.
     */
    private ArrayList<Integer> pageNumbers = new ArrayList<>();
    /**
     * current sql.
     */
    private String currentSql;
    /**
     * selected genre id.
     */
    private int selectedGenreId;
    /**
     * selected letter.
     */
    private char selectedLetter;
    /**
     * request from pager.
     */
    private boolean requestFromPager = false;
    /**
     * search type.
     */
    private SearchType searchType;
    /**
     * search string.
     */
    private String searchString;

    /**
     * constructor.
     */
    public BooksController() {
        fillBooksAll();
    }

    /**
     * submit values.
     * @param selectedLetter - selected letter
     * @param selectedGenreId - selected genre id
     * @param selectedPageNumber - selected page number
     * @param requestFromPager - request from pager
     */
    public void submitValues(Character selectedLetter, int selectedGenreId,
                             int selectedPageNumber, boolean requestFromPager) {
        this.selectedLetter = selectedLetter;
        this.selectedGenreId = selectedGenreId;
        this.selectedPageNumber = selectedPageNumber;
        this.requestFromPager = requestFromPager;
    }

    /**
     * fill Books by sql.
     * @param sql - string
     */
    public void fillBooksBySql(String sql) {
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;

        this.currentSql = sql;

        StringBuilder sqlBuilder = new StringBuilder(sql);

        this.currentBookList = new ArrayList<>();

        try {
            conn = DBManager.getConnection();
            stm = conn.createStatement();
            if (!requestFromPager) {
                rs = stm.executeQuery(sql);
                rs.last();
                this.totalBooksCount = rs.getRow();
                fillPageNumbers(this.totalBooksCount, this.booksOnPage);
            }

            if (this.totalBooksCount > this.booksOnPage) {
                sqlBuilder.append(" limit ").append(this.selectedPageNumber * this.booksOnPage - this.booksOnPage).append(",").append(this.booksOnPage);
            }
            rs = stm.executeQuery(sqlBuilder.toString());
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setName(rs.getString("name"));
                book.setPageCount(rs.getInt("page_count"));
                book.setIsbn(rs.getString("isbn"));
                book.setGenre(rs.getString("genre"));
                book.setAuthor(rs.getString("author"));
                book.setPublishDate(rs.getInt("publish_year"));
                book.setPublisher(rs.getString("publisher"));
                book.setDescr(rs.getString("descr"));
                this.currentBookList.add(book);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.info(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    stm.close();
                    conn.close();
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    /**
     * fill all books.
     */
    public String fillBooksAll() {
        this.fillBooksBySql("select b.id, b.name, b.page_count, b.isbn, g.name as genre, a.fio as author," +
                "b.publish_year, p.name as publisher, b.descr FROM book b " +
                "INNER JOIN author a ON b.author_id = a.id " +
                "INNER JOIN genre g ON b.genre_id = g.id " +
                "INNER JOIN publisher p ON b.publisher_id = p.id " +
                "order by b.name ");
        return "books";
    }

    /**
     * fill books by genre id.
     */
    public void fillBooksByGenre() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        this.selectedGenreId = Integer.valueOf(params.get("genre_id"));
        submitValues(' ',this.selectedGenreId, 1, false);
        this.fillBooksBySql("SELECT b.id, b.name, b.page_count, b.isbn, g.name as genre, a.fio as author, " +
                "b.publish_year, p.name as publisher, b.descr FROM book b " +
                "INNER JOIN author a ON b.author_id = a.id " +
                "INNER JOIN genre g ON b.genre_id = g.id " +
                "INNER JOIN publisher p ON b.publisher_id = p.id " +
                "WHERE b.genre_id = " + this.selectedGenreId +
                " ORDER BY b.name ");
    }

    /**
     * fill books by letter.
     */
    public void fillBooksByLetter() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        this.selectedLetter = params.get("letter").charAt(0);
        submitValues(this.selectedLetter,-1, 1, false);
        this.fillBooksBySql("SELECT b.id, b.name, b.page_count, b.isbn, g.name as genre, a.fio as author," +
                "b.publish_year, p.name as publisher, b.descr FROM book b " +
                "INNER JOIN author a ON b.author_id = a.id " +
                "INNER JOIN genre g ON b.genre_id = g.id " +
                "INNER JOIN publisher p ON b.publisher_id = p.id " +
                "WHERE substr(b.name, 1, 1) = '" + this.selectedLetter +
                "' ORDER BY b.name ");
    }

    /**
     * fill books by search.
     */
    public void fillBooksBySearch() {
        submitValues(' ',-1, 1, false);
        StringBuilder stringBuilder = new StringBuilder("SELECT b.id, b.name, b.page_count, b.isbn, g.name as genre, a.fio as author," +
                "b.publish_year, p.name as publisher, b.descr FROM book b " +
                "INNER JOIN author a ON b.author_id = a.id " +
                "INNER JOIN genre g ON b.genre_id = g.id " +
                "INNER JOIN publisher p ON b.publisher_id = p.id ");
        if (this.searchType == SearchType.AUTHOR) {
            stringBuilder.append(" WHERE LOWER(author) LIKE '%" + this.searchString.toLowerCase() + "%' order by b.name");
        }
        if (this.searchType == SearchType.TITLE) {
            stringBuilder.append(" WHERE LOWER(b.name) LIKE '%" + this.searchString.toLowerCase() + "%' order by b.name");
        }
        this.fillBooksBySql(stringBuilder.toString());
    }

    /**
     * selected page.
     */
    public void selectedPage() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        this.selectedPageNumber = Integer.valueOf(params.get("page_number"));
        this.requestFromPager = true;
        fillBooksBySql(this.currentSql);
    }

    /**
     * gets content.
     * @param id - books id
     * @return byte[] content
     */
    public byte[] getContent(int id) {
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        byte[] content = null;
        try {
            conn = DBManager.getConnection();
            stm = conn.createStatement();
            rs = stm.executeQuery("SELECT content FROM book WHERE book.id = " + id);
            while (rs.next()) {
                content = rs.getBytes("content");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            DBManager.closeConnection(rs, stm, conn);
        }
        return content;
    }

    /**
     * gets image by book id
     * @param id - id of book
     * @return - image
     */
    public byte[] getImage(int id) {
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        byte[] image = null;

        try {
            conn = DBManager.getConnection();
            stm = conn.createStatement();
            rs = stm.executeQuery("SELECT image FROM book WHERE book.id = " + id);
            while (rs.next()) {
                image = rs.getBytes("image");
            }
        } catch (SQLException e){
            LOGGER.error(e.getMessage(), e);
        } finally {
            DBManager.closeConnection(rs, stm, conn);
        }
        return image;
    }

    /**
     * fill page number.
     * @param totalBooksCount - total books
     * @param booksOnPage books on page
     */
    public void fillPageNumbers(int totalBooksCount, int booksOnPage) {
        int count = booksOnPage > 0 ? (int) ((totalBooksCount/booksOnPage) + 1) : 0;
        this.pageNumbers.clear();
        for (int i = 1; i <= count; i++) {
            this.pageNumbers.add(i);
        }
    }

    public ArrayList<Book> getCurrentBookList() {
        return currentBookList;
    }

    public void setCurrentBookList(ArrayList<Book> currentBookList) {
        this.currentBookList = currentBookList;
    }

    public int getBooksOnPage() {
        return booksOnPage;
    }

    public void setBooksOnPage(int booksOnPage) {
        this.booksOnPage = booksOnPage;
    }

    public int getSelectedPageNumber() {
        return selectedPageNumber;
    }

    public void setSelectedPageNumber(int selectedPageNumber) {
        this.selectedPageNumber = selectedPageNumber;
    }

    public int getTotalBooksCount() {
        return totalBooksCount;
    }

    public void setTotalBooksCount(int totalBooksCount) {
        this.totalBooksCount = totalBooksCount;
    }

    public ArrayList<Integer> getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(ArrayList<Integer> pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    public String getCurrentSql() {
        return currentSql;
    }

    public void setCurrentSql(String currentSql) {
        this.currentSql = currentSql;
    }

    public int getSelectedGenreId() {
        return selectedGenreId;
    }

    public void setSelectedGenreId(int selectedGenreId) {
        this.selectedGenreId = selectedGenreId;
    }

    public char getSelectedLetter() {
        return selectedLetter;
    }

    public void setSelectedLetter(char selectedLetter) {
        this.selectedLetter = selectedLetter;
    }

    public boolean isRequestFromPager() {
        return requestFromPager;
    }

    public void setRequestFromPager(boolean requestFromPager) {
        this.requestFromPager = requestFromPager;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
