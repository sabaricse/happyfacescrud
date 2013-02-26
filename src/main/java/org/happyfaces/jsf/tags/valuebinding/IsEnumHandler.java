package org.happyfaces.jsf.tags.valuebinding;

import javax.faces.view.facelets.TagConfig;

/**
 * Jsf handler to check if entity field is enum type.
 * 
 * @author Ignas
 */
public class IsEnumHandler extends IsTypeHandler {
	
	/**
     * Create tag.
     *
     * @param config TagConfig
     */
    public IsEnumHandler(final TagConfig config) {
        super(config);
    }

    /**
     * Is Enum.
     *
     * @param type Type class.
     *
     * @return True if this is a Enum.
     */
    @SuppressWarnings("rawtypes")
    protected boolean isType(final Class type) {
        /* If the type is a enum, process the body of the tag.*/
        if (type.isEnum()) {
            return true;
        }
        return false;
    }

}
