package org.happyfaces.services.base;

import org.happyfaces.domain.base.BaseEntity;
import org.happyfaces.domain.base.IEntity;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class that provides some standard dao methods for any entity. Hovewer
 * it must pass entity class together with other parameters. 
 * P.S. Currently only one method from GenericService is implemented but its easy to add any method
 * from generic service same way.
 * 
 * @author Ignas
 * 
 */
@Transactional(readOnly = true)
public class VariableTypeService implements IVariableTypeService {

    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public BaseEntity getById(Class<? extends IEntity> entityClass, int id) {
        return new BaseService<BaseEntity>(entityClass, sessionFactory).getById(id);
    }

}
