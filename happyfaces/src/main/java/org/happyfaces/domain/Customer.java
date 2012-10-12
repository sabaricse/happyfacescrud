package org.happyfaces.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.happyfaces.domain.base.BaseEntity;

@Entity
@Table(name = "CUSTOMER")
public class Customer extends BaseEntity {

    @Column(name = "CUSTOMER_NAME", nullable = false)
    private String name;

    @Embedded
    private Contacts contacts;

    @OneToMany(mappedBy="customer", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Account> accounts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

}