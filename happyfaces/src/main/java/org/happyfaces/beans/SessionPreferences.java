 package org.happyfaces.beans;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;

/**
 * Static and non static methods for session related information and actions.
 * 
 * @author Ignas
 *
 */
@ManagedBean(name = "sessionPreferences")
@SessionScoped
public class SessionPreferences implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Locale locale;
    
    public SessionPreferences() {
    	this.locale = new Locale("ru");
    	FacesContext.getCurrentInstance().getViewRoot().setLocale(this.locale);
    }

    public static Locale getCurrentLocale() {
        return FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }
    
    public Locale getLocale() {
        if (locale == null) {
            changeLocale(SessionPreferences.getCurrentLocale().getLanguage());
        }
        return locale;
    }

    public void changeLocale(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
    
    /**
     * Get session duration.
     */
    public int getSessionDuration() {
        return ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getMaxInactiveInterval();
    }
    
    /**
     * When 60 sec left, show session expiration popup.
     */
    public int getSessionTimeoutAlertDuration() {
        return 60;
    }
    
    /**
     * Maintain session.
     */
    public void keepSessionAlive() {
       FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }
    
    /**
     * Check if user has authority to edit. Override if specific role is
     * required for different pages.
     * 
     * @return true if edit is allowed
     */
    public boolean isEditAllowed() {
        return hasRole("ROLE_USER");
    }

    /**
     * Check if user has role.
     */
    private boolean hasRole(String role) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        SecurityContextHolderAwareRequestWrapper sc = new SecurityContextHolderAwareRequestWrapper(req, "");
        return sc.isUserInRole(role);
    }

    /**
    * Return authenticated user name.
    */
   public static String getUserName() {
       try {
           UserDetails authenticatedUser = (UserDetails) ((SecurityContext) SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
           return authenticatedUser.getUsername();
       } catch (Throwable e) {
           return "authentication error";
       }
   }
    
}
