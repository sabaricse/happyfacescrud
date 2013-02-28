package org.happyfaces.beans;

import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.happyfaces.beans.base.BaseBean;
import org.happyfaces.domain.Account;
import org.happyfaces.services.IAccountService;
import org.happyfaces.services.base.IService;

@ManagedBean(name = "accountBean")
@ViewScoped
public class AccountBean extends BaseBean<Account> {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{accountService}")
    private IAccountService accountService;

    public AccountBean() {
        super(Account.class);
    }

    @Override
    protected IService<Account> getPersistenceService() {
        return accountService;
    }

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Fetch customer field so no LazyInitialize exception is thrown when
     * we access it from account list view.
     * 
     * @see org.happyfaces.beans.base.BaseBean#getListFieldsToFetch()
     */
    @Override
    protected List<String> getListFieldsToFetch() {
        return Arrays.asList("customer", "operations");
    }

    /**
     * Fetch customer field so no LazyInitialize exception is thrown when
     * we access it from account edit view.
     * 
     * @see org.happyfaces.beans.base.BaseBean#getFormFieldsToFetch()
     */
    @Override
    protected List<String> getFormFieldsToFetch() {
        return Arrays.asList("customer", "operations");
    }

}
