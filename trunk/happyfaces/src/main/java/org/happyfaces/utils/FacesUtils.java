package org.happyfaces.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

/**
 * @author Ignas
 *
 */
public class FacesUtils {
    
    /** Logger. */
    private static Logger log = Logger.getLogger(FacesUtils.class.getName());
    
    /**
     * Resource bundles.
     */
    private static final ResourceBundle bundle = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    
    public static void info(String messageKey) {
        addFacesMessage(FacesMessage.SEVERITY_INFO, messageKey);
    }
    
    public static void warn(String messageKey) {
        addFacesMessage(FacesMessage.SEVERITY_WARN, messageKey);
    }
    
    public static void error(String messageKey) {
        addFacesMessage(FacesMessage.SEVERITY_ERROR, messageKey);
    }
    
    
    private static void addFacesMessage(Severity severity, String messageKey) {
        String message = messageKey;
        try {
            message = bundle.getString(messageKey);
        } catch (MissingResourceException e) {
            log.warn(String.format("There was no resource in messages.properties for %s", messageKey));
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, ""));
    }

}
