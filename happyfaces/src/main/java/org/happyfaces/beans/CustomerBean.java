package org.happyfaces.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.happyfaces.beans.base.BaseBean;
import org.happyfaces.domain.Customer;
import org.happyfaces.services.ICustomerService;
import org.happyfaces.services.base.IService;

@ManagedBean(name = "customerBean")
@ViewScoped
public class CustomerBean extends BaseBean<Customer> {

    private static final long serialVersionUID = 1L;
    
    @ManagedProperty(value = "#{customerService}")
    private ICustomerService customerService;
    
    public CustomerBean() {
        super(Customer.class);
    }

    @Override
    public IService<Customer> getPersistenceService() {
        return customerService;
    }

    public void setCustomerService(ICustomerService customerService) {
        this.customerService = customerService;
    }
    
    public List<Customer> getCustomers() {
        return customerService.list();
    }

}
