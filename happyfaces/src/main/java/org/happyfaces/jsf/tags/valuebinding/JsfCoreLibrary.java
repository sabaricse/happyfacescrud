package org.happyfaces.jsf.tags.valuebinding;

import com.sun.faces.facelets.tag.AbstractTagLibrary;

/**
 * @author Ignas
 */
public final class JsfCoreLibrary extends AbstractTagLibrary {

	/** Namespace used to import this library in Facelets pages. */
	public static final String NAMESPACE = "http://happyfaces.org/bind";

	/** Current instance of library. */
	public static final JsfCoreLibrary INSTANCE = new JsfCoreLibrary();

	/**
	 * Creates a new JsfCoreLibrary object.
	 */
	public JsfCoreLibrary() {
		super(NAMESPACE);
		this.addTagHandler("setValueBinding", SetValueBindingHandler.class);
		this.addTagHandler("isBoolean", IsBooleanHandler.class);
		this.addTagHandler("isText", IsTextHandler.class);
		this.addTagHandler("isDate", IsDateHandler.class);
		this.addTagHandler("isEnum", IsEnumHandler.class);
		this.addTagHandler("isFloat", IsFloatHandler.class);
		this.addTagHandler("isDouble", IsDoubleHandler.class);
		this.addTagHandler("isList", IsListHandler.class);
		this.addTagHandler("isInteger", IsIntegerHandler.class);
		this.addTagHandler("isLong", IsLongHandler.class);
		this.addTagHandler("isByte", IsByteHandler.class);
		this.addTagHandler("isShort", IsShortHandler.class);
		this.addTagHandler("isBigDecimal", IsBigDecimalHandler.class);
	}

}
