package org.happyfaces.beans;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.happyfaces.utils.FacesUtils;


/**
 * Session scoped bean for authenticating users and keeping their information.
 * 
 * @author Ignas
 * 
 */
@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean {

    /** Logger. */
    private static Logger log = Logger.getLogger(LoginBean.class.getName());

    /**
     * This method delegates login check to spring security filters.
     */
    public String doLogin() throws IOException, ServletException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check");

        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();
        // It's OK to return null here because Faces is just going to exit.
        return null;
    }

    /**
     * Spring security is configured to logout on /logout.jsf url so this method
     * redirects user to that url and other logout logic is performed by spring.
     */
    public void logout() {
        try {
            String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/logout.jsf");
            FacesContext.getCurrentInstance().getExternalContext().redirect(realPath);
        } catch (IOException e) {
            log.warn("Error while redirecting to logout page", e);
            FacesUtils.error("logout.failed");
        }
    }

}
