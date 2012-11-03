package org.happyfaces.beans.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;

import org.apache.log4j.Logger;
import org.happyfaces.beans.SessionPreferences;
import org.happyfaces.domain.base.IEntity;
import org.happyfaces.jsf.datatable.PaginationConfiguration;
import org.happyfaces.services.base.IService;
import org.happyfaces.utils.FacesUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * Base bean class. Other jsf backing beans extends this class to get basic crud
 * functionality + out of box search functionality. Bean should be ViewScoped.
 * 
 * @author Ignas
 */
public abstract class BaseBean<T extends IEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Logger. */
    private static Logger log = Logger.getLogger(BaseBean.class.getName());

    /** Search filters. */
    protected Map<String, Object> filters;

    /** Class of backing bean. */
    private Class<T> clazz;

    /**
     * Loaded entity for edit or view.
     */
    private T entity;

    /**
     * Request parameter. Used for loading in object by its id.
     */
    private Integer objectId;

    /**
     * Request parameter.
     */
    private boolean edit = false;
    
    /**
     * 
     */
    private LazyDataModel<T> dataModel;

    /**
     * Constructor.
     * 
     * @param clazz
     *            Class.
     */
    public BaseBean(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    /**
     * Returns entity class
     * 
     * @return Class
     */
    public Class<T> getClazz() {
        return clazz;
    }

    /**
     * Initiates entity from request parameter id. If request parameter does not
     * exist - create new object for entity.
     * 
     * @param objectClass
     *            Class of the object.
     * @return Entity from database.
     */
    public T initEntity() {
        if (getObjectId() != null) {
            if (getFormFieldsToFetch() == null) {
                entity = getPersistenceService().getById(getObjectId());
            } else {
                entity = getPersistenceService().getById(getObjectId(), getFormFieldsToFetch());
            }
        } else {
            try {
                entity = getInstance();
            } catch (InstantiationException e) {
                log.error("Unexpected error!", e);
                throw new IllegalStateException("could not instantiate a class, abstract class");
            } catch (IllegalAccessException e) {
                log.error("Unexpected error!", e);
                throw new IllegalStateException("could not instantiate a class, constructor not accessible");
            }
        }
        return entity;
    }

    /**
     * When opened to view or edit entity - this getter method returns it. In
     * case entity is not loaded it will initialize it.
     * 
     * @return Entity in current view state.
     */
    public T getEntity() {
        return entity != null ? entity : initEntity();
    }

    /**
     * This method is invoked from JSF page and current entity loaded in view
     * scope is saved or updated to database.
     */
    public String save() {
        try {
            if (entity != null) {
                saveOrUpdate(entity);
                return getViewAfterSave();
            } else {
                log.error("save() method was invoked when when no entity was loaded");
                FacesUtils.error("system.saveError");
            }
        } catch (Throwable e) {
            log.error(String.format("Unexpected error when saving entity %s with id %s", clazz.getName(), entity.getId()), e);
            FacesUtils.error("system.saveError");
        }
        return null;
    }

    /**
     * Save or update entity depending on if entity is transient.
     * 
     * @param entity
     *            Entity to save.
     */
    public void saveOrUpdate(T entity) {
        if (entity.isTransient()) {
            getPersistenceService().add(entity);
            FacesUtils.info("save.successful");
        } else {
            getPersistenceService().update(entity);
            FacesUtils.info("update.successful");
        }
    }
    
    /**
     * Lists all entities.
     */
    public List<T> listAll() {
        return getPersistenceService().list();
    }

    /**
     * Returns view after save() operation. By default it goes back to list
     * view. Override if need different logic (for example return to one view
     * for save and another for update operations)
     */
    public String getViewAfterSave() {
        return getListViewName();
    }

    /**
     * Generating action name to get to entity creation page. Override this
     * method if its view name does not fit.
     */
    public String getNewViewName() {
        return getEditViewName();
    }

    /**
     * Generating action name to get to entity view/edit page. Override this
     * method if its view name does not fit.
     */
    public String getEditViewName() {
        String className = clazz.getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("Edit");
        char[] dst = new char[1];
        sb.getChars(0, 1, dst, 0);
        sb.replace(0, 1, new String(dst).toLowerCase());
        return sb.toString();
    }

    /**
     * Generating action name to get back to entity list view. Invoked from
     * save() method. Override this method if its view name does not fit.
     */
    public String getListViewName() {
        String className = clazz.getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        char[] dst = new char[1];
        sb.getChars(0, 1, dst, 0);
        sb.replace(0, 1, new String(dst).toLowerCase());
        sb.append("s");
        return sb.toString();
    }
    
    /**
     * Delete Entity using it's ID. Add error message to {@link statusMessages}
     * if unsuccessful.
     * 
     * @param id
     *            Entity id to delete
     */
    public void delete(Long id) {
        try {
            log.info(String.format("Deleting entity %s with id = %s", clazz.getName(), id));
            getPersistenceService().delete(id.intValue());
            FacesUtils.info("delete.successful");
        } catch (Throwable t) {
            if (t.getCause() instanceof EntityExistsException) {
                log.info("delete was unsuccessful because entity is used in the system", t);
                FacesUtils.error("delete.entityUsed");
            } else {
                log.info("unexpected exception when deleting!", t);
                FacesUtils.error("delete.unexpected");
            }
        }
    }

    // TODO implement
    /**
     * Delete checked entities. Add error message to {@link statusMessages} if
     * unsuccessful.
     */
    public void deleteMany() {
        /*
         * try { log.info(String.format("Deleting entities %s with id = %s",
         * clazz.getName(), id)); Set<Long> idsToDelete = new HashSet<Long>();
         * for (Long id : checked.keySet()) { if (checked.get(id)) {
         * idsToDelete.add(id); } }
         * getPersistenceService().deleteMany(idsToDelete);
         * FacesContext.getCurrentInstance().addMessage(null, new
         * FacesMessage(bundle.getString("delete.entitities.successful"))); }
         * catch (Throwable t) { if (t.getCause() instanceof
         * EntityExistsException) {
         * log.info("delete was unsuccessful because entity is used in the system"
         * , t); FacesContext.getCurrentInstance().addMessage(null, new
         * FacesMessage(bundle.getString("error.delete.entityUsed"))); } else {
         * log.info("unexpected exception when deleting!", t);
         * FacesContext.getCurrentInstance().addMessage(null, new
         * FacesMessage(bundle.getString("error.delete.unexpected"))); } }
         */
    }

    /**
     * Gets search filters map.
     * 
     * @return Filters map.
     */
    public Map<String, Object> getFilters() {
        if (filters == null)
            filters = new HashMap<String, Object>();
        return filters;
    }

    /**
     * Clean search fields in datatable.
     */
    public void clean() {
        filters = new HashMap<String, Object>();
    }

    /**
     * Get new instance for backing bean class.
     * 
     * @return New instance.
     * 
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public T getInstance() throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }

    /**
     * Method that returns concrete PersistenceService. That service is then
     * used for operations on concrete entities (eg. save, delete etc).
     * 
     * @return Persistence service
     */
    protected abstract IService<T> getPersistenceService();

    /**
     * Override this method if you need to fetch any fields when selecting list
     * of entities in data table. Return list of field names that has to be
     * fetched.
     */
    protected List<String> getListFieldsToFetch() {
        return null;
    }

    /**
     * Override this method if you need to fetch any fields when selecting one
     * entity to show it a form. Return list of field names that has to be
     * fetched.
     */
    protected List<String> getFormFieldsToFetch() {
        return null;
    }

    /**
     * DataModel for primefaces lazy loading datatable component.
     * 
     * @return LazyDataModel implementation.
     */
    public LazyDataModel<T> getLazyDataModel() {
        if (dataModel == null) {
            dataModel = new LazyDataModel<T>() {
                private static final long serialVersionUID = 1L;

                private Integer rowCount;
                
                private Integer rowIndex;

                @Override
                public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> loadingFilters) {
                    Map<String, Object> copyOfFilters = new HashMap<String, Object>();
                    copyOfFilters.putAll(filters);
                    setRowCount(getPersistenceService().count(new PaginationConfiguration(first, pageSize, copyOfFilters, getListFieldsToFetch(), sortField, sortOrder)));
                    if (getRowCount() > 0) {
                        copyOfFilters = new HashMap<String, Object>();
                        copyOfFilters.putAll(filters);
                        return getPersistenceService().list(new PaginationConfiguration(first, pageSize, copyOfFilters, getListFieldsToFetch(), sortField, sortOrder));
                    } else {
                        return null; // no need to load then
                    }
                }

                @Override
                public T getRowData(String rowKey) {
                    return getPersistenceService().getById(Integer.valueOf(rowKey));
                }

                @Override
                public Object getRowKey(T object) {
                    return object.getId();
                }

                @Override
                public void setRowIndex(int rowIndex) {
                    if (rowIndex == -1 || getPageSize() == 0) {
                        this.rowIndex = rowIndex;
                    } else {
                        this.rowIndex = rowIndex % getPageSize();
                    }
                }
                
                @SuppressWarnings("unchecked")
                @Override
                public T getRowData() {
                    return ((List<T>)getWrappedData()).get(rowIndex);
                }
                
                @SuppressWarnings({ "unchecked" })
                @Override
                public boolean isRowAvailable() {
                    if(getWrappedData() == null) {
                        return false;
                    }

                    return rowIndex >= 0 && rowIndex < ((List<T>)getWrappedData()).size();
                }
                
                @Override
                public int getRowIndex() {
                    return this.rowIndex;
                }

                @Override
                public void setRowCount(int rowCount) {
                    this.rowCount = rowCount;
                }
                
                @Override
                public int getRowCount() {
                    if (rowCount == null) {
                        rowCount = getPersistenceService().count();
                    }
                    return rowCount;
                }

            };
        }
        return dataModel;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    /**
     * Check if user has authority (from spring security) to edit. Override if
     * specific role is required for different pages.
     * 
     * @return true if edit is allowed
     */
    public boolean isEditAllowed() {
        return SessionPreferences.hasRole("ROLE_USER");
    }

}
