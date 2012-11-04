package org.happyfaces.jsf.tags.valuebinding;

import java.util.Date;

import javax.faces.view.facelets.TagConfig;

/**
 * Jsf handler to check if entity field is date type.
 * 
 * @author Ignas
 */
public class IsDateHandler extends IsTypeHandler {
	
	/**
     * Create tag.
     *
     * @param config TagConfig
     */
    public IsDateHandler(final TagConfig config) {
        super(config);
    }

    /**
     * Is Date.
     *
     * @param type Type class.
     *
     * @return True if this is a Date.
     */
    @SuppressWarnings("rawtypes")
    protected boolean isType(final Class type) {
        /* If the type is a date, process the body of the tag.
         */
        if (type == Date.class) {
            return true;
        }
        return false;
    }

}
