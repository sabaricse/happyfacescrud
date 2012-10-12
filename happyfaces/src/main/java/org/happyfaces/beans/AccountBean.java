package org.happyfaces.beans;

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
    
}
