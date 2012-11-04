package org.happyfaces.jsf.tags.valuebinding;

import javax.faces.view.facelets.TagConfig;

/**
 * @author Ignas
 *
 */
public class IsFloatHandler extends IsTypeHandler {

	/**
	 * Create tag.
	 * 
	 * @param config
	 *            TagConfig
	 */
	public IsFloatHandler(final TagConfig config) {
		super(config);
	}

	/**
	 * Is Float
	 * 
	 * @param type
	 *            Type class.
	 * 
	 * @return True if this is Float.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected boolean isType(Class type) {

		/*
		 * If the type is a rational number, process the body of the tag.
		 */
		if (type == Float.class
				|| (type.isPrimitive() && type.getName().equals("float"))) {
			return true;
		} else {
			return false;
		}

	}
}
