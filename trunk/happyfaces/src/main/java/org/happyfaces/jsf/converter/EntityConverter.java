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
 * Custom generic entity converter. Fits for all subclasses of
 * {@link BaseEntity}, no need to create new converter for each entity.
 * 
 * @author Ignas
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

    /**
     * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) throws ConverterException {
        BaseEntity entity;
        if (value == null || "".equals(value)) {
            entity = null;
        } else {
            String[] idAndClass = value.split("_");

            Long id = Long.valueOf(idAndClass[0]);
            @SuppressWarnings("rawtypes")
            Class clazz;
            try {
                clazz = Class.forName(idAndClass[1]);
            } catch (ClassNotFoundException e) {
                throw new ConverterException("Class with name " + idAndClass[1] + " was not found.");
            }

            entity = (BaseEntity) variableTypeService.getById(clazz, id);
            if (entity == null) {
                log.error("There is no entity with id:  " + id + " for class " + clazz);
            }
        }
        return entity;
    }

    /**
     * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent, java.lang.Object)
     */
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) throws ConverterException {
        if (value == null || "".equals(value)) {
            return "";
        }
        if (value != null && !(value instanceof BaseEntity)) {
            throw new IllegalArgumentException("This converter only handles instances of BaseEntity");
        }
        if (value instanceof String) {
            return (String) value;
        }
        BaseEntity entity = (BaseEntity) value;
        return entity.getId() == null ? "" : entity.getId().toString() + "_" + entity.getClass().getCanonicalName();
    }

}