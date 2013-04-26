package org.happyfaces.services;

import java.util.List;

import org.happyfaces.domain.Customer;
import org.happyfaces.domain.CustomerPerk;
import org.happyfaces.services.base.BaseService;

/**
 * Service implementation for Customer.
 * 
 * @author Ignas
 *
 */
public class CustomerService extends BaseService<Customer> implements ICustomerService {

    private static final long serialVersionUID = 1L;

    /**
     * @see org.happyfaces.services.ICustomerService#getAllCustomerPerks()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerPerk> getAllCustomerPerks() {
        return em.createQuery("from CustomerPerk").getResultList();
    }

}
