package ru.dinis.library.beans.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.ResourceBundle;
/**
 * class LoginValidator checks validate input text.
 * User: Dinis Saetgareev (dinis0086@gmail.com)
 * Date: 25.11.17
 */
public class LoginValidator implements Validator {
    ResourceBundle bundle = ResourceBundle.getBundle("messages",
            FacesContext.getCurrentInstance().getViewRoot().getLocale());

    /**
     * validate.
     * @param facesContext - faces context
     * @param uiComponent - ui component
     * @param o - String
     * @throws ValidatorException - exception
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        try {
            String value = o.toString();

            if (value.length() < 5) {
                throw new IllegalArgumentException(bundle.getString("login_length_error"));
            }
            if (!Character.isLetter(value.charAt(0))) {
                throw new IllegalArgumentException(bundle.getString("login_letter_error"));
            }
        } catch (IllegalArgumentException e) {
            FacesMessage message = new FacesMessage(e.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }
}
