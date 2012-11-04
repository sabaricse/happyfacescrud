package org.happyfaces.utils;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.happyfaces.jsf.datatable.PaginationConfiguration;
import org.hibernate.Query;
import org.hibernate.Session;



public class QueryBuilder {

    private StringBuffer q;
    private Map<String, Object> params;

    private boolean hasOneOrMoreCriteria;
    private boolean inOrClause;
    private int nbCriteriaInOrClause;

    private PaginationConfiguration paginationConfiguration;
    private String paginationSortAlias;

    public QueryBuilder(String sql) {
        q = new StringBuffer(sql);
        params = new HashMap<String, Object>();
        hasOneOrMoreCriteria = false;
        inOrClause = false;
        nbCriteriaInOrClause = 0;
    }

    public QueryBuilder(QueryBuilder qb) {
        this.q = new StringBuffer(qb.q);
        this.params = new HashMap<String, Object>(qb.params);
        this.hasOneOrMoreCriteria = qb.hasOneOrMoreCriteria;
        this.inOrClause = qb.inOrClause;
        this.nbCriteriaInOrClause = qb.nbCriteriaInOrClause;
    }

    public QueryBuilder(Class<?> clazz, String alias, List<String> fetchFields) {
        this(getInitQuery(clazz, alias, fetchFields));
    }

    private static String getInitQuery(Class<?> clazz, String alias, List<String> fetchFields) {
        StringBuilder query = new StringBuilder("from " + clazz.getName() + " " + alias);
        if (fetchFields != null && !fetchFields.isEmpty()) {
            for (String fetchField : fetchFields) {
                query.append(" left join fetch " + alias + "." + fetchField  + " as " + fetchField);
            }
        }
        return query.toString();
    }

    public StringBuffer getSqlStringBuffer() {
        return q;
    }

    public QueryBuilder addPaginationConfiguration(PaginationConfiguration paginationConfiguration) {
        return addPaginationConfiguration(paginationConfiguration, null);
    }

    public QueryBuilder addPaginationConfiguration(PaginationConfiguration paginationConfiguration, String sortAlias) {
        this.paginationSortAlias = sortAlias;
        this.paginationConfiguration = paginationConfiguration;
        return this;
    }

    public QueryBuilder addSql(String sql) {
        return addSqlCriterion(sql, null, null);
    }

    public QueryBuilder addSqlCriterion(String sql, String param, Object value) {
        if (param != null && isValueBlank(value)) return this;

        if (hasOneOrMoreCriteria) {
            if (inOrClause && nbCriteriaInOrClause != 0)
                q.append(" or ");
            else
                q.append(" and ");
        } else
            q.append(" where ");

        if (inOrClause && nbCriteriaInOrClause == 0) q.append("(");

        q.append(sql);

        if (param != null) params.put(param, value);

        hasOneOrMoreCriteria = true;
        if (inOrClause) nbCriteriaInOrClause++;

        return this;
    }

    public QueryBuilder addBooleanCriterion(String field, Boolean value) {
        if (isValueBlank(value)) return this;

        addSql(field + (value.booleanValue() ? " is true " : " is false "));
        return this;
    }

    public QueryBuilder addCriterion(String field, String operator, Object value, boolean caseInsensitive) {
        if (isValueBlank(value)) return this;

        StringBuffer sql = new StringBuffer();
        String param = convertFieldToParam(field);
        Object nvalue = value;

        if (caseInsensitive && (value instanceof String))
            sql.append("lower(" + field + ")");
        else
            sql.append(field);

        sql.append(operator + ":" + param);

        if (caseInsensitive && (value instanceof String)) nvalue = ((String) value).toLowerCase();

        return addSqlCriterion(sql.toString(), param, nvalue);
    }

    public QueryBuilder addCriterionEntity(String field, Object entity) {
        if (entity == null) return this;

        String param = convertFieldToParam(field);

        return addSqlCriterion(field + "=:" + param, param, entity);
    }

    @SuppressWarnings("rawtypes")
    public QueryBuilder addCriterionEnum(String field, Enum enumValue) {
        if (enumValue == null) return this;

        String param = convertFieldToParam(field);

        return addSqlCriterion(field + "=:" + param, param, enumValue);
    }

    public QueryBuilder like(String field, String value, int style, boolean caseInsensitive) {
        if (isValueBlank(value)) return this;

        String v = value;

        if (style != 0) {
            if (style == 1 || style == 2) v = v + "%";
            if (style == 2) v = "%" + v;
        }

        return addCriterion(field, " like ", v, caseInsensitive);
    }

    public QueryBuilder addCriterionWildcard(String field, String value, boolean caseInsensitive) {
        if (isValueBlank(value)) return this;
        boolean wildcard = (value.indexOf("*") != -1);

        if (wildcard)
            return like(field, value.replace("*", "%"), 0, caseInsensitive);
        else
            return addCriterion(field, "=", value, caseInsensitive);
    }

    public QueryBuilder addCriterionDate(String field, Date value) {
        if (isValueBlank(value)) return this;
        return addCriterion(field, "=", value, false);

    }

    public QueryBuilder addCriterionDateTruncatedToDay(String field, Date value) {
        if (isValueBlank(value)) return this;
        Calendar c = Calendar.getInstance();
        c.setTime(value);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        c.set(year, month, date, 0, 0, 0);
        Date start = c.getTime();
        c.set(year, month, date, 23, 59, 59);
        Date end = c.getTime();

        String startDateParameterName = "start" + field.replace(".", "");
        String endDateParameterName = "end" + field.replace(".", "");
        return addSqlCriterion(field + ">=:" + startDateParameterName, startDateParameterName, start).addSqlCriterion(
                field + "<=:" + endDateParameterName, endDateParameterName, end);
    }

