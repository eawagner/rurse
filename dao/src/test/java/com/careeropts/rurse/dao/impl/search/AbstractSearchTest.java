package com.careeropts.rurse.dao.impl.search;

import com.careeropts.rurse.dao.IBaseDao;
import org.hibernate.SessionFactory;
import org.hibernate.search.Search;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public abstract class AbstractSearchTest<T> {

    private static final String SEARCH_TEXT = "This is the string for Unit-Testing";

    protected abstract IBaseDao<T> getDao();
    protected abstract T genTestEntity(String searchText);
    protected abstract T copy(T entity);

    List<T> expected = null;

    @Autowired
    SessionFactory sessionFactory;

    @Before
    public void setup() {
        expected = Arrays.asList(
                copy(getDao().save(genTestEntity(SEARCH_TEXT))),
                copy(getDao().save(genTestEntity(SEARCH_TEXT))),
                copy(getDao().save(genTestEntity(SEARCH_TEXT))),
                copy(getDao().save(genTestEntity(SEARCH_TEXT))),
                copy(getDao().save(genTestEntity(SEARCH_TEXT)))
        );

        Search.getFullTextSession(sessionFactory.getCurrentSession()).flushToIndexes();
    }


    @Test
    public void searchExactKeywordTest() {
        List<T> actual = getDao().search("string", 0, Integer.MAX_VALUE);

        assertEquals(expected.size(), actual.size());
        for (T entity : actual)
            assertTrue(expected.contains(entity));
    }


    @Test
    public void searchPaginationTest() {
        List<T> actual = getDao().search("string", 0, 3);

        assertEquals(3, actual.size());
        for (T entity : actual)
            assertTrue(expected.contains(entity));

        actual = getDao().search("string", 1, 3);

        assertEquals(2, actual.size());
        for (T entity : actual)
            assertTrue(expected.contains(entity));
    }

    @Test
    public void searchNullTest() {
        List<T> actual = getDao().search(null, 0, Integer.MAX_VALUE);

        assertEquals(0, actual.size());

    }

    @Test
    public void searchMultKeywordTest() {
        List<T> actual = getDao().search("string Unit-Testing", 0, Integer.MAX_VALUE);

        assertEquals(expected.size(), actual.size());
        for (T entity : actual)
            assertTrue(expected.contains(entity));
    }

    @Test
    public void searchPunctKeywordTest() {
        List<T> actual = getDao().search("Unit Testing", 0, Integer.MAX_VALUE);

        assertEquals(expected.size(), actual.size());
        for (T entity : actual)
            assertTrue(expected.contains(entity));
    }

    @Test
    public void searchCaseKeywordTest() {
        List<T> actual = getDao().search("unit-testing", 0, Integer.MAX_VALUE);

        assertEquals(expected.size(), actual.size());
        for (T entity : actual)
            assertTrue(expected.contains(entity));
    }

    @Test
    public void searchStemPorterKeywordTest() {
        List<T> actual = getDao().search("unit-tests", 0, Integer.MAX_VALUE);

        assertEquals(expected.size(), actual.size());
        for (T entity : actual)
            assertTrue(expected.contains(entity));

        Search.getFullTextSession(sessionFactory.getCurrentSession()).flushToIndexes();
    }

    @After
    public void teardown() {
        for (T entity : getDao().getAll(0, Integer.MAX_VALUE))
            getDao().delete(entity);
    }

}
