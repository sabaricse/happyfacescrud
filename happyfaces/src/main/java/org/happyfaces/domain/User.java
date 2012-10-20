package org.happyfaces.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.happyfaces.domain.base.BaseEntity;

/**
 * Application user entity. Used to demonstrate logging in. TODO
 * 
 * @author Ignas
 *
 */
@Entity
@Table(name = "USER")
public class User extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
