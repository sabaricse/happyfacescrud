package org.happyfaces.services.base;

import org.happyfaces.domain.base.BaseEntity;
import org.happyfaces.domain.base.IEntity;

/**
 * @author Ignas
 *
 */
public interface IVariableTypeService {
    
    public BaseEntity getById(Class<? extends IEntity> entityClass, int id);

}
