package org.happyfaces.jsf.tags.valuebinding;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

/**
 * Maps value binding and type so other tags can access it.
 * 
 * @author Ignas
 */
public class SetValueBindingHandler extends TagHandler {

    /** The name of the new variable that this tag defines. */
    private final TagAttribute var;

    /** The actual value binding expression. */
    private final TagAttribute valueBinding;

    /**
     * Constructor. Set up the attributes for this tag.
     * 
     * @param config
     *            TagConfig
     */
    public SetValueBindingHandler(final TagConfig config) {
        super(config);
        /* Define var and valueBinding attributes. */
        this.var = this.getRequiredAttribute("var");
        this.valueBinding = this.getRequiredAttribute("valueBinding");
    }

    /**
     * Apply.
     * 
     * @param faceletsContext
     *            faceletsContext
     * @param parent
     *            parent
     * 
     * @throws IOException
     *             IOException
     */
    public void apply(final FaceletContext faceletsContext, final UIComponent parent) {
        /* Create the ValueExpression from the valueBinding attribute. */
        ValueExpression valueExpression = this.valueBinding.getValueExpression(faceletsContext, Object.class);

        /* Get the name of the new value. */
        String tvar = this.var.getValue(faceletsContext);
        @SuppressWarnings("rawtypes")
        Class type = valueExpression.getType(faceletsContext);

        /*
         * Put the value binding into the FaceletsContext where we can retrieve
         * it from other components.
         */
        faceletsContext.setAttribute(tvar, valueExpression);

        /*
         * Cache the type so we don't have to look it up in each tag.
         */
        faceletsContext.setAttribute(tvar + "Type", type);
    }

}
