package org.happyfaces.jsf.tags.valuebinding;

import javax.faces.view.facelets.TagConfig;

/**
 * Jsf handler to check if entity field is text type.
 * 
 * @author Ignas
 */
public class IsTextHandler extends IsTypeHandler {

	/**
	 * Create tag.
	 * 
	 * @param config
	 *            TagConfig
	 */
	public IsTextHandler(final TagConfig config) {
		super(config);
	}

	/**
	 * Is Text.
	 * 
	 * @param type
	 *            Type class.
	 * 
	 * @return True if this is a text.
	 */
	@SuppressWarnings("rawtypes")
    protected boolean isType(final Class type) {
		
		/*
		 * If the type is a string, process the body of the tag.
		 */
		if (type == String.class || type == Character.class) {
			return true;
		} else {
			return false;
		}
	}

}
