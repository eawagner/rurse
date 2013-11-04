package com.careeropts.rurse.dao.impl;


import com.careeropts.rurse.dao.IBaseDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public abstract class AbstractBaseDaoTest<T> {

    protected abstract IBaseDao<T> getDao();
    protected abstract T genTestEntity();
    protected abstract T genUpdateEntity(T entity);
    protected abstract T copy(T entity);
    protected abstract Long getId(T entity);

    @Test
    public void getSingle() {
        T expected = copy(getDao().save(genTestEntity()));

        //Validate that the single element can be retrieved.
        T actual = getDao().getSingle(getId(expected));
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void getSingleNonExistent() {

        T actual = getDao().getSingle(1);
        assertNull(actual);
    }

    @Test
    public void getAllTest() {
        List<T> expected = asList(
                copy(getDao().save(genTestEntity())),
                copy(getDao().save(genTestEntity())),
                copy(getDao().save(genTestEntity())),
                copy(getDao().save(genTestEntity())),
                copy(getDao().save(genTestEntity()))
        );

        List<T> actual = getDao().getAll(0, Integer.MAX_VALUE);

        assertEquals(expected.size(), actual.size());
        for (T entity : actual)
            assertTrue(expected.contains(entity));
    }

    @Test
    public void getAllPaginationTest() {
        List<T> expected = asList(
                copy(getDao().save(genTestEntity())),
                copy(getDao().save(genTestEntity())),
                copy(getDao().save(genTestEntity())),
                copy(getDao().save(genTestEntity())),
                copy(getDao().save(genTestEntity()))
        );

        List<T> actual = getDao().getAll(0, 3);

        assertEquals(3, actual.size());
        for (T entity : actual)
            assertTrue(expected.contains(entity));

        actual = getDao().getAll(1, 3);

        assertEquals(2, actual.size());
        for (T entity : actual)
            assertTrue(expected.contains(entity));

    }

    @Test
    public void saveNullTest() {

        T actual = getDao().save(null);
        assertNull(actual);
    }

    @Test
    public void updateTest() {
        T original = copy(getDao().save(genTestEntity()));

        T updated = copy(getDao().update(genUpdateEntity(original)));
        assertNotNull(updated);
        assertNotEquals(original, updated);

        T stored = getDao().getSingle(getId(original));
        assertNotNull(stored);
        assertEquals(updated, stored);
    }

    @Test
    public void updateNullTest() {

        T actual = getDao().update(null);
        assertNull(actual);
    }

    @Test
    public void deleteTest() {
        T original = getDao().save(genTestEntity());
        assertEquals(1, getDao().getAll(0, Integer.MAX_VALUE).size());

        assertTrue(getDao().delete(original));
        assertEquals(0, getDao().getAll(0, Integer.MAX_VALUE).size());
    }

    @Test
    public void deleteNullTest() {
        assertFalse(getDao().delete(null));
    }

}
