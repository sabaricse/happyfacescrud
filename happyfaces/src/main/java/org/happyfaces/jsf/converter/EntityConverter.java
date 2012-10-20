package org.happyfaces.jsf.converter;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.log4j.Logger;
import org.happyfaces.domain.base.BaseEntity;
import org.happyfaces.services.base.IVariableTypeService;

/**
 * Generic Entity Converter for any Entity that extends BaseIdentityEntity
 * 
 */
@RequestScoped
@ManagedBean(name = "entityConverter")
public class EntityConverter implements javax.faces.convert.Converter, Serializable {
    
    private static final long serialVersionUID = 1L;

    private static Logger log = Logger.getLogger(EntityConverter.class);
    
    @ManagedProperty(value = "#{variableTypeService}")
    private IVariableTypeService variableTypeService;

    public void setVariableTypeService(IVariableTypeService variableTypeService) {
        this.variableTypeService = variableTypeService;
    }

    @SuppressWarnings("unchecked")
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) throws ConverterException {
        BaseEntity entity;
        if (value == null) {
            entity = null;
        } else {
            Integer id = new Integer(value);
            entity = (BaseEntity) variableTypeService.getById(getClazz(facesContext, component), id);
            if (entity == null) {
                log.error("There is no entity with id:  " + id + " for class " + getClazz(facesContext, component));
            }
        }
        return entity;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) throws ConverterException {
        if (value != null && !(value instanceof BaseEntity)) {
            throw new IllegalArgumentException("This converter only handles instances of BaseEntity");
        }
        if (value == null) {
            return "";
        }
        if (value instanceof String) {
            return (String) value;
        }
        BaseEntity entity = (BaseEntity) value;
        return entity.getId() == null ? "" : entity.getId().toString();
    }

    @SuppressWarnings("rawtypes")
    private Class getClazz(FacesContext facesContext, UIComponent component) {
        return component.getValueExpression("value").getType(facesContext.getELContext());
    }

}