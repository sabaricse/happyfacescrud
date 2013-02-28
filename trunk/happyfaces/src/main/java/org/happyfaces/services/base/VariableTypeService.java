package org.happyfaces.services.base;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.happyfaces.domain.base.BaseEntity;
import org.happyfaces.domain.base.IEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class that provides some standard dao methods for any entity. Hoverer
 * it must pass entity class together with other parameters. 
 * P.S. Currently only one method from GenericService is implemented but its easy to add any method
 * from generic service same way.
 * 
 * @author Ignas
 * 
 */
@Transactional(readOnly = true)
public class VariableTypeService implements IVariableTypeService {
    
    @PersistenceContext
    private EntityManager em;

    public BaseEntity getById(Class<? extends IEntity> entityClass, int id) {
        return new BaseService<BaseEntity>(entityClass, em).getById(id);
    }

}
