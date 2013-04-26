package org.happyfaces.services.base;

import java.util.List;
import java.util.Set;

import org.happyfaces.domain.base.IEntity;
import org.happyfaces.jsf.datatable.PaginationConfiguration;

/**
 * Base service interface with all provided methods.
 * 
 * @author Ignas
 * 
 * @param <T> Type of Service.
 */
public interface IService<T extends IEntity> {

    /**
     * Add entity.
     * 
     * @param entity
     *            to add
     */
    public void add(T entity);

    /**
     * Update entity
     * 
     * @param entity
     *            to update
     */
    public void update(T entity);

    /**
     * Delete entity
     * 
     * @param entity
     *            to delete
     */
    public void delete(T entity);

    /**
     * Delete entity by its id.
     * 
     * @param id
     *            entity id to delete
     */
    public void delete(Long id);

    /**
     * Delete many entities provided with list of ids
     * 
     * @param ids
     *            to delete
     */
    public void deleteMany(Set<Long> ids);

    /**
     * Get entity by ID
     * 
     * @param entity
     *            Id
     */
    public T getById(Long id);

    /**
     * Load entity and eager fetch its fields.
     * 
     * @param id
     *            entity Id
     * @param fetchFields
     *            String list of field names that needs to be eager fetched
     */
    public T getById(Long id, List<String> fetchFields);

    /**
     * @return count of all entities in db.
     */
    public int count();

    /**
     * @param config
     *            Pagination/sorting/filtering data.
     * @return count of # entities in db.
     */
    public int count(PaginationConfiguration config);

    /**
     * Get All entities from db
     * 
     * @return List - entities list
     */
    public List<T> list();

    /**
     * Get entries according to pagination/sorting/filtering data in
     * PaginationConfiguration.
     * 
     * @param config
     *            Pagination/sorting/filtering data.
     * 
     * @return List - entities list
     */
    public List<T> list(PaginationConfiguration config);

}
