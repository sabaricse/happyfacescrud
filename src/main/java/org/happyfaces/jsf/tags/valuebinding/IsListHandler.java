package org.happyfaces.jsf.tags.valuebinding;

import java.util.List;
import java.util.Set;

import javax.faces.view.facelets.TagConfig;

/**
 * Jsf handler to check if entity field is of list type.
 * 
 * @author Ignas
 *
 */
public class IsListHandler extends IsTypeHandler {
    
    /**
     * Create tag.
     *
     * @param config TagConfig
     */
    public IsListHandler(final TagConfig config) {
        super(config);
    }
    
    /**
     * Checks if is of List type. It {@link Set} is also considered as List type.
     *
     * @param type Type class.
     *
     * @return True if this is a List.
     */
    @SuppressWarnings("rawtypes")
    protected boolean isType(final Class type) {
        if (type == List.class || type == Set.class) {
            return true;
        }
        return false;
    }

}
