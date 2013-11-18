package com.careeropts.rurse.web.service.impl;


import com.careeropts.rurse.dao.IBaseDao;
import com.careeropts.rurse.web.exception.BadRequestException;
import com.careeropts.rurse.web.exception.InternalServerError;
import com.careeropts.rurse.web.exception.NotFoundException;
import com.careeropts.rurse.web.service.ISimpleService;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public abstract class AbstractSimpleServiceTest<T, U> {

    protected abstract IBaseDao<U> mockDao();
    protected abstract ISimpleService<T> getService(IBaseDao<U> dao);
    protected abstract T genModel(Long id, String title, String description);
    protected abstract U toEntity(T model);

    @Test
    public void getSingleTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        T expected = genModel(1L, "title", "description");

        when(dao.getSingle(1L)).thenReturn(toEntity(expected));

        T actual = service.getSingle(1L);

        verify(dao).getSingle(1L);

        assertEquals(expected, actual);
    }

    @Test(expected = NotFoundException.class)
    public void getSingleNullIdTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        service.getSingle(null);
    }

    @Test(expected = NotFoundException.class)
    public void getSingleNotFoundTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        when(dao.getSingle(1L)).thenReturn(null);

        service.getSingle(null);
    }

    @Test
    public void queryAllTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        T expected = genModel(1L, "title", "description");

        when(dao.getAll(anyInt(), anyInt())).thenReturn(
                asList(toEntity(expected))
        );

        List<T> actual = service.query(null, 0, 100);

        verify(dao).getAll(0, 100);

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(expected, actual.get(0));
    }

    @Test
    public void querySearchTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        T expected = genModel(1L, "title", "description");

        when(dao.search(anyString(), anyInt(), anyInt())).thenReturn(
                asList(toEntity(expected))
        );

        List<T> actual = service.query("search", 0, 100);

        verify(dao).search("search", 0, 100);

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(expected, actual.get(0));
    }

    @Test
    public void queryNormalizeNullTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        service.query(null, null, null);
        verify(dao).getAll(0, 50);
    }

    @Test
    public void queryNormalizeNegTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        service.query(null, -1, -1);
        verify(dao).getAll(0, 50);
    }

    @Test
    public void saveTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        T expected = genModel(null, "title", "description");

        when(dao.save(Matchers.<U>any())).then(returnsFirstArg());

        T actual = service.save(expected);

        verify(dao).save(toEntity(expected));

        assertEquals(expected, actual);
    }

    @Test(expected = BadRequestException.class)
    public void saveNullDataTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        service.save(null);
    }

    @Test(expected = InternalServerError.class)
    public void saveSaveFailTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        when(dao.save(Matchers.<U>any())).thenReturn(null);

        service.save(genModel(null, "title", "description"));
    }

    @Test(expected = BadRequestException.class)
    public void saveValidateTitleTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        service.save(genModel(null, null, "description"));
    }

    @Test(expected = BadRequestException.class)
    public void saveValidateDescriptionTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        service.save(genModel(null, "title", null));
    }

    @Test
    public void updateTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        T expected = genModel(1L, "title", "description");

        when(dao.getSingle(1L)).thenReturn(toEntity(expected));
        when(dao.update(Matchers.<U>any())).thenReturn(toEntity(expected));

        T actual = service.update(expected);

        verify(dao).getSingle(1L);
        verify(dao).update(toEntity(expected));

        assertEquals(expected, actual);
    }

    @Test(expected = NotFoundException.class)
    public void updateNullIdTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        service.update(genModel(null, "title", "description"));
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFoundTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        T expected = genModel(1L, "title", "description");

        when(dao.getSingle(1L)).thenReturn(null);

        service.update(expected);
    }

    @Test(expected = InternalServerError.class)
    public void updateFailTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        T expected = genModel(1L, "title", "description");

        when(dao.getSingle(1L)).thenReturn(toEntity(expected));
        when(dao.update(Matchers.<U>any())).thenReturn(null);

        service.update(expected);
    }

    @Test(expected = BadRequestException.class)
    public void updateValidateTitleTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        T expected = genModel(1L, null, "description");

        when(dao.getSingle(1L)).thenReturn(toEntity(expected));

        service.update(expected);
    }

    @Test(expected = BadRequestException.class)
    public void updateValidateDescriptionTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        T expected = genModel(1L, "title", null);

        when(dao.getSingle(1L)).thenReturn(toEntity(expected));

        service.update(expected);
    }

    @Test
    public void deleteTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        T model = genModel(1L, "title", "description");

        when(dao.getSingle(1L)).thenReturn(toEntity(model));
        when(dao.delete(Matchers.<U>any())).thenReturn(true);

        service.delete(1L);

        verify(dao).getSingle(1L);
        verify(dao).delete(toEntity(model));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNullIdTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        service.delete(null);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFoundTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        when(dao.getSingle(1L)).thenReturn(null);

        service.delete(1L);
    }

    @Test(expected = NotFoundException.class)
    public void deleteFailTest() {
        IBaseDao<U> dao = mockDao();
        ISimpleService<T> service = getService(dao);

        when(dao.getSingle(1L)).thenReturn(toEntity(genModel(1L, "title", "description")));
        when(dao.delete(Matchers.<U>any())).thenReturn(false);

        service.delete(1L);
    }
}
