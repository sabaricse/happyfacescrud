package org.happyfaces.services.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.happyfaces.domain.base.IEntity;
import org.happyfaces.jsf.datatable.PaginationConfiguration;
import org.happyfaces.services.base.BaseService;
import org.happyfaces.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.primefaces.model.SortOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mysema.query.types.Predicate;

/**
 * Unit tests for base service.
 * 
 * @author Ignas
 * 
 */
public class BaseServiceTest {

    @SuppressWarnings({ "serial", "rawtypes" })
    @Test
    public void testGetPredicate() {
        Map<String, Object> filters = new HashMap<String, Object>();
        filters.put("testText", "text");
        filters.put("testEntity", new TestEntity());
        filters.put("testBigDecimal", new BigDecimal("1.1"));
        filters.put("testByte", new Byte("1"));
        filters.put("testShort", new Short("2"));
        filters.put("testInteger", new Integer("3"));
        filters.put("testLong", new Long("4"));
        filters.put("testDouble", new Double("5.5"));
        filters.put("testFloat", new Float("6.6"));
        filters.put("testDate", DateUtils.createDate(2013, 1, 1));
        PaginationConfiguration configuration = new PaginationConfiguration(0, 10, filters, Arrays.asList("entity1"),
                "test1", SortOrder.ASCENDING);
        BaseService<TestEntity> service = new BaseService<TestEntity>() {
            @SuppressWarnings("unchecked")
            @Override
            protected JpaRepository getRepository() {
                return null;
            }
        };

        Predicate predicate = service.getPredicate(configuration);
        Assert.assertEquals(
                "testEntity.testDate = Tue Jan 01 00:00:00 EET 2013 && testEntity.testDouble = 5.5 && "
                        + "testEntity.testByte = 1 && startsWithIgnoreCase(testEntity.testText,text) && testEntity.testInteger = 3 && "
                        + "testEntity.testShort = 2 && testEntity.testFloat = 6.6 && testEntity.testLong = 4 && testEntity.testBigDecimal = 1.1",
                predicate.toString());
    }

    @SuppressWarnings({ "serial", "rawtypes" })
    @Test
    public void testGetPredicateForRangeSearch() {
        Map<String, Object> filters = new HashMap<String, Object>();
        filters.put("fromRange-testBigDecimal", new BigDecimal("1.1"));
        filters.put("toRange-testBigDecimal", new BigDecimal("1.2"));
        filters.put("fromRange-testByte", new Byte("1"));
        filters.put("toRange-testByte", new Byte("2"));
        filters.put("fromRange-testShort", new Short("2"));
        filters.put("toRange-testShort", new Short("3"));
        filters.put("fromRange-testInteger", new Integer("3"));
        filters.put("toRange-testInteger", new Integer("4"));
        filters.put("fromRange-testLong", new Long("4"));
        filters.put("toRange-testLong", new Long("5"));
        filters.put("fromRange-testDouble", new Double("5.5"));
        filters.put("toRange-testDouble", new Double("6.5"));
        filters.put("fromRange-testFloat", new Float("6.6"));
        filters.put("toRange-testFloat", new Float("7.6"));
        filters.put("fromRange-testDate", DateUtils.createDate(2013, 2, 1));
        filters.put("toRange-testDate", DateUtils.createDate(2013, 3, 1));
        PaginationConfiguration configuration = new PaginationConfiguration(0, 10, filters, Arrays.asList("entity1"),
                "test1", SortOrder.ASCENDING);
        BaseService<TestEntity> service = new BaseService<TestEntity>() {
            @SuppressWarnings("unchecked")
            @Override
            protected JpaRepository getRepository() {
                return null;
            }
        };

        Predicate predicate = service.getPredicate(configuration);
        Assert.assertEquals(
                "testEntity.testDouble >= 5.5 && testEntity.testDate <= Fri Mar 01 00:00:00 EET 2013 && testEntity.testShort >= 2 && "
                        + "testEntity.testFloat >= 6.6 && testEntity.testInteger >= 3 && testEntity.testLong >= 4 && testEntity.testBigDecimal >= 1.1 && "
                        + "testEntity.testFloat <= 7.6 && testEntity.testDate >= Fri Feb 01 00:00:00 EET 2013 && testEntity.testLong <= 5 && testEntity.testByte >= 1 && "
                        + "testEntity.testBigDecimal <= 1.2 && testEntity.testByte <= 2 && testEntity.testDouble <= 6.5 && testEntity.testInteger <= 4 && testEntity.testShort <= 3",
                predicate.toString());
    }

    private static class TestEntity implements IEntity {
        @Override
        public Serializable getId() {
            return null;
        }

        @Override
        public boolean isTransient() {
            return false;
        }
    }
}
