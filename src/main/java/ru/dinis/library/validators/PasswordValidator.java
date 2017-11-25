package ru.dinis.library.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.ResourceBundle;
/**
 * class LoginValidator checks validate input text.
 * User: Dinis Saetgareev (dinis0086@gmail.com)
 * Date: 25.11.17
 */
@FacesValidator("ru.dinis.library.validators.PasswordValidator")
public class PasswordValidator implements Validator {
    /**
     * validate.
     * @param facesContext - faces context
     * @param uiComponent - ui component
     * @param o - String
     * @throws ValidatorException - exception
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        ResourceBundle bundle = ResourceBundle.getBundle("messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
            String value = o.toString();
        if (value.length() <= 5) {
            FacesMessage message = new FacesMessage(bundle.getString("password_length_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