    public QueryBuilder addCriterionDateRangeFromTruncatedToDay(String field, Date valueFrom) {
        if (isValueBlank(valueFrom)) return this;
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(valueFrom);
        int yearFrom = calFrom.get(Calendar.YEAR);
        int monthFrom = calFrom.get(Calendar.MONTH);
        int dateFrom = calFrom.get(Calendar.DATE);
        calFrom.set(yearFrom, monthFrom, dateFrom, 0, 0, 0);
        Date start = calFrom.getTime();

        String startDateParameterName = "start" + field.replace(".", "");
        return addSqlCriterion(field + ">=:" + startDateParameterName, startDateParameterName, start);
    }

    public QueryBuilder addCriterionDateRangeToTruncatedToDay(String field, Date valueTo) {
        if (isValueBlank(valueTo)) return this;
        Calendar calTo = Calendar.getInstance();
        calTo.setTime(valueTo);
        int yearTo = calTo.get(Calendar.YEAR);
        int monthTo = calTo.get(Calendar.MONTH);
        int dateTo = calTo.get(Calendar.DATE);
        calTo.set(yearTo, monthTo, dateTo, 23, 59, 59);
        Date end = calTo.getTime();

        String endDateParameterName = "end" + field.replace(".", "");
        return addSqlCriterion(field + "<=:" + endDateParameterName, endDateParameterName, end);
    }

    public void addOrderCriterion(String orderColumn, boolean ascending) {
        q.append(" ORDER BY " + orderColumn);
        if (ascending) {
            q.append(" ASC ");
        } else {
            q.append(" DESC ");
        }

    }

    public QueryBuilder addOrderDoubleCriterion(String orderColumn, boolean ascending, String orderColumn2,
            boolean ascending2) {
        q.append(" ORDER BY " + orderColumn);
        if (ascending) {
            q.append(" ASC ");
        } else {
            q.append(" DESC ");
        }
        q.append(", " + orderColumn2);
        if (ascending2) {
            q.append(" ASC ");
        } else {
            q.append(" DESC ");
        }
        return this;
    }

    public QueryBuilder addOrderUniqueCriterion(String orderColumn, boolean ascending) {
        q.append(" ORDER BY " + orderColumn);
        if (ascending) {
            q.append(" ASC ");
        } else {
            q.append(" DESC ");
        }
        return this;
    }

    public QueryBuilder startOrClause() {
        inOrClause = true;
        nbCriteriaInOrClause = 0;
        return this;
    }

    public QueryBuilder endOrClause() {
        if (nbCriteriaInOrClause != 0) q.append(")");

        inOrClause = false;
        nbCriteriaInOrClause = 0;
        return this;
    }

    public Query getQuery(Session session) {
        applyPagination(paginationSortAlias);

        Query result = session.createQuery(q.toString());
        applyPagination(result);

        for (Map.Entry<String, Object> e : params.entrySet())
            result.setParameter(e.getKey(), e.getValue());
        return result;
    }

    public Query getCountQuery(Session session) {
        String from = "from ";
        String s = "select count(*) " + q.toString().substring(q.indexOf(from));
        
        s = s.replaceAll("left join fetch", "join");
        
        Query result = session.createQuery(s);
        for (Map.Entry<String, Object> e : params.entrySet())
            result.setParameter(e.getKey(), e.getValue());
        return result;
    }

    public Integer count(Session session) {
        Query query = getCountQuery(session);
        return ((Long) query.uniqueResult()).intValue();
    }

    @SuppressWarnings("rawtypes")
    public List find(Session session) {
        Query query = getQuery(session);
        return query.list();
    }

    private String convertFieldToParam(String field) {
        field = field.replace(".", "_").replace("(", "_").replace(")", "_");
        StringBuilder newField = new StringBuilder(field);
        while (params.containsKey(newField.toString()))
            newField = new StringBuilder(field).append("_" + String.valueOf(new Random().nextInt(100)));
        return newField.toString();
    }

    private void applyPagination(String alias) {
        if (paginationConfiguration == null) return;

        if (paginationConfiguration.isSorted())
            addOrderCriterion(((alias != null) ? (alias + ".") : "") + paginationConfiguration.getSortField(),
                    paginationConfiguration.isAscendingSorting());

    }

    private void applyPagination(Query query) {
        if (paginationConfiguration == null) return;

        query.setFirstResult(paginationConfiguration.getFirstRow());
        query.setMaxResults(paginationConfiguration.getNumberOfRows());
    }

    /* DEBUG */
    public void debug() {
        System.out.println("Requete : " + q.toString());
        for (Map.Entry<String, Object> e : params.entrySet())
            System.out.println("Param name:" + e.getKey() + " value:" + e.getValue().toString());
    }

    public String getSqlString() {
        return q.toString();
    }

    public Map<String, Object> getParams() {
        return Collections.unmodifiableMap(params);
    }

    public QueryBuilder(Class<?> clazz, String alias) {
        this("from " + clazz.getName() + " " + alias);
    }
    
    private boolean isValueBlank(Object value) {
        return value == null || ((value instanceof String) && ((String) value).trim().length() == 0);
    }
}
