package org.happyfaces.jsf.tags.valuebinding;

import javax.faces.view.facelets.TagConfig;

/**
 * Jsf handler to check if entity field is boolean type.
 *
 * @author Ignas
 */
public class IsBooleanHandler extends IsTypeHandler {

    /**
     * Create tag.
     *
     * @param config TagConfig
     */
    public IsBooleanHandler(final TagConfig config) {
        super(config);
    }

    /**
     * Is Boolean.
     * 
     * @param type Type class.
     *
     * @return true if this is a boolean.
     */
    @SuppressWarnings("rawtypes")
    protected boolean isType(final Class type) {
        /* If the type is a boolean, process the body of the tag.
         */
        if (type == Boolean.class) {
            return true;
        } else if (type.isPrimitive() && "boolean".equals(type.getName())) {
            return true;
        }

        return false;
    }

}
