package org.happyfaces.services.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.happyfaces.domain.IdentifiableEnum;
import org.happyfaces.domain.base.BaseEntity;
import org.happyfaces.domain.base.IEntity;
import org.happyfaces.jsf.datatable.PaginationConfiguration;
import org.happyfaces.utils.QueryBuilder;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base service that other persistence services can extend. It provides all
 * common crud operations. Also provide default search capabilities which work
 * nicely with composite jsf search components.
 * 
 * @author Ignas
 * 
 */
@Transactional(readOnly = true)
public class BaseService<T extends IEntity> implements IService<T>, Serializable {

    private static final long serialVersionUID = 1L;

    protected final Class<? extends IEntity> entityClass;

    @PersistenceContext
    protected EntityManager em;

    private static Logger log = Logger.getLogger(BaseService.class.getName());

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public BaseService() {
        Class clazz = getClass();
        while (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
            clazz = clazz.getSuperclass();
        }
        Object o = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];

        if (o instanceof TypeVariable) {
            this.entityClass = (Class<T>) ((TypeVariable) o).getBounds()[0];
        } else {
            this.entityClass = (Class<T>) o;
        }
    }
    
    public BaseService(Class<? extends IEntity> entityClass, EntityManager em) {
        this.entityClass = entityClass;
        this.em = em;
    }   

    @Override
    @Transactional(readOnly = false)
    public void add(T entity) {
        em.persist(entity);
        log.info("Entity saved"); // TODO add
    }

    @Override
    @Transactional(readOnly = false)
    public void update(T entity) {
        em.merge(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(T entity) {
        em.remove(entity);
    }
    
    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        Query query = em.createQuery("delete from " + entityClass.getName() + " where id = :id)");
        query.setParameter("id", id);
        query.executeUpdate();
    }
        

    @Override
    @Transactional(readOnly = false)
    public void deleteMany(Set<Long> ids) {
        Query query = em.createQuery("delete from " + entityClass.getName() + " where id in (:ids)");
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    @Override
    public T getById(Long id) {
        @SuppressWarnings("unchecked")
        List<T> list = em.createQuery("from " + entityClass.getName() + " where id=?").setParameter(1, id).getResultList();
        return list.size() > 0 ? (T) list.get(0) : null;
    }

    @Override
    public T getById(Long id, List<String> fetchFields) {
        log.debug(String.format("start of find %s by id (id=%s) ..", entityClass.getSimpleName(), id));
        StringBuilder queryString = new StringBuilder("from " + entityClass.getName() + " a");
        if (fetchFields != null && !fetchFields.isEmpty()) {
            for (String fetchField : fetchFields) {
                queryString.append(" left join fetch a." + fetchField);
            }
        }
        queryString.append(" where a.id = :id");
        Query query = em.createQuery(queryString.toString());
        query.setParameter("id", id);

        @SuppressWarnings("unchecked")
        List<T> list = query.getResultList();

        return list.size() > 0 ? (T) list.get(0) : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> list() {
        List<T> list = em.createQuery("from " + entityClass.getName()).getResultList();
        return list;
    }

    @Override
    @SuppressWarnings({ "unchecked" })
    public List<T> list(PaginationConfiguration config) {
        QueryBuilder queryBuilder = getQuery(config);
        Query query = queryBuilder.getQuery(em);
        return query.getResultList();
    }

    @Override
    public int count(PaginationConfiguration config) {
        QueryBuilder queryBuilder = getQuery(config);
        return queryBuilder.count(em);
    }

    @Override
    public int count() {
        QueryBuilder queryBuilder = new QueryBuilder(entityClass, "a", null);
        return queryBuilder.count(em);
    }

    /**
     * Creates query to filter entities according data provided in pagination
     * configuration.
     * 
     * @param config
     *            PaginationConfiguration data holding object
     * @return query to filter entities according pagination configuration data.
     */
    protected QueryBuilder getQuery(PaginationConfiguration config) {

        QueryBuilder queryBuilder = new QueryBuilder(entityClass, "a", config.getFetchFields());
        Map<String, Object> filters = config.getFilters();
        if (filters != null) {
            // first we process nonstandard filters
            List<String> filtersToRemove = processNonStandardFilters(filters, queryBuilder);
            filters = removeUsedFilters(filtersToRemove, filters);
            if (!filters.isEmpty()) {
                for (String key : filters.keySet()) {
                    Object filter = filters.get(key);
                    if (filter != null) {
                        addFilter(key, filter, queryBuilder);
                    }
                }
            }
        }
        queryBuilder.addPaginationConfiguration(config, "a");
        return queryBuilder;
    }

    //TODO rewrite in Criteria API
    /**
     * Add filter to QueryBuilder. If some non standard filter is needed - this
     * method can be overridden, however better approach is to add filter in
     * processNonStandardFilters() method. (if decided to override this method
     * do not forget to invoke super.addFilter() in overridden method if default
     * functionality is needed).
     */
    @SuppressWarnings("rawtypes")
    protected void addFilter(String key, Object filter, QueryBuilder queryBuilder) {
        // if ranged search (from - to fields)
        if (key.contains("fromRange-")) {
            String parsedKey = key.substring(10);
            if (filter instanceof Double) {
                BigDecimal rationalNumber = new BigDecimal((Double) filter);
                queryBuilder.addCriterion("a." + parsedKey, " >= ", rationalNumber, true);
            } else if (filter instanceof Number) {
                queryBuilder.addCriterion("a." + parsedKey, " >= ", filter, true);
            } else if (filter instanceof Date) {
                queryBuilder.addCriterionDateRangeFromTruncatedToDay("a." + parsedKey, (Date) filter);
            }
        } else if (key.contains("toRange-")) {
            String parsedKey = key.substring(8);
            if (filter instanceof Double) {
                BigDecimal rationalNumber = new BigDecimal((Double) filter);
                queryBuilder.addCriterion("a." + parsedKey, " <= ", rationalNumber, true);
            } else if (filter instanceof Number) {
                queryBuilder.addCriterion("a." + parsedKey, " <= ", filter, true);
            } else if (filter instanceof Date) {
                queryBuilder.addCriterionDateRangeToTruncatedToDay("a." + parsedKey, (Date) filter);
            }
        } else if (key.contains("list-")) {
            // if searching elements from list
            String parsedKey = key.substring(5);
            queryBuilder.addSqlCriterion(":" + parsedKey + " in elements(a." + parsedKey + ")", parsedKey, filter);
        }
        // if not ranged search
        else {
            if (filter instanceof String) {
                // if contains dot, that means join is needed
                String filterString = (String) filter;
                queryBuilder.addCriterionWildcard("a." + key, filterString, true);
            } else if (filter instanceof Date) {
                queryBuilder.addCriterionDateTruncatedToDay("a." + key, (Date) filter);
            } else if (filter instanceof Number) {
                queryBuilder.addCriterion("a." + key, " = ", filter, true);
            } else if (filter instanceof Boolean) {
                queryBuilder.addCriterion("a." + key, " is ", filter, true);
            } else if (filter instanceof Enum) {
                if (filter instanceof IdentifiableEnum) {
                    String enumIdKey = new StringBuilder(key).append("Id").toString();
                    queryBuilder.addCriterion("a." + enumIdKey, " = ", ((IdentifiableEnum) filter).getId(), true);
                } else {
                    queryBuilder.addCriterionEnum("a." + key, (Enum) filter);
                }
            } else if (BaseEntity.class.isAssignableFrom(filter.getClass())) {
                queryBuilder.addCriterionEntity("a." + key, filter);
            }
        }
    }

    /**
     * This method groups some filters to one. This might be needed when several
     * filters are dependent on each other, for example when we have several
     * text fields and we want all of them to participate in search and we need
     * OR functionality between them.
     * 
     * @return processed filters keys.
     */
    @SuppressWarnings("unused")
    protected List<String> processNonStandardFilters(Map<String, Object> filters, QueryBuilder queryBuilder) {
        return Collections.emptyList();
    }

    /**
     * Remove filters from further processing.
     */
    private Map<String, Object> removeUsedFilters(List<String> filtersToRemove, Map<String, Object> filtersMap) {
        for (String key : filtersToRemove) {
            filtersMap.remove(key);
        }
        return filtersMap;
    }

}
