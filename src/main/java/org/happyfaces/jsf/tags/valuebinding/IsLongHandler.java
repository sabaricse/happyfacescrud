package org.happyfaces.jsf.tags.valuebinding;

import javax.faces.view.facelets.TagConfig;

/**
 * @author Ignas
 *
 */
public class IsLongHandler extends IsTypeHandler {

	/**
	 * Create tag.
	 * 
	 * @param config
	 *            TagConfig
	 */
	public IsLongHandler(final TagConfig config) {
		super(config);
	}

	/**
	 * Is Long
	 * 
	 * @param type
	 *            Type class.
	 * 
	 * @return True if this is Long
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected boolean isType(Class type) {
		/*
		 * If the type is a string, process the body of the tag.
		 */
		if (type == Long.class
				|| (type.isPrimitive() && type.getName().equals("long"))) {
			return true;
		} else {
			return false;
		}
	}
}
