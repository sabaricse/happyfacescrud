package org.happyfaces.services;

import java.util.List;

import org.happyfaces.domain.Customer;
import org.happyfaces.domain.CustomerPerk;
import org.happyfaces.services.base.IService;

public interface ICustomerService extends IService<Customer> {
    
    public List<CustomerPerk> getAllCustomerPerks();

}
