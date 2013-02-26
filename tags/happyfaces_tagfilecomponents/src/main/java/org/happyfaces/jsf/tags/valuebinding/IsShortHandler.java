package org.happyfaces.jsf.tags.valuebinding;

import javax.faces.view.facelets.TagConfig;

/**
 * @author Ignas
 *
 */
public class IsShortHandler extends IsTypeHandler {

	/**
	 * Create tag.
	 * 
	 * @param config
	 *            TagConfig
	 */
	public IsShortHandler(final TagConfig config) {
		super(config);
	}

	/**
	 * Is Number Short
	 * 
	 * @param type
	 *            Type class.
	 * 
	 * @return True if this is a Short.
	 */
	@SuppressWarnings("rawtypes")
    @Override
	protected boolean isType(Class type) {
		/*
		 * If the type is a string, process the body of the tag.
		 */
		if (type == Short.class
				|| (type.isPrimitive() && type.getName().equals("short"))) {
			return true;
		} else {
			return false;
		}
	}

}
