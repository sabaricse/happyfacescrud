package org.happyfaces.domain;

/**
 * Interface for identifiableEnum. This is used when enum is mapped in model as
 * integer.
 * 
 * @author Ignas
 * 
 */
public interface IdentifiableEnum {

    public Integer getId();
    
    public String getLabel();

}
