package ru.dinis.library.controllers;

import ru.dinis.library.enums.SearchType;

import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
/**
 * class SearchController.
 * User: Dinis Saetgareev (dinis0086@gmail.com)
 * Date: 26.11.17
 */
public class SearchController implements Serializable {
    /**
     * search list.
     */
    private Map<String, SearchType> searchList = new HashMap<>();

    /**
     * constructor.
     */
    public SearchController() {
        ResourceBundle bundle = ResourceBundle.getBundle("messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        this.searchList.put(bundle.getString("author_name"), SearchType.AUTHOR);
        this.searchList.put(bundle.getString("book_name"), SearchType.TITLE);
    }

    public Map<String, SearchType> getSearchList() {
        return searchList;
    }

    public void setSearchList(Map<String, SearchType> searchList) {
        this.searchList = searchList;
    }
}
