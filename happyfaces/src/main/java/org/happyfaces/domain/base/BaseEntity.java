package org.happyfaces.domain.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * @author Ignas
 *
 */
@MappedSuperclass
public class BaseEntity implements IEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Override
    public Serializable getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean isTransient() {
        return id == null;
    }

}
