package org.happyfaces.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.happyfaces.domain.base.BaseEntity;

/**
 * @author Ignas
 *
 */
@Entity
@Table(name = "CITY")
public class City  extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    private String name;
    
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
}
