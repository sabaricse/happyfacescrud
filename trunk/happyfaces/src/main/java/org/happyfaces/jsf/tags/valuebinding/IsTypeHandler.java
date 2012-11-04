package org.happyfaces.jsf.tags.valuebinding;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

/**
 * Super class for concrete type handlers.
 * 
 * @author Ignas
 */
public abstract class IsTypeHandler extends TagHandler {
	
    /** ID.  */
    private final TagAttribute id;

    /**
     * Create tag.
     *
     * @param config TagConfig
     */
    public IsTypeHandler(final TagConfig config) {
        super(config);
        this.id = this.getRequiredAttribute("id");
    }

    /**
     * Is the current field a boolean.
     *
     * @param faceletsContext ctx
     * @param aParent parent
     *
     * @throws IOException IOException
     */
    @SuppressWarnings("rawtypes")
    public void apply(final FaceletContext faceletsContext, final UIComponent aParent)
        throws IOException {
        /* Get the name of the value binding. */
        String tid = this.id.getValue(faceletsContext);
        Class type = 
          (Class) faceletsContext.getVariableMapper().resolveVariable(tid + "Type")
                                                .getValue(faceletsContext);

        if (isType(type)) {
            this.nextHandler.apply(faceletsContext, aParent);
        }
    }

    /**
     *
     * @param type Type.
     *
     * @return True if this is the correct type.
     */
    @SuppressWarnings("rawtypes")
    protected abstract boolean isType(Class type);
}

