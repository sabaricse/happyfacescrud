package org.happyfaces.jsf.tags.valuebinding;

import javax.faces.view.facelets.TagConfig;

/**
 * @author Ignas
 *
 */
public class IsByteHandler extends IsTypeHandler {

	/**
	 * Create tag.
	 * 
	 * @param config
	 *            TagConfig
	 */
	public IsByteHandler(final TagConfig config) {
		super(config);
	}

	/**
	 * Is Number (byte, short, int, long, BigInteger).
	 * 
	 * @param type
	 *            Type class.
	 * 
	 * @return True if this is a number.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected boolean isType(Class type) {
		/*
		 * If the type is a string, process the body of the tag.
		 */
		if (type == Byte.class
				|| (type.isPrimitive() && type.getName().equals("byte"))) {
			return true;
		} else {
			return false;
		}
	}

}
