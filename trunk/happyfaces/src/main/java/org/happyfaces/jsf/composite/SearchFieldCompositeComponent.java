package org.happyfaces.jsf.composite;

import java.util.Map;

import javax.faces.component.FacesComponent;

/**
 * Backing UINamingContainer for searchField.xhtml composite component.
 * 
 * @author Ignas
 * 
 */
@FacesComponent(value = "searchField")
public class SearchFieldCompositeComponent extends BackingBeanBasedCompositeComponent {

    /**
     * Helper method to get filters from backing bean.
     */
    public Map<String, Object> getFilters() {
        return super.getBackingBeanFromParentOrCurrent().getFilters();
    }

}
