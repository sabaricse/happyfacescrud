package org.happyfaces.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

/**
 * Example of embeded object and some hibernate validation.
 * 
 * @author Ignas
 *
 */
@Embeddable
public class Contacts {
    
    @Column(name = "CITY")
    private String city;
    
    @Column(name = "ADDRESS")
    private String address;
    
    @Email
    @Column(name = "EMAIL")
    private String email;
    
    @Length(min=7, max=7)
    @Column(name = "PHONE")
    private String phone;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
