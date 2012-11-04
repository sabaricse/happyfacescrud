package org.happyfaces.jsf.tags.valuebinding;

import javax.faces.view.facelets.TagConfig;

/**
 * @author Ignas
 *
 */
public class IsDoubleHandler extends IsTypeHandler {

	/**
	 * Create tag.
	 * 
	 * @param config
	 *            TagConfig
	 */
	public IsDoubleHandler(final TagConfig config) {
		super(config);
	}

	/**
	 * Is Double
	 * 
	 * @param type
	 *            Type class.
	 * 
	 * @return True if this is Double.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected boolean isType(Class type) {

		/*
		 * If the type is a rational number, process the body of the tag.
		 */
		if (type == Double.class
				|| (type.isPrimitive() && type.getName().equals("double"))) {
			return true;
		} else {
			return false;
		}
	}

}
