package org.happyfaces.jsf.exceptionhandler;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.log4j.Logger;
import org.happyfaces.utils.FacesUtils;

/**
 * Exception handler implementation. This handler is central part where all
 * unexpected errors in application is processed.
 * 
 * @author Ignas
 * 
 */
public class HappyFacesExceptionHandler extends ExceptionHandlerWrapper {

    private static final Logger log = Logger.getLogger(HappyFacesExceptionHandler.class.getName());

    private ExceptionHandler wrapped;

    HappyFacesExceptionHandler(ExceptionHandler exception) {
        this.wrapped = exception;
    }

    /**
     * @see javax.faces.context.ExceptionHandlerWrapper#getWrapped()
     */
    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    /**
     * For ViewExpiredException it redirects to login page for all other
     * unexpected exception it redirects to error.xhtml with error message.
     * 
     * @see javax.faces.context.ExceptionHandlerWrapper#handle()
     */
    @Override
    public void handle() throws FacesException {

        final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

            Throwable t = context.getException();

            final FacesContext fc = FacesContext.getCurrentInstance();
            final NavigationHandler nav = fc.getApplication().getNavigationHandler();

            try {

                if (t instanceof ViewExpiredException) {

                    FacesUtils.error("error.sessionExpired");
                    nav.handleNavigation(fc, null, "/login.xhtml");
                    fc.renderResponse();

                } else {

                    log.error("Unexpected exception.", t);

                    FacesUtils.error("error.unexpected");

                    // add this if need to show exception. Also might add
                    // automatic email notification to admin about error, or at
                    // least admin info for customer etc..
                    // FacesUtils.addFacesMessageWithoutKey(FacesMessage.SEVERITY_WARN,
                    // t.getCause().toString());

                    // redirect to error page
                    nav.handleNavigation(fc, null, "/error.xhtml");
                    fc.renderResponse();
                }

            } finally {
                // remove it from queue
                i.remove();
            }
        }
        getWrapped().handle();
    }

}
