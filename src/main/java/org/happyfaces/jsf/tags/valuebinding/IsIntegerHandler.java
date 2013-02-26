package org.happyfaces.jsf.tags.valuebinding;

import java.math.BigInteger;

import javax.faces.view.facelets.TagConfig;

/**
 * @author Ignas
 *
 */
public class IsIntegerHandler extends IsTypeHandler {
	/**
	 * Create tag.
	 * 
	 * @param config
	 *            TagConfig
	 */
	public IsIntegerHandler(final TagConfig config) {
		super(config);
	}

	/**
	 * Is Integer
	 * 
	 * @param type
	 *            Type class.
	 * 
	 * @return True if this is Integer.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected boolean isType(Class type) {
		/*
		 * If the type is a string, process the body of the tag.
		 */
		if (type == Integer.class || type == BigInteger.class
				|| (type.isPrimitive() && type.getName().equals("int"))) {
			return true;
		} else {
			return false;
		}
	}
}
