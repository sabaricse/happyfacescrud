package org.happyfaces.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.happyfaces.domain.base.BaseEntity;

/**
 * @author Ignas
 *
 */
@Entity
@Table(name = "ACCOUNT")
public class Account extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;
    
    @NotNull
    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    private String accountNumber;
    
    @Column(name = "ACCOUNT_ACTIVE")
    private Boolean active;
    
    @OneToMany(mappedBy="account", cascade = CascadeType.ALL)
    private List<Operation> operations;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "OPENING_DATE")
    private Date openingDate;
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }
    
}
