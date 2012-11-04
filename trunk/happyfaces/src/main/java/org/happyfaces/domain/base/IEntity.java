package org.happyfaces.domain.base;

import java.io.Serializable;

/**
 * @author Ignas
 *
 */
public interface IEntity {
    
    public Serializable getId();
    
    public boolean isTransient();

}
