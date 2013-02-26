package org.happyfaces.jsf.tags.valuebinding;

import java.math.BigDecimal;

import javax.faces.view.facelets.TagConfig;

/**
 * Jsf handler to check if entity field is BigDecimal type.
 * 
 * @author Ignas
 *
 */
public class IsBigDecimalHandler extends IsTypeHandler {

	/**
	 * Create tag.
	 * 
	 * @param config
	 *            TagConfig
	 */
	public IsBigDecimalHandler(final TagConfig config) {
		super(config);
	}

	/**
	 * Is Rational number BigDecimal
	 * 
	 * @param type
	 *            Type class.
	 * 
	 * @return True if this is a BigDecimal.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected boolean isType(Class type) {

		/*
		 * If the type is a rational number, process the body of the tag.
		 */
		if (type == BigDecimal.class) {
			return true;
		} else {
			return false;
		}
	}

}
