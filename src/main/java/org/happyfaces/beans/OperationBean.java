package org.happyfaces.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.happyfaces.beans.base.BaseBean;
import org.happyfaces.domain.Operation;
import org.happyfaces.services.IOperationService;
import org.happyfaces.services.base.IService;

@ManagedBean(name = "operationBean")
@ViewScoped
public class OperationBean extends BaseBean<Operation> {

    private static final long serialVersionUID = 1L;
    
    @ManagedProperty(value = "#{operationService}")
    private IOperationService operationService;

    public OperationBean() {
        super(Operation.class);
    }

    @Override
    protected IService<Operation> getPersistenceService() {
        return operationService;
    }

    public void setOperationService(IOperationService operationService) {
        this.operationService = operationService;
    }
    
}
