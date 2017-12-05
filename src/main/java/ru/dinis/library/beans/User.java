package ru.dinis.library.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * class User.
 * User: Dinis Saetgareev (dinis0086@gmail.com)
 * Date: 25.11.17
 */
public class User implements Serializable{

    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);
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

    public String login() {
        try {
            ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
                    .login(this.name, this.password);
            return "books";
        } catch (ServletException e) {
            LOGGER.error(e.getMessage(), e);
            ResourceBundle bundle = ResourceBundle.getBundle("messages",
                    FacesContext.getCurrentInstance().getViewRoot().getLocale());
            FacesMessage message = new FacesMessage(bundle.getString("login_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("login_form", message);
        }
        return "index";
    }

    public String logout() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            request.logout();
        } catch (ServletException e) {
            LOGGER.error(e.getMessage(), e);
        }
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
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
